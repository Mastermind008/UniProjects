
import	java.io.*;
import	java.util.*;

class Dictionary implements Serializable {

private String word;
private String meaning;


public Dictionary(){

	word=" ";
	meaning=" ";
}

public Dictionary(String w,String m){
	word=w;
	meaning=m;
}

public void search(String w) throws IOException, ClassNotFoundException{
	boolean b=false;
	Dictionary dc=null;
	FileInputStream in=new FileInputStream("dic.ser");
	ObjectInputStream i=new ObjectInputStream(in);
	do{
	dc=(Dictionary) i.readObject();
	if((dc.word).contains(w)){
	System.out.println( dc.word +"\t\t"+dc.meaning);
	b=true;
	}
	}
	while(in.available()!=0);
	i.close();
	if(!b){
	System.out.println("Word not exist.");}
}

public void add() throws IOException,ClassNotFoundException{
	Dictionary dc=null;
	Scanner ip=new Scanner(System.in);

	System.out.print("Enter a word: ");
	word=new Scanner(System.in).nextLine();
	System.out.print("Enter the meaning: ");
	meaning=new Scanner(System.in).nextLine();

	File f1 = new File("dic.ser");
	File f2 = new File("temp.ser");

	FileOutputStream out=new FileOutputStream(f2);
	ObjectOutputStream o=new ObjectOutputStream(out);
	if ( f1.exists() )
	{	
		FileInputStream in = new FileInputStream(f1);
		ObjectInputStream	i = new ObjectInputStream(in);
		while ( in.available()!=0)
		{
			dc = (Dictionary)i.readObject();
			o.writeObject(dc);
		}
		i.close();				
	}
	o.writeObject(this);
	o.close();
	out.close();
	f1.delete();
	f2.renameTo(new File("dic.ser"));
}

public void edit(String w) throws IOException,ClassNotFoundException{
	Scanner ip=new Scanner(System.in);
	Dictionary dc;
	File f1 = new File("dic.ser");
	File f2 = new File("temp.ser");
	boolean b=false;
	FileOutputStream out=new FileOutputStream(f2);
	ObjectOutputStream o=new ObjectOutputStream(out);
	if ( f1.exists() )
	{
	FileInputStream in = new FileInputStream(f1);
	ObjectInputStream	i = new ObjectInputStream(in);
	while ( in.available()!=0)
	{
		dc = (Dictionary)i.readObject();
		if((dc.word).equals(w)){
		System.out.print("Enter the new meaning: ");
		String str=new Scanner(System.in).nextLine();
		b=true;
		dc.meaning=str;}
		o.writeObject(dc);
	}
		i.close();
	}
	o.close();		
	f1.delete();			
	f2.renameTo(new File("dic.ser"));
	if(!b){
	System.out.println("word not exist.");}
}

public void remove(String w) throws IOException,ClassNotFoundException{
	Scanner ip=new Scanner(System.in);
	Dictionary dc;boolean b=false;
	File f1 = new File("dic.ser");
	File f2 = new File("temp.ser");

	FileOutputStream out=new FileOutputStream(f2);
	ObjectOutputStream o=new ObjectOutputStream(out);

	if ( f1.exists() )
	{
	FileInputStream in = new FileInputStream(f1);
	ObjectInputStream	i = new ObjectInputStream(in);
	while ( in.available()!=0)
	{
		dc = (Dictionary)i.readObject();
		if((dc.word).equals(w)){
		if(in.available()==0){
		break;
		}b=true;
		dc= (Dictionary)i.readObject();}
		o.writeObject(dc);
	}
		i.close();
	}
	o.close();		
	f1.delete();			
	f2.renameTo(new File("dic.ser"));
	if(!b){
	System.out.println("word not exist.");}
}
public void showdic() throws IOException,ClassNotFoundException{

	Dictionary dc=null;
	FileInputStream in=new FileInputStream("dic.ser");
	ObjectInputStream i=new ObjectInputStream(in);
	System.out.println("Words\t\tMeanings");

	while(in.available()!=0){
	dc=(Dictionary) i.readObject();
	System.out.println(dc.word + "\t\t" +dc.meaning);	
	}
	
	i.close();
}
}
public class Dic
{
	
public static void main(String [] args)
{	boolean boo=true;
	boolean b=true;
	String choice=" ";
	Scanner ip=new Scanner(System.in);
	Dictionary dic=new Dictionary();
	System.out.println("Dictionary is loading...");
	do{
	System.out.println("\n--------------MAIN MENU--------------");
	System.out.println("\n	1.Insert\n	2.Search\n	3.Edit\n	4.Show\n	5.Remove\n	6.Exit");
	int ch=0;
	System.out.print("\nEnter your choice: ");
	try{
	ch=ip.nextInt();
	}
	catch(Exception e){
	boo=false;
	}
	if(1>ch || ch>6){
	System.out.print("You have entered wrong input,try again:");
	
	}
	while(1>ch || ch>6);
	switch(ch){
	case 1:
		System.out.println("\n-----INSERT MENU-----	\n");
		try{
		dic.add();
		}
		catch(IOException obj){
		System.out.println("File not found or any other IO exception");
		}
		catch(ClassNotFoundException obj){
			System.out.println(obj);
		}
		finally{
		break;
		}
		
	case 2:
		System.out.println("\n-----SEARCH MENU-----	\n");

		System.out.print("Enter the word : ");
		String str=ip.next();

		try{
			dic.search(str);
			System.out.println("press enter to continue...");
			new Scanner(System.in).nextLine();
		}
		catch( IOException obj){
			System.out.println("File not found or any other IO exception");
		}
		catch(ClassNotFoundException obj){
			System.out.println(obj);
		}
		finally{
		break;
		}	
	case 3:
		System.out.println("\n-----EDIT MENU-----	\n");
		System.out.print("Enter a word : ");
		String st=ip.next();

		try{
			dic.edit(st);
		}
		catch( IOException obj){
			System.out.println(obj);
		}
		catch(ClassNotFoundException obj){
			System.out.println(obj);
		}
		finally{
		break;
		}

	case 4:
		System.out.println("\n-----SHOW MENU-----\n");
		try{
			dic.showdic();
			System.out.println("\npress enter to continue...");
			new Scanner(System.in).nextLine();

		}
	
		catch(ClassNotFoundException obj){
			System.out.println(obj);
		}
		catch(IOException obj){
			System.out.println(obj);
		}
		finally{
		break;
		}
	case 5:
		System.out.println("\n-----REMOVE MENU-----\n");
		System.out.print("Enter the word : ");
		String s=ip.next();
		try{
			dic.remove(s);
		}
	
		catch(ClassNotFoundException obj){
			System.out.println(obj);
		}
		catch(IOException obj){
			System.out.println(obj);
		}
		finally{
		break;
		}
	case 6:
		System.out.println("\nshutting down...");
		b=false;
	}
	}
	while(b);	
}

}

