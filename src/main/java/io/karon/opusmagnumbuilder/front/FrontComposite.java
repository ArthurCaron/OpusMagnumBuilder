package io.karon.opusmagnumbuilder.front;

import io.karon.opusmagnumbuilder.Grid;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class FrontComposite extends Composite {
	private static final int MIN_CANVAS_WIDTH = 1000;

	private final Grid grid;
	private final Canvas canvas;

	public FrontComposite(Shell parent, int style, final int shellWidth, final int shellHeight, Grid grid) {
		super(parent, style);

		this.grid = grid;

		GridLayout compositeLayout = new GridLayout(1, false);
		setLayout(compositeLayout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		canvas = new Canvas(this, SWT.DOUBLE_BUFFERED);
		GridData canvasGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		canvasGridData.minimumWidth = MIN_CANVAS_WIDTH;
		canvas.setLayoutData(canvasGridData);

		GridLayout canvasGridLayout = new GridLayout(1, false);
		canvas.setLayout(canvasGridLayout);

		canvas.addPaintListener(new HexPaintListener(getDisplay(), shellWidth, shellHeight, grid));
		canvas.addMouseListener(getOnClickMouseAdapter());
		canvas.redraw();
	}

	private MouseAdapter getOnClickMouseAdapter() {
		return new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent event) {
				int x = event.x;
				int y = event.y;
			}
		};
	}
}