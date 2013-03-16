package fr.esiea.pair.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.esiea.pair.api.model.ErrorModel;
import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.service.exception.InvalidObjectIdException;
import fr.esiea.pair.service.exception.WrongFormatException.WrongFormatException;

@Controller
public abstract class ApiExceptionHandlerController {
	
	@ExceptionHandler(InvalidObjectIdException.class)
	@ResponseBody
	public ResponseEntity<ErrorModel> handleExceptionInvalidObjectIdException(InvalidObjectIdException ex) {

		ErrorModel error = new ErrorModel(ex.getStatus(), ex.getCode(), ex.getMsg(), ex.getDeveloperMsg(), ex.getMoreInfoUrl());
		return new ResponseEntity<ErrorModel>(error, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(WrongFormatException.class)
	@ResponseBody
	public ResponseEntity<ErrorModel> handleExceptionWrongFormatException(WrongFormatException ex) {

		ErrorModel error = new ErrorModel(ex.getStatus(), ex.getCode(), ex.getMsg(), ex.getDeveloperMsg(), ex.getMoreInfoUrl());
		return new ResponseEntity<ErrorModel>(error, HttpStatus.BAD_REQUEST);

	}
	
//	@ExceptionHandler(HttpMessageNotWritableException.class)
//	@ResponseBody
//	public ResponseEntity<ErrorModel> InternalServerError() {
//		//mongoDB is not running
//		//TODO etre sur c'est ça
//		return new ResponseEntity<ErrorModel>(HttpStatus.INTERNAL_SERVER_ERROR);
//	
//	}
	
	@ExceptionHandler(NoDataBaseConnectionException.class)
	@ResponseBody
	public ResponseEntity<String> InternalServerError(NoDataBaseConnectionException e) {
		//TODO make it json
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	
	}
	
	@ExceptionHandler(HttpMessageNotWritableException.class)
	@ResponseBody
	public ResponseEntity<String> InternalServerError(HttpMessageNotWritableException e) {
		//TODO make it json & wtf? Users need that to detect NoDatabaseConecction
		e.printStackTrace();
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	
	}

}
