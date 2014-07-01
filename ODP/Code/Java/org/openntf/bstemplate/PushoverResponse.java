package org.openntf.bstemplate;

public class PushoverResponse {
	private String message;
	private int status;

	public PushoverResponse() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

}
