import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.event.*;

public class TicTac implements ActionListener{

	boolean singlep,twop,repeat,won;
	String in,name,t,query,p,pl,names,delName;
	int size,count,id;
	String []namesArray;
	Container c;
	JMenuBar bar;
	JMenu list,list1;
	JMenuItem item,item1,item2,item3,item4,item5,item6;
	JFrame jf;
	JPanel jp,jp1,jp2;
	JLabel l1,l2,menu,load;
	JButton [][]barr;
	JButton sp,tp,ld,ex,ld1,del;
	Random rn;
	JList<String> nameList;
	JScrollPane scroll;
	Connection conn;
	Statement st;
	ResultSet rs;
	
	public TicTac(){														//Constructor
		initGUI();
		count=0;
		singlep=twop=false;
		repeat=true;
	}
	
	public void initGUI(){										//initial GUI
		
		jf=new JFrame("TicTac");								//frame and container
		c=jf.getContentPane();
		c.setLayout(new BorderLayout());
		
		nameList =new JList<String>();
		nameList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION);
		nameList.setVisibleRowCount( 8);
		
		
		bar=new JMenuBar();
		jf.setJMenuBar(bar);
		
		list=new JMenu("Game");
		list.setMnemonic('G');
		bar.add(list);
			
		item=new JMenuItem("New Game");
		item.setMnemonic('N');
		list.add(item);
		item1=new JMenuItem("Save Game");
		item1.setMnemonic('S');
		list.add(item1);
		item2=new JMenuItem("Load Game");
		item2.setMnemonic('L');
		list.add(item2);
		item3=new JMenuItem("Main Menu");
		item3.setMnemonic('M');
		list.add(item3);
		item6=new JMenuItem("Quit Game");
		item6.setMnemonic('Q');
		list.add(item6);
		
		list1=new JMenu("Help");
		list1.setMnemonic('H');
		bar.add(list1);
		item4=new JMenuItem("View help");
		item4.setMnemonic('V');
		list1.add(item4);
		item5=new JMenuItem("About TicTacToe");
		item5.setMnemonic('A');
		list1.add(item5);
		
		item.addActionListener(this);
		item1.addActionListener(this);
		item2.addActionListener(this);
		item3.addActionListener(this);
		item4.addActionListener(this);
		item5.addActionListener(this);
		item6.addActionListener(this);
		
		bar.setVisible(false);
		
		l1=new JLabel("Player O turn");							//label for player turn
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setFont(new Font("Times New Roman",Font.PLAIN,16));
		
		menu=new JLabel("Main Menu");							//label for main menu
		menu.setFont(new Font("Andalus",Font.BOLD,18));
		menu.setHorizontalAlignment(SwingConstants.CENTER);
		
		load=new JLabel("Load Game");							//label for load games
		load.setFont(new Font("Andalus",Font.BOLD,18));
		load.setHorizontalAlignment(SwingConstants.CENTER);
		
		jp1=new JPanel(new GridLayout(4,1,25,5));				//Gridpanel for main menu 
		sp=new JButton("Single Player");
		jp1.add(sp);
		tp=new JButton("Two Players");
		jp1.add(tp);
		ld=new JButton("Load Game");
		jp1.add(ld);
		ex=new JButton("Exit");
		jp1.add(ex);
		
		jp2=new JPanel(new GridLayout(1,2,10,5));
		del=new JButton ("Delete");
		jp2.add(del);
		ld1=new JButton("Load");
		jp2.add(ld1);
		
		c.add(jp1,BorderLayout.CENTER);
		c.add(menu,BorderLayout.NORTH);							//adding labels
		c.add(new JLabel("        "),BorderLayout.EAST);
		c.add(new JLabel("        "),BorderLayout.WEST);
		c.add(new JLabel("        "),BorderLayout.SOUTH);
		
		sp.addActionListener(this);
		tp.addActionListener(this);
		ld.addActionListener(this);
		ex.addActionListener(this);
		ld1.addActionListener(this);
		del.addActionListener(this);
		
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.setSize(250,250);
		jf.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent e){								//actionPerformed
	
	if(e.getSource()==tp){													//two player
		ButtonArray(inputSize());
		menu.setVisible(false);
		jp1.setVisible(false);
		bar.setVisible(true);
		c.add(l1,BorderLayout.NORTH);
		l1.setVisible(true);
		c.add(jp,BorderLayout.CENTER);
		jp.setVisible(true);
		if(count%2==0)
			l1.setText("Player O turn");
		if(count%2!=0)
			l1.setText("Player X turn");
		twop=true;
		singlep=false;
	}
	
	if(e.getSource()==sp){						//single player
		size=inputSize();
		
			ButtonArray(size);
			menu.setVisible(false);
			jp1.setVisible(false);
			bar.setVisible(true);
			c.add(l1,BorderLayout.NORTH);
			l1.setVisible(true);
			c.add(jp,BorderLayout.CENTER);
			jp.setVisible(true);
			singlep=true;
			twop=false;
			l1.setText("Your Turn");
		
	}
	
	if(e.getSource()==ex){													//exit
		jf.setVisible(false);
			System.exit(0);	
	}
	
	if(e.getSource()==ld){													//load game
		try{
		menu.setVisible(false);
		jp1.setVisible(false);
		showList();
		bar.setVisible(true);
		c.add(jp2,BorderLayout.SOUTH);
		jp2.setVisible(true);
		c.add(load,BorderLayout.NORTH);
		load.setVisible(true);
		c.add(nameList,BorderLayout.CENTER);
		nameList.setVisible(true);
		scroll=new JScrollPane( nameList );
		jf.add(scroll );
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
	}
	
	
	if(e.getSource()==item){												//newGame (Menu item)
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++){
				barr[i][j].setEnabled(true);
				barr[i][j].setText(" ");
			}
	}
	
	if(e.getSource()==item1){												// saveGame(Menu item)
		try{
		name=inputName();
		if(name!=null){
			saveGame(name);
			}
		}
		catch(SQLException sql){
			System.out.println(sql);
		}	
		
	}
	if(e.getSource()==item2){											// load game (Menu item)
		try{
		
		l1.setVisible(false);
		jp.setVisible(false);
		showList();
		c.add(jp2,BorderLayout.SOUTH);
		jp2.setVisible(true);
		c.add(load,BorderLayout.NORTH);
		load.setVisible(true);
		c.add(nameList,BorderLayout.CENTER);
		nameList.setVisible(true);
		scroll=new JScrollPane( nameList );
		jf.add(scroll );
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
	}
	
	if(e.getSource()==item3){												// Main menu (Menu item)
		l1.setVisible(false);
		jp.setVisible(false);
		menu.setVisible(true);
		jp1.setVisible(true);
		bar.setVisible(false);
		
	}		
	
	if(e.getSource()==item4){
		JOptionPane.showMessageDialog(null,"No help is available at this time");			// help (menu item)
	}
	if(e.getSource()==item5){
		JOptionPane.showMessageDialog(null,"The Game is made by Muzamil.\nHe is a Software Engineer.\nContact at link2muzammal@gmail.com\nCopyright in 2014 ");			//about (menu item)
	}
	
	if(e.getSource()==item6){											// quit game(Menu item)
		jf.setVisible(false);
		System.exit(0);	
	}
	
	
		for(int i=0;i<size;i++)												//game buttons
			for(int j=0;j<size;j++)
				if(e.getSource()==barr[i][j]){
					if(twop)
						twoplayer(i,j);
					if(singlep)
						singleplayer(i,j);
						
				}
		
		if(e.getSource()==ld1){											//load record
			try{
			name=nameList.getSelectedValue();
			if(name!=null){
			nameList.setVisible(false);
			load.setVisible(false);
			jp2.setVisible(false);
			scroll.setVisible(false);
			c.add(new JLabel("        "),BorderLayout.SOUTH);
			loadGame();
			c.add(l1,BorderLayout.NORTH);
			l1.setVisible(true);
			c.add(jp,BorderLayout.CENTER);
			jp.setVisible(true);
			bar.setVisible(true);
			}
		}	
		catch(SQLException sql){
			System.out.println(sql);
		}		
		}
		if(e.getSource()==del){											// delete record
			try{
				delName=nameList.getSelectedValue();
				if(delName!=null){
					delRecord(delName);
					showList();
					
				}
			}
			catch(SQLException sql){
				System.out.println(sql);
			}	
		
		}
	
	}
	public void twoplayer(int i,int j){											//two player method
		if(count%2!=0)
			l1.setText("Player O turn");
		if(count%2==0)
			l1.setText("Player X turn");
						
		barr[i][j].setEnabled(false);
		if(count%2!=0){
			barr[i][j].setText("X");
			turn(i,j,"X");
			if(!won)
			draw();
		}
		else {
			barr[i][j].setText("O");
			turn(i,j,"O");
			if(!won)
			draw();
		}
		count++;
		
	}
	public void singleplayer(int i,int j){									//single player method
		rn = new Random();
		l1.setText("Your Turn");
		barr[i][j].setEnabled(false);
		barr[i][j].setText("H");
		turn(i,j,"H");
		if(!won)	{
		draw();
		}
		while(repeat){
			i=rn.nextInt(size);
			j=rn.nextInt(size);		
			if(barr[i][j].getText().equals(" ")){
				barr[i][j].setEnabled(false);
				barr[i][j].setText("C");
				turn(i,j,"C");
				repeat=false;
			}
			}

	}
	public void turn(int i,int j,String p)										//to check won or not
	{	
		for(int x=0 ;  x<size ; x++)
		{
	
			if(barr[i][x].getText()!=p  )
			{	
				for(int y=0 ;  y<size ; y++)
				{	if(barr[y][j].getText()!=p)	
					{	for(int m=0, n=0; m<size; m++,n++)
						{	if(barr[m][n].getText()!=p)
							{	for(int a=size-1,b=0 ; b<size ; a--,b++)
								{	if(barr[a][b].getText()!=p)
									{	won=false;
										repeat=true;
										return;	}	
								}
							}										
						}
					}
				}
			}
		}	
			if(p.equals("H"))
				JOptionPane.showMessageDialog(null,"You won the game");
			if(p.equals("C"))
				JOptionPane.showMessageDialog(null,"You lose the game");
			if(!p.equals("C") && !p.equals("H")  )
				JOptionPane.showMessageDialog(null,"Player "+p+" won the game");
		l1.setVisible(false);
		jp.setVisible(false);
		menu.setVisible(true);
		jp1.setVisible(true);
		bar.setVisible(false);	
		won=true;
		repeat=false;
	}
	public void draw()																//to check draw
	{
		for(int i=0 ; i<size; i++)
			for( int j=0 ;  j<size ; j++)
				if(barr[i][j].getText()!="X" && barr[i][j].getText()!="O" && barr[i][j].getText()!="H" && barr[i][j].getText()!="C")
					return;	
			
			
		JOptionPane.showMessageDialog(null,"Game is draw");	
		l1.setVisible(false);
		jp.setVisible(false);
		menu.setVisible(true);
		jp1.setVisible(true);
		bar.setVisible(false);	
		repeat=false;
			
	}
	
	public void loadGame() throws SQLException{										// load game
		
		query = "Select Type,Turn,Size From Game where Name='"+name+"'";
		rs = st.executeQuery(query);
		while ( rs.next() ){
				size=rs.getInt("Size");
				ButtonArray(size);
			if(rs.getString("Type").equals("SinglePlayer")){
				singlep=true;
				twop=false;
				l1.setText("Your Turn");
			}
			t=rs.getString("Turn");
			if(rs.getString("Type").equals("TwoPlayer")){
				singlep=false;
				twop=true;
				if(t.equals("X")){
					count=1;
					l1.setText("Player X turn");
				}
				if(t.equals("O")){
					count=0;
					l1.setText("Player O turn");
				}
			}
		}
		query = "Select i,j,p From Detail,Game where Game.ID=Detail.ID and Name='"+name+"'";
		rs = st.executeQuery(query);
		
		while ( rs.next() )
			{	
					int i = rs.getInt("i");
					int j = rs.getInt("j");
					p = rs.getString("p");
					if(p.equals("X") || p.equals("O") || p.equals("H") || p.equals("C"))
						barr[i][j].setEnabled(false);
					if(p.equals("X"))
						barr[i][j].setText("X");
					if(p.equals("O"))
						barr[i][j].setText("O");
					if(p.equals("H"))
						barr[i][j].setText("H");
					if(p.equals("C"))
						barr[i][j].setText("C");
			}
			
	}
	public void showList() throws SQLException{									//show saved game list
		conn = DriverManager.getConnection("jdbc:ucanaccess://Game.accdb");
		st = conn.createStatement();
		
		query="Select Name from Game";
		rs=st.executeQuery(query);
		rs.next();
		names=rs.getString("Name");
		while(rs.next()){
			names=names+" "+rs.getString("Name");
		}
		
		namesArray=names.split(" ");
		nameList.setListData(namesArray);
			
	}	
	public void saveGame(String name) throws SQLException{							//save Game
	
		if(singlep && count%2==0)
			query="INSERT INTO Game(Name,Type,Turn,Size) VALUES('"+name+"','SinglePlayer','O',"+size+")";
		if(twop && count%2==0)	
			query="INSERT INTO Game(Name,Type,Turn,Size) VALUES('"+name+"','TwoPlayer','O',"+size+")";
		if(singlep && count%2!=0)
			query="INSERT INTO Game(Name,Type,Turn,Size) VALUES('"+name+"','SinglePlayer','X',"+size+")";
		if(twop && count%2!=0)	
			query="INSERT INTO Game(Name,Type,Turn,Size) VALUES('"+name+"','TwoPlayer','X',"+size+")";	
		if ( st.executeUpdate(query) == 1 ){
			query="select ID from Game where Name='"+name+"'";
			rs=st.executeQuery(query);
			while(rs.next())
				id=rs.getInt("ID");
			for(int i=0;i<size;i++)
				for(int j=0;j<size;j++){
					pl=barr[i][j].getText();
					if(pl!=" ")
						query="INSERT INTO Detail(ID,i,j,p) VALUES("+id+","+i+","+j+",'"+pl+"')";
					else
						query="INSERT INTO Detail(ID,i,j,p) VALUES("+id+","+i+","+j+",'0')";
					st.executeUpdate(query);
				}
		}		
			JOptionPane.showMessageDialog(null,"Game Saved");
	}
	public String inputName()throws SQLException{										//input name
	
		conn = DriverManager.getConnection("jdbc:ucanaccess://Game.accdb");
		st = conn.createStatement();
		
		name=JOptionPane.showInputDialog("Enter the game name");
		query="Select Name from Game ";
		rs=st.executeQuery(query);
			
		while(rs.next()){
			if(rs.getString("Name").equals(name)){
				JOptionPane.showMessageDialog(null,"Name already exist");
				inputName();
				}
		}

		return name;	
	}	
	public int inputSize(){														//input size
		try{
			in=JOptionPane.showInputDialog("Enter the game size");
			
			char []arr=in.toCharArray();
			for(int i=0; i<in.length(); i++)
				if((int)arr[i]<51 || (int)arr[i]>57)
					throw new inputException();
				
			size=Integer.parseInt(in);
				
			
		}
		catch(inputException e){
			inputSize();
		}
		return  size;
	}
	public void delRecord(String dname) throws SQLException{						//delete record
		query="Delete from Game where Name='"+dname+"'";
		if(st.executeUpdate(query)==1)
			JOptionPane.showMessageDialog(null,"Record Deleted");
	}
	public void ButtonArray(int size){
		barr=new JButton[size][size];
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++){
				barr[i][j]=new JButton(" ");
				barr[i][j].setFont(new Font("Arial Rounded MT Bold",Font.BOLD,26));		
				}
		jp=new JPanel(new GridLayout(size,size));				
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				jp.add(barr[i][j]);
		
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				barr[i][j].addActionListener(this);		
	}
	public static void main(String []args){
		TicTac tc=new TicTac();
	}
}
		