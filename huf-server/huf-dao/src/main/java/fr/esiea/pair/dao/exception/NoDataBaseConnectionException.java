package fr.esiea.pair.dao.exception;

@SuppressWarnings("serial")
public class NoDataBaseConnectionException extends Exception {

    public NoDataBaseConnectionException() {
    }

    @Override
    public String getMessage() {
        return "The database does not respond";
    }

}
