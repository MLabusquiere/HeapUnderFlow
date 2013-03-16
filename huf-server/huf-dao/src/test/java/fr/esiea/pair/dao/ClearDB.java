package fr.esiea.pair.dao;

import com.mongodb.Mongo;

import java.net.UnknownHostException;

public class ClearDB {

    /**
     * @param args
     * @throws UnknownHostException
     * @author DDeis
     * <p/>
     * To drop all databases
     */
    public static void main(String[] args) throws Exception {
        Mongo m = new Mongo();

        for (String s : m.getDatabaseNames()) {
            System.out.println("Dropped : " + s);
            m.dropDatabase(s);
        }
    }

}

