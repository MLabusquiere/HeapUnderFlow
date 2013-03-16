package fr.esiea.pair.model;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Question extends Post {

	@JsonProperty("title")
	private String title;
	@JsonProperty("answers")
	private ArrayList<Answer> answers;

	@SuppressWarnings("unused") //use by serialization
	private Question()	{
	}

	public Question(User user, String title, String text) {
		super(user, text);
		this.title = title;
		this.answers	= 	new ArrayList<Answer>();
	}

	public Question(String authorId, String authorPseudo, String title,String text)	{
		this.authorId = new ObjectId(authorId);
		this.authorPseudo = authorPseudo;
		this.title = title;
		this.text = text;
		new ArrayList<Answer>();
	}

	/*** Getters ***/

	public String getTitle() {
		return title;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}


	/*** Setters ***/

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}


	/*** Methods ***/

	public void addAnswer(Answer answer)	{
		answers.add(answer);
	}
    
	@Override
	public String toString() {
		return "Question [userId=" + getAuthorId() 
				+ ", userPseudo=" + getAuthorPseudo()
				+ ", title=" + getTitle() 
				+", text=" + getText() 
				+", answers=" 
				+ getAnswers() + "]";
	}


}
