//slightly changed but should still be good as there's two constructors.
//the old pre-edit cell object class is commented out below
public class Cell {
	double terrain;
	double probOfFinding;
	boolean target;
	int x;
	int y;
	int timesSearched;
	public Cell(double c, double prob, int i, int j, boolean temp) {
		terrain=c;
		probOfFinding=prob;
		x=i;
		y=j;
		target=temp;
	}
	public Cell(double c, double prob) {
		terrain=c;
		probOfFinding=prob;
	}
}
/*public class Cell {
	
	double terrain;
	double probOfFinding;
	boolean target;
	int x;
	int y;
	
	public Cell(double c, double prob) {
		terrain=c;
		probOfFinding=prob;
		
	}
	
	
}*/
