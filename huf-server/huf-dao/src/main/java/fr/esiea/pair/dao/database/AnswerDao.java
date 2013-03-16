package fr.esiea.pair.dao.database;

import org.bson.types.ObjectId;

import fr.esiea.pair.model.Answer;

public interface AnswerDao {

    public Answer getOne(ObjectId id);

    public Iterable<Answer> getAll();

    public Iterable<Answer> getSome(String query);

    public Iterable<Answer> getSome(String query, int skip, int limit);

    public Iterable<Answer> getSome(String field, ObjectId id);

    public Iterable<Answer> getSome(String field, ObjectId id, int skip, int limit);

    public void save(Answer data);

    public void remove(ObjectId id);
    
    public void insert(Answer data);
}
