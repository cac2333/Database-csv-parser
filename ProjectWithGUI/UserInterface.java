package ProjectWithGUI;

import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class UserInterface extends JFrame{

	private JPanel pvisitor;
	private JPanel pstaff;
	private static Map <String, JPanel> panelMap=new HashMap();
	
	static final int TABSIZE=20;  
	static final int BWIDTH = 250;
	static final int BLENGTH = 40;
	
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
		pvisitor.setLayout(null);
		
		JButton b1=new JButton("Search Activities");
		//b1.addActionListener(l);
		
		
		b1.setBounds(250, 150 , 300, 50);
		b1.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		
		JButton b2=new JButton("Make a reservation");
		b2.setBounds(250, 250 , 300, 50);
		b2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		
		pvisitor.add(b1);
		pvisitor.add(b2);

		
	}
	
	void initStaff(){
		
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
