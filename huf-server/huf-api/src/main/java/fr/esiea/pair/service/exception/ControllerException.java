package fr.esiea.pair.service.exception;


@SuppressWarnings("serial")
public abstract class ControllerException extends Exception {

	protected int status;
	protected int code;
	protected String developerMsg;
	protected String moreInfoUrl;
	protected StringBuilder messageBuilder;
	protected String msg;

	public int getStatus() {
		return status;
	}

	public int getCode() {
		return code;
	}

	public String getDeveloperMsg() {
		return developerMsg;
	}

	public String getMoreInfoUrl() {
		return moreInfoUrl;
	}

	public String getMsg() {
		return msg;
	}

	
}