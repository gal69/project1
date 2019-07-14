
	public class Coordinates{
		int x;
		int y;
		int dim;
		double weight=0;
		public Coordinates(int first, int second, int third){
			x=first;
			y=second;
			dim=third;
			weight=(double)dim;
		}
		public Coordinates(Coordinates a){
			x=a.x;
			y=a.y;
			dim=a.dim;
			weight= a.weight;
		}
		public void setThis(int i,int j)
		{
			this.x=i;
			this.y=j;
		}
		public boolean equals(Coordinates a, Coordinates b) {
			if(a.x==b.x && a.y==b.y) {
				return true;
			}	
			return false;
		}
		public boolean isEqual(Coordinates a, Coordinates b) {
			if(a.x==b.x && a.y==b.y) 
			{
				return true;
			}	
			return false;
		}
	
		public int compareTo(Coordinates a, Coordinates b) {
			if(isEqual(a,b)) {
				return 0;
			}
			return 1;	
		}
		public String toString()
		{
			return ("("+this.x +","+this.y+") " );
		}
	}

