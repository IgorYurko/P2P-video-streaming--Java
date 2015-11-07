import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;



class app
{
	JLabel l1,l2,l3;
	JButton button,b;
	JTextField t1,t2,t3;
	 
	
	public static void main1()
	{
		app gui=new app();
		gui.go();
	}

	public void go()
	{
		JFrame frame=new JFrame("Peer-Join");
		JPanel panel=new JPanel();
		//panel.setBackground(color.darkGray);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		button=new JButton("JOIN");
		//b=new JButton("REQUEST");
		button.addActionListener(new LabelListener());
		//b.addActionListener(new LabelListener());
		l1=new JLabel("AVAILABLE VIDEOS	:");
		l2=new JLabel("FULL VIDEO	:");
		//l3=new JLabel("PART VIDEO	:");
		t1=new JTextField(20);
		t2=new JTextField(20);
		//t3=new JTextField(20);
		panel.add(button);
		
		panel.add(l1);
		panel.add(t1);
		
		panel.add(l2);
		panel.add(t2);

		//panel.add(l3);
		//panel.add(t3);
		
		/*JRadioButton b1 = new JRadioButton("FULL VIDEO");
   		JRadioButton b2 = new JRadioButton("PART VIDEO");

    		ButtonGroup group = new ButtonGroup();
    		group.add(b1);
    		group.add(b2);
		button.addActionListener();*/
	
		frame.getContentPane().add(BorderLayout.WEST, panel);
		frame.setSize(500,250);
		frame.setVisible(true);
	}
	class LabelListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
		String	msg="join";
		String videocontent=t1.getText();
		String amt=t2.getText();
		//String parts=t3.getText();
		//System.out.println(videocontent+"  "+amt);
sendingpeer2.appcontent(videocontent,amt);
			
		}
		
	}	

}
