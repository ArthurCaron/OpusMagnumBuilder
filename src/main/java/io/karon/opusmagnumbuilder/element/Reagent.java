package io.karon.opusmagnumbuilder.element;

public class Reagent implements Element {
	public ReagentType reagentType;

	public Reagent(ReagentType reagentType) {
		this.reagentType = reagentType;
	}

	@Override
	public String toDisplayString() {
		return "R" + reagentType.toDisplayString();
	}
}
