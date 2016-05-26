package hw8;

import java.util.Comparator;

public class LocationComparator implements Comparator<Location> {

	@Override
	public int compare(Location o1, Location o2) {
		// TODO Auto-generated method stub
		return o1.shortName().compareTo(o2.shortName());
	}

}
