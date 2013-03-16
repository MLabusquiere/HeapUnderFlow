package fr.esiea.pair.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.esiea.pair.api.model.ErrorModel;


@Controller
@RequestMapping("/*")
public class UtilController {

	@RequestMapping(value  = "")
	@ResponseBody
	public ResponseEntity<ErrorModel> URIError(){

		ErrorModel error = new ErrorModel(404, 1025,"URL not found.", null, "/rest/error.jsp");
		return new ResponseEntity<ErrorModel>(error, HttpStatus.NOT_FOUND);
		
	}
	@RequestMapping(value = "/fun")
	@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
	public void fun()	{
		
	}
	
}
