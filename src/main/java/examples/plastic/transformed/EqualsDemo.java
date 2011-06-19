package examples.plastic.transformed;

import examples.plastic.annotations.ImplementsEqualsHashCode;

@ImplementsEqualsHashCode
public class EqualsDemo {

	private int intValue;

	private String stringValue;

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
}
