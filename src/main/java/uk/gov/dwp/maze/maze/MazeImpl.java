package uk.gov.dwp.maze.maze;

import uk.gov.dwp.maze.constants.AppConstants;
import uk.gov.dwp.maze.enums.GridLocationType;
import uk.gov.dwp.maze.exception.InvalidMazeLocation;
import uk.gov.dwp.maze.gridbuilder.GridLocation;
import uk.gov.dwp.maze.gridbuilder.GridResult;
import uk.gov.dwp.maze.gridbuilder.Point;

public class MazeImpl implements Maze{

  private GridResult gridResult;

  public MazeImpl(GridResult gridResult){
    this.gridResult = gridResult;
  }

  public GridLocation getStartLocation() {
    return this.gridResult.getStartLocation();
  }

  public GridLocation getFinishLocation() {
    return this.gridResult.getFinishLocation();
  }

  @Override
  public boolean isValidMazeLocation(int xCoord, int yCoord) {
    try {
      return this.getLocationByCoordinate(xCoord , yCoord) != null;
    } catch (InvalidMazeLocation invalidMazeLocation){
      return false;
    }
  }

  @Override
  public void markVistedMazeLocation(int xCoord, int yCoord) {
    if (isValidMazeLocation(xCoord, yCoord)){
      if (AppConstants.LOCATION_PARTIALLY_VISITED_MARK.equalsIgnoreCase(this.getMarkAtVisitedLocation(xCoord, yCoord))) {
        this.gridResult.getGrid()[yCoord][xCoord] = AppConstants.LOCATION_FULLY_VISITED_MARK;
      } else {
        this.gridResult.getGrid()[yCoord][xCoord] = AppConstants.LOCATION_PARTIALLY_VISITED_MARK;
      }
    }
  }

  @Override
  public String getMarkAtVisitedLocation(int xCoord, int yCoord) {
    if (isValidMazeLocation(xCoord, yCoord)){
      return this.gridResult.getGrid()[yCoord][xCoord];
    }
    return null;
  }

  @Override
  public void setMarkAtMazeLocation(String mark, int xCoord, int yCoord) {
    if (isValidMazeLocation(xCoord, yCoord)) {
      this.gridResult.getGrid()[yCoord][xCoord] = mark;
    } else {
      throw new InvalidMazeLocation("mark cannot be set at invalid grid location");
    }
  }


  public int getNumberOfWalls() {
    return this.gridResult.getNumberOfWalls();
  }
  public int getNumberOfSpaces() {
    return this.gridResult.getNumberOfSpaces();
  }

  public GridLocation getLocationByCoordinate(int xCoord, int yCoord) {

    GridLocation location;
    if (xCoord >= 0  && yCoord >= 0 && yCoord < this.gridResult.getGrid().length
        && xCoord < this.gridResult.getGrid()[0].length ){

      Point point = new Point(xCoord, yCoord);
      String gridLocTypeStr = this.gridResult.getGrid()[yCoord][xCoord];

      gridLocTypeStr = gridLocTypeStr.equalsIgnoreCase(AppConstants.START_MARK)
          ||gridLocTypeStr.equalsIgnoreCase(AppConstants.FINISH_MARK)
          ||gridLocTypeStr.equalsIgnoreCase(AppConstants.LOCATION_PARTIALLY_VISITED_MARK)
          || gridLocTypeStr.trim().equals("") ? GridLocationType.SPACE.name() : GridLocationType.WALL.name();
      GridLocationType gridLocationType = GridLocationType.valueOf(gridLocTypeStr);
      location = new GridLocation(point, gridLocationType);

    } else {
      String errMsg = String.format( "The requested maze location is invalid: XCoord: %d, YCoord: %d", xCoord, yCoord);
      throw new  InvalidMazeLocation(errMsg);
    }

    return location;
  }

  @Override
  public String toString() {
     StringBuilder stringBuilder = new StringBuilder("");
    for(int row = 0; row < this.gridResult.getGrid().length; row++){
      for(int col = 0; col < this.gridResult.getGrid()[0].length; col++){
        stringBuilder.append(this.gridResult.getGrid()[row][col]);
      }
      stringBuilder.append("\n");
    }

    return stringBuilder.toString();
  }
}
