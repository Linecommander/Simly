

package Simly.core;


public class SimlyValue extends SimlyObject{

	private Class type	= null;
	private long value = 0;
	

	public Class getType() {
		if ( type != null )
			return type;
		return long.class;
	}
	

	public void setType(Class type) {
		this.type = type;
	}
	

	public long getValue() {
		return value;
	}
	

	public void setValue(long value) {
		this.value = value;
	}
}
