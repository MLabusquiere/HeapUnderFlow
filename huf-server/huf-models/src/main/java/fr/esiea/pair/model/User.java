package fr.esiea.pair.model;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User extends model {


    @JsonProperty("Pseudo")
    private String 	pseudo;
    @JsonProperty("Password")
    private String 	password;

    @SuppressWarnings("unused") //use by serialization
	private User() { }

    public User(String pseudo, String password) {
        this.id 			= 	new ObjectId();
        this.pseudo 	= 	pseudo;
        this.password 	= 	password;

    }

    public User(String pseudo, String password,ObjectId id) {
        this.id		 		= 	id;
        this.pseudo 	= 	pseudo;
        this.password 	= 	password;

    }

    
    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPseudo(String userPseudo) {

        this.pseudo = userPseudo;

    }

    public void setPassword(String userPassword) {

        this.password = userPassword;

    }

    /*** Methods ***/

    @Override
    public String toString() {

        return "User [userId=" + this.id
                + ", userPseudo=" + this.pseudo
                + ", userPassword=" + this.password + "]";
        
    }
  

}