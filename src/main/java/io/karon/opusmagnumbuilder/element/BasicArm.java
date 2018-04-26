package io.karon.opusmagnumbuilder.element;

import java.util.List;

public class BasicArm implements Mechanism {
	public List<Operation> availableOperations;

	public BasicArm(List<Operation> availableOperations) {
		this.availableOperations = availableOperations;
	}

	@Override
	public String toDisplayString() {
		return "A";
	}
}
