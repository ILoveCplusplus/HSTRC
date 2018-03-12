import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;

public class HandHandler {
	Frame currentFrame;
	//should i add a leap device to the variables
	HandHandler(Frame frame)
	{
		currentFrame = frame;
	}
	public void update(Frame frame)
	{
		currentFrame = frame;
	}
	//returns the position of a given digit (finger) (thumb:0 pointer:1 middle:2 ring:3 pinky:4)
	public Vector getFingerTipPosition(int Digit)
	{
		Vector retval = null;
		//setting the return val to the finger tip position of the first visible hand.
		retval = currentFrame.hand(0).finger(Digit).tipPosition();
		return retval;
	}
	
	//will take the hand and tunr it into a binary value.d 0 if a finger is down, and 1 if it is extended
	public String getBinary()
	{
		String retval = "";
		FingerList fingers = currentFrame.hands().frontmost().fingers();
		if(fingers != null && !fingers.isEmpty())
		{
			//going through the hand to build the binary.
			for( Finger f  : fingers)
			{
				if(f.isExtended())
				{
					retval = retval + "1";
				}
				else
				{
					retval = retval + "0";
				}
			}
		}
		else
		{
			retval = "no fingers found";
		}
		
		return retval;
	}
	

}
