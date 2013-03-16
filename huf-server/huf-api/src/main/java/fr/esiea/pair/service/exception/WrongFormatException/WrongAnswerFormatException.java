package fr.esiea.pair.service.exception.WrongFormatException;

@SuppressWarnings("serial")
public class WrongAnswerFormatException extends WrongFormatException {


	public WrongAnswerFormatException(String message) {

		status = 400;
		code = 10025;
		developerMsg = null;
		moreInfoUrl = "/rest/error.jsp";
		messageBuilder = new StringBuilder(message);
		this.msg = message;

	}

	public WrongAnswerFormatException() {


		status = 400;
		code = 10025;
		developerMsg = null;
		moreInfoUrl = "/rest/error.jsp";
		messageBuilder = new StringBuilder();
		this.msg = null;

	}
}
