package de.ews.server.communication;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Util {

	// Semaphore for synchronizing the IStatement.asynchronousPerform()
	public static final Semaphore SEMAPHORE = new Semaphore(1, true);
	
	// limit values specified in sw-spec 2
	public static final float MIN_TEMP_CHILD = 36.5f;
	public static final float MAX_TEMP_CHILD = 37.5f; 
	public static final int MIN_DIASTOLIC_CHILD = 67;
	public static final int MAX_SYSTOLIC_CHILD = 126;
	public static final int MIN_BREATHRATE_CHILD = 20;
	public static final int MAX_BREATHRATE_CHILD = 30;
	
	public static final float MIN_TEMP_ADULT = 36.3f;
	public static final float MAX_TEMP_ADULT = 37.4f; 
	public static final int MIN_DIASTOLIC_ADULT = 80;
	public static final int MAX_SYSTOLIC_ADULT = 130;
	public static final int MIN_BREATHRATE_ADULT = 12;
	public static final int MAX_BREATHRATE_ADULT = 18;
	
	public static final float MIN_TEMP_SENIOR = 36.3f;
	public static final float MAX_TEMP_SENIOR = 37.4f; 
	public static final int MIN_DIASTOLIC_SENIOR = 80;
	public static final int MAX_SYSTOLIC_SENIOR = 130;
	public static final int MIN_BREATHRATE_SENIOR = 12;
	public static final int MAX_BREATHRATE_SENIOR = 18;
	
	public static LinkedList<FetchItem> fetchList = new LinkedList<FetchItem>();
	
	public static int selectValue(int age, int child, int adult, int senior)
	{
		return age < 18 ? child : (age < 65 ? adult : senior);
	}
	
	public static float selectValue(int age, float child, float adult, float senior)
	{
		return age < 18 ? child : (age < 65 ? adult : senior);
	}
}
