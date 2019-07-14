
import java.util.*;
public class CoordinatesComparator implements Comparator<Coordinates> {
	
	public int compare(Coordinates a, Coordinates b)
	{
		return (int)(a.weight -b.weight);
		
	}

}
