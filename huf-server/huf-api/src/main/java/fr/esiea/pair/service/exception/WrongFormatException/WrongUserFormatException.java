package fr.esiea.pair.service.exception.WrongFormatException;



@SuppressWarnings("serial")
public class WrongUserFormatException extends WrongFormatException {
	
	public WrongUserFormatException(String string) {
	
		status = 400;
		code = 10024;
		developerMsg = null;
		moreInfoUrl = "/rest/error.jsp";
		messageBuilder = new StringBuilder(string);
		msg = string;
		
	
	}
	
	public WrongUserFormatException() {
			
		status = 400;
		code = 10024;
		developerMsg = null;
		moreInfoUrl = "/rest/error.jsp";
		messageBuilder = new StringBuilder();
		msg = null;
		
	
	}

}
