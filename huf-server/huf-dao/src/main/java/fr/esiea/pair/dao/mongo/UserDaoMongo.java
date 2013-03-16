package fr.esiea.pair.dao.mongo;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import fr.esiea.pair.dao.database.UserDao;
import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.model.User;
import fr.esiea.pair.model.model;

public class UserDaoMongo implements UserDao {

    private String collection = "Users";
    private MongoCollection userCollection;

    public UserDaoMongo() throws NoDataBaseConnectionException {

        userCollection = MongoConnection.getConnection().getCollection(collection);

    }
    @Override
    public User getOne(ObjectId id) throws NoDataBaseConnectionException {

        return userCollection.findOne(id).as(User.class);

    }
    @Override
    public Iterable<User> getAll() throws NoDataBaseConnectionException {
    	
    	return userCollection.find().as(User.class);

    }
    @Override
    public Iterable<User> getSome(String query) throws NoDataBaseConnectionException {

        return userCollection.find(query).as(User.class);

    }
    @Override
    public Iterable<User> getSome(String query, int skip, int limit) {

        return userCollection.find(query).skip(skip).limit(limit).as(User.class);

    }
    @Override
    public void save(model data) throws NoDataBaseConnectionException {

        userCollection.save(data);

    }
    @Override
    public void remove(ObjectId id) throws NoDataBaseConnectionException {

        userCollection.remove(id);

    }
	@Override
	public void insert(model data) throws NoDataBaseConnectionException {
		
		 userCollection.save(data);
		
	}

}
