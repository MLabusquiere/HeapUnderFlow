package fr.esiea.pair.dao.database;

import org.bson.types.ObjectId;

import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.model.Question;

public interface QuestionDao {

    public Question getOne(ObjectId id) throws NoDataBaseConnectionException;

    public Iterable<Question> getAll() throws NoDataBaseConnectionException;

    public Iterable<Question> getSome(String query) throws NoDataBaseConnectionException;

    public Iterable<Question> getSome(String query, int skip, int limit) throws NoDataBaseConnectionException;

    public Iterable<Question> getSome(String field, ObjectId id) throws NoDataBaseConnectionException;

    public Iterable<Question> getSome(String field, ObjectId id, int skip, int limit) throws NoDataBaseConnectionException;

    public void save(Question data) throws NoDataBaseConnectionException;

    public void remove(ObjectId id) throws NoDataBaseConnectionException;

    public void insert(Question data) throws NoDataBaseConnectionException;

	public Iterable<Question> getAll(int skip, int limit) throws NoDataBaseConnectionException;
}
