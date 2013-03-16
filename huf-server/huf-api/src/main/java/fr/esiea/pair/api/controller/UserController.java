package fr.esiea.pair.api.controller;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.model.Answer;
import fr.esiea.pair.model.Question;
import fr.esiea.pair.model.User;
import fr.esiea.pair.model.model;
import fr.esiea.pair.service.exception.InvalidObjectIdException;
import fr.esiea.pair.service.exception.WrongFormatException.WrongFormatException;
import fr.esiea.pair.service.manager.UserManager;

@Controller
@RequestMapping("/users")
public class UserController extends ApiExceptionHandlerController{

	private UserManager userManager;

	public UserController() throws NoDataBaseConnectionException {

		userManager = 	new UserManager();
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Iterable<User> list() throws NoDataBaseConnectionException {
		
		return userManager.list();
	
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public model getById(@PathVariable("id") String userId) throws InvalidObjectIdException, NoDataBaseConnectionException {   

		return userManager.get(userId); 

	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody User user) throws NoDataBaseConnectionException, WrongFormatException {

		userManager.create(user);

	} 

	@RequestMapping(value  = "", method = RequestMethod.PUT,consumes = "application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void edit(@RequestBody User user) throws NoDataBaseConnectionException, WrongFormatException {

		userManager.update(user);

	} 

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) throws InvalidObjectIdException, NoDataBaseConnectionException {

		userManager.delete(new ObjectId(id));

	}

	@RequestMapping(value = "/{userId}/questions", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Iterable<Question> getQuestions(@PathVariable String userId) throws  InvalidObjectIdException, NoDataBaseConnectionException {

		return userManager.getQuestionFrom(new ObjectId(userId));

	}

	@RequestMapping(value = "/{userId}/answers", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Iterable<Answer> getAnswers(@PathVariable String userId) throws  InvalidObjectIdException, NoDataBaseConnectionException {

		return userManager.getAnswersFrom(new ObjectId(userId));

	}

}