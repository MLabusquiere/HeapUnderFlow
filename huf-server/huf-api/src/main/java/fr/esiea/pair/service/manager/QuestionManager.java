package fr.esiea.pair.service.manager;


import org.bson.types.ObjectId;

import fr.esiea.pair.dao.database.QuestionDao;
import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.dao.mongo.QuestionDaoMongo;
import fr.esiea.pair.model.Question;
import fr.esiea.pair.service.exception.InvalidObjectIdException;
import fr.esiea.pair.service.exception.WrongFormatException.WrongFormatException;
import fr.esiea.pair.service.exception.WrongFormatException.WrongQuestionFormatException;

public class QuestionManager {

	private QuestionDao questionDao;

	public QuestionManager() throws NoDataBaseConnectionException {

		questionDao	= new QuestionDaoMongo();

	}

	public void create(Question question) throws NoDataBaseConnectionException, WrongQuestionFormatException {

		checkQuestion(question);

		if(question.getId()!=null)	{
			throw new WrongQuestionFormatException("If you want to update a question use PUT");
		}

		questionDao.save(question);

	}

	public void update(Question question) throws NoDataBaseConnectionException, WrongQuestionFormatException {
		checkQuestion(question);
		if(question.getId().equals(null))	{
			throw new WrongQuestionFormatException("If you want to create a question use POST");
		}
		questionDao.save(question);
	}

	public void delete(ObjectId questionId) throws NoDataBaseConnectionException, InvalidObjectIdException {

		try	{	
			questionDao.remove(questionId);
		} catch(IllegalArgumentException e) {
			throw new InvalidObjectIdException("Invalid id");
		}

	}

	public Question get(ObjectId questionId) throws NoDataBaseConnectionException, InvalidObjectIdException {

		Question question;
		
		try	{
			question = questionDao.getOne(questionId);
				if(question == null)	{
					throw new InvalidObjectIdException(questionId);
				}
		}catch(java.lang.IllegalArgumentException e) {
			throw new InvalidObjectIdException("Invalid id");
		}

		return question;
	}
	
	public Question get(String questionId) throws InvalidObjectIdException, NoDataBaseConnectionException	{
		
		
		ObjectId objectId;
	
		try	{	
			objectId = new ObjectId(questionId);
		} catch(java.lang.IllegalArgumentException e) {
			throw new InvalidObjectIdException("Invalid id");
		}	
		
		return get(objectId); 
		
	}

	public Iterable<Question> getQuery(String query) throws NoDataBaseConnectionException {
		if(query==null) {
			//What should we do? give him a message to use getAll()?
		}
		
		return questionDao.getSome(query);
	}

	public Iterable<Question> getQuery(String query,int skip,int limit) throws NoDataBaseConnectionException, InvalidObjectIdException {
		if(query==null) {
			return this.list(skip,limit);
		}
		
		Iterable<Question> questions;
		
		questions = questionDao.getSome(query);
		
		if(questions.iterator().hasNext())
			return questions;
		return null;
	}

	private Iterable<Question> list(int skip, int limit)  throws NoDataBaseConnectionException {
		
		return questionDao.getAll(skip,limit);
	
	}

	public Iterable<Question> list() throws NoDataBaseConnectionException {
		
		return questionDao.getAll();
		
	}

	protected Iterable<Question> getQuestionFrom(ObjectId autorId) throws NoDataBaseConnectionException, InvalidObjectIdException {
		//use by getAnswerFrom in questionManager

		Iterable<Question> questions;
		
		try	{
			questions = questionDao.getSome("userId",autorId);
			if(!questions.iterator().hasNext())	{
				throw new InvalidObjectIdException(autorId);
			}
		}catch(IllegalArgumentException e)	{
			throw new InvalidObjectIdException("Invalid id");
		}
		return questions;
	}
	protected Iterable<Question> getQuestionFrom(ObjectId autorId,int skip,int limit) throws NoDataBaseConnectionException, InvalidObjectIdException {
		//use by getAnswerFrom in questionManager

		Iterable<Question> questions;
		
		try	{
			questions = questionDao.getSome("userId",autorId);
			if(!questions.iterator().hasNext())	{
				throw new InvalidObjectIdException(autorId);
			}
		}catch(IllegalArgumentException e)	{
			throw new InvalidObjectIdException("Invalid id");
		}
		return questions;
	}

	/*** Private methods 
	 * @throws WrongFormatException ***/

	private void checkQuestion(Question question) throws WrongQuestionFormatException {

		StringBuilder error = new StringBuilder();

		if(question.getAuthorPseudo() == null) 
			error.append("Null Author Pseudo\n");
		else 
			if(question.getAuthorPseudo().isEmpty()) 
				error.append("Missing Author Pseudo\n");

		if(question.getAuthorId() == null) 
			error.append("No Author Reference\n");
		
		if(question.getTitle()	==	null)
			error.append("Null Title");
		else 
			if(question.getTitle().isEmpty()) 
				error.append("Missing Title\n");

		if(question.getText()	==	null)
			error.append("Null Text");
		else 
			if(question.getTitle().isEmpty()) 
				error.append("Missing Text\n");

		if(error.length()!=0){
			throw new WrongQuestionFormatException(error.toString());
		}

	}


}
