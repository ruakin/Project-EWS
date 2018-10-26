package de.ews.server.communication;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * 
 * @author Jenne Hilberts
 *
 *         class for interpreting a json formated String given by an
 *         AppMessenger
 */
public class AppInterpret implements IInterpret {

	
	private AppMessenger am;
	
	/**
	 * 
	 * @param am
	 * 		delivers the json formated String
	 */
	public AppInterpret(AppMessenger am)
	{
		this.am = am;
	}
	
	/**
	 * 
	 * @return return an Statement which knows what to do
	 */
	public IStatement getStatement() throws NullPointerException {
		
		JsonReader reader = Json.createReader(new StringReader(am.getNextMessage()));
		JsonObject obj = reader.readObject();
		IStatement sret = null;
		
		switch (obj.getString("type"))
		{
		case "station-select":
			sret = new SelectStationStatement(obj.getString("name"));
			break;
		case "insert-measure":
			sret = new InsertMeasureStatement(
					obj.getInt("hospid"),
					obj.getInt("systolic"),
					obj.getInt("diastolic"),
					(float)obj.getJsonNumber("temp").doubleValue(),
					obj.getInt("breathrate"),
					obj.getString("time"));
		}
		reader.close();
		return sret;
	}
}
