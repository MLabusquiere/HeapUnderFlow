package controller;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasItems;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mongodb.Mongo;

import fr.esiea.pair.dao.database.QuestionDao;
import fr.esiea.pair.dao.database.UserDao;
import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.dao.mongo.QuestionDaoMongo;
import fr.esiea.pair.dao.mongo.UserDaoMongo;
import fr.esiea.pair.model.Answer;
import fr.esiea.pair.model.Question;
import fr.esiea.pair.model.User;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


//HELP to make test
//
//MockMvc plus ou moin simule le conteneur de servlets : pour nous tomcat 
//
//Debugg use andDo(print()); as below
//this.mockMvc.perform(get("/users/").accept(MediaType.APPLICATION_JSON)).andDo(print());
//to use uncomment the import
//
//All reference about jsonPath here : http://goessner.net/articles/JsonPath/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml"})
@WebAppConfiguration
public class QuestionTest {//implements ControllerTest{

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	private UserDao userDB;
	private User cecile,damien,soraya,maxence;
	
	private QuestionDao questionDB;
	private Question question1,question2,question3;
	private String titleQuestion1,titleQuestion2,titleQuestion3;
	private String textQuestion1,textQuestion2,textQuestion3;
	private Answer answer1Q1,answer1Q2,answer2Q2,answer1Q3,answer2Q3,answer3Q3;
	private String answer1Q1text,answer1Q2text,answer2Q2text,answer1Q3text,answer2Q3text,answer3Q3text;
	
	@Before
	public void setup() throws NoDataBaseConnectionException, UnknownHostException {

		mockMvc =  MockMvcBuilders.webAppContextSetup(wac).build();
		//Users
		cecile = new User("Cecile D.", "pwd");
		soraya = new User("Soraya B.", "pwd");
		damien = new User("Damien D.", "pwd");
		maxence = new User("Maxence L.","pwd");
		//Question1
		titleQuestion1 	="humour noir";
		textQuestion1 	="L'inventeur du mot 'Tumeur' voulait-il faire de l'humour noir ?";
		answer1Q1text 	= "No comment";
		//Question2
		titleQuestion2 	="Bored";
		textQuestion2 	= "Faut-il s'armer de patience pour tuer le temps ?";
		answer1Q2text 	= "Shut up";
		answer2Q2text 	= "A defaut de tuer le temps, travaille!";
		//Question3
		titleQuestion3 	= "Windows";
		textQuestion3 	= "Pourquoi la fonction 'ajout/suppression de programmes' sous Windows ne sert elle qu'a supprimer les programmes?";
		answer1Q3text 	= "Parceque c'est windows";
		answer2Q3text	= "Solution : quitte windows!";
		answer3Q3text   = "Un windows qui ne plante pas, c'est un windows ou les buggs ont plante";
		//Questions
		question1 = new Question(cecile,titleQuestion1,textQuestion1);
		question2 = new Question(damien,titleQuestion2,textQuestion2);
		question3 = new Question(soraya,titleQuestion3,textQuestion3);
		
		//Answers
		answer1Q1 = new Answer(damien,answer1Q1text,question1);
		answer1Q2 = new Answer(cecile,answer1Q2text,question2);
		answer2Q2 = new Answer(maxence,answer2Q2text,question2);
		answer1Q3 = new Answer(damien,answer1Q3text,question3);
		answer2Q3 = new Answer(soraya,answer2Q3text,question3);
		answer3Q3 = new Answer(maxence,answer3Q3text,question3);		
		
		//Add answers
		question1.addAnswer(answer1Q1);
		question2.addAnswer(answer1Q2);
		question2.addAnswer(answer2Q2);
		question3.addAnswer(answer1Q3);
		question3.addAnswer(answer2Q3);
		question3.addAnswer(answer3Q3);

		//Erase the database 
		Mongo m;
		m = new Mongo();

		for (String s : m.getDatabaseNames()) {
			m.dropDatabase(s);
		}
		userDB = new UserDaoMongo();

		userDB.insert(cecile);
		userDB.insert(soraya);
		userDB.insert(damien);
		userDB.insert(maxence);
		
		questionDB = new QuestionDaoMongo();

		questionDB.insert(question1);
		questionDB.insert(question2);
		questionDB.insert(question3);
	}


	@After
	public void tearDown() throws UnknownHostException {
		
		/*Mongo m;

		m = new Mongo();
		

		for (String s : m.getDatabaseNames()) {
			m.dropDatabase(s);
		}*/
	}

	//@Override
	@Test
	public void test() throws Exception {
	
		//TEST GET /questions
		
			this.mockMvc.perform(get("/questions").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$[*]._id",
						hasItems(question1.getId(),question2.getId(),question3.getId())))
				.andExpect(jsonPath("$[*].text",
						hasItems(question1.getText(),question2.getText(),question3.getText())))
				.andExpect(jsonPath("$[*].title",
						hasItems(question1.getTitle(),question2.getTitle(),question3.getTitle())))
				.andExpect(jsonPath("$[*].AuthorId",
						hasItems(question1.getAuthorId(),question2.getAuthorId(),question3.getAuthorId())))
				.andExpect(jsonPath("$[*].AuthorPseudo",
						hasItems(question1.getAuthorPseudo(),question3.getAuthorPseudo(),question3.getAuthorPseudo())))
				.andExpect(jsonPath("$[*].answers[*].text",
						hasItems(answer1Q1.getText(),answer1Q2.getText(),answer2Q2.getText(),answer1Q3.getText(),answer2Q3.getText(),answer3Q3.getText())))
				.andExpect(jsonPath("$[*].answers[*]._id",
						hasItems(answer1Q1.getId(),answer1Q2.getId(),answer2Q2.getId(),answer1Q3.getId(),answer2Q3.getId(),answer3Q3.getId())))
				.andExpect(jsonPath("$[*].answers[*].AuthorId",
						hasItems(answer1Q1.getAuthorId(),answer1Q2.getAuthorId(),answer2Q2.getAuthorId(),answer1Q3.getAuthorId(),answer2Q3.getAuthorId(),answer3Q3.getAuthorId())))
				.andExpect(jsonPath("$[*].answers[*].AuthorPseudo",
						hasItems(answer1Q1.getAuthorPseudo(),answer1Q2.getAuthorPseudo(),answer2Q2.getAuthorPseudo(),answer1Q3.getAuthorPseudo(),answer2Q3.getAuthorPseudo(),answer3Q3.getAuthorPseudo())))
				.andExpect(jsonPath("$[*].answers[*].refQuestion",
						hasItems(answer1Q1.getRefQuestion(),answer1Q2.getRefQuestion(),answer2Q2.getRefQuestion(),answer1Q3.getRefQuestion(),answer2Q3.getRefQuestion(),answer3Q3.getRefQuestion())));
			
		//TEST GET /questions/{id} 
		
		this.mockMvc.perform(get("/questions/"+question1.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print())
			.andExpect(jsonPath("$._id").value(question1.getId()))
			.andExpect(jsonPath("$.text").value(question1.getText()))
			.andExpect(jsonPath("$.title").value(question1.getTitle()))
			.andExpect(jsonPath("$.AuthorId").value(question1.getAuthorId()))
			.andExpect(jsonPath("$.AuthorPseudo").value(question1.getAuthorPseudo()))
			.andExpect(jsonPath("$.answers[*].text").value(answer1Q1.getText()))
			.andExpect(jsonPath("$.answers[*]._id").value(answer1Q1.getId()))
			.andExpect(jsonPath("$.answers[*].AuthorId").value(answer1Q1.getAuthorId()))
			.andExpect(jsonPath("$.answers[*].AuthorPseudo").value(answer1Q1.getAuthorPseudo()))
			.andExpect(jsonPath("$.answers[*].refQuestion").value(answer1Q1.getRefQuestion()));
		
		///TEST POST /users
		
		Question question4 = new Question(maxence,"testTitle","testText");
		question4.setId(null);

		this.mockMvc.perform(post("/questions")
			.content(question4.toJsonString().getBytes())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
		
//		Iterable<User> users = userDB.getSome("{title: 'testTitle'}");
//		assertThat(users.iterator().hasNext(),is(true));
		
		//TEST PUT /users 
		/*
		damien.setUserPseudo("Lutin DD");
		
		this.mockMvc.perform(put("/users")
			.content(damien.toJsonString().getBytes())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
		
		 assertThat(userDB.getOne(new ObjectId(damien.getUserId())),equalTo(damien));
		
		//DELETE /users/{id}
		
		this.mockMvc.perform(delete("/users/"+soraya.getUserId())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
		
		 assertThat(userDB.getOne(new ObjectId(soraya.getUserId())),is(nullValue()));
		 
		
		//TODO a Bouger dans un autre test
		//GET users/{id}/questions
		//Retourne les questions postees par l’utilisateur correspondant a l’id

		//TODO a Bouger dans un autre test
		//GET users/{id}/answers
		//Retourne les reponses[a] postees par l’utilisateur correspondant a l’id
		
		////TODO test Erreur
		 * */
		 
	}
}



