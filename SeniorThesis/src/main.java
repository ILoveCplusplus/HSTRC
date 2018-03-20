import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.firmata4j.Pin;
import org.firmata4j.Pin.Mode;
import org.firmata4j.firmata.FirmataDevice;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

public class main {

    public static void main(String[] args) {
    	//the time it waits between each frame pull (update)
    	int sleepTime = 5000;
    	IRHelper helper = new IRHelper();
    	boolean shouldRun = true;
    	LeapTestGui gui = new LeapTestGui();
    	gui.setVisible(true);
    	Controller leap = new Controller();
    	FirmataDevice myArduino = helper.getDevice();
    	setOnExit(helper);
    
    	while(shouldRun && gui != null && gui.isVisible() && !leap.isConnected())
    	{
    		
    		if(myArduino == null)
    		{
    			log("device not connected");
    		}
    		else
    		{
    			try {
					for(int i = 0; i < 10000; i++)
					{
						if(i%100 == 0)
						{
							log(Integer.toString(i));
						}
						helper.setPin(14,i, 20);
					}
    				
					
					
				} catch (IllegalArgumentException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
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
	
	private static void setOnExit(final IRHelper helper)
	{
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
			public void run(){
				helper.exit();
			}
		}));
	}
}
