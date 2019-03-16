package ProjectWithGUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;



public class UserInterface extends JFrame{

	private JPanel pvisitor;
	private JPanel pstaff;
	private Database db=new Database();
	
	static final int TABSIZE=20;  
	static final int BWIDTH = 250;
	static final int BLENGTH = 40;
	
	UserInterface(Database db){
		
		this.db=db;
		
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
	

	
	//creating the first view for visitor 
	
	void initVisitor(){
		
		pvisitor.removeAll();
		pvisitor.setLayout(null);
		
		JButton b1=new JButton("Search Activities");
		
		b1.setBounds(250, 150 , 300, 50);
		b1.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		b1.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				searchActivity();
			}
		});
		
		JButton b2=new JButton("Make a reservation");
		b2.setBounds(250, 250 , 300, 50);
		b2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		b1.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				book();
			}
		});
		
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
		
		//clear the panel
		pvisitor.removeAll();
		
		JLabel msg=new JLabel("Please select a date");
		msg.setFont(new Font("TimesRoman", Font.BOLD, 24));
		msg.setBounds(200,80,350,40);
		
		
		// Year
		
		String[] year=new String[Calendar.getInstance().get(Calendar.YEAR)-2010];
		for(int i=2010, k=0; i<Calendar.getInstance().get(Calendar.YEAR); i++, k++){
			year[k]=i+"";
		}
   
		JComboBox yearBox=new JComboBox(year);
		JLabel yearField=fieldName("Year", 200, 170);
		yearBox.setFont(new Font(yearBox.getFont().getFontName(), Font.PLAIN, 16));
		yearBox.setBounds(300, 170 , 200, 30);
		yearBox.setName("Year");

		
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
	
				db.activityQuery(yearBox.getSelectedItem().toString()+
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
	
	
	
}
