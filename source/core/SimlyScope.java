
package Simly.core;

import java.util.Hashtable;

public class SimlyScope extends SimlyObject {

	private Hashtable <String,SimlyVariable> childs = new Hashtable<String,SimlyVariable>();


	public SimlyScope( SimlyObject parent )
	{
		super(parent);
	}

	public Hashtable <String,SimlyVariable> getChilds()
	{
		return childs;
	}

	public boolean pushChild(String name, SimlyVariable child)
	{
		return this.childs.put( name , child) != null;
	}
	
	
	public SimlyVariable child(String name)
	{
		return child ( name , this);
	}
	

	private SimlyVariable child(String name, SimlyScope scope)
	{
		if( scope.getChilds().containsKey( name ) )
		{
			return scope.getChilds().get( name );
		}
		else if( scope.getParent() != null && scope.getParent() instanceof SimlyScope )
		{
			return child(name, (SimlyScope)scope.getParent() );
		}
		return null;
	}
	

	public boolean existsChild(String name){
		return existsChild(name, this);
	}
	

	private boolean existsChild(String name, SimlyScope scope)
	{
		if( scope.getChilds().containsKey( name ) )
		{
			return true;
		}
		else if( scope.getParent() != null && scope.getParent() instanceof SimlyScope )
		{
			return existsChild(name, (SimlyScope) scope.getParent());
		}
		return false;
	}
}
