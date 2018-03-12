import java.util.concurrent.TimeUnit;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

public class main {

    public static void main(String[] args) {
    	//the time it waits between each frame pull (update)
    	int sleepTime = 100;
    	
    	boolean shouldRun = true;
    	LeapTestGui gui = new LeapTestGui();
    	gui.setVisible(true);
    	Controller leap = new Controller();
    	while(shouldRun && gui != null && gui.isVisible() && !leap.isConnected())
    	{
    		System.out.println("waiting for a leap connection.");
    		mySleep(250);
    	}
    	//let us know if the leap is connected.
    	if(leap.isConnected())
    	{
    		System.out.println("Leap device seen!");
    	}
    	//while we have a leap connection update the gui with information.
    	while(leap.isConnected())
    	{
    		updateGUI(gui,leap.frame());
    		mySleep(sleepTime);
    	}

    	
        System.out.println("Hello, World");
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
        	HandHandler hh = new HandHandler(frame);
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
	private static void log(String s)
	{
		 System.out.println(s);
	}
}
