package gear;

import java.util.HashMap;

public class GearList {
	
	// contains all of the gear
	
	private  HashMap<String, Gear> gearList;
	
	// get the gear.
	public HashMap<String, Gear> getGear()
	{
		return this.gearList;
	}
	
	// add a gear to the slot.
	
	public void addGear(Gear gear)
	{
		gearList.put("" + gearList.size(), gear);
	}
}
