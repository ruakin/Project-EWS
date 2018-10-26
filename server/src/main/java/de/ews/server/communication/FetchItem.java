package de.ews.server.communication;

public class FetchItem {

	private int patientId;
	private int alarm;
	
	public FetchItem(int p, int a)
	{
		this.patientId = p;
		this.alarm = a;
	}
	
	public int getPatientId() {
		return patientId;
	}
	
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	
	public int getAlarm() {
		return alarm;
	}
	
	public void setAlarm(int alarm) {
		this.alarm = alarm;
	}
	
}
