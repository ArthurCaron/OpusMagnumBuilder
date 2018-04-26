package io.karon.opusmagnumbuilder;

import io.karon.opusmagnumbuilder.element.BasicArm;
import io.karon.opusmagnumbuilder.element.Product;
import io.karon.opusmagnumbuilder.element.Reagent;
import io.karon.opusmagnumbuilder.element.ReagentType;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.backport.Optional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Solver {
	public static Grid solvePuzzle() {
		Grid initialGrid = new Grid();

		// TMP: center for 15/15 grid
		initialGrid.setByCubeCoordinate(CubeCoordinate.fromCoordinates(4, 7), new SatelliteDataImpl(new Product()));

		List<Grid> nextSteps = nextSteps(initialGrid);











		return initialGrid;
	}

	private static List<Grid> nextSteps(Grid initialGrid) {
		System.out.println("initialGrid: " + initialGrid.toUniqueString());
		List<Grid> nextSteps = new ArrayList<>();

		List<Hexagon<SatelliteDataImpl>> hexNeighbors = new ArrayList<>();
		for (Hexagon<SatelliteDataImpl> hexagon : initialGrid.hexagonalGrid.getHexagons()) {
			Optional<SatelliteDataImpl> optionalSatelliteData = hexagon.getSatelliteData();
			if (optionalSatelliteData.isPresent()) {
				SatelliteDataImpl satelliteData = optionalSatelliteData.get();
				if (satelliteData.hasElement()) {
					for (Hexagon<SatelliteDataImpl> hexNeighbor : initialGrid.hexagonalGrid.getNeighborsOf(hexagon)) {
						Optional<SatelliteDataImpl> optionalSatelliteData2 = hexNeighbor.getSatelliteData();
						if (!optionalSatelliteData2.isPresent() || !optionalSatelliteData2.get().hasElement()) {
							hexNeighbors.add(hexNeighbor);
						}
					}
				}
			}
		}

		for (Hexagon<SatelliteDataImpl> hexNeighbor : hexNeighbors) {
			Grid newGrid = initialGrid.copy();
			newGrid.setByCubeCoordinate(hexNeighbor.getCubeCoordinate(), new SatelliteDataImpl(new Reagent(ReagentType.BASIC)));
			nextSteps.add(newGrid);
			System.out.println("newGrid: " + newGrid.toUniqueString());
		}

		return nextSteps;
	}
}


// Terminology:
// Product is the end solution
// Reagent is an atom
// Mechanism is an arm
// Glyphs are the things used to transform reagents


//		 Opus Magnum 'MVP' puzzle:
//		 One atom
//		 End molecule being the same atom
//		 Only the arm that rotates
//		 The rotating arm cannot stretch, it is a fixed size, and it has an orientation
//		 The operations available are:
//		     Grab
//		     Let loose
//		     rotate by 1/6 clockwise
//		     maybe a 'stay still' operation
//
//		 I need classes for:
//		 Items (arm, atom, etc)
//		 Operations
//		 The map (hexagonal map, should read amit guide) infinite map will be problematic. Maybe just 'big enough'? That may make things simpler, and I can always update the size later
//		 The list of operations per item (or just a list in items)
//
//		 I start with an empty map (so no operations since they are in items and we have no items)
//		 I need a function that gives a list of possible 'moves'
//		 So it should add:
//		 for each item available, check if we can put more of them (atom and end molecule are limited, but arms are not)
//		 If we can put more, create one 'move' per items we can put and each position they can be put in
//		 Maybe sub divide, like have a 'block' for 'put 1 new atom on the map' and then one move per place we can put them
//		 We need to find ways to reduce the available places. Arms are straightforward I think
//		 We can just say that, since the arm has a range of 1, it should be placed at one range of an existing node
//		 It's not true because other nodes might be put down but that will be a path for later.
//		 Same thing for atom and molecules. We could automatically evaluate (and remember), for the existing solution, which nodes are reachable.
//		 For instance, if we have one arm, we can only put an atom at 1 range so it can grab it
//		 We don't have a "move existing", but we have a "remove existing" maybe?
//		 It would probably not be used much at first, but when we hit worse and worse solutions it might.
//		 Maybe the "move existing" should exists. It makes more available solutions (bad) but might give a better temp score which would be helpful.
//		 Especially if we want to move an atom. It will obviously lower the score, but not as much if we just move it.
//		 We also have the "add instruction"
//		 Cycle through items to find which ones can add an instruction
//		 How can we limit the placement? Because if it adds an instruction in 100 cycles it will be awful, but if wan pre-evaluate the solution then there is an infinite amount of operation placements.
//		 We could limit how much cycles can be added with one operation added. But which number? 10 ?
//		 I know it can be fiddle with if needed but still...
//		 We could say we can't add an operation that adds more than 1 cycle, but then maybe we need "empty" operations?
//		 Yeah, a "stay still" operation might be nice. The fitness would still go up so no infinite path.
//		 It would be nice to have a central place for things to take into account to evaluate moves.
//		 Like, every time we add an arm (or remove an arm) we update a list of "items that can take operations"
//
//		 Okay so, we generate a list of moves, for each one we calculate the fitness value and store it.
//		 Maybe we sort, for each node, by fitness value for each move available.
//
//		 We need a list of the best moves for each nodes that are at the extremities?
//
//		 We also need a function that helps us direct towards the best score.
//		 Simplest way to do that would be:
//		 For the problem at hand, calculate the theorical minimum needed.
//		 For instance, for the problem above, it would be "one grab, one let loose, one rotation" for operations and "one atom, one final molecule and one arm".
//		 We try to get there. If we have some missing, we try them first, if we have more, we try those that remove the excess first.
//
//		 Hm. Dunno if it's the best way to go about it.
//		 BRB gonna read:
//		 https://www.redblobgames.com/pathfinding/a-star/introduction.html
//		 https://www.redblobgames.com/pathfinding/a-star/implementation.html
//		 https://www.redblobgames.com/pathfinding/tower-defense/
//		 https://www.redblobgames.com/pathfinding/tower-defense/implementation.html
//		 https://www.redblobgames.com/grids/hexagons/
//		 https://www.redblobgames.com/grids/hexagons/implementation.html
//
//		 Notes:
//		 The removal (or just moving) of an item maybe should have the restrictions of adding, meaning we can't remove an item if it makes another item not respect the rules in place.
//		 If we remove an arm which makes an atom be alone, maybe it shouldn't be permitted.
//		 We also need rules for the start, because we can't put anything if there's nothing there, since they have to be close to others.
//		 The simplest way may be to start with the end molecule at the center. It makes sense since there's just one and every solution can be found anyway, doesn't matter where the end solution is
//		 Since the map is infinite.
//		 Note that it is true since we don't have the orientation of the solution to worry about (just one node for now).
//		 We'll have to revise it when rotation comes into play.
//		 Or maybe we do everything for each possible different rotation. Nice way to split the volume... Or not.
//		 We could forbid the removal/movement of the solution that way.
//		 But we may want to let it run as long as possible to find the best path, so having multiple algo running might not be a good solution.
//		 The advantage of starting with the end solution is: We only care about path that can reach it, so nice :).
//
//		Graph
//		a data structure that can tell me the neighbors for each location (see this tutorial). A weighted graph can also tell me the cost of moving along an edge.
//
//		cost being upped if we add an operation that increases the number of cycles
//		since cycles are the way to evaluate each solution
//
//		Locations
//		a simple value (int, string, tuple, etc.) that labels locations in the graph
//
//
//		Search
//		an algorithm that takes a graph, a starting location, and optionally a goal location, and calculates some useful information (visited, parent pointer, distance) for some or all locations
//
//
//		Queue
//		a data structure used by the search algorithm to decide the order in which to process the locations
//
//
//
//
//
//
//
//
//
//
//
//
//