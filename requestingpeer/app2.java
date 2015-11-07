import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;



class app2
{
	JLabel l1,l2,l3;
	JButton button,b;
	JTextField t1,t2,t3;
	 
	
	public static void main1()
	{app2 gui=new app2();
		gui.go();
	}

	public void go()
	{
		JFrame frame=new JFrame("Video Request");
		JPanel panel=new JPanel();
		//panel.setBackground(color.darkGray);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		button=new JButton("REQUEST");
		//b=new JButton("CANCEL");
		button.addActionListener(new LabelListener());
		//b.addActionListener(new LabelListener1());
		l1=new JLabel("Video Name	:");
		t1=new JTextField(20);
		
		panel.add(button);
		//panel.add(b);
		
		panel.add(l1);
		panel.add(t1);
		
				
		/*JRadioButton b1 = new JRadioButton("FULL VIDEO");
   		JRadioButton b2 = new JRadioButton("PART VIDEO");

    		ButtonGroup group = new ButtonGroup();
    		group.add(b1);
    		group.add(b2);
		button.addActionListener();*/
	
		frame.getContentPane().add(BorderLayout.WEST, panel);
		frame.setSize(500,130);
		frame.setVisible(true);
	}
	class LabelListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
		//String	msg="join";
		String videocontent=t1.getText();
		receivingpeer.appcontent(videocontent);	
		}
		
	}
	
}
