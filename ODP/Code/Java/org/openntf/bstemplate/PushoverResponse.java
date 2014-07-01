package org.openntf.bstemplate;

import java.util.ArrayList;
import java.util.List;

public class PushoverResponse {
	private String message;
	private int status;
	private final List<String> errors;

	public PushoverResponse() {
		this.errors = new ArrayList<String>();
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

	public String getError() {
		return errors.get(0).toString();
	}

	public void addError(final String errmsg) {
		this.errors.add(errmsg);
	}

}
