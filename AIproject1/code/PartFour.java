import java.util.*;
import java.util.concurrent.TimeUnit;


//almost exactly the same as part three, except with a nanosecond timer
//and also the reverse A* implementation (Comparison)
public class PartFour
{


public static int[][] grid;
public static char[][] knwgrid;
public static double[][] ggrid;
public static double[][] huegrid;
public static double[][] fgrid;
public static String path="";
public static Coordinates prev;
public static Coordinates curr;
public static PriorityQueue<Coordinates> openlist= new PriorityQueue<Coordinates>(1, new CoordinatesComparator());
public static LinkedList<Coordinates> closedlist = new LinkedList<Coordinates>();
public static LinkedList<Coordinates> reallyclosed = new LinkedList<Coordinates>();

  	public static void main(String[] args)
  	{
	    final int DIM= 10;
	    final double P=.3333;
	    int running=0;
	    int found=0;
	    int notfound=0;
	    double gaverage = 0.0;
	    
	    //timer declaration for Forward A*
	    long startTime = System.nanoTime();
	    while (running<100)
	    {
	    	closedlist.clear();
	    	openlist.clear();
	    	reallyclosed.clear();
	    	prev = new Coordinates(0,0,DIM);
	    	curr = new Coordinates(0,0,DIM);
	    	path= "";
	    // we need to keep track of some stuff so just use arrays to do that
	    //initialize stuff
	    
	    intGrid(DIM, P);
	    intAllgrids(DIM);
	    //start at 0,0
	    //grid = new int[][]{ {0,0,0,0} , {1,0,1,1}, {0,0,0,1},{0,0,0,0} };
	    Coordinates start= new Coordinates(0,0,DIM);
	    start.weight =(int)DIM;
	    prev= new Coordinates(0,0,DIM);
	    boolean status = true;
	    int i=0;
	    do
	    {
	    	openlist.clear();
			closedlist.clear();
			// put all the walls back in the list
			closedlist.addAll(reallyclosed);
			 //System.out.println(closedlist+"\n");
	    	path = "";
	    	start= new Coordinates(0,0,DIM);
	    	start.weight= (int)DIM;
	    	status = ASTAR(start);
			i++;
			
	    }
		while((openlist.peek()!= null)&& i<=DIM*DIM );
	    // path should have the last ordered pair of destination if not failure
	   	 	if(!(path.equals("")) && ((prev.x==(DIM-1) && prev.y==(DIM-2) || (prev.x==(DIM-2) && prev.y==(DIM-1)))))
	    	{
	    
	    		found++;
	    		gaverage+= closedlist.size();
	    		//System.out.println("\na solution was found by: "+editPath(path)+" \n");
	    	}
	   		else
	    	{
	    		notfound++;
	    	
	       		//System.out.println("\n no path was found:\n");
	    	}
	    	running++;
	  	}
		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
		   
   /*STARTING REVERSE A STAR TESTING*/
   int reverseStarCounter = 0;
   running=0;
   found=0;
   notfound=0;
   gaverage = 0.0;
   
   long startTime2 = System.nanoTime();
  	while (running<100)
   	{
		reverseStarCounter++;
	   	closedlist.clear();
	   	openlist.clear();
	   	reallyclosed.clear();
	   	prev = new Coordinates(DIM-1,DIM-1,DIM);
	   	curr = new Coordinates(DIM-1,DIM-1,DIM);
	   	path= "";
	   // same process with no start and end points
	   //initialize stuff again, BUT DO NOT REDECLARE
	   
	   	intGrid(DIM, P);
	   	intAllgrids(DIM);
	  	//start at DIM-1,DIM-1


	   	Coordinates start= new Coordinates(DIM-1,DIM-1,DIM);
	   	start.weight =(int)DIM;
	   	prev= new Coordinates(DIM-1,DIM-1,DIM);
	   	boolean status = true;
	   	int i=0;
	   	do
	   	{
			reverseStarCounter++;
	   		openlist.clear();
			closedlist.clear();
			// put all the walls back in the list
			closedlist.addAll(reallyclosed);
			 //System.out.println(closedlist+"\n");
	   	path = "";
	   	start= new Coordinates(DIM-1,DIM-1,DIM);
	   	start.weight= (int)DIM;
	   	status = ASTAR(start);
		i++;
		reverseStarCounter = reverseStarCounter % 8;
		reverseStarCounter++;
	   	}
		while((openlist.peek()!= null)&& i<=DIM*DIM );
	 
   // path should have the last ordered pair of destination if not failure
  	 	if(!(path.equals("")) && ((prev.x==(1) && prev.y==(DIM-2) || (prev.x==(2) && prev.y==(1)))))
   		{
   
	   		found++;
	   		gaverage+= closedlist.size();
	   		reverseStarCounter++;
   		}
  		else
   		{
   			notfound++;
   			reverseStarCounter--;
   		}
   			running++;
   			reverseStarCounter++;
  	}
  long endTime2 = System.nanoTime();
  long timeElapsed2 = endTime2 - startTime2;
  
  System.out.println(" DIM: 10 total runs: 100");
  System.out.println("Total time taken for Repeated Forward A* Search (nanoseconds): " + timeElapsed);
  System.out.println("Total time taken for Repeated Reverse A* Search (nanoseconds): " + timeElapsed2);
}


// first note this IS a recursive func next if you have question its not going to be easy to explain
   // the fuction edits static varibles to make other parts easier if you need to print things out
   // as we traverse the grid
   public static  boolean ASTAR( Coordinates point)
   {
   	int x= point.x;
	int y=point.y;
	
	closedlist.add(point);
	
	if(grid[x][y]== 1)
	{
		knwgrid[x][y]='1';
	}
	else
	{
		knwgrid[x][y]='0';
	}
	// you hit a wall repeat
	
	if(grid[x][y]==1)
	{
		Coordinates wall= new Coordinates(x,y,point.dim);
		reallyclosed.add(wall);
		return false;
	}
	
	// there is no solution stop
	if(!(path.equals("")) && openlist.isEmpty() && validAdj(point).equals("0000"))
	{
		return true;
	}
	else
	{
		path= path+" "+x+" "+y+" ";
		
		// you finished stop
		if(x==y && (y==(point.dim-1) )  )
		{
			//System.out.println("dowe get here");
			// great we are at the destination
			return true;
		}
		// now we have work...
		// we need to find valid edges compute g f and hue and add to the queue
	
		
		// hue is already set so now we can go straight to g
		// this g formula is probably bad but it should not effect overal ability
		if(x==y && y==0)
		{
			ggrid[x][y]=(point.dim-1)*2;
		}
		else
		{
			ggrid[x][y]=Math.max((ggrid[prev.x][prev.y]-1),((point.dim-1)*2-x-y));
		}
		
		// recall check will be a 4 bit string of 0 or 1's a 0 means dont add
		//to the list 1 means add it
		String check= validAdj(point);
		//System.out.println(check);
		
		if(check.charAt(0)=='1')
		{
			Coordinates temp1= new Coordinates(x-1,y,point.dim);
			temp1.weight = ggrid[x][y]-1 +huegrid[x-1][y];
			openlist.add(temp1);
			
		}
		if(check.charAt(1)=='1')
		{
			//System.out.println("dowe get here");
			Coordinates temp2= new Coordinates(x,y+1,point.dim);
			temp2.weight = ggrid[x][y]-1 +huegrid[x][y+1];
			openlist.add(temp2);
			//System.out.println("do we get here too");
			//System.out.println(openlist);
		}
		if(check.charAt(2)=='1')
		{
			Coordinates temp3= new Coordinates(x+1,y,point.dim);
			temp3.weight = ggrid[x][y]-1 +huegrid[x+1][y];
			openlist.add(temp3);
		}
		if(check.charAt(3)=='1')
		{
			Coordinates temp4= new Coordinates(x,y-1,point.dim);
			temp4.weight = ggrid[x][y]-1 +huegrid[x][y-1];
			openlist.add(temp4);
		}
		//System.out.println(check);
		prev = new Coordinates(x,y, point.dim);
	
		// this should be the end it means we need to traverse more
		// but if the openlist is empty then we cant do anything
		if(openlist.peek() == null )
		{
			return true ;
		}
		else
		{
		return  ASTAR(openlist.poll()) ;
		}
	 }		
	}
    
    // kind of confusing basically it returns a binary string '0000'
    //where bits 0-3 are up right down left repectivly are valid adjacent neighbors of 
    // some point in the KNWGRID not the actual grid. ? is considered a valid neighbor
    // but 1, and out of bounds is not.
    
    // i added this later it will also check if the point is IN THE CLOSEDLIST if it is its zero
    
	public static String validAdj(Coordinates point)
	{
	 	int x=point.x;
		int y=point.y;
		int dim = point.dim;
		
		char[] r = {'1','1','1','1'};
		Coordinates point1 = new Coordinates((x-1),y, point.dim);
		if( (x-1) <0 || knwgrid[x-1][y]==1 || isThisInThat(point1,closedlist))
		{
			r[0]='0';
		}
		Coordinates point2 = new Coordinates((x+1),y, point.dim);
		if( (x+1)>=dim || knwgrid[x+1][y] ==1 ||  isThisInThat(point2,closedlist))
		{
			r[2]='0';
		}
		Coordinates point3 = new Coordinates(x,(y-1), point.dim);
		if( (y-1) <0 || knwgrid[x][y-1]==1 ||  isThisInThat(point3,closedlist))
		{
			r[3]='0';
		}
		Coordinates point4 = new Coordinates(x,(y+1), point.dim);
		if( (y+1)>=dim || knwgrid[x][y+1] ==1 ||  isThisInThat(point4,closedlist))
		{
			r[1]='0';
		}
			
		point.setThis(x,y);
		
		return ""+r[0]+r[1]+r[2]+r[3]+"";	
	}
  
  //nvm made them all one thing
	public static void intAllgrids(int dim)
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
		  		huegrid[i][j]=  Math.max( Math.abs(i-dim),Math.abs(j-dim) )/1.0 ;;
		  		fgrid[i][j]=-1.0;
	        }
	    }
	     knwgrid[0][0]='0';
	     knwgrid[dim-1][dim-1]=0;
	  }
  	public static boolean isThisInThat(Coordinates a, LinkedList<Coordinates> b)
  	{
		Iterator<Coordinates> i= b.iterator() ;
		 
		while(i.hasNext())
		{
			if( a.equals(a,(Coordinates)i.next()))
			{
				return true;
			}
			 
		}
		return false;
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

	public static String editPath(String path2) {
		try
		{
		String returner = "";
		int x,y ;
		
		StringTokenizer str = new StringTokenizer(path2, " ");
		
		while (str.hasMoreTokens())
		{
		
			returner= returner +" "+str.nextToken()+" "+str.nextToken()+"";
		
			
		}
		//now remove unneeded points
		returner = returner.substring(1,returner.length());
		//System.out.println("\n"+returner+" length of : "+returner.length());
		int i=0;
		while(i+6 < (returner.length()-1))
		{
			x= Integer.parseInt(String.valueOf(returner.charAt(i)));
			y= Integer.parseInt(String.valueOf(returner.charAt(i+2)));
			int x1 = Integer.parseInt(String.valueOf(returner.charAt(i+4)));
			int y1 = Integer.parseInt(String.valueOf(returner.charAt(i+6)));
			if((Math.abs(x1-x)+Math.abs(y1-y) )>= 2)
			{
				//System.out.println("\n on i =: "+i+" "+returner);
				//System.out.println(" x y x1 y1: "+x+""+y+""+x1+""+y1+"\n");
				returner= returner.substring(0, i)+ returner.substring(i+4);
				
				//System.out.println("\n"+returner);
				 i=0;
			}
			else
			{
				i+=4;	
			}
		}
		//re tokentize it
	     str = new StringTokenizer(returner, " ");
		String newone= null;
		while (str.hasMoreTokens())
		{
		
			newone= newone +"("+str.nextToken()+","+str.nextToken()+")->";
		
			
		}
		return newone;
		}
		catch (Exception e)
		{
			return " soultion found but path could not be constructed";
		}
		
	}
	public static void printInt( int[][] temp)
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
	public static void printDouble( double[][] temp)
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

}

