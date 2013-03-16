package fr.esiea.pair.service.manager;

import org.bson.types.ObjectId;

import fr.esiea.pair.dao.database.UserDao;
import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.dao.mongo.UserDaoMongo;
import fr.esiea.pair.model.Answer;
import fr.esiea.pair.model.Question;
import fr.esiea.pair.model.User;
import fr.esiea.pair.model.model;
import fr.esiea.pair.service.exception.InvalidObjectIdException;
import fr.esiea.pair.service.exception.WrongFormatException.WrongUserFormatException;

public class UserManager {

	private UserDao userDao;

	public UserManager() throws NoDataBaseConnectionException  {

		userDao	= new UserDaoMongo();

	}

	public void create(User user) throws NoDataBaseConnectionException, WrongUserFormatException {

		checkUser(user);
		
		if(user.getId()!=null ) {
			throw new WrongUserFormatException("If you want to update an user use PUT");
		}

		userDao.save(user);

	}

	public void update(User user) throws NoDataBaseConnectionException, WrongUserFormatException {

		checkUser(user);

		if(user.getId() == null) {
			throw new WrongUserFormatException("If you want to create an user use POST");
		}

		userDao.save(user);
	}

	public void delete(ObjectId userId) throws NoDataBaseConnectionException, InvalidObjectIdException {

		try	{	
			userDao.remove(userId);
		} catch(IllegalArgumentException e) {
			throw new InvalidObjectIdException("Invalid id");
		}

	}

	public model get(ObjectId userId) throws NoDataBaseConnectionException, InvalidObjectIdException {

		model user;

		try	{	
			user = userDao.getOne(userId);
			if(user == null) {	
				throw new InvalidObjectIdException(userId);
			}	
		} catch(java.lang.IllegalArgumentException e) {
			throw new InvalidObjectIdException("Invalid id");
		}

		return user;

	}
	public model get(String userId) throws InvalidObjectIdException, NoDataBaseConnectionException	{


		ObjectId objectId;

		try	{	
			objectId = new ObjectId(userId);
		} catch(java.lang.IllegalArgumentException e) {
			throw new InvalidObjectIdException("Invalid id");
		}	

		return get(objectId); 

	}

	public Iterable<User> getQuery(String query) throws NoDataBaseConnectionException, InvalidObjectIdException {
		if(query==null) {
			return this.list();
		}

		Iterable<User> users;

		users = userDao.getSome(query);
		
		if(users.iterator().hasNext()) 
			return users;
		return null;
	}

	public Iterable<User> getQuery(String query,int skip,int limit) throws NoDataBaseConnectionException {

		return userDao.getSome(query,skip,limit);

	}

	public Iterable<User> list() throws NoDataBaseConnectionException {

		return userDao.getAll();

	}

	public Iterable<Question> getQuestionFrom(ObjectId userId) throws NoDataBaseConnectionException, InvalidObjectIdException {

		QuestionManager questionManager;
		Iterable<Question> questions;

		try	{	
			questionManager = new QuestionManager();
			questions = questionManager.getQuestionFrom(userId);
			if(!questions.iterator().hasNext()) {	
				return null;
			}	
		} catch(java.lang.IllegalArgumentException e) {
			throw new InvalidObjectIdException("Invalid id");
		}

		return questions;

	}

	public Iterable<Question> getQuestionFrom(ObjectId userId, int skip, int limit) throws NoDataBaseConnectionException, InvalidObjectIdException {

		QuestionManager questionManager;
		Iterable<Question> questions;

		try	{	
			questionManager = new QuestionManager();
			questions = questionManager.getQuestionFrom(userId,skip,limit);
			if(!questions.iterator().hasNext()) {	
				return null;
			}	
		} catch(java.lang.IllegalArgumentException e) {
			throw new InvalidObjectIdException("Invalid id");
		}

		return questions;

	}

	public Iterable<Answer> getAnswersFrom(ObjectId userId) throws NoDataBaseConnectionException, InvalidObjectIdException {

		AnswerManager answerManager;
		Iterable<Answer> answers;

		try	{	
			answerManager = new AnswerManager();
			answers = answerManager.getAnswerFrom(userId);
			if(!answers.iterator().hasNext()) {	
				return null;
			}	
		} catch(java.lang.IllegalArgumentException e) {
			throw new InvalidObjectIdException("Invalid id");
		}

		return answers;

	}

	public Iterable<Answer> getAnswersFrom(ObjectId userId,int skip,int limit) throws NoDataBaseConnectionException, InvalidObjectIdException {

		AnswerManager answerManager;
		Iterable<Answer> answers;

		try	{	
			answerManager = new AnswerManager();
			answers = answerManager.getAnswerFrom(userId,skip,limit);
			if(!answers.iterator().hasNext()) {	
				return null;
			}	
		} catch(java.lang.IllegalArgumentException e) {
			throw new InvalidObjectIdException("Invalid id");
		}

		return answers;
	}

	/*** Private methods ***/

	private void checkUser(User user) throws WrongUserFormatException {

		StringBuilder error =  new StringBuilder();

		if(user.getPseudo() == null) {
			error.append("Null Pseudo\n");
		}
		else {
			if(user.getPseudo().isEmpty()) {
				error.append("Missing Pseudo\n");
			}
		}

		if(user.getPassword() == null) {
			error.append("Null Password\n");
		}
		else {
			if(user.getPassword().isEmpty()) {
				error.append("Missing Pseudo\n");
			}
		}
		
		if(error.length()!=0){
			throw new WrongUserFormatException(error.toString());
		}

	}

}
