
import arduino.*;

public class ArduinoConnection
{
	
	Arduino arduino;
	public ArduinoConnection()
	{
		arduino = new Arduino("COM5", 9600); //enter the port name here, and ensure that Arduino is connected, otherwise exception will be thrown.
	}
	
	public void write(char c)
	{
		arduino.openConnection();
		arduino.serialWrite(c);
		arduino.closeConnection();
	}
	public void write(String s)
	{
		arduino.openConnection();
		for(char c: s.toCharArray())
		{
			arduino.serialWrite(c);
		}
		arduino.closeConnection();
	}
	
	
}