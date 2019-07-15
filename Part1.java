import java.util.*;

public class Part1 {
	
	//main function creates 100 grids for each probability increment of 0.05 and gives the amount that were solvable for each

	
	public static void main(String[] args){
		int n=100;
		double p=.25;
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
	
	//recode of part0

	
	public static boolean isSolvable(int[][] grid) {
		int n=grid.length;
		MinHeap<Coordinates> openList= new MinHeap<Coordinates>();
		ArrayList<Coordinates> closedList= new ArrayList<Coordinates>();
		Coordinates temp= new Coordinates(0,0,n);
		closedList.add(temp);
		openList.insert(temp);
		boolean finished=false;
		while(openList.size()!=0) {
			temp = openList.deleteMin();
			finished=addNeighbors(temp,openList,grid,closedList);
			if(finished==true) {
				return true;
			}
		}
		return false;
	}
	
	//adds neighbors of expanded node

	
	public static boolean addNeighbors(Coordinates position, MinHeap<Coordinates> openList, int[][] grid, ArrayList<Coordinates> closedList) {
		int x=position.x;
		int y=position.y;
		int n=grid.length;
		Coordinates a= new Coordinates(x-1,y,n);
		if(a.x<n && a.x>=0 && a.y<n && a.y>=0) {
			if(closedList.contains(a)==false) {
				if(grid[a.x][a.y]==0) {
					openList.insert(a);
					closedList.add(a);
				}
			}
		}
		Coordinates b= new Coordinates(x+1,y,n);
		if(b.x<n && b.x>=0 && b.y<n && b.y>=0) {
			if(closedList.contains(b)==false) {
				if(grid[b.x][b.y]==0) {
					openList.insert(b);
					closedList.add(b);
				}
			}
		}
		Coordinates c= new Coordinates(x,y-1,n);
		if(c.x<n && c.x>=0 && c.y<n && c.y>=0) {
			if(closedList.contains(c)==false) {
				if(grid[c.x][c.y]==0) {
					openList.insert(c);
					closedList.add(c);
				}
			}
		}
		Coordinates d= new Coordinates(x,y+1,n);
		if(d.x<n && d.x>=0 && d.y<n && d.y>=0) {
			if(closedList.contains(d)==false) {
				if(grid[d.x][d.y]==0) {
					openList.insert(d);
					closedList.add(d);
				}
			}
		}
		boolean finished=false;
		Coordinates test= new Coordinates(n-1,n-1,n);
		if(a.equals(test)||b.equals(test)||c.equals(test)||d.equals(test)) {
			finished=true;
		}
		return finished;
	}	
}
