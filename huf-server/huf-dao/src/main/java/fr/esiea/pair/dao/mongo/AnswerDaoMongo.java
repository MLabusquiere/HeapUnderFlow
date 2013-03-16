package fr.esiea.pair.dao.mongo;

import static org.jongo.MongoCollection.MONGO_QUERY_OID;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import fr.esiea.pair.dao.database.AnswerDao;
import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.model.Answer;

public class AnswerDaoMongo implements AnswerDao {

    private String collection = "Answers";
    private MongoCollection answerCollection;

    public AnswerDaoMongo() throws NoDataBaseConnectionException {

        answerCollection = MongoConnection.getConnection().getCollection(collection);

    }

    public Answer getOne(ObjectId id) {

        return answerCollection.findOne(id).as(Answer.class);

    }

    public Iterable<Answer> getAll() {

        return answerCollection.find().as(Answer.class);

    }

    public Iterable<Answer> getSome(String query) {

        return answerCollection.find(query).as(Answer.class);

    }

    public Iterable<Answer> getSome(String query, int skip, int limit) {

        return answerCollection.find(query).skip(skip).limit(limit).as(Answer.class);

    }

    public void save(Answer data) {

        answerCollection.save(data);

    }

    public void remove(ObjectId id) {

        answerCollection.remove(id);

    }

    @Override
    public Iterable<Answer> getSome(String field, ObjectId id) {

        return answerCollection.find("{" + field + ": {" + MONGO_QUERY_OID + ":\"" + id.toString().toString() + "\"}}").as(Answer.class);

    }

    @Override
    public Iterable<Answer> getSome(String field, ObjectId id,int skip, int limit) {
    	
        return answerCollection.find("{'" + field + "' : {" + MONGO_QUERY_OID + ":\"" + id.toString() + "\"}}").skip(skip).limit(limit).as(Answer.class);
    }

	@Override
	public void insert(Answer data) {
		
		 answerCollection.save(data);
		
	}





}
