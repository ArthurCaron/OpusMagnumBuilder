package io.karon.opusmagnumbuilder;

import io.karon.opusmagnumbuilder.front.FrontComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {
	public static void main(String[] args) {
		Grid grid = Solver.solvePuzzle();

//		printResult(grid);
	}

	private static void printResult(Grid grid) {
		Display display = new Display();
		final Shell shell = new Shell(display);

		final int shellWidth = 1200;
		final int shellHeight = 900;

		shell.setSize(shellWidth, shellHeight);
		shell.setLayout(new GridLayout(1, false));

		new FrontComposite(shell, SWT.NONE, shellWidth, shellHeight, grid);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
