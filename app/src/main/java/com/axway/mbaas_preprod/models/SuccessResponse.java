 

package com.axway.mbaas_preprod.models;

import com.axway.mbaas_preprod.JSONUtil;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class SuccessResponse {

	@SerializedName("meta")
	private String meta;

	@SerializedName("response")
	private String response;

	public SuccessResponse() {
	}

	/**
	 * Gets 
	 * @return String
	 **/
	public String getMeta() {
		return meta;
	}

	/**
	 * Sets 
	 * @param val
	 **/
	public void setMeta(String val) {
		meta = val;
	}

	/**
	 * Gets 
	 * @return String
	 **/
	public String getResponse() {
		return response;
	}

	/**
	 * Sets 
	 * @param val
	 **/
	public void setResponse(String val) {
		response = val;
	}




  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SuccessResponse otherObj = (SuccessResponse) o;

    return (this.meta == null ? otherObj.meta == null : this.meta.equals(otherObj.meta))  &&
	 (this.response == null ? otherObj.response == null : this.response.equals(otherObj.response)) ;

  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.meta == null ? 0: this.meta.hashCode());
    result = 31 * result + (this.response == null ? 0: this.response.hashCode());
    return result;
  }

  @Override
  public String toString() {
		return JSONUtil.serialize(this);
	}
}
