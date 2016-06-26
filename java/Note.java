import	java.io.*;
import	java.util.*;

public class Note{
private static String copy=null;
private static Scanner ip=new Scanner(System.in);
public static void main(String []args){
boolean b=true;
int ch=0;
do{
System.out.println("\n-------------------------------------------------------------------------------");
System.out.println("1.File	2.Edit	3.Formate      4.Exit");
System.out.println("-------------------------------------------------------------------------------");
System.out.print("\t\t\t\t\t\t\t\t Choice>");
ch=ip.nextInt();
switch(ch){
case 1:
	System.out.print("1.New\n2.Open\n3.Save As\n4.Remove\n5.Back");
	System.out.print("\t\t\t\t\t\t\t\t Choice>");
	ch=ip.nextInt();
	switch(ch){
	case 1:
		try{
			create();
		}
		catch(IOException obj){
			System.out.println(obj);}
		finally{
			break;}
	case 2:
		try{
			open();
		}
		catch(IOException obj){
			System.out.println(obj);
		}
		catch(ClassNotFoundException obj){
			System.out.println(obj);
		}
		finally{
		break;	
		}
	case 3:
		try{
		saveAs();
		}
		catch(IOException obj){
		System.out.println(obj);
		}
		catch(ClassNotFoundException obj){
			System.out.println(obj);
		}
		finally{
		break;}	
		
	case 4:
		try{
		remove();
		}
		catch(IOException obj){
		System.out.println(obj);
		}
		catch(ClassNotFoundException obj){
			System.out.println(obj);
		}
		finally{
			break;}		
	case 5:
		break;
	}
	break;
	
case 2:
	System.out.println("	1.Find\n	2.Replace\n	3.Copy\n	4.Paste\n\t5.Back");
	System.out.print("\t\t\t\t\t\t\t\t Choice>");
	ch=ip.nextInt();
	switch(ch){
	case 1:
		try{
		boolean bo=find();
		if(bo)
		System.out.println("String exist");
		else
		System.out.println("String doesn't exist");
		}
		catch(IOException obj){
			System.out.println("File does'nt exist or any other IOException.");}
		finally{
			break;}
	case 2:
		try{
			replace();
		}
		catch(IOException obj){
			System.out.println(obj);
		}
		catch(ClassNotFoundException obj){
			System.out.println(obj);
		}
		finally{
		break;	
		}
	case 3:
		try{
		copy();
		}
		catch(IOException obj){
		System.out.println(obj);
		}
		catch(ClassNotFoundException obj){
			System.out.println(obj);
		}
		finally{
		break;}	
		
	case 4:
		try{
		paste();
		}
		catch(IOException obj){
		System.out.println(obj);
		}
		catch(ClassNotFoundException obj){
			System.out.println(obj);
		}
		finally{
			break;}
		
	case 5:
		break;
	}
	break;
	
case 3:
	System.out.println("\t\t1.Upper case\n\t\t2.Lower case\n\t\t3.back\n");
	System.out.print("\t\t\t\t\t\t\t\t Choice>");
	ch=ip.nextInt();
	break;
case 4:
	System.out.println("\nShutting down...");
	b=false;
}
}while(b);
}

public static void create() throws IOException{
String str=null;
ObjectOutputStream o=null;
System.out.print("Enter the name of File: ");
str=ip.next();
File f=new File(str + ".txt");
if(!f.exists()){
	System.out.println("\n-------------------------------------------------------------------------------");
	System.out.println("				" + str + ".txt");
	System.out.println("-------------------------------------------------------------------------------");
	str=new Scanner(System.in).nextLine();
	System.out.println("\n\n\n\n\n\n\n_______________________________________________________________________________");
	System.out.print("\nDo you want to save? press 'Y': ");
	String s=ip.next();
	if(s.charAt(0)=='y' || s.charAt(0)=='Y'){
	FileOutputStream out=new FileOutputStream(f);
	o=new ObjectOutputStream(out);
	o.writeObject(str);
	System.out.println("File is saved.");
	}
	else{
	System.out.println("File is not saved.");
	f.delete();
	}
	o.close();
}
else
System.out.println("File already exist, select 'Open' to open it.");

}
public static void open() throws IOException,ClassNotFoundException{
String str1;
String str;
System.out.print("Enter the name of File you want to open: ");
str1=ip.next();
File f=new File(str1 + ".txt");
File f1=new File("temp.txt");
if(f.exists()){
	System.out.println("\n-------------------------------------------------------------------------------");
	System.out.println("				" + str1 + ".txt");
	System.out.println("-------------------------------------------------------------------------------");
	FileOutputStream out=new FileOutputStream(f1);
	ObjectOutputStream o=new ObjectOutputStream(out);
	FileInputStream in=new FileInputStream(f);
	ObjectInputStream i=new ObjectInputStream(in);
	while(in.available()!=0){
		str=(String)i.readObject();
		System.out.print(str);
		o.writeObject(str);
	}
	str=new Scanner(System.in).nextLine();
	System.out.println("\n\n\n\n\n\n\n_______________________________________________________________________________");
	System.out.print("\nDo you want to save? press 'Y': ");
	String s=ip.next();
	if(s.charAt(0)=='y' || s.charAt(0)=='Y'){
	o.writeObject(str);
	System.out.println("File is saved.");
	}
	else{
	System.out.println("File is closing...");
	}
	i.close();
	o.close();
	f.delete();
	f1.renameTo(new File(str1 +".txt"));
}
else{
f.delete();
f1.delete();
System.out.println("File doesn't exist.");
}
}
public static void saveAs() throws IOException, ClassNotFoundException{
System.out.print("\nEnter the name of File to open: ");
String str1=new Scanner(System.in).nextLine();
File f=new File(str1 + ".txt");
if(f.exists()){
	System.out.print("Enter the path and file like (c:/java/new): ");
	str1=new Scanner(System.in).nextLine();
	File f1=new File(str1+".txt");
	boolean b=true;
	if(f1.exists()){
		System.out.println("File already exist.Do you want to replace it?");
		String s=ip.next();
		if(s.charAt(0)!='y' || s.charAt(0)!='Y'){	
		b=false;
		}
}
	if(b){
		FileOutputStream out=new FileOutputStream(f1);
		ObjectOutputStream o=new ObjectOutputStream(out);
		FileInputStream in=new FileInputStream(f);
		ObjectInputStream i=null;
		while(in.available()!=0){
			i=new ObjectInputStream(in);
			o.writeObject((String)i.readObject());
		}
		o.close();
		i.close();
	}
}
else 
	System.out.println("File doesn't exist.");
}

public static void remove() throws IOException, ClassNotFoundException{
System.out.print("Enter the name of File: ");
String str1=ip.next();
File f=new File(str1 + ".txt");
if(f.exists()){
	f.delete();
	System.out.println("File is removed.");
}
else 
System.out.println("File doesn't exist.");
}
public static boolean find() throws IOException, ClassNotFoundException{
	String str1=null;
	System.out.print("Enter the File name: ");
	str1=ip.next();
	FileInputStream in=new FileInputStream(str1+".txt");
	ObjectInputStream i=new ObjectInputStream(in);
	System.out.print("Enter the string: ");
	String str=new Scanner(System.in).nextLine();
	while(in.available()!=0){
		str1=(String)i.readObject();
		if(str1.lastIndexOf(str)!=-1){
			i.close();
			return true;
		}	
	}
	i.close();
	return false;

}
public static void replace() throws IOException, ClassNotFoundException{
	String str1=null;
	System.out.print("Enter the File name: ");
	String fn=ip.next();
	File f=new File(fn + ".txt");
	File f1=new File("temp.txt");
	if(f.exists()){
		FileOutputStream out=new FileOutputStream(f1);
		ObjectOutputStream o =new ObjectOutputStream(out);
		FileInputStream in=new FileInputStream(f);
		ObjectInputStream i=new ObjectInputStream(in);
		System.out.print("Enter the string: ");
		String str=new Scanner(System.in).nextLine();
		System.out.print("Enter the replacement: ");
		String rep=new Scanner(System.in).nextLine();
		while(in.available()!=0){
			str1=(String)i.readObject();
			o.writeObject(str1.replaceAll(str,rep));
		}//while
	i.close();
	o.close();
	f.delete();
	f1.renameTo(new File(fn + ".txt"));
	System.out.println("String is replaced.");
	}//if
	else
		System.out.println("File does'nt exist");
}

public static void copy() throws IOException, ClassNotFoundException{
System.out.print("Enter the File name: ");
String str1=ip.next();
FileInputStream in=new FileInputStream(str1+".txt");
ObjectInputStream i=new ObjectInputStream(in);
int index=0;
System.out.println("\n\t1.Copy a string\n\t2.Copy whole text");
System.out.print("\t\t\t\t\t\t\t\t Choice>");
int ch=ip.nextInt();
if(ch==1){
	System.out.print("Enter the string: ");
	String str=new Scanner(System.in).nextLine();
	while(in.available()!=0){
		str1=(String)i.readObject();
		if((index=str1.lastIndexOf(str))!=-1){
		copy=str1.substring(index,(index+str.length()));	
		System.out.println("String copied..");
		break;
		}
	}//while
}//if
else if(ch==2)
		System.out.println("File copied..");
else
	System.out.println("Wrong input");
i.close();
}

public static void paste() throws IOException, ClassNotFoundException{
String str1=null;
System.out.print("Enter the File name: ");
String fn=ip.next();
File f=new File(fn+".txt");
File f1=new File("temp.txt");
if(f.exists()){
	FileInputStream in=new FileInputStream(f);
	ObjectInputStream i=new ObjectInputStream(in);
	FileOutputStream out=new FileOutputStream(f1,true);
	ObjectOutputStream o=new ObjectOutputStream(out);
	System.out.println("\n\t1.Paste the string \n\t2.Paste the text file");
	System.out.print("\t\t\t\t\t\t\t\t Choice>");
	int ch=ip.nextInt();
	if(ch==1){
		while(in.available()!=0){
		str1=(String)i.readObject();
		o.writeObject(str1);
		}
		i.close();
		o.writeObject(copy);
		System.out.println("String is pasted...");
	}
	if(ch==2){
	System.out.print("Enter the file from where to copy: ");
	String str=ip.next();
	FileInputStream in2=new FileInputStream(str + ".txt");
	ObjectInputStream i2=new ObjectInputStream(in2);
	while(in.available()!=0){
		str1=(String)i.readObject();
		o.writeObject(str1);
		}
	i.close();
	while(in2.available()!=0){
		str1=(String)i2.readObject();
		o.writeObject(str1);
		}
	i2.close();
	System.out.println("File is pasted...");
	}
	o.close();
	f.delete();
	f1.renameTo(new File(fn + ".txt"));
}
}	
}