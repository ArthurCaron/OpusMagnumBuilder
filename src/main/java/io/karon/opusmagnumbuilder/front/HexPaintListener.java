package io.karon.opusmagnumbuilder.front;

import io.karon.opusmagnumbuilder.Grid;
import io.karon.opusmagnumbuilder.SatelliteDataImpl;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.backport.Optional;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Display;

public class HexPaintListener implements PaintListener {
	private DrawUtils drawUtils;
	private int shellWidth;
	private int shellHeight;
	private Grid grid;

	public HexPaintListener(Display display, int shellWidth, int shellHeight, Grid grid) {
		drawUtils = new DrawUtils(display, grid.radius);
		this.shellWidth = shellWidth;
		this.shellHeight = shellHeight;
		this.grid = grid;
	}

	@Override
	public void paintControl(final PaintEvent event) {
		drawUtils.drawDisplayRectangle(event.gc, shellWidth, shellHeight);

		for (Hexagon<SatelliteDataImpl> hexagon : grid.getHexagonsForPrinting()) {
			Optional<SatelliteDataImpl> data = hexagon.getSatelliteData();
			drawUtils.drawEmptyHexagon(event.gc, hexagon);
			if (data.isPresent() && data.get().hasElement()) {
				drawUtils.drawTextCenterHexagon(event.gc, hexagon, data.get().getElement().toDisplayString());
			}
		}
	}
}
