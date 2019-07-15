import java.util.*;

public class Part1 {
	
  //main function creates 100 grids for each probability increment of 0.05 and gives the amount that were solvable for each
  
	public static void main(String[] args){
		int n=100;
		double p=.05;
		int count=0;
		while (p<1) {
			for(int i=0;i<100;i++) {
				int a [][]=genArray(n,p);
				if(isSolvable(a)==true) {
					count+=1;
				}
			}
			System.out.println(count);
			count=0;
			p+=0.05;

		}
		return;
	}
  
  //recode of part0
  
	public static int[][] genArray(int n, double p) {
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
		return arr;
	}
	
	public static boolean isSolvable(int[][] grid) {
		int n=grid.length;
		ArrayList<Coordinates> openList= new ArrayList<Coordinates>();
		ArrayList<Coordinates> closedList= new ArrayList<Coordinates>();
		Coordinates temp= new Coordinates(0,0,n);
		closedList.add(temp);
		openList.add(temp);
		Coordinates test= new Coordinates(n-1,n-1,n);
		while(openList.size()!=0) {
			temp = openList.remove(0);
			addNeighbors(temp,openList,grid,closedList);			
			
			if(openList.contains(test)) {
				return true;
			}
		}
		return false;
	}
	
	//adds neighbors of expanded node
  
	public static void addNeighbors(Coordinates position, ArrayList<Coordinates> openList, int[][] grid, ArrayList<Coordinates> closedList) {
		int x=position.x;
		int y=position.y;
		int n=grid.length;
		Coordinates a= new Coordinates(x-1,y,n);
		if(a.x<n && a.x>=0 && a.y<n && a.y>=0) {
			if(closedList.contains(a)==false) {
				if(grid[a.x][a.y]==0) {
					openList.add(a);
					closedList.add(a);
				}
			}
		}
		Coordinates b= new Coordinates(x+1,y,n);
		if(b.x<n && b.x>=0 && b.y<n && b.y>=0) {
			if(closedList.contains(b)==false) {
				if(grid[b.x][b.y]==0) {
					openList.add(b);
					closedList.add(b);
				}
			}
		}
		Coordinates c= new Coordinates(x,y-1,n);
		if(c.x<n && c.x>=0 && c.y<n && c.y>=0) {
			if(closedList.contains(c)==false) {
				if(grid[c.x][c.y]==0) {
					openList.add(c);
					closedList.add(c);
				}
			}
		}
		Coordinates d= new Coordinates(x,y+1,n);
		if(d.x<n && d.x>=0 && d.y<n && d.y>=0) {
			if(closedList.contains(d)==false) {
				if(grid[d.x][d.y]==0) {
					openList.add(d);
					closedList.add(d);
				}
			}
		}
		return;
	}
}
