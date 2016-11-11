package uk.gov.dwp.maze.explorer;

import uk.gov.dwp.maze.constants.AppConstants;
import uk.gov.dwp.maze.enums.Instruction;
import uk.gov.dwp.maze.gridbuilder.GridLocation;
import uk.gov.dwp.maze.guidance.GuidanceSystem;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;


public class ExplorationEngineImpl implements ExplorationEngine {
  public ExplorationEngineImpl(){

  }
  @Override
  public Stack<GridLocation> explore(Explorer explorer, GuidanceSystem guidanceSystem) {
    if (guidanceSystem == null)
      throw new IllegalArgumentException("guidanceSystem cannot be null");

    if (explorer == null)
      throw new IllegalArgumentException("explorer cannot be null");

    GridLocation startLocation = guidanceSystem.getStartLocation();
    explorer.setLocation(startLocation);
    guidanceSystem.markMazeLocation(startLocation.getPoint().getX(), startLocation.getPoint().getY());

    List<GridLocation> availableMoves = guidanceSystem.getValidMoves(explorer);
    Stack<GridLocation> routeTaken = new Stack<>();

    routeTaken.push(explorer.getLocation());
    routeTaken = exploreMaze(explorer,guidanceSystem,availableMoves, routeTaken);

    return routeTaken;
  }

  private Stack<GridLocation> exploreMaze(Explorer explorer, GuidanceSystem guidanceSystem, List<GridLocation> validMoves, Stack<GridLocation> routeTaken){
    if (routeTaken.peek().equals(guidanceSystem.getFinishLocation()))
      return  routeTaken;

    GridLocation nextLocation = validMoves.get(0);
    GridLocation nextGridLocationInOrientationDirection = guidanceSystem.peekNextForwardLocation(explorer);

    while(!nextLocation.equals(nextGridLocationInOrientationDirection)){
      guidanceSystem.turn(Instruction.RIGHT,explorer);
      nextGridLocationInOrientationDirection = guidanceSystem.peekNextForwardLocation(explorer);
    }

    routeTaken.push(nextLocation);
    guidanceSystem.move(explorer);
    guidanceSystem.markMazeLocation(nextLocation.getPoint().getX(), nextLocation.getPoint().getY());

    List<GridLocation> unmarkedLocations = guidanceSystem.getValidMoves(explorer)
          .stream()
          .filter(move -> "".equals(guidanceSystem.getLocationMark(move.getPoint().getX(), move.getPoint().getY()).trim())
                       || AppConstants.FINISH_MARK.equals(guidanceSystem.getLocationMark(move.getPoint().getX(), move.getPoint().getY()).trim()))
          .collect(Collectors.toList());

    List<GridLocation> partiallyMarkedLocations = guidanceSystem.getValidMoves(explorer)
        .stream()
        .filter(move -> AppConstants.LOCATION_PARTIALLY_VISITED_MARK.equals(guidanceSystem.getLocationMark(move.getPoint().getX(), move.getPoint().getY())))
        .collect(Collectors.toList());

    unmarkedLocations.addAll(partiallyMarkedLocations);
    validMoves = unmarkedLocations;

    return exploreMaze(explorer,guidanceSystem,validMoves, routeTaken);
  }
}
