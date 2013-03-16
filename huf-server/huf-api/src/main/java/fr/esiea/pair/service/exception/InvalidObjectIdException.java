package fr.esiea.pair.service.exception;

import org.bson.types.ObjectId;

@SuppressWarnings("serial")
public class InvalidObjectIdException extends ControllerException {

    private ObjectId id;

    public InvalidObjectIdException(ObjectId id) {
        status = 500;
        code = 10023;
        developerMsg = null;
        moreInfoUrl = "/rest/error.jsp";
        this.msg = "This id doesn't exist: " + id.toString();

    }

    public InvalidObjectIdException(String string) {
        status = 500;
        code = 10023;
        developerMsg = null;
        moreInfoUrl = "/rest/error.jsp";
        this.msg = string;
        // TODO Auto-generated constructor stub
    }

    /**
     * Getters **
     */

    public ObjectId getId() {
        return id;
    }

}
