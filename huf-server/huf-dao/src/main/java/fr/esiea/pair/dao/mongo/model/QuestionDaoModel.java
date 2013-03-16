package fr.esiea.pair.dao.mongo.model;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.esiea.pair.model.Answer;
import fr.esiea.pair.model.Post;
import fr.esiea.pair.model.Question;

public class QuestionDaoModel extends Post{
	@JsonProperty("title")
	private String title;
	@JsonProperty("answersRef")
	private ArrayList<ObjectId> answersRef;

	public QuestionDaoModel() {}

	public QuestionDaoModel(Question data, ArrayList<Answer> answersComplete) {
		if(data.getId()==null)
			this.id	= new ObjectId();
		else
			this.id	= new ObjectId(data.getId());
		this.authorId = new ObjectId(data.getAuthorId());
		this.authorPseudo = data.getAuthorPseudo();

		this.text =	data.getText();
		this.title = data.getTitle();

		this.answersRef = new ArrayList<ObjectId>();
		
		if(!answersComplete.isEmpty()) {
			for(Answer answerComplete: answersComplete)	{
				if(!answersRef.contains(answerComplete.getId())) {
					answersRef.add(new ObjectId(answerComplete.getId()));
				}

			}
		}
	}

	/*** Getters ***/

	public String getTitle() {
		return title;
	}

	public ArrayList<ObjectId> getAnswers() {
		return answersRef;
	}


	/*** Setters ***/

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAnswers(ArrayList<ObjectId> answersRef) {
		this.answersRef = answersRef;
	}


	/*** Methods ***/

	public void addAnswer(ObjectId answerRef)	{
		answersRef.add(answerRef);
	}

	@Override
	public String toString() {
		return "Question [userId=" + getAuthorId() 
				+ ", userPseudo=" + getAuthorPseudo()
				+ ", title=" + getTitle() 
				+", text=" + getText() 
				+", answers=" 
				+ getId() + "]";
	}


}
