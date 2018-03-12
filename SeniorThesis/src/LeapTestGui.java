import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Vector;

public class LeapTestGui extends JFrame implements ActionListener {
	boolean rightHandVisible;
	boolean leftHandVisible;
	JTextField LHVisibleText;//hold the true or false text for lft hand
	JTextField RHVisibleText;//right hand.
	// vector positions of the finger tips.
	Vector fingerPos0;
	Vector fingerPos1;
	Vector fingerPos2;
	Vector fingerPos3;
	Vector fingerPos4;
	//variables for the text areas
	JTextArea textArea0;
	JTextArea textArea1;
	JTextArea textArea2;
	JTextArea textArea3;
	JTextArea textArea4;
	
	//finger extension color boxes
	JButton fingerButton1;
	JButton fingerButton2;
	JButton fingerButton3;
	JButton fingerButton4;
	JButton fingerButton5;
	
	
	LeapTestGui()
	{
		//general gui setting stuff.
		super("Leap Hand Data");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 300, 400,200);
		leftHandVisible = false;
		rightHandVisible = false;
		//initially setting them to null.
		fingerPos0 = null;
		fingerPos1 = null;
		fingerPos2 = null;
		fingerPos3 = null;
		fingerPos4 = null;
		//initially setting them to null
		textArea0 = null;
		textArea1 = null;
		textArea2 = null;
		textArea3 = null;
		textArea4 = null;
		
		//calls the function to make the gui contents originally.
		createLayout();
		
	}
	private void createLayout()
	{
		//creates the main panel. and sets it to be the main content pane
		//also uses a border an a borderLayout
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		//panel that will hold the hand visible labels and data.
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		//making labels
		JLabel rightHandLabel = new JLabel("Right Hand Visible");
		JLabel leftHandLabel = new JLabel("Left Hand Visible");
		JLabel fingerExtensionLabel = new JLabel("finger extension notifiers");
		
		//right hand text field stuff
		RHVisibleText = new JTextField();
		RHVisibleText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		RHVisibleText.setEditable(false);
		RHVisibleText.setText(booleanToString(rightHandVisible));
		

		//left hand text field stuff
		LHVisibleText = new JTextField();
		LHVisibleText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		LHVisibleText.setEditable(false);
		LHVisibleText.setText(booleanToString(leftHandVisible));
		
		//extended finger button / panel setup
		JPanel fingerExtensionPanel = new JPanel();
		fingerExtensionPanel.setLayout(new BoxLayout(fingerExtensionPanel, BoxLayout.X_AXIS));
		fingerButton1 = new JButton();
		fingerButton2 = new JButton();
		fingerButton3 = new JButton();
		fingerButton4 = new JButton();
		fingerButton5 = new JButton();
		//setting color
		fingerButton1.setBackground(Color.WHITE);
		fingerButton2.setBackground(Color.WHITE);
		fingerButton3.setBackground(Color.WHITE);
		fingerButton4.setBackground(Color.WHITE);
		fingerButton5.setBackground(Color.WHITE);
		//adding to panel
		fingerExtensionPanel.add(fingerButton1);
		fingerExtensionPanel.add(fingerButton2);
		fingerExtensionPanel.add(fingerButton3);
		fingerExtensionPanel.add(fingerButton4);
		fingerExtensionPanel.add(fingerButton5);
		
		
		//adding the objects to the panel
		leftPanel.add(rightHandLabel);
		leftPanel.add(RHVisibleText);
		leftPanel.add(leftHandLabel);
		leftPanel.add(LHVisibleText);
		leftPanel.add(fingerExtensionLabel);
		leftPanel.add(fingerExtensionPanel, BorderLayout.EAST);
		contentPane.add(leftPanel,BorderLayout.WEST);
		
		//adding the finger position panel stuff.
		JPanel fingerDataPanel = new JPanel();
		fingerDataPanel.setLayout(new BoxLayout(fingerDataPanel, BoxLayout.Y_AXIS));
		for(int i = 0; i < 5; i++)
		{

			String fingerName = "null";
			if(i == 0)
			{
				fingerName = "Thumb" ;
				textArea0 = new JTextArea();
				textArea0.setEditable(false);
				fingerDataPanel.add(textArea0);
			}
			else if(i == 1)
			{
				fingerName = "Pointer";
				textArea1 = new JTextArea();
				textArea1.setEditable(false);
				fingerDataPanel.add(textArea1);
				
			}
			else if(i == 2)
			{
				fingerName = "Middle";
				textArea2 = new JTextArea();
				textArea2.setEditable(false);
				fingerDataPanel.add(textArea2);
			} 
			else if(i == 3)
			{
				fingerName = "Ring";
				textArea3 = new JTextArea();
				textArea3.setEditable(false);
				fingerDataPanel.add(textArea3);
			} 
			else if(i == 4)
			{
				fingerName = "Pinky";
				textArea4 = new JTextArea();
				textArea4.setEditable(false);
				fingerDataPanel.add(textArea4);
			} 
			fingerName = fingerName + " position";
			JLabel currentFingerLabel = new JLabel(fingerName);
			fingerDataPanel.add(currentFingerLabel);
			
		}
		
		contentPane.add(fingerDataPanel, BorderLayout.EAST);
		
		
	}
	private String vectorToString(Vector v)
	{
		String retval = "";
		//turns a vector into a string format i need. as long as it isnt null
		if(v != null)
		{
			retval = retval + "X = " + v.getX();
			retval = retval + " Y = " + v.getY();
			retval = retval + " Z = " + v.getZ();
		}
		log("string from vector: "+ retval);
		return retval;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	private String booleanToString(boolean bool)
	{
		if(bool == true) 
		{
			return "true";
		}
		else
		{
			return "false";
		}
	}
	public void UpdateVector(int fingerNum, Vector update)
	{
		if(fingerNum == 0)
		{
			fingerPos0 = update;
			textArea0.setText( vectorToString(update));
		}
		else if(fingerNum == 1)
		{
			fingerPos1 = update;
			textArea1.setText( vectorToString(update));
		}
		else if(fingerNum == 2)
		{
			fingerPos2 = update;
			textArea2.setText( vectorToString(update));
		}
		else if(fingerNum == 3)
		{
			fingerPos3 = update;
			textArea3.setText( vectorToString(update));
		}
		else if(fingerNum == 4)
		{
			fingerPos4 = update;
			textArea4.setText( vectorToString(update));
		}
		
	}
	public void updateExtension(int fingerNum, boolean extended)
	{
		if(fingerNum == -1)
		{
			fingerButton1.setBackground(Color.WHITE);
			fingerButton2.setBackground(Color.WHITE);
			fingerButton3.setBackground(Color.WHITE);
			fingerButton4.setBackground(Color.WHITE);
			fingerButton5.setBackground(Color.WHITE);
		}
		if(fingerNum == 0)
		{
			if(extended)
			{
				fingerButton1.setBackground(Color.BLACK);
			}
			else
			{
				fingerButton1.setBackground(Color.WHITE);
			}
			
			
		}
		else if(fingerNum == 1)
		{
			if(extended)
			{
				fingerButton2.setBackground(Color.BLACK);
			}
			else
			{
				fingerButton2.setBackground(Color.WHITE);
			}
		}
		else if(fingerNum == 2)
		{
			if(extended)
			{
				fingerButton3.setBackground(Color.BLACK);
			}
			else
			{
				fingerButton3.setBackground(Color.WHITE);
			}
		}
		else if(fingerNum == 3)
		{
			if(extended)
			{
				fingerButton4.setBackground(Color.BLACK);
			}
			else
			{
				fingerButton4.setBackground(Color.WHITE);
			}
		}
		else if(fingerNum == 4)
		{
			if(extended)
			{
				fingerButton5.setBackground(Color.BLACK);
			}
			else
			{
				fingerButton5.setBackground(Color.WHITE);
			}
		}
	}
	public void handVisibilityUpdate(HandList hl)
	{
		log("in hand visibility");
		for(Hand h: hl )
		{
			if(h != null && h.isValid())
			{
				log("found valid hand for visibility");
				if(h.isRight())
				{
					log("found right hand");
					rightHandVisible = true;
					RHVisibleText.setText(booleanToString(rightHandVisible));
				}
				if(h.isLeft())
				{
					log("log found left hand");
					leftHandVisible = true;
					LHVisibleText.setText(booleanToString(leftHandVisible));
				}
			}
		}
		if(hl.count() == 1)
		{
			log("one hand found");
			if(hl.get(0).isRight())
			{
				leftHandVisible = false;
				LHVisibleText.setText(booleanToString(leftHandVisible));
			}
			else
			{
				rightHandVisible = false;
				RHVisibleText.setText(booleanToString(rightHandVisible));
			}
		}
		if(!hl.get(0).isValid()&& !hl.get(1).isValid())
		{
			log("no hands found");
			rightHandVisible = false;
			leftHandVisible = false;
			LHVisibleText.setText(booleanToString(leftHandVisible));
			RHVisibleText.setText(booleanToString(rightHandVisible));
		}
	}
	private static void log(String s)
	{
		 System.out.println(s);
	}
}
