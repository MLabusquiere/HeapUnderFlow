package fr.esiea.pair.model;

import org.bson.types.ObjectId;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Post extends model {

	@JsonProperty("AuthorId")
	protected ObjectId authorId;
	@JsonProperty("AuthorPseudo")
	protected String 	authorPseudo;

	@JsonProperty("text")
	protected String text = null;

	public Post() { }

	public Post(User author, String text)	{
		this.id		= new ObjectId();
		this.authorId 	= 	new ObjectId(author.getId());
		this.authorPseudo = 	author.getPseudo();

		this.text		=	text;
	}
	
	public String getAuthorId() {
		return authorId.toString();
	}
	
	public void setAuthorId(String authorId) {
		this.authorId = new ObjectId(authorId);
	}

	public String getAuthorPseudo() {
		return authorPseudo;
	}

	public void setAuthorPseudo(String authorPseudo) {
		this.authorPseudo = authorPseudo;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


}