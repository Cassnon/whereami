package org.mtrec.wheramitools.response;

public class JsonResult {

	private int isSuccess;
	
	private String errorMsg;

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public JsonResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
