package fr.esiea.pair.service.exception.WrongFormatException;

import fr.esiea.pair.service.exception.ControllerException;

@SuppressWarnings("serial")
public abstract class WrongFormatException extends ControllerException {

	protected StringBuilder messageBuilder;

	public void add(String msg)	{

		messageBuilder.append(" & " + msg);
		this.msg = messageBuilder.toString();

	}


	public boolean isThereAnError()	{
		
		if(this.msg.isEmpty())
			return false;
		return true;
		
	}

}