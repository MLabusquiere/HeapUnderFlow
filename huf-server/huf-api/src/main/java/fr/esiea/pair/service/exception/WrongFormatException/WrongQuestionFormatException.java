package fr.esiea.pair.service.exception.WrongFormatException;

@SuppressWarnings("serial")
public class WrongQuestionFormatException extends WrongFormatException {

    public WrongQuestionFormatException(String string) {

        status = 400;
        code = 10024;
        developerMsg = null;
        moreInfoUrl = "/rest/error.jsp";
        messageBuilder = new StringBuilder(string);
        this.msg = string;

    }

    public WrongQuestionFormatException() {

        status = 400;
        code = 10024;
        developerMsg = null;
        moreInfoUrl = "/rest/error.jsp";
        messageBuilder = new StringBuilder();
        this.msg = null;

    }

}

