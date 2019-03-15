package start;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Parser {
	
	private final String path="data\\";
	private Scanner sc;
	private int[] format; //0 for no quotation, 1 for single quotation
	private char delimiter;
	
	
	Parser(String file, int[] format){
		try{
			sc=new Scanner(new File(path+file));
			sc.useDelimiter("\r\n");  //only windows csv 
		}catch(FileNotFoundException e){
			System.out.println(file+" not Found");
		}
		this.format=format;
		delimiter=',';
	}
	
	Parser(String file, int[] format, String delimiter1, char delimiter2){
		try{
			sc=new Scanner(new File(path+file));
			sc.useDelimiter(delimiter1);  //only windows csv 
		}catch(FileNotFoundException e){
			System.out.println(file+" not Found");
		}
		this.format=format;
		delimiter=delimiter2;
	}
	
	
	
	//return a single record 
	public String getLine(){
		
		if(sc.hasNext()){
			StringBuilder record=new StringBuilder (sc.next());
			try {
				//check if the last character is ",". If so, remove that ","
				while(record.charAt(record.length()-1)==delimiter)
					record=record.deleteCharAt(record.length()-1);
				return parse(record.toString());
				
			}catch(IndexOutOfBoundsException e){
				System.out.println("Data file wrong format.");
				return null;
			}
		}else 
			return null;

	}
	
	
	
	
	//parse a line according to the format array
	private String parse(String record) throws IndexOutOfBoundsException{

		int index=0;  //index of format 
		String substring=""; //contain each attribute value 
		StringBuilder data=new StringBuilder();
		char c;

		for(int i=0; i<record.length(); i++){
			c=record.charAt(i);
			
			//separate values next to ","
			if(c==delimiter){  
				substring=format[index++]==0?substring:"\'"+substring+"\'";  //add quotation around values
				data.append(substring+delimiter);
				substring=""; //empty the substring 
			//edge case, handle the last character of the string	
			}else if(i==record.length()-1){
				substring=format[index++]==0?substring:"\'"+substring+c+"\'";
				data.append(substring); //append this attribute to the record data
			}
			//append the char to substring
			else{
				substring=substring+c; 
			}
		}
		return data.toString();

	}
	
	
}
