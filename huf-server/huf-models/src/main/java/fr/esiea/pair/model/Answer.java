package fr.esiea.pair.model;

import org.bson.types.ObjectId;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Answer extends Post {
	
	@JsonProperty("refQuestion")
	ObjectId refQuestion;

	public Answer(){
		super();
	}

	public Answer(User user, String text,Question question) {
		super(user, text);
		refQuestion	= new ObjectId(question.getId());
	}

	
	public String getRefQuestion() {
		return refQuestion.toString();
	}

	public void setRefQuestion(String refQuestion) {
		this.refQuestion = new ObjectId(refQuestion);
	}

	@Override
	public String toString() {
		return "Answer [userId=" + getAuthorId() 
				+ ", userPseudo=" + getAuthorPseudo()
				+ ", text=" + getText() +"]";
	}

}
