
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
	
<<<<<<< HEAD
	
=======
	/*SerialPort serialPort = null;
	OutputStream outStream;
	InputStream inStream;
	
	public ArduinoConnectionTest() throws NoSuchPortException, PortInUseException
	{
		listPorts();
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("COM5");
		 if ( portIdentifier.isCurrentlyOwned() )
	        {
	            System.out.println("Error: Port is currently in use");
	        }
	        else
	        {
	        	serialPort = (SerialPort) portIdentifier.open("Demo application", 5000);
	        	int baudRate = 9600; // 9600bps
	        	try {
	        	  // Set serial port
	        	  serialPort.setSerialPortParams(
	        	    baudRate,
	        	    SerialPort.DATABITS_8,
	        	    SerialPort.STOPBITS_1,
	        	    SerialPort.PARITY_NONE);
	        	  //setting flow of control is apperantly needed
	        	  serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
	        	  
	        	 outStream = serialPort.getOutputStream();
	        	 inStream = serialPort.getInputStream();
	        	  
	        	} catch (UnsupportedCommOperationException ex) {
	        	  System.err.println(ex.getMessage());
	        	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		 listPorts();
	}
	public SerialPort getSerialPort()
	{
		return serialPort;
	}
	public InputStream getInStream()
	{
		return inStream;
	}
	public OutputStream getOutStream()
	{
		return outStream;
	}
	
	@Override
	public void serialEvent(SerialPortEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void write(int Num) {
		// TODO Auto-generated method stub
		try {
			outStream.write(Num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void listPorts()
    {
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while ( portEnum.hasMoreElements() ) 
        {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            System.out.println(portIdentifier.getName()  +  " - " +  getPortTypeName(portIdentifier.getPortType()) );
        }        
    }
	static String getPortTypeName ( int portType )
    {
        switch ( portType )
        {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }*/
>>>>>>> f51c82cad0fc0f713bc461d7c533cad74f1036a5
}