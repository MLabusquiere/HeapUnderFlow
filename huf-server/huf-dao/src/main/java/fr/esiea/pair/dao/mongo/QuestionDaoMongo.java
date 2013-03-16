package fr.esiea.pair.dao.mongo;

import static org.jongo.MongoCollection.MONGO_QUERY_OID;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import com.mongodb.MongoException;

import fr.esiea.pair.dao.database.QuestionDao;
import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.dao.mongo.model.QuestionDaoModel;
import fr.esiea.pair.model.Answer;
import fr.esiea.pair.model.Question;

public class QuestionDaoMongo implements QuestionDao {

	private String collection = "Questions";
	private MongoCollection questionCollection;

	public QuestionDaoMongo() throws NoDataBaseConnectionException {

		questionCollection	= MongoConnection.getConnection().getCollection(collection);

	}

	@Override
	public Question getOne(ObjectId id) throws NoDataBaseConnectionException {

		QuestionDaoModel questionDaoModel = questionCollection.findOne(id).as(QuestionDaoModel.class);
		return questionDaoModelToQuestion(questionDaoModel);

	}


	@Override
	public Iterable<Question> getAll() throws NoDataBaseConnectionException  {
		Iterable<Question> questions = null;
		
		try	{
			questions = questionDaoModelToQuestion(questionCollection.find().as(QuestionDaoModel.class));
		}catch(MongoException e)	{
			throw new NoDataBaseConnectionException();
		}
		
		return questions;
	}

	@Override
	public Iterable<Question> getSome(String query) throws NoDataBaseConnectionException {

		return questionDaoModelToQuestion(questionCollection.find(query).as(QuestionDaoModel.class));

	}

	@Override
	public Iterable<Question> getSome(String query,int skip,int limit) throws NoDataBaseConnectionException {

		return questionDaoModelToQuestion(questionCollection.find(query).skip(skip).limit(limit).as(QuestionDaoModel.class));

	}

	@Override
	public void save(Question data) throws NoDataBaseConnectionException {

		AnswerDaoMongo answerDao;
		ArrayList<Answer> answers = data.getAnswers();
		answerDao = new AnswerDaoMongo();
		try	{

			for(Answer answer : answers )
				answerDao.save(answer);

		}catch (java.lang.NullPointerException e)	{
			System.out.println("there is no answer??");
			// There is no answer. Probably the first insertion.
		}

		QuestionDaoModel questionDao = new QuestionDaoModel(data,answers);
		questionCollection.save(questionDao);

	}

	@Override
	public void remove(ObjectId id) throws NoDataBaseConnectionException {

		AnswerDaoMongo answerDao;
		ArrayList<Answer> answers = this.getOne(id).getAnswers();
		answerDao = new AnswerDaoMongo();
		try	{

			for(Answer answer : answers )
				answerDao.remove(new ObjectId(answer.getId()));

		} catch (java.lang.NullPointerException e)	{
			// There is no answer. 
		}
		questionCollection.remove(id);

	}

	@Override
	public Iterable<Question> getSome(String field, ObjectId id) throws NoDataBaseConnectionException {

		return questionDaoModelToQuestion(questionCollection.find("{"+field+": {" + MONGO_QUERY_OID + ":\""+id.toString() + "\"}}").as(QuestionDaoModel.class));

	}

	@Override
	public Iterable<Question> getSome(String field, ObjectId id, int skip, int limit) throws NoDataBaseConnectionException {

		return questionDaoModelToQuestion(questionCollection.find("{"+field+": {" + MONGO_QUERY_OID + ":\""+id.toString() + "\"}}").skip(skip).limit(limit).as(QuestionDaoModel.class));

	}

	private static Question questionDaoModelToQuestion(QuestionDaoModel questionDaoModel) throws NoDataBaseConnectionException {

		ArrayList<Answer> answers=new ArrayList<Answer>();
		AnswerDaoMongo answerDaoMongo = null;

		answerDaoMongo=new AnswerDaoMongo();

		try {
			for(ObjectId ref:questionDaoModel.getAnswers())
				answers.add(answerDaoMongo.getOne(ref));

		} catch (java.lang.NullPointerException e) {
			// bugg
		} catch (java.lang.IllegalArgumentException e) {
			// the is no answer
		}

        Question question = new Question(questionDaoModel.getAuthorId(),questionDaoModel.getAuthorPseudo(),questionDaoModel.getTitle(),questionDaoModel.getText());
        question.setAnswers(answers);
        question.setId(questionDaoModel.getId());
        return question;
    }

	private static Iterable<Question> questionDaoModelToQuestion(Iterable<QuestionDaoModel> questionsDaoModel) throws NoDataBaseConnectionException  {

		Iterable<Question> questions 	=	 new ArrayList<Question>();


		for(QuestionDaoModel questionDaoModel : questionsDaoModel)
			((ArrayList<Question>) questions).add(questionDaoModelToQuestion(questionDaoModel));

		return questions;
	}

	@Override
	public void insert(Question data) throws NoDataBaseConnectionException {
		
		this.save(data);
		
	}

	@Override
	public Iterable<Question> getAll(int skip, int limit) throws NoDataBaseConnectionException {
		Iterable<Question> questions = null;
		
		try	{
			questions = questionDaoModelToQuestion(questionCollection.find().skip(skip).limit(limit).as(QuestionDaoModel.class));
		}catch(MongoException e)	{
			throw new NoDataBaseConnectionException();
		}
		
		return questions;
	}
}
