import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class UserInterface extends JFrame{

	private JPanel pvisitor;
	private JPanel pstaff;
	static final int TABSIZE=20;
	private static final String PWD = "COMP421G66";
    private JPasswordField password;
    private String typedPassword;
	
	UserInterface(){
		
		pvisitor = new JPanel();
		pstaff = new JPanel();
		initVisitor();
		initStaff();
		
		JTabbedPane tp=new JTabbedPane();  
		tp.setBounds(0,0,800,800);  
	    tp.add("Visitor",pvisitor);  
	    tp.add("Staff",pstaff);    
	
	    add(tp);  
	    setSize(800,800);  
	    setLayout(null);  
	    setVisible(true);  
	    
	    tp.setFont(new Font("TimesRoman", Font.BOLD, TABSIZE));
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	void initVisitor(){
		
		pvisitor.add(new myButton("Search activities"));
		
	}
	
	void initStaff(){
		// Login button
		JButton login = new JButton("Login");
		login.setFont(new Font("TimesRoman", Font.BOLD, 24));
		login.setBounds(300,500,300,40);
		// Text label
		JLabel txt = new JLabel();
		txt.setText("Please Enter Password :");
		txt.setFont(new Font("TimesRoman", Font.BOLD, 48));
		txt.setBounds(100, 200, 600, 70);
		// Empty label to display password feedback
		JLabel feedback = new JLabel();
		feedback.setBounds(400, 650, 200, 100);
		// Initialize password field
		ActionListener pwdListener = new ActionListener() {
			
	        @Override
	        public void actionPerformed(ActionEvent e) {
	
	            password = (JPasswordField) e.getSource();
	            char [] tempPass = password.getPassword();
	            typedPassword = new String(tempPass);
	
	            if (!typedPassword.equals(PWD)){
	
	                JOptionPane.showMessageDialog(null,
	            "Your password is not correct",
	            "WRONG PASSWORD!",
	            JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    };
	    
	    createPasswordField(pwdListener);
		
		// Add to frame
		pstaff.add(feedback);
		pstaff.add(password);
		pstaff.add(txt);
		pstaff.add(login);    
		pstaff.setSize(300,300);    
		pstaff.setLayout(null);    
		pstaff.setVisible(true);
		// Action listener
		login.addActionListener(pwdListener);      
	}
	
	// Create a password field
    public void createPasswordField(ActionListener pwdListener){

	    password = new JPasswordField(30);
	    password.setBounds(180, 300, 400, 70);
	    password.setEchoChar('*');
	    password.setBackground(Color.white);
	 	password.setFont(new Font("TimesRoman", Font.PLAIN ,36));
	    password.addActionListener(pwdListener);
	}


	
	//the windows displace alternative options 
	//by default in visitor mode
	void entry(){
		
	}
	
	
	//void 
	void searchActivity(){
		
	}
	
	
	void book(){
		
	}
	
	
	
}
