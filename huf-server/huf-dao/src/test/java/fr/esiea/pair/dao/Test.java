package fr.esiea.pair.dao;

import fr.esiea.pair.dao.database.QuestionDao;
import fr.esiea.pair.dao.database.UserDao;
import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.dao.mongo.QuestionDaoMongo;
import fr.esiea.pair.dao.mongo.UserDaoMongo;
import fr.esiea.pair.dao.tools.DataTool;
import fr.esiea.pair.model.Answer;
import fr.esiea.pair.model.Question;
import fr.esiea.pair.model.User;


public class Test {

    /**
     * @param args
     * @throws NoDataBaseConnectionException
     *
     */
    public static void main(String[] args) {

        DataTool.clearAllDB();

        UserDao userDB;
        QuestionDao questionDao;
        try {
            userDB = new UserDaoMongo();
            questionDao = new QuestionDaoMongo();
            
            User cecile = new User("Cecile Dispa", "pwd");
            User soraya = new User("Soraya Bouakkaz", "pwd");
            User damien = new User("Damien Deis", "pwd");
            System.out.println(damien.getId());
            userDB.insert(damien);
            System.out.println(damien.getId());
            Question q1 = new Question(cecile, "Juste un test", "A cynic is a man who knows the price of everything but the value of nothing.");
            Answer a1 = new Answer(damien, "kezako", q1);
            Answer a2 = new Answer(soraya, "kezako", q1);
           

            Question q2 = new Question(damien, "Deuxième question", "Juste pour voir une liste de questions.");

            
            q1.addAnswer(a1);
            questionDao.save(q1);


            q1.addAnswer(a2);
            questionDao.save(q1);

            questionDao.save(q2);

            DataTool.showAllDB("Final");

            System.out.println("Done");
        } catch (NoDataBaseConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
