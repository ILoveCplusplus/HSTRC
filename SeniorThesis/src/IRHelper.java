import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jssc.SerialPortList;

import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.Pin.Mode;
import org.firmata4j.firmata.FirmataDevice;


public class IRHelper {

	//this is the connection to the arduino on the latte panda
	FirmataDevice device;
	String portName;
	//Pin ourPin;
	String ourPinName = "org.firmata4j.firmata.FirmataPin@c2e1f26";
	
	public IRHelper()
	{
		setPortName();
		
		device = new FirmataDevice(portName);
		
		try {
			device.start();
			device.ensureInitializationIsDone();
			log("arduino is ready");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			log("device start failed");
			e.printStackTrace();
		}
		printPins();
	}
	public boolean isConnected()
	{
		boolean retval = false;
		if( device != null && device.isReady())
		{
			retval = true;
		}
		return retval;
	}
	public FirmataDevice getDevice()
	{
		return device;
	}
	private void setPortName()
	{
		String[] portNames = SerialPortList.getPortNames();
		if (portNames.length == 0) 
		{
		    log("There are no serial-ports :( You can use an emulator, such ad VSPE, to create a virtual serial port.");
		}
		else
		{
			log("port names:");
			for(String current : portNames)
			{
				log(current);
			}
			portName = portNames[1];
		}
	}
	public void setPin(int pinNum, long data,int time) throws IllegalArgumentException, IOException
	{
		if(device.isReady())
		{
			
			Pin pin = (Pin)device.getPins().toArray()[pinNum];
			if(pin.getMode() != Mode.OUTPUT)
			{
				
				log("attempting to set pin to output mode");
				pin.setMode(Mode.OUTPUT);
			}
			//setting the value
			pin.setValue(data);
			mySleep(time);
			pin.setValue(0);
				
			
			
			
		}
			
	}
	public void sendDataArray(long[] data)
	{
		log("sending data array");
		for(long l : data)
		{
			log("sending: "+ l);
			try {
				setPin(14,l,100);
			} catch (IllegalArgumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		log("data array sent");
	}
	private static void log(String s)
	{
		 System.out.println(s);
	}
	public Object[] getPins()
	{
		return device.getPins().toArray();
	}
	public void printPins()
	{
		for(Pin p : device.getPins())
		{
			log(p.toString());
		}
	}
	private static void mySleep(int i) {
    	// TODO Auto-generated method stub
    	try {
    		TimeUnit.MICROSECONDS.sleep(i);
    	} catch (InterruptedException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
	public long[] getDecimalArrayFromHex(String hexData)
	{
		String hexDataArray[] = hexData.split(" ");
    	long returnArray[] = new long[hexDataArray.length];
    	int i = 0;
    	for(String h : hexDataArray)
    	{
    		returnArray[i] = (long) Integer.parseInt(h, 16);
    		i++;
    	}
    	return returnArray;
	}
	public void exit()
	{
		try {
			device.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log("device could not be stopped");
			e.printStackTrace();
		}
	}
}
