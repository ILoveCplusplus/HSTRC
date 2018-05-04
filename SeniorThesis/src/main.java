import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.firmata4j.Pin;
import org.firmata4j.Pin.Mode;
import org.firmata4j.firmata.FirmataDevice;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

public class main {
	
	static HashMap<String, Character> signalMap; 
	static HashMap<String, Integer> sleepMap;

    public static void main(String[] args) throws IOException {
    	//the time it waits between each frame pull (update)
    	populateMap();
    	int sleepTime = 1000;
    	
    	boolean shouldRun = true;
    	LeapTestGui gui = new LeapTestGui();
    	gui.setVisible(true);
    	HandHandler leap = new HandHandler();
    	ArduinoConnection ACT = null;
		
		ACT = new ArduinoConnection();
		//map that will hold hand sign to signal relations
		
		
		
    	
		//ACT.listPorts();
    	while(shouldRun && gui != null && gui.isVisible() && !leap.isConnected())
    	{
    		
    		log("waiting for a leap connection.");
    		mySleep(250);
    	}
    	//let us know if the leap is connected.
    	if(leap.isConnected())
    	{
    		log("Leap device seen!");
    	}
    	//while we have a leap connection update the gui with information.
    	while(leap.isConnected())
    	{
    		updateGUI(gui,leap.frame());
    		if(leap.handIsVisible())
    		{
    			String handData = leap.getBinaryHand();
    			
    			if(handData != null && signalMap.get(handData) != null)
    			{
    				char iRData = signalMap.get(handData);
    				log("sending code" + Integer.toHexString((int)iRData));
    				log("sending signal: "+ signalMap.get(handData));
            		ACT.write((iRData));
            		ACT.write((char)0xa90);
            		mySleep(sleepMap.get(handData));
    			}
    		}
    		else
    		{
    			log("no hand, sleeping for "+ (sleepTime /1000) + "second(s)");
    			mySleep(sleepTime);
    		}
    		
    	}
    }

	private static void populateMap() {
		// TODO Auto-generated method stub
		signalMap = new HashMap<String,Character>();
		sleepMap = new HashMap<String, Integer>();
		//power on/off
		String onOffCode = "ttttt";
		signalMap.put(onOffCode, 'a');
		sleepMap.put(onOffCode, 5000);
		
		//volume down
		String volumeDownCode = "fttff";
		signalMap.put(volumeDownCode, 'b');
		sleepMap.put(volumeDownCode, 100);
		
		//volume up
		String volumeUpCode = "ftfff";
		signalMap.put(volumeUpCode, 'c');
		sleepMap.put(volumeUpCode, 100);
		
		//enter
		String enterCode = "ftfft";
		signalMap.put(enterCode, 'd');
		sleepMap.put(enterCode, 1000);
		
		
		//return
		String returnCode = "tffff";
		signalMap.put(returnCode, 'e');
		sleepMap.put(returnCode, 1000);
		
		//input
		String inputCode = "ttfft";
		signalMap.put(inputCode, 'f');
		sleepMap.put(inputCode, 700);
	}

	//used to update the information on the gui
    private static void updateGUI(LeapTestGui gui, Frame frame) {
    	if(frame.isValid())
    	{
    		log("in update gui");
    		// TODO Auto-generated method stub
        	Hand firstHand = null;
        	//getting the hand that is closest to the device
        	firstHand = frame.hands().frontmost();
        	//making a hand handler object that will be used to get data from a frame. finger position / distance.
        	int i = 0;
 
        	//steps through the fingers on the hand and updates the corresponding gui vector 
        	if(firstHand.isValid())
        	{
        		log("we see a hand: " + firstHand.toString());
        		gui.handVisibilityUpdate(frame.hands());
        		for(Finger f : firstHand.fingers())
            	{
        			//log("Finger: "+f.toString());
            		gui.UpdateVector(i, f.stabilizedTipPosition());
            		gui.updateExtension(i, f.isExtended());
            		i++;
            	}
        	}
        	else
        	{
        		gui.updateExtension(-1, false);
        	}
    	}
    	
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
	public static boolean[] stringToBooleanArray(String s)
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
		log("printing b array from tanslation:");
		printBArray(retval);
		return retval;
	}
	private static void log(String s)
	{
		 System.out.println(s);
	}
	private static void printBArray(boolean[] data) {
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
	
}
