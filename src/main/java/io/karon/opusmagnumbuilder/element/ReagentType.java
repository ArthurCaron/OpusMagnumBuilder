package io.karon.opusmagnumbuilder.element;

public enum ReagentType {
	BASIC("B");

	private String toDisplayString;

	ReagentType(String toDisplayString) {
		this.toDisplayString = toDisplayString;
	}

	public String toDisplayString() {
		return toDisplayString;
	}
}
