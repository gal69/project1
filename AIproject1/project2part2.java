package proj2;

import java.util.*;
import java.lang.*;
//personal ideas for how the moveTarget return of type1xtype2 can help us:
//after that report comes in (assuming a failed search) then we can scan
//the grid and look at each cell one by one checking its neighboring cells
//and keep the two cells if they match the type1xtype2 description
//however, for part 4 when the agent can only move one by one, I don't know
//how the data gathering will work for that.

public class partTwo {
	
	public static void main(String args[]) {

		double superCount=0;
		boolean search;
		for(int i=0;i<1;i++) {
			Cell[][] area=genArray();
			Cell temp=Search2(area);
			search=false;
			double p=Math.random();
			if(p>temp.terrain&&temp.target==true) {
				search=true;
			}
			int count=1;
			
			double[] types = new double[2];
			
			while(search==false) {
				//System.out.println(temp.x+", "+temp.y);
				temp=Search2(area);
				p=Math.random();
				if(p>temp.terrain&&temp.target==true) {
					search=true;
				}
				else
				{
					//moving target 1 space in a random direction
				    //returns a double[2]; NOTE:
				    //double[0] = type1; double[1] = type2;
					types = moveTarget(area);
					System.out.println("Moved target\n");
				}
				count++;
			}
			superCount+=count;
		}
		System.out.println(superCount/1);
		
		return;
	}
	
	//this is the function that is mainly most of part 2 (Moving Target)
	//At each iteration of the search, if fail:
	//target will move to a neighboring cell (With uniform prob for ea. spot)
	//returns the coords of where the target was seen (type1 x type2)
	public static double[] moveTarget(Cell[][] area)
	{
		double[] types = new double[2];
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
			if(tCoords.coordinate2 == 0)
			{
				types[0] = area[tCoords.coordinate1][tCoords.coordinate2].terrain;
				area[tCoords.coordinate1][tCoords.coordinate2].target = false;
				tCoords.coordinate2 = tCoords.coordinate2 + 1;
				area[tCoords.coordinate1][tCoords.coordinate2].target = true;
			}
			else
			{
				types[0] = area[tCoords.coordinate1][tCoords.coordinate2].terrain;
				area[tCoords.coordinate1][tCoords.coordinate2].target = false;
				tCoords.coordinate2 = tCoords.coordinate2 - 1;
				area[tCoords.coordinate1][tCoords.coordinate2].target = true;
			}
		}
		else if(rNum == 2) //right
		{
			if(tCoords.coordinate2 == 49)
			{
				types[0] = area[tCoords.coordinate1][tCoords.coordinate2].terrain;
				area[tCoords.coordinate1][tCoords.coordinate2].target = false;
				tCoords.coordinate2 = tCoords.coordinate2 - 1;
				area[tCoords.coordinate1][tCoords.coordinate2].target = true;
			}
			else
			{
				types[0] = area[tCoords.coordinate1][tCoords.coordinate2].terrain;
				area[tCoords.coordinate1][tCoords.coordinate2].target = false;
				tCoords.coordinate2 = tCoords.coordinate2 + 1;
				area[tCoords.coordinate1][tCoords.coordinate2].target = true;
			}
		}
		else if(rNum == 3) //up
		{
			if(tCoords.coordinate1 == 0)
			{
				types[0] = area[tCoords.coordinate1][tCoords.coordinate2].terrain;
				area[tCoords.coordinate1][tCoords.coordinate2].target = false;
				tCoords.coordinate1 = tCoords.coordinate1 + 1;
				area[tCoords.coordinate1][tCoords.coordinate2].target = true;
			}
			else
			{
				types[0] = area[tCoords.coordinate1][tCoords.coordinate2].terrain;
				area[tCoords.coordinate1][tCoords.coordinate2].target = false;
				tCoords.coordinate1 = tCoords.coordinate1 - 1;
				area[tCoords.coordinate1][tCoords.coordinate2].target = true;
			}
		}
		else if(rNum == 4) //down
		{
			if(tCoords.coordinate1 == 49)
			{
				types[0] = area[tCoords.coordinate1][tCoords.coordinate2].terrain;
				area[tCoords.coordinate1][tCoords.coordinate2].target = false;
				tCoords.coordinate1 = tCoords.coordinate1 - 1;
				area[tCoords.coordinate1][tCoords.coordinate2].target = true;
			}
			else
			{
				types[0] = area[tCoords.coordinate1][tCoords.coordinate2].terrain;
				area[tCoords.coordinate1][tCoords.coordinate2].target = false;
				tCoords.coordinate1 = tCoords.coordinate1 + 1;
				area[tCoords.coordinate1][tCoords.coordinate2].target = true;
			}
		}
		
		return types;
	}
	
	public static Cell Search1(Cell[][] area) {
		Cell max=area[0][0];
		double temp;
		int coor1=0;
		int coor2=0;
		for(int i=0;i<50;i++) {
			for(int j=0;j<50;j++) {
				if(area[i][j].probOfFinding>max.probOfFinding) {
					max=area[i][j];
					coor1=i;
					coor2=j;
					max.x=i;
					max.y=j;
				}
			}
		}
		temp=max.probOfFinding*(1.0-max.terrain)/2499.0;
		max.probOfFinding=max.probOfFinding*max.terrain;
		for(int i=0;i<50;i++) {
			for(int j=0;j<50;j++) {
				if(i==coor1&&j==coor2) {
					continue;
				}
				area[i][j].probOfFinding+=temp;
			}
		}
		return max;
	}
	
	public static Cell Search2(Cell[][] area) {
		Cell max=area[0][0];
		double temp;
		int coor1=0;
		int coor2=0;
		for(int i=0;i<50;i++) {
			for(int j=0;j<50;j++) {
				if(area[i][j].probOfFinding*(1.0-area[i][j].terrain)>max.probOfFinding*(1.0-max.terrain)) {
					max=area[i][j];
					coor1=i;
					coor2=j;
					max.x=i;
					max.y=j;
				}
			}
		}
		temp=max.probOfFinding*(1.0-max.terrain)/2499.0;
		max.probOfFinding=max.probOfFinding*max.terrain;
		for(int i=0;i<50;i++) {
			for(int j=0;j<50;j++) {
				if(i==coor1&&j==coor2) {
					continue;
				}
				area[i][j].probOfFinding+=temp;
			}
		}
		return max;
	}
	
	public static Cell[][] genArray() {
		Cell[][] area=new Cell[50][50];
		double k=1.0/2500.0;
		for(int i=0;i<50;i++) {
			for(int j=0;j<50;j++) {
				double p=Math.random();
				double temp=0.3;
				if(p<=.5) {
					temp=0.7;
				}
				if(p<=.2) {
					temp=0.1;
				}
				if(p>.8) {
					temp=0.9;
				}
				area[i][j]= new Cell(temp,k);
				area[i][j].target=false;
			}
		}
		area[tCoords.coordinate1][tCoords.coordinate2].target=true;
		
		return area;
	}	
	
}
