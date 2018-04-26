package io.karon.opusmagnumbuilder;

import org.codetome.hexameter.core.api.*;
import org.codetome.hexameter.core.backport.Optional;

// It would be nice to have a list of all hex that have elements in them, instead of checking everything
public class Grid {
	public static final int DEFAULT_GRID_WIDTH = 15;
	public static final int DEFAULT_GRID_HEIGHT = 15;
	public static final int DEFAULT_RADIUS = 30;
	public static final HexagonOrientation DEFAULT_HEXAGON_ORIENTATION = HexagonOrientation.POINTY_TOP;
	public static final HexagonalGridLayout DEFAULT_HEXAGONAL_GRID_LAYOUT = HexagonalGridLayout.RECTANGULAR;

	public final int width;
	public final int height;
	public final int radius;
	public final HexagonOrientation hexagonOrientation;
	public final HexagonalGridLayout hexagonalGridLayout;

	public final HexagonalGrid<SatelliteDataImpl> hexagonalGrid;
	public final HexagonalGridCalculator<SatelliteDataImpl> hexagonalGridCalculator;

	public Grid() {
		this(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT, DEFAULT_RADIUS, DEFAULT_HEXAGON_ORIENTATION, DEFAULT_HEXAGONAL_GRID_LAYOUT);
	}

	public Grid(int width, int height, int radius, HexagonOrientation hexagonOrientation, HexagonalGridLayout hexagonalGridLayout) {
		this.width = width;
		this.height = height;
		this.radius = radius;
		this.hexagonOrientation = hexagonOrientation;
		this.hexagonalGridLayout = hexagonalGridLayout;

		HexagonalGridBuilder builder = new HexagonalGridBuilder()
				.setGridWidth(this.width)
				.setGridHeight(this.height)
				.setRadius(this.radius)
				.setOrientation(this.hexagonOrientation)
				.setGridLayout(this.hexagonalGridLayout);

		hexagonalGrid = builder.build();
		hexagonalGridCalculator = builder.buildCalculatorFor(hexagonalGrid);
	}

	public Grid copy() {
		Grid grid = new Grid(this.width, this.height, this.radius, this.hexagonOrientation, this.hexagonalGridLayout);
		for (Hexagon<SatelliteDataImpl> hexagon : this.hexagonalGrid.getHexagons()) {
			Optional<SatelliteDataImpl> optionalSatelliteData = hexagon.getSatelliteData();
			if (optionalSatelliteData.isPresent()) {
				SatelliteDataImpl satelliteData = optionalSatelliteData.get();
				if (satelliteData.hasElement()) {
					grid.setByCubeCoordinate(hexagon.getCubeCoordinate(), satelliteData);
				}
			}
		}
		return grid;
	}

	public void setByCubeCoordinate(CubeCoordinate cubeCoordinate, SatelliteDataImpl satelliteData) {
		hexagonalGrid.getByCubeCoordinate(cubeCoordinate).ifPresent(
				hexagon -> hexagon.setSatelliteData(satelliteData)
		);
	}

	public Iterable<Hexagon<SatelliteDataImpl>> getHexagonsForPrinting() {
		return hexagonalGrid.getHexagons();
	}

	public String toUniqueString() {
		StringBuilder stringBuilder = new StringBuilder();

		for (Hexagon<SatelliteDataImpl> hexagon : this.hexagonalGrid.getHexagons()) {
			Optional<SatelliteDataImpl> optionalSatelliteData = hexagon.getSatelliteData();
			if (optionalSatelliteData.isPresent()) {
				SatelliteDataImpl satelliteData = optionalSatelliteData.get();
				if (satelliteData.hasElement()) {
					stringBuilder.append(hexagon.getGridX());
					stringBuilder.append(",");
					stringBuilder.append(hexagon.getGridY());
					stringBuilder.append(",");
					stringBuilder.append(satelliteData.getElement().toDisplayString());
					stringBuilder.append("|");
				}
			}
		}

		return stringBuilder.toString();
	}
}
