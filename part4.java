import java.util.*;
import java.lang.*;
import java.util.concurrent.TimeUnit;
import Coordinates;



// coded jsut needs to be debugged now

public class part4
{


public static int[][] grid;
public static char[][] knwgrid;
public static int[][] ggrid;
public static double[][] huegrid;
public static double[][] fgrid;
public static String path="";
public  static Coordinates prev;
public static PriorityQueue<Coordinates> openlist= new PriorityQueue<Coordinates>();
public static LinkList<Coordinates> closedlist = new LinkList<Coordinates>();

	public static void main(String[] args)
	{
	    final int DIM=3;
	    final double P=.1;
	    
	    // we need to keep track of some stuff so just use arrays to do that
	    //initialize stuff for forward A star measurements
	    
	    intGrid(DIM, P);
	    intAllgrids(DIM);
	    //start at 0,0
	    
	    Coordinates start= new Coordinates(0,0,DIM);
	    start.weight =(int)DIM;
	    prev= new Coordinates(0,0,DIM);
	    boolean status = true;
	    
	    System.out.println("Starting forward A star algorithm\n");

	    long startTime = System.nanoTime();
	    while(!status)
	    {
	    	path = "";
	    	Coordinates start= new Coordinates(0,0,DIM);
			start.weight= (int)DIM;
			status = ASTAR(start);
			openlist.clear();
			closedlist.clear();
	    }
	    long endTime = System.nanoTime();
	    long timeElapsed = endTime - startTime;

	    // path should have the last ordered pair of destination if not failure
	    if(path.charAt(DIM-1)== (char)DIM)
	    {
	    	System.out.println("a solution was found by: \n\n"+path+" path\n");
	    }
	    else
	    {
	       System.out.println("\n no path was found:\n");
	    }

	    System.out.println("Execution time in nanoseconds : " + timeElapsed);

	    // do it again for reverse A star measurements
	    intGrid(DIM, P);
	    intAllgrids(DIM);
	    //start at n,n? assuming end dimensions are dim or dim-1
	    
	    Coordinates start= new Coordinates(DIM-1,DIM-1,DIM);
	    start.weight =(int)DIM;
	    prev= new Coordinates(DIM-1,DIM-1,DIM);
	    boolean status = true;
	    
	    System.out.println("Starting reverse A star algorithm\n");

	    long startTime = System.nanoTime();
	    while(!status)
	    {
	    	path = "";
	    	Coordinates start= new Coordinates(DIM-1,DIM-1,DIM);
			start.weight= (int)DIM;
			status = ASTAR(start);
			openlist.clear();
			closedlist.clear();
	    }
	    long endTime = System.nanoTime();
	    long timeElapsed = endTime - startTime;

	    // path should have the last ordered pair of destination if not failure
	    if(path.charAt(DIM-1)== (char)DIM)
	    {
	    	System.out.println("a solution was found by: \n\n"+path+" path\n");
	    }
	    else
	    {
	       System.out.println("\n no path was found:\n");
	    }

	    System.out.println("Execution time in nanoseconds : " + timeElapsed);
	}
	   
	   
	   // first note this IS a recursive func next if you have question its not going to be easy to explain
	   // the fuction edits static varibles to make other parts easier if you need to print things out
	   // as we traverse the grid
	   public boolean ASTAR( Coordinates point)
	   {
	   	int x= point.x;
		int y= point.y;
		closedlist.add(point);
		
		knwgrid[x][y]= (char)grid[x][y];
		// you hit a wall repeat
		
		if(grid[x][y]==1)
		{
			return false;
		}
		
		// there is no solution stop
		if(!(path.isEqual("")) && openlist.isEmpty() && validAdj(point).isEqual("0000"))
		{
			return true;
		}
		else
		{
			path= path+""+x+""+y+"";
			
			// you finished stop
			if(x==y && (y==(point.dim-1) )  )
			{
				// great we are at the destination
				return true;
			}
			// now we have work...
			// we need to find valid edges compute g f and hue and add to the queue
		
			
			// hue is already set so now we can go straight to g
			// this g formula is probably bad but it should not effect overal ability
			if(x==y && y==0)
			{
				ggrid[x][y]=(dim-1)*2;
			else
			{
				ggrid[x][y]=Math.max((ggrid[prev.x][prev.y]-1),((dim-1)*2-x-y));
			}
			
			// recall check will be a 4 bit string of 0 or 1's a 0 means dont add
			//to the list 1 means add it
			String check= validAdj(point);
			
			if(check.charAt(0)==1)
			{
				Coordinates temp1= new Coordinates(x-1,y,point.dim);
				temp1.weight = ggrid[x][y]-1 +huegrid[x-1][y];
				openlist.add(temp1);
				
			}
			if(check.charAt(1)==1)
			{
				Coordinates temp2= new Coordinates(x,y+1,point.dim);
				temp2.weight = ggrid[x][y]-1 +huegrid[x][y+1];
				openlist.add(temp2);
			}
			if(check.charAt(2)==1)
			{
				Coordinates temp3= new Coordinates(x+1,y,point.dim);
				temp3.weight = ggrid[x][y]-1 +huegrid[x+1][y];
				openlist.add(temp3);
			}
			if(check.charAt(3)==1)
			{
				Coordinates temp4= new Coordinates(x,y-1,point.dim);
				temp4.weight = ggrid[x][y]-1 +huegrid[x][y-1];
				openlist.add(temp4);
			}
			
			prev = new Coordinates(x,y);
			
			// this should be the end it means we need to traverse more
			return (false || ASTAR(openlist.poll()) );
	}

	    
	    
	    // kind of confusing basically it returns a binary string '0000'
	    //where bits 0-3 are up right down left repectivly are valid adjacent neighbors of 
	    // some point in the KNWGRID not the actual grid. ? is considered a valid neighbor
	    // but 1, and out of bounds is not.
	    
	    // i added this later it will also check if the point is IN THE CLOSEDLIST if it is its zero
	    
	public String validAdj(Coordinates point)
	{
	 	int x=point.x;
		int y=point.y;
		int dim = point.dim;
		
		char[] r = {'1','1','1','1'};
		
		if( (x-1) <0 || knwgrid[x-1][y]==1 || (closedlist.contains(point.setThis((x-1),y)))
		{
			r[0]='0';
		}
		if( (x+1)>=dim || knwgrid[x+1][y] ==1 || (closedlist.contains(point.setThis((x+1),y)))
		{
			r[2]='0';
		}
		if( (y-1) <0 || knwgrid[x][y-1]==1 || (closedlist.contains(point.setThis(x,(y-1))))
		{
			r[3]='0';
		}
		if( (y+1)>=dim || knwgrid[x][y+1] ==1 || (closedlist.contains(point.setThis(x,(y+1)))
		{
			r[1]='0';
		}
			
		point.setThis(x,y);
		
		return ""+r[0]+r[1]+r[2]+r[3]+"";
	}


	//nvm made them all one thing
	public void intAllgrids(int dim)
	{
	    knwgrid= new char[dim][dim];
	    huegrid= new double[dim][dim]; 
	    fgrid= new double[dim][dim];
	    ggrid= new double[dim][dim];
	     
	    for(int i =0;i<dim;i++)
	    {
	    	for(int j=0;j<dim;j++)
	      	{
	          	knwgrid[i][j]='?';
		  		ggrid[i][j]= 0;
		 		huegrid[i][j]= (Math.sqrt(Math.pow((i-point.dim),2)+Math.pow((j-point.dim),2)))+
				Math.abs(i-point.dim)+Math.abs(j-point.dim) +
				Math.max( Math.abs(i-point.dim),Math.abs(j-point.dim) ))/3.0 ;;
		 		fgrid[i][j]=-1.0;
	        }
	    }

	    knwgrid[0][0]='0';
	    knwgrid[dim-1][dim-1]=0;
	}

	public static void intGrid(int n, double p) {
			int [][] arr= new int [n][n];
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
					if(rand<=p) {
						arr[i][j]=1;
					}
					if(rand>p) {
						arr[i][j]=0;
					}
				}
			}
			grid= arr;
		}

	public void printIt( T[][] temp)
	{
		for(int i=0;i<temp.length;i++)
		{
			for(intj=0;j<temp.length;j++)
			{
				System.out.print(""+temp[i][j]+" ")
			}
			System.out.println();
		}
	}
  /* 
  public int[][] flipForFour(int[][] start, int dim)
  {
    int[][] flippped = new int[dim][dim];
    int i=0;
    int j=0;
    
    for(i=0;i<n;i++)
    {
      for(j=0;j<n;j++)
      {
          flipped[n-1-i][n-1-j]=start[i][j];
      }
    }
    return flipped;
  }
  */
  
}
