
 

package com.axway.mbaas_preprod.models;

import com.axway.mbaas_preprod.JSONUtil;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class ErrorResponse {

	@SerializedName("status")
	private String status;

	@SerializedName("code")
	private Integer code;

	@SerializedName("message")
	private String message;

	@SerializedName("method_name")
	private String methodName;

	public ErrorResponse() {
	}

	/**
	 * Gets 
	 * @return String
	 **/
	public String getStatus() {
		return status;
	}

	/**
	 * Sets 
	 * @param val
	 **/
	public void setStatus(String val) {
		status = val;
	}

	/**
	 * Gets 
	 * @return Integer
	 **/
	public Integer getCode() {
		return code;
	}

	/**
	 * Sets 
	 * @param val
	 **/
	public void setCode(Integer val) {
		code = val;
	}

	/**
	 * Gets 
	 * @return String
	 **/
	public String getMessage() {
		return message;
	}

	/**
	 * Sets 
	 * @param val
	 **/
	public void setMessage(String val) {
		message = val;
	}

	/**
	 * Gets 
	 * @return String
	 **/
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Sets 
	 * @param val
	 **/
	public void setMethodName(String val) {
		methodName = val;
	}




  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse otherObj = (ErrorResponse) o;

    return (this.status == null ? otherObj.status == null : this.status.equals(otherObj.status))  &&
	 (this.code == null ? otherObj.code == null : this.code.equals(otherObj.code))  &&
	 (this.message == null ? otherObj.message == null : this.message.equals(otherObj.message))  &&
	 (this.methodName == null ? otherObj.methodName == null : this.methodName.equals(otherObj.methodName)) ;

  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.status == null ? 0: this.status.hashCode());
    result = 31 * result + (this.code == null ? 0: this.code.hashCode());
    result = 31 * result + (this.message == null ? 0: this.message.hashCode());
    result = 31 * result + (this.methodName == null ? 0: this.methodName.hashCode());
    return result;
  }

  @Override
  public String toString() {
		return JSONUtil.serialize(this);
	}
}
