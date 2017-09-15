
 
package com.axway.mbaas_preprod;

import android.webkit.MimeTypeMap;
import android.support.annotation.NonNull;
import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpEncoding;
import com.google.api.client.http.HttpEncodingStreamingContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StreamingContent;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;

public class SdkMultipartFormDataContent extends AbstractHttpContent {

    private static final String NEWLINE = "\r\n";

    private static final String TAG = "SdkMultipartFormData";

    private static final String TWO_DASHES = "--";
    /**
     * Set the Boundary to an arbitrary value
     */
    public static final String BOUNDARY = "------SdkMultipartFormDataBoundary";

    private ArrayList<Part> parts = new ArrayList<Part>();


    /**
     * Constructor with Form Parameters
     * @param formParams {@link Map} Key = String and Value  = Object -> String or File
     */
    public SdkMultipartFormDataContent(@NonNull Map<String, Object> formParams) {
        super(new HttpMediaType("multipart/form-data").setParameter("boundary", BOUNDARY));
        for (Map.Entry<String, Object> item : formParams.entrySet()) {


            if (item.getValue() instanceof File) {
                // Log.d(TAG, "we have a file: " + item);
                File file = (File) item.getValue();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String extension = MimeTypeMap.getFileExtensionFromUrl(file.getName());
                String mimeType = mime.getMimeTypeFromExtension(extension);

                Part part = new Part();
                part.setName(item.getKey());
                part.setFilename(file.getName());
                part.setContent(new FileContent(mimeType, file));
                this.addPart(part);

            } else {
                if (item.getValue() instanceof String) {
                    this.addUrlEncodedContent(item.getKey(), (String) item.getValue());
                }
            }
        }
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {

        Writer writer = new OutputStreamWriter(out, getCharset());
        String boundary = getBoundary();
        for (Part part : parts) {
            HttpHeaders headers = new HttpHeaders().setAcceptEncoding(null);
            if (part.headers != null) {
                headers.fromHttpHeaders(part.headers);
            }
            headers.setContentEncoding(null).setUserAgent(null).setContentType(null).setContentLength(null);
            // analyze the content
            HttpContent content = part.content;
            StreamingContent streamingContent = null;
            String contentDisposition = String.format("form-data; name=\"%s\"", part.name);
            if (part.filename != null) {
                headers.setContentType(content.getType());
                contentDisposition += String.format("; filename=\"%s\"", part.filename);
            }
            headers.set("Content-Disposition", contentDisposition);
            HttpEncoding encoding = part.encoding;
            if (encoding == null) {
                streamingContent = content;
            } else {
                headers.setContentEncoding(encoding.getName());
                streamingContent = new HttpEncodingStreamingContent(content, encoding);
            }
            // write separator
            writer.write(TWO_DASHES);
            writer.write(boundary);
            writer.write(NEWLINE);
            // write headers
            HttpHeaders.serializeHeadersForMultipartRequests(headers, null, null, writer);
            // write content
            if (streamingContent != null) {
                writer.write(NEWLINE);
                writer.flush();
                streamingContent.writeTo(out);
                writer.write(NEWLINE);
            }
        }
        // write end separator
        writer.write(TWO_DASHES);
        writer.write(boundary);
        writer.write(TWO_DASHES);
        writer.write(NEWLINE);
        writer.flush();
    }

    @Override
    public boolean retrySupported() {
        for (Part part : parts) {
            if (!part.content.retrySupported()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public SdkMultipartFormDataContent setMediaType(HttpMediaType mediaType) {
        super.setMediaType(mediaType);
        return this;
    }

    /**
     * Adds an HTTP multipart part.
     * <p>
     * <p>
     * Overriding is only supported for the purpose of calling the super implementation and changing the return type, but nothing else.
     * </p>
     */
    public SdkMultipartFormDataContent addPart(@NonNull Part part) {
        parts.add(Preconditions.checkNotNull(part));
        return this;
    }

    /**
     * Adds the {@link UrlEncodedContent} to the HttpContent
     * @param name Name {@link String}
     * @param value Value {@link String}
     * @return
     */
    public SdkMultipartFormDataContent addUrlEncodedContent(@NonNull String name, @NonNull String value) {
        GenericData data = new GenericData();
        data.put(value, "");

        Part part = new Part();
        part.setContent(new UrlEncodedContent(data));
        part.setName(name);

        this.addPart(part);

        return this;
    }

    /**
     * Returns the boundary string to use.
     */
    public final String getBoundary() {
        return BOUNDARY;

    }

    /**
     * Single part of a multi-part request.
     * <p>
     * <p>
     * Implementation is not thread-safe.
     * </p>
     */
    public static final class Part {
        /**
         * Part Name
         */
        private String name;
        /**
         * Part File name
         */
        private String filename;
        /**
         * Part HttpContent
         * @see HttpContent
         */
        private HttpContent content;
        /**
         * Part Headers
         * @see HttpHeaders
         */
        private HttpHeaders headers;
        /**
         * Part Encoding
         * @see HttpEncoding
         */
        private HttpEncoding encoding;

        /**
         * Sets the HttpContent for the Part
         * @param content {@link HttpContent}
         * @return Part
         */
        public Part setContent(HttpContent content) {
            this.content = content;
            return this;
        }

        /**
         * Sets the HttpHeaders for the Part
         * @param headers {@link HttpHeaders}
         * @return Part
         */
        public Part setHeaders(HttpHeaders headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Sets the HttpEncoding for the Part
         * @param encoding {@link HttpEncoding}
         * @return Part
         */
        public Part setEncoding(HttpEncoding encoding) {
            this.encoding = encoding;
            return this;
        }

        /**
         * Sets the Name for the Part
         * @param name {@link String}
         * @return Part
         */
        public Part setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the File Name for the Part
         * @param fileName {@link String}
         * @return Part
         */
        public Part setFilename(String fileName) {
            this.filename = fileName;
            return this;
        }
    }

}
