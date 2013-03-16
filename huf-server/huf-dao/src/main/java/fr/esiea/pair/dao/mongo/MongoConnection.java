package fr.esiea.pair.dao.mongo;

import com.mongodb.DB;
import com.mongodb.Mongo;
import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import org.jongo.Jongo;

import java.net.UnknownHostException;

public class MongoConnection {

    /*
     *  L'utilisation du mot clé volatile permet,
     *   en Java version 5 et supérieur, d'éviter
     *   le cas où "Singleton.instance" est non-nul,
     *   mais pas encore "réellement" instancié.
     */
    private static volatile MongoConnection instance;
    private Jongo jongo;

    private MongoConnection() throws UnknownHostException {
        DB db = new Mongo().getDB("HeapUnderFlow");
        jongo = new Jongo(db);
    }

    public static Jongo getConnection() throws NoDataBaseConnectionException {

		/*
         * Le "Double-Checked instance"/"instance doublement vérifié"
		 *permet d'éviter un appel coûteux à synchronized, une fois
		 * que l'instanciation est faite.
		 */
        if (instance == null) {

			/*
			 *  Le mot-clé synchronized sur ce bloc empêche toute 
			 *  instanciation multiple même par différents "threads".
			 */
            synchronized (MongoConnection.class) {
                if (instance == null) {
                    try {
                        instance = new MongoConnection();
                    } catch (Exception e) {
                        throw new NoDataBaseConnectionException();
                    }
                }
            }
        }
        return instance.jongo;
    }

    public static void close(Jongo jongo) {

    }

}