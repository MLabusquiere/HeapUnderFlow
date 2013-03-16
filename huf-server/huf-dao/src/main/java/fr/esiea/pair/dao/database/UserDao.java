package fr.esiea.pair.dao.database;

import org.bson.types.ObjectId;

import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.model.User;
import fr.esiea.pair.model.model;

public interface UserDao {

    public User getOne(ObjectId id) throws NoDataBaseConnectionException;

    public Iterable<User> getAll() throws NoDataBaseConnectionException;

    public Iterable<User> getSome(String query) throws NoDataBaseConnectionException;

    public Iterable<User> getSome(String query, int skip, int limit) throws NoDataBaseConnectionException;

    public void save(model data) throws NoDataBaseConnectionException;

    public void remove(ObjectId id) throws NoDataBaseConnectionException;

	public void insert(model data) throws NoDataBaseConnectionException;
}
