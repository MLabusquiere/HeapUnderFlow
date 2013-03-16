package fr.esiea.pair.service.manager;

import fr.esiea.pair.dao.database.AnswerDao;
import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.dao.mongo.AnswerDaoMongo;
import fr.esiea.pair.model.Answer;
import fr.esiea.pair.service.exception.InvalidObjectIdException;
import fr.esiea.pair.service.exception.WrongFormatException.WrongAnswerFormatException;
import org.bson.types.ObjectId;

public class AnswerManager {

    private AnswerDao answerDao;

    public AnswerManager() throws NoDataBaseConnectionException {

        answerDao = new AnswerDaoMongo();

    }

    public void create(Answer answer) throws NoDataBaseConnectionException, WrongAnswerFormatException {

        checkAnswer(answer);

        if (!answer.getId().equals(null)) {
            throw new WrongAnswerFormatException("If you want to update a answer use PUT");
        }

        answerDao.save(answer);

    }

    public void update(Answer answer) throws NoDataBaseConnectionException, WrongAnswerFormatException {
        checkAnswer(answer);
        if (answer.getId().equals(null)) {
            throw new WrongAnswerFormatException("If you want to create a answer use POST");
        }
        answerDao.save(answer);
    }

    public void delete(ObjectId answerId) throws NoDataBaseConnectionException, InvalidObjectIdException {

        try {
            answerDao.remove(answerId);
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException("Invalid id");
        }

    }

    public Answer get(ObjectId answerId) throws NoDataBaseConnectionException, InvalidObjectIdException {

        Answer answer;

        try {
            answer = answerDao.getOne(answerId);
            if (answer == null) {
                throw new InvalidObjectIdException(answerId);
            }
        } catch (java.lang.IllegalArgumentException e) {
            throw new InvalidObjectIdException("Invalid id");
        }

        return answer;
    }

    public Answer get(String answerId) throws InvalidObjectIdException, NoDataBaseConnectionException {

        ObjectId objectId;

        try {
            objectId = new ObjectId(answerId);
        } catch (java.lang.IllegalArgumentException e) {
            throw new InvalidObjectIdException("Invalid id");
        }

        return get(objectId);

    }

    public Iterable<Answer> getQuery(String query) throws NoDataBaseConnectionException {
        if (query == null) {
            //What should we do? give him a message to use getAll()?
        }

        return answerDao.getSome(query);
    }

    public Iterable<Answer> getQuery(String query, int skip, int limit) throws NoDataBaseConnectionException, InvalidObjectIdException {
        if (query == null) {
            return this.list();
        }

        Iterable<Answer> answers;

        answers = answerDao.getSome(query);
     
        if(answers.iterator().hasNext())
        	return answers;
        return null;
    }

    public Iterable<Answer> list() throws NoDataBaseConnectionException {

        return answerDao.getAll();

    }

    protected Iterable<Answer> getAnswerFrom(ObjectId autorId) throws NoDataBaseConnectionException, InvalidObjectIdException {
        //use by getAnswerFrom in answerManager

        Iterable<Answer> answers;

        try {
            answers = answerDao.getSome("userId", autorId);
            if (!answers.iterator().hasNext()) {
                throw new InvalidObjectIdException(autorId);
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException("Invalid id");
        }
        return answers;
    }

    protected Iterable<Answer> getAnswerFrom(ObjectId autorId, int skip, int limit) throws NoDataBaseConnectionException, InvalidObjectIdException {
        //use by getAnswerFrom in answerManager

        Iterable<Answer> answers;

        try {
            answers = answerDao.getSome("userId", autorId);
            if (!answers.iterator().hasNext()) {
                throw new InvalidObjectIdException(autorId);
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException("Invalid id");
        }
        return answers;
    }

    /**
     * Private methods
     *
     * @throws WrongAnswerFormatException **
     */

    private void checkAnswer(Answer answer) throws WrongAnswerFormatException {
        /*
		WrongAnswerFormatException e = new WrongAnswerFormatException();
		
		if(answer.getUserId()	==	null)	
			e.add("Missing userId");
		if(answer.getUserPseudo()	==	null)
			e.add("Missing UserPseudo");
		if(answer.getTitle()	==	null)
			e.add("g Title");
		if(answer.getText()	==	null)
			e.add("Missing Text");
		
		if(e.isThereAnError())	{
			throw e;
		}
		*/
    }

}
