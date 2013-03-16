package fr.esiea.pair.dao.tools;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import java.net.UnknownHostException;
import java.util.Set;

public class DataTool {

    public static void showAllDB() {

        System.out.println("\t- All Datas Beginning -");

        Mongo m;
        try {
            m = new Mongo();

            for (String s : m.getDatabaseNames()) {
                System.out.println("DB : " + s);
                DB db = m.getDB(s);
                Set<String> colls = db.getCollectionNames();

                for (String t : colls) {
                    System.out.println();
                    System.out.println("\tCollection : " + t);
                    DBCollection c = db.getCollection(t);
                    DBCursor cur = c.find();

                    while (cur.hasNext()) {
                        System.out.println(cur.next());
                    }
                }
                System.out.println("\t- All Datas End -");
                System.out.println();
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void showAllDB(String debug) {

        System.out.println("\t- All Datas " + debug + " Beginning -");

        Mongo m;
        try {
            m = new Mongo();

            for (String s : m.getDatabaseNames()) {
                System.out.println("DB : " + s);
                DB db = m.getDB(s);
                Set<String> colls = db.getCollectionNames();

                for (String t : colls) {
                    System.out.println();
                    System.out.println("\tCollection : " + t);
                    DBCollection c = db.getCollection(t);
                    DBCursor cur = c.find();

                    while (cur.hasNext()) {
                        System.out.println(cur.next());
                    }
                }
                System.out.println("\t- All Datas " + debug + " End -");
                System.out.println();
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void showDB(String dataBase) {
        Mongo m;
        try {
            m = new Mongo();

            System.out.println("DB : " + dataBase);
            DB db = m.getDB(dataBase);
            Set<String> colls = db.getCollectionNames();

            for (String s : colls) {
                System.out.println("\tCollection : " + s);
                DBCollection c = db.getCollection(s);
                DBCursor cur = c.find();

                while (cur.hasNext()) {
                    System.out.println(cur.next());
                }
                System.out.println();
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void showCollection(String dataBase, String collection) {
        Mongo m;
        try {
            m = new Mongo();

            System.out.println("DB : " + dataBase);
            DB db = m.getDB(dataBase);

            System.out.println("\tCollection : " + collection);
            DBCollection c = db.getCollection(collection);
            DBCursor cur = c.find();

            while (cur.hasNext()) {
                System.out.println(cur.next());
            }
            System.out.println();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void clearAllDB() {
        Mongo m;
        try {
            m = new Mongo();

            for (String s : m.getDatabaseNames()) {
                System.out.println("Dropped : " + s);
                m.dropDatabase(s);
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println();
    }

}
