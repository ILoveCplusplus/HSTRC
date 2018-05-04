import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.Set;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;

public class HandHandler {
	Controller leap = null;
	//should i add a leap device to the variables
	HandHandler()
	{
		leap = new Controller();
	}
	
	//will take the hand and tunr it into a binary value.d 0 if a finger is down, and 1 if it is extended
	public String getBinaryHand()
	{
		
		int numChecks = 50;
		String retval = "";
		boolean[][] allData = new boolean[numChecks][5];
		FingerList fingers = null;//currentFrame
		Frame currentFrame = null;
		
		//this loop:
		//gets the current frame, gets the finger data (each finger extended or not)
		//and stores it as an array of 5 booleans, into an array of 100 5 long arrays.
		for(int i = 0; i < numChecks; i++)
		{
			currentFrame = leap.frame();
			boolean[] currData = getFingerArray(currentFrame);
			mySleep(10);
			allData[i] = currData;
		}
		//allData should now be filled with all the hand data.
		//we need to find the most common hand data.
		
		//hashmap to store amount of each data gathered.
		HashMap<String,Integer> dataCountMap = new HashMap<String,Integer>();
		for(boolean[] data : allData)
		{
			String stringData = boolArrayToString(data);
			if(dataCountMap.containsKey(stringData))//dataCountMap.containsKey(data))
			{
				log("already found data");
				log("size: " + dataCountMap.size());
				int updateVal = (dataCountMap.get(stringData) + 1);
				log("update val is: " + updateVal);
				dataCountMap.put(stringData, updateVal );
			}
			else
			{
				log("found new data: ");
				log(stringData);
				dataCountMap.put(stringData,1);
				log("this is right after we put the data in: "+ dataCountMap.get(data));
			}
			
		}
		log("data count map size: " + dataCountMap.size());
		//datacountmap now has all data and how many times it occurs
		String topElement = null;
		int frequency = 1;
		Set<Entry<String, Integer>> entrySet = dataCountMap.entrySet();
		for(Entry<String, Integer> entry : entrySet)
		{
			log("test");
			if(entry.getValue() >= frequency)
			{
				log("found new top elem");
				
				topElement = entry.getKey();
				log("topElement is: " + topElement);
				frequency = entry.getValue();
			}
		}
		//top element should now contain the most frequent hand signal given in 100 tries.ust finished 
		retval = topElement;
		log("PRINTING THE RETVAL: ");
		log("retval:" + retval);
		return retval;
	}
	private void printBArray(boolean[] data) {
		// TODO Auto-generated method stub
		for(boolean b : data)
		{
			if(b)
			{
				log("true");
			}
			else
			{
				log("False");
			}
		}
	}
	public boolean[] stringToBooleanArray(String s)
	{
		boolean[] retval = new boolean[5];
		int i = 0;
		for(char c : s.toCharArray())
		{
			if(i > 4)
			{
				log("WOAH HOWd YOU GET HERE MR 6 FINGERS");
				break;
			}
			if(c == 't')
			{
				retval[i] = true;
			}
			else
			{
				retval[i] = false;
			}
			i++;
		}
		return retval;
	}
	public String boolArrayToString(boolean[] ba)
	{
		String retval = "";
		for(boolean b: ba)
		{
			if(b)
			{
				retval += "t";
			}
			else
			{
				retval += "f";
			}
		}
		return retval;
	}
	boolean handIsVisible()
	{
		boolean retval = false;
		if(leap.frame().hands().frontmost().isValid())
		{
			retval = true;
		}
		return retval;
	}
	
	//input is the current frame from leap device.
	//takes the frame, gets the finger mlist and turns it into an array of booleans.
	private boolean[] getFingerArray(Frame currFrame)
	{
		boolean[] retval = {false,false,false,false,false};
		FingerList fingers = currFrame.hands().frontmost().fingers();
		if(fingers != null && !fingers.isEmpty())
		{
			//iterator
			int i = 0;
			//going through the hand to build the binary.
			for( Finger f  : fingers)
			{
				
				retval[i] = f.isExtended();
				i++;
			}
		}
		
		return retval;
	}
	public boolean isConnected()
	{
		return leap.isConnected();
	}
	public Frame frame()
	{
		return leap.frame();
	}
	private static void log(String s)
	{
		 System.out.println(s);
	}
	private boolean mapHasKey(HashMap<boolean[], Integer> map, boolean[] keyCompare)
	{
		boolean retval = false;
		for(boolean[] b : map.keySet())
		{
			log("printing the dam array");
			printBArray(b);
			if(Arrays.equals(b,keyCompare))
			{
				retval = true;
				break;
			}
			else
			{
				log("the array : ");
				printBArray(b);
				log("is not equal to :");
				printBArray(keyCompare);
				
				
			}
		}
		return retval;
	}
	private static void mySleep(int i) {
    	// TODO Auto-generated method stub
    	try {
    		TimeUnit.MILLISECONDS.sleep(i);
    	} catch (InterruptedException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
}
