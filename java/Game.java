import java.util.Scanner;
import java.util.Random;
public class Game{
	public static void main(String [ ]args){
		boolean boo=true;int ch=0;
		System.out.println("\n-------------------MAIN MENU-------------------");
		System.out.print("\n\t1.Single player\n\t2.Two players\n");
		while(boo){
		boo=false;
		System.out.print("\t\t\t\t\tchoice>");
		try{
		ch=new Scanner(System.in).nextInt();
		}
		catch(Exception e){
		System.out.println("Wrong choice entered.");
		boo=true;}
		if(ch!=1 && ch!=2 && boo==false){
		System.out.println("Wrong choice entered.");
		boo=true;}
		}
		int size=0;int i=0 ; int j=0;
		Scanner ip=new Scanner(System.in);
		System.out.print("Enter the size of game:");
		size=ip.nextInt();
		int [ ][ ]arr=new int[size][size];
		for( i=0 ; i<arr.length; i++)
		{
			for( j=0 ; j<arr[i].length ; j++)
			{
				arr[i][j]=0;
			}
		}		
		show(arr);
		i=j=0;
		boolean a=true;
		switch(ch){
			case 2:
		while(a==true){
			do{
			System.out.println("Player 1 turn. Enter the index:");	
			System.out.print("row=");
			i=ip.nextInt()-1;
			System.out.print("col=");
			j=ip.nextInt()-1;
			}
			while( i>(size-1) || j>(size-1) || arr[i][j]!=0 );
			a=turn(arr,i,j,1);
			if(!a)
			{	show(arr);
				break;	}
			a=draw(arr);
			show(arr);
			if(!a)
			{	break;	}	
			do
			{
			System.out.println("Player 2 turn. Enter the index:");	
			System.out.print("row=");
			i=ip.nextInt()-1;
			System.out.print("col=");
			j=ip.nextInt()-1;
			
			}
			while( i>(size-1) || j>(size-1) || arr[i][j]!=0 );
			a=turn(arr,i,j,2);
			if(!a)
			{	show(arr);
				break;	}
			a=draw(arr);
			show(arr);	
		}
		break;
			case 1:
			while(a==true){
			do{
			System.out.println("Player 1, Enter the index:");	
			System.out.print("row=");
			i=ip.nextInt()-1;
			System.out.print("col=");
			j=ip.nextInt()-1;
			}
			while( i>(size-1) || j>(size-1) || arr[i][j]!=0 );
			a=turn(arr,i,j,1);
			if(!a)
			{	show(arr);
				break;	}
			a=draw(arr);
			show(arr);
			if(!a)
			{	break;	}

			System.out.println("Player 2:");
			do
			{	
			Random rn = new Random();
			i=rn.nextInt(size);
			j=rn.nextInt(size);
			}
			while( i>(size-1) || j>(size-1) || arr[i][j]!=0 );
			a=turn(arr,i,j,2);
			if(!a)
			{	show(arr);
				break;	}
			a=draw(arr);
			show(arr);
		}
		break;		
	}
	}
	public static boolean turn(int [ ][ ]arr,int i,int j,int p)
	{	
		arr[i][j]=p;

		for(int x=0 ;  x<arr.length ; x++)
		{
			if(arr[i][x]!=p  )
			{	for(int y=0 ;  y<arr.length ; y++)
				{	if(arr[y][j]!=p)	
					{	for(int m=0, n=0; m<arr.length; m++,n++)
						{	if(arr[m][n]!=p)
							{	for(int a=arr.length-1,b=0 ; b<arr.length ; a--,b++)
								{	if(arr[a][b]!=p)
									{	return true;	}	
								}
							}										
						}
					}
				}
			}
		}	
			
		System.out.println("Player " +p+ " won the game");
			return false;
		
		
	}
	public static boolean draw(int [ ][ ]arr)
	{
		
		for(int i=0 ; i<arr.length; i++)
		{	
			for( int j=0 ;  j<arr[i].length ; j++)
			{
				if(arr[i][j]==0)
			{	return true;	}
			}
		}
			
		System.out.println("\nMatch is draw");
		return false;	
			
	}
		
	public static void show(int [ ][ ]arr)
	{
		System.out.println();
		for(int i=0; i<arr.length; i++)
		{
			System.out.print("  ");
			for(int j=0 ; j<arr[i].length ; j++)
			{
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}