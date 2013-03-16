package fr.esiea.pair.model;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class model {
	
	@JsonProperty("_id")
    protected ObjectId id;

	public String getId() {
    	
    	if(this.id == null)	{
    		return null;
    	}
    	
        return id.toString();
    
    }

    public void setId(String id) {
    		
		if(id==null)
			this.id=null;
		else
			this.id = new ObjectId(id);
    }
	
	public String toJsonString() throws JsonProcessingException	{
    	
    	String Data=null;
    	ObjectMapper mapper= new ObjectMapper();
		
    	Data = mapper.writeValueAsString(this);

    	return Data;
	}
	
	@Override
	public boolean equals(Object obj) {
		model objModel= (model) obj;
		if(objModel == null)
			return false;
		if(objModel.getId().equals(this.getId()))
			return true;
		return true;
	}

}