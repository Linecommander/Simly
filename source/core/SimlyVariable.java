
package Simly.core;

public class SimlyVariable extends SimlyObject {

	private String variableName = null;
	private SimlyValue	variableValue = null;
	

	public String getVariableName() {
		return variableName;
	}
	

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public SimlyValue getVariableValue() {
		return variableValue;
	}
	

	public void setVariableValue(SimlyValue variableValue) {
		this.variableValue = variableValue;
	}
	
}
