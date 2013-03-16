package fr.esiea.pair.dao;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import java.util.Set;

public class ShowDB {

    /**
     * @param args
     * @throws Exception
     * @author DDeis
     * <p/>
     * Display every MongoDB datas
     */
    public static void main(String[] args) throws Exception {
        Mongo m = new Mongo();

        for (String s : m.getDatabaseNames()) {
            System.out.println("DB : " + s);
            DB db = m.getDB(s);
            Set<String> colls = db.getCollectionNames();

            for (String t : colls) {
                System.out.println("\tCollection : " + t);
                DBCollection c = db.getCollection(t);
                DBCursor cur = c.find();

                while (cur.hasNext()) {
                    System.out.println(cur.next());
                }
                System.out.println();
            }
            System.out.println();
        }
    }

}
