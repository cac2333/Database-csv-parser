package ProjectWithGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;



public class UserInterface extends JFrame implements Runnable{

	private JPanel pvisitor;
	private JPanel pstaff;
	private Database db;
	
	static final int TABSIZE=20;  
	static final int BWIDTH = 250;
	static final int BLENGTH = 40;
	static final int FRAME_LENGTH=800;
	static final int FRAME_WIDTH=800;
	
	
	private static final String PWD = "COMP421G66";
    private JPasswordField password;
    private String typedPassword;
	
    
	UserInterface(Database db){
		
		this.db=db;
		
		pvisitor = new JPanel();
		pstaff = new JPanel();
		initVisitor();
		initStaff();
		
		JTabbedPane tp=new JTabbedPane();  
		tp.setBounds(0,0,FRAME_LENGTH,FRAME_WIDTH);  
	    tp.add("Visitor",pvisitor);  
	    tp.add("Staff",pstaff);    
	
	    add(tp);  
	    setSize(FRAME_LENGTH,FRAME_WIDTH);  
	    setLayout(null);  
	    setVisible(true);  
	    
	    tp.setFont(new Font("TimesRoman", Font.BOLD, TABSIZE));
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	}
	

	
	//creating the first view for visitor 
	
	void initVisitor(){
		
		pvisitor.removeAll();
		pvisitor.setLayout(null);
		
		JButton b1=new JButton("Search Activities");
		
		b1.setBounds(250, 280 , 300, 100);
		b1.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		b1.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				searchActivity();
			}
		});
		
		pvisitor.add(b1);
		
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
		
		//clear the panel
		pvisitor.removeAll();
		
		JLabel msg=new JLabel("Please select a date");
		msg.setFont(new Font("TimesRoman", Font.BOLD, 24));
		msg.setBounds(200,80,350,40);
		
		
		// Year
		
		String[] year=new String[Calendar.getInstance().get(Calendar.YEAR)-2010+1];
		for(int i=2010, k=0; i<=Calendar.getInstance().get(Calendar.YEAR); i++, k++){
			year[k]=i+"";
		}
   
		JComboBox yearBox=new JComboBox(year);
		JLabel yearField=fieldName("Year", 200, 170);
		yearBox.setFont(new Font(yearBox.getFont().getFontName(), Font.PLAIN, 16));
		yearBox.setBounds(300, 170 , 200, 30);

		
		//Month 
		
		String[] month=new String[12];
		for(int i=0; i<12;){
			month[i]=++i+"";
		}

		JComboBox monthBox=new JComboBox(month);
		JLabel monthField=fieldName("Month", 200, 240);
		monthBox.setFont(new Font(monthBox.getFont().getFontName(), Font.PLAIN, 16));
		monthBox.setBounds(300, 240 , 200, 30);
		
		
		//Day
		
		String[] day=new String[31];
		for(int i=0; i<31;){
			day[i]=++i+"";
		}

		JComboBox dayBox=new JComboBox(day);
		JLabel dayField=fieldName("Day", 200, 310);
		dayBox.setFont(new Font(dayBox.getFont().getFontName(), Font.PLAIN, 16));
		dayBox.setBounds(300, 310 , 200, 30);
		
		
		//search
		
		JButton search=new JButton("Search");
		search.setBounds(300, 400 , 200, 50);
		search.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		search.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent arg0) {
	
				displayActivities(yearBox.getSelectedItem().toString()+
						'-'+monthBox.getSelectedItem().toString()
						+'-'+dayBox.getSelectedItem().toString());
			}
		});
		
		
		pvisitor.add(yearBox);
		pvisitor.add(monthBox);
		pvisitor.add(dayBox);
		pvisitor.add(msg);	
		pvisitor.add(dayField);
		pvisitor.add(monthField);
		pvisitor.add(yearField);
		pvisitor.add(search);
		
		//tells Swing this area is dirty
		repaint();
		//recompute the layout
		revalidate();
		
	}
	
	
	private JLabel fieldName(String name, int x, int y){
		JLabel label=new JLabel(name);
		label.setFont(new Font("TimesRoman", Font.BOLD, 16));
		label.setBounds(x,y,100,40);
		return label;
	}
	
	void book(){
		
	}
	
	
	
	
	void displayActivities(String date){
		
		pvisitor.removeAll();
		
			
		String[] activityList=db.selectActivities(date);
			
			
		
		
	}
	
	
	
	
	void checkBoxBlock(String[] list, int x, int y){
		
		int side=FRAME_LENGTH-200;
		int margin=side/list.length;
		JPanel bg=new JPanel();
		
		bg.setBounds(10, 10, FRAME_WIDTH-20, FRAME_LENGTH);
		JScrollPane scrollArea = new JScrollPane(bg);  
	
		
		
	}



	@Override
	
	public void run() {
		// TODO Auto-generated method stub
		
	}



	
}
