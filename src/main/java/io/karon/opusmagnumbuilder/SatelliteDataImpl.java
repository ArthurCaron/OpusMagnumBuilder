package io.karon.opusmagnumbuilder;

import io.karon.opusmagnumbuilder.element.Element;
import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

public class SatelliteDataImpl extends DefaultSatelliteData {
	private Element element;
	private boolean hasElement;

	public SatelliteDataImpl() {}

	public SatelliteDataImpl(Element element) {
		this.element = element;
	}

	public Element getElement() { return element; }

	public void setElement(Element element) { this.element = element; }

	public boolean hasElement() { return element != null; }
}
