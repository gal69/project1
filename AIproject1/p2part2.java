package project2;
import java.util.*;
import java.lang.*;

public class part2
{

public static char[][] knwgrid;
public static int targetx;
public static int targety;
public static double[][] containingP;

public static double[][] findingP;

  public static void main(String[] args)
  {
  	final int DIM= 50;
    char [][] grid = genArray( DIM);
    
    printChar(grid);
    
    
    for(int i=0; i< DIM;i++)
    {
    	for(int j=0;j<DIM;j++)
	{
		knwgrid[i][j]='?';
		containingP[i][j]=(1/2500);
		findingP[i][j]=(1/2500);
		
	}
    }
    
    // now to make the target just random *50
    
     targetx = (int)(Math.random()*DIM);
     targety = (int)(Math.random()*DIM);
    
    System.out.println("the target is at: "+targetx +" "+targety); 
    
    // we need to count total number of terrain
    int totalsearches =0;
    int numoff=0;
    int numofh=0;
    int numofo=0;
    int numoffc=0;
    boolean found =false ;
    
    //first count terrain
    
    for( int g=0;g<DIM;g++)
    {
    	for(int h=0;h<DIM;h++)
	{
		if( grid[g][h] =='F')
		{
			numoff++;
		}
		if( grid[g][h] =='H')
		{
			numofh++;
		}
		if( grid[g][h] =='O')
		{
			numofo++;
		}
		if( grid[g][h] =='C')
		{
			numoff++;
		}
	}
   }
		// now pick first choice uniformally at random
	int pickinga= (int)(Math.random()*50);
	int pickingb= (int)(Math.random()*50);
		
		
    
    // this is basically the examine
    int[][] temp;
    
    do
    {
    found= examine(grid, pickinga, pickingb);
    
    doConP(pickinga, pickingb);
    
    temp= maxIn(containingP);
    pickinga = temp[0][0];
    pickingb=temp[0][1];
    
    //moving target 1 space in a random direction
    //returns a char[2]; NOTE:
    //char[0&&1] == 'X' then agent found target
    //char[0] = type1; char[1] = type2;
    moveTarget(found, grid);
    
    totalsearches++;
    }
    while(!found);
    
    System.out.println("great we found it in: "+totalsearches+" tries");
    System.out.println("the target was at: ("+pickinga+", "+pickingb+")");
    
    
  
  }
  
//this is the function that is mainly most of part 2 (Moving Target)
  //At each iteration of the search, if fail:
  //target will move to a neighboring cell (With uniform prob for ea. spot)
  //returns the coords of where the target was seen (type1 x type2)
  //int[0][0] && int[0][1] will hold coords for type 1 
  //int[1][0] && int[1][1] will hold coords type 2.
  //those coords will be translated into the way it was asked for:
  //retTypes[0] = type1; retTypes[1] = type2; (no order!)
  //note: if returned char[0 or 1] == 'X' means target has been found
  //int[][] types is there just incase we need the actual coords (can del later)
  public static char[] moveTarget(boolean found, char[][] grid)
  {
  	int[][] types = new int[2][2];
  	char[] retTypes = new char[2];
  	//if found --> target found, no need to move target
  	if(found)
  	{
  		retTypes[0] = 'X';
  		retTypes[1] = 'X';
  		return retTypes;
  	}
  	
  	//personal check:
  	//System.out.println("the target is at: "+targetx +" "+targety); 
  	//get random number 1-4 (1 = left, 2 = right, 3 = up, 4 = down)
  	Random rand = new Random();
  	int rNum = rand.nextInt((4 - 1) + 1) + 1;
  	//depending on where target may be, it may not be able to move a certain way
  	//it will just move onto the opposite direction
  	//Ex: target is at (5, 50) and the function chooses 2, it cannot move right
  	//so the target will automatically if (x||y = 50) --> 50 - 1 instead if needed
  	if(rNum == 1) //left
  	{
  		if(targety == 0)
  		{
  			//it is column 0 so we cannot move left, go right instead
  			types[0][0] = targetx;
  			types[0][1] = targety;
  			retTypes[0] = grid[targetx][targety];
  			targety = targety + 1;
  			types[1][0] = targetx;
  			types[1][1] = targety;
  			retTypes[1] = grid[targetx][targety];
  		}
  		else
  		{
  			//target is not at a point where it cannot move left
  			types[0][0] = targetx;
  			types[0][1] = targety;
  			retTypes[0] = grid[targetx][targety];
  			targety = targety - 1;
  			types[1][0] = targetx;
  			types[1][1] = targety;
  			retTypes[1] = grid[targetx][targety];
  		}
  	}
  	else if(rNum == 2) //right
  	{
  		if(targety == 49)
  		{
  			//it is column 50 so we cannot move right, go left instead
  			types[0][0] = targetx;
  			types[0][1] = targety;
  			retTypes[0] = grid[targetx][targety];
  			targety = targety - 1;
  			types[1][0] = targetx;
  			types[1][1] = targety;
  			retTypes[1] = grid[targetx][targety];
  		}
  		else
  		{
  			//target is not at point where it cannot move right
  			types[0][0] = targetx;
  			types[0][1] = targety;
  			retTypes[0] = grid[targetx][targety];
  			targety = targety + 1;
  			types[1][0] = targetx;
  			types[1][1] = targety;
  			retTypes[1] = grid[targetx][targety];
  		}
  	}
  	else if(rNum == 3) //up
  	{
  		if(targetx == 0)
  		{
  			//target cannot move up, go down instead
  			types[0][0] = targetx;
  			types[0][1] = targety;
  			retTypes[0] = grid[targetx][targety];
  			targetx = targetx + 1;
  			types[1][0] = targetx;
  			types[1][1] = targety;
  			retTypes[1] = grid[targetx][targety];
  		}
  		else
  		{
  			//go up
  			types[0][0] = targetx;
  			types[0][1] = targety;
  			retTypes[0] = grid[targetx][targety];
  			targetx = targetx - 1;
  			types[1][0] = targetx;
  			types[1][1] = targety;
  			retTypes[1] = grid[targetx][targety];
  		}
  	}
  	else if(rNum == 4) //down
  	{
  		if(targetx == 49)
  		{
  			//targetx cannot move down, go up instead
  			types[0][0] = targetx;
  			types[0][1] = targety;
  			retTypes[0] = grid[targetx][targety];
  			targetx = targetx - 1;
  			types[1][0] = targetx;
  			types[1][1] = targety;
  			retTypes[1] = grid[targetx][targety];
  		}
  		else
  		{
  			//targetx moves down
  			types[0][0] = targetx;
  			types[0][1] = targety;
  			retTypes[0] = grid[targetx][targety];
  			targetx = targetx + 1;
  			types[1][0] = targetx;
  			types[1][1] = targety;
  			retTypes[1] = grid[targetx][targety];
  		}
  	}
  	else
  	{
  		System.out.println("This function is not working, prob due to rNum (line 151)\n");
  	}

  	return retTypes;
  }
  
  public static void doConP( int x, int y)
  {
  	double intP = knwgrid[x][y];
	
  	if(knwgrid[x][y]=='F')
	{
		containingP[x][y]*= (.1);
		intP*=(.9);
	}
	if(knwgrid[x][y]=='H')
	{
		containingP[x][y]*= (.3);
		intP*=(.7);
	}
	if(knwgrid[x][y]=='O')
	{
		containingP[x][y]*= (.7);
		intP*=(.3);
	}
	if(knwgrid[x][y]=='C')
	{
		containingP[x][y]*= (.9);
		intP*=(.1);
	}
	
	intP/=(2499);
	for( int i=0;i<50;i++)
	{
		for(int j=0;j<50;j++)
		{
			if(i==x && y==j)
			{
			continue;
			}
			else
			{
			containingP[i][j]+=(intP);
			}
		}
	}
	
	
  
  }
  
  
  // return a 4 by 4 int arrray with x and y targets of adj but -1 if not a valid adj
  public static int[][] searchAdj(int x ,int y)
  {
  	int[][] returner = new int[4][4];
	
	//0== up 1 == right == 2==down 3==left
	// -1 not a valid adj
	// first we can set up the coordinates  then check if out of bounds
	
	
	// up
	returner[0][0]=x--;
	returner[0][1]=y;
	
	// right
	returner[0][0]=x;
	returner[0][1]=y++;
	
	// down
	returner[0][0]=x++;
	returner[0][1]=y;
	
	// left
	returner[0][0]=x;
	returner[0][1]=y--;
	
	
	// now change if out of bounds
	// up
	if( x<=0)
	{
		returner[0][0] = -1;
		returner[0][1] = -1;
	}
	//down
	if( x>=50)
	{
		returner[2][0] = -1;
		returner[2][1] = -1;
	}
	
	// left
	if( y<=0)
	{
		returner[3][0] = -1;
		returner[3][1] = -1;
	}
	// right
	if( y>=50)
	{
		returner[1][0] = -1;
		returner[1][1] = -1;
	}
	return returner;
	
}

// we will need to have a function  that returns the x y in an array in a
// P array thats what this does
public static int[][] maxIn( double[][] compare)
{
	double  ret =0;
	int[][] point = new int[1][1];
	for(int i=0;i<50;i++)
	{
		for(int j=0;j<50;j++)
		{
			if(compare[i][j] > ret)
			{
				ret= compare[i][j];
				point[0][0]=i;
				point[0][1]=j;
			}
		}
	}
	return point;
}


// we willneed a fuction that returns the max of adj points aswell


public static int[][] maxOfAdj( double[][] compare, int x ,int y)
{
	double  ret =0;
	int[][] point =searchAdj( x,y);
	int[] returner = new {0,0};
	
	for(int i=0;i<4;i++)
	{
		if(point[i][0]<0 || point[i][1]<0)
		{
			continue;
		}
		else
		{
			if(compare[point[i][0]][point[i][1]] > ret)
			{
				ret= compare[point[i][0]][point[i][1]];
				returner[0]=point[i][0];
				returner[1]=point[i][1];
			}
		}
	}
	return returner;
}
// this fuction returns the point that has the max P out of the whole array of points

public static int[][] maxOfPointArray(double compare, int[][] point)
{
	double  ret =0;
	
	int[] returner = new {0,0};
	
	for(int i=0;i<point.length;i++)
	{
		if(point[i][0]<0 || point[i][1]<0)
		{
			continue;
		}
		else
		{
			if(compare[point[i][0]][point[i][1]] > ret)
			{
				ret= compare[point[i][0]][point[i][1]];
				returner[0]=point[i][0];
				returner[1]=point[i][1];
			}
		}
	}
	return returner;

}
  public static boolean examine( char[][] grid, int x,int y)
  {
  	// false negative stuff here 
	double random = Math.random();// recall its between 0-1
	//
	
	if( x==targetx && y=targety)
	{
		if( grid[x][y]=='F' && random <=.1)
		{
		return false;
		}
		if(grid[x][y]=='H' && random <=.3)
		{
		return false;
		}
		if(grid[x][y]=='O' && random <=.7)
		{
		return false;
		}
		if(grid[x][y]=='C' && random <=.9)
		{
		return false;
		}
		return true;
	}
	else
	{
		return false ;
	}
}
  public static void printChar( char[][] temp)
{
	
	
	for(int i=0;i<temp.length;i++)
	{
		for(int j=0;j<temp.length;j++)
		{
			System.out.print(""+temp[i][j]+" ");
		}
		System.out.println();
	}
}
  public static char[][] genArray(int n) {
		char [][] arr= new int [n][n];
		double rand;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(i==0 && j==0) {
					arr[i][j]=0;
					continue;
				}
				if(i==n-1 && j==n-1) {
					arr[i][j]=0;
					continue;
				}
				rand=Math.random();
				if(rand<=.2) {
					arr[i][j]='F';
					
				}
				else
				{
				if(rand<=.5) {
					arr[i][j]='H';
				}
				else
				{
					if(rand<=.8)
					{
						arr[i][j]='O';
					}
					else
					{
					arr[i][j]='C';
					}
				}
				}
				
			}
		}
		return arr;
	}

  
}