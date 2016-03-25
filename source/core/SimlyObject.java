
package Simly.core;


public class SimlyObject {

	private SimlyObject parent  = null;

	public SimlyObject()
	{
		
	}
	
	
	public SimlyObject (SimlyObject parent)
	{
		this.parent = parent;
	}
	
    
	public SimlyObject getParent()
	{
		return parent;
	}
}
