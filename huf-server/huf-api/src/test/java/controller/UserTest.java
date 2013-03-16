package controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.net.UnknownHostException;


import org.bson.types.ObjectId;
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

import fr.esiea.pair.dao.database.UserDao;
import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.dao.mongo.UserDaoMongo;
import fr.esiea.pair.model.User;
import fr.esiea.pair.model.model;


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
public class UserTest {//implements ControllerTest{

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	UserDao userDB;
	private User cecile,damien,soraya;

	@Before
	public void setup() throws NoDataBaseConnectionException, UnknownHostException {

		mockMvc =  MockMvcBuilders.webAppContextSetup(wac).build();

		cecile = new User("Cecile D.", "pwd");
		soraya = new User("Soraya B.", "pwd");
		damien = new User("Damien D.", "pwd");
		//question = new Question(damien, "titrequestion", "hey");
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

	}


	@After
	public void tearDown() throws UnknownHostException {
		
		Mongo m;

		m = new Mongo();
		

		for (String s : m.getDatabaseNames()) {
			m.dropDatabase(s);
		}
	}

	//@Override
	@Test
	public void test() throws Exception {
	
		//TEST GET /users
		
		this.mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.[*].Pseudo", hasItems(damien.getPseudo(), soraya.getPseudo(),cecile.getPseudo() )))
			.andExpect(jsonPath("$.[*].Password", hasItems(damien.getPassword(), soraya.getPassword(),cecile.getPassword() )))	
			.andExpect(jsonPath("$.[*]._id", hasItems(damien.getId(), soraya.getId(),cecile.getId() )));
			
		//TEST GET /users/{id} 
		
		this.mockMvc.perform(get("/users/"+damien.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.Pseudo").value(damien.getPseudo()))
			.andExpect(jsonPath("$._id").value(damien.getId()))
			.andExpect(jsonPath("$.Password").value(damien.getPassword()));
		
		///TEST POST /users
		
		model maxence = new User("Maxence L.","pwd");
		maxence.setId(null);

		this.mockMvc.perform(post("/users")
			.content(maxence.toJsonString().getBytes())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
		
		Iterable<User> users = userDB.getSome("{Pseudo: 'Maxence L.'}");
		assertThat(users.iterator().hasNext(),is(true));
		
		//TEST PUT /users 
		
		damien.setPseudo("Lutin DD");
		
		this.mockMvc.perform(put("/users")
			.content(damien.toJsonString().getBytes())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
		
		 assertThat(userDB.getOne(new ObjectId(damien.getId())),equalTo(damien));
		
		//DELETE /users/{id}
		
		this.mockMvc.perform(delete("/users/"+soraya.getId())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
		
		 assertThat(userDB.getOne(new ObjectId(soraya.getId())),is(nullValue()));
		 
		
		//TODO a Bouger dans un autre test
		//GET users/{id}/questions
		//Retourne les questions postées par l’utilisateur correspondant à l’id

		//TODO a Bouger dans un autre test
		//GET users/{id}/answers
		//Retourne les réponses[a] postées par l’utilisateur correspondant à l’id
		
		////TODO test Erreur
		 
	}
}



