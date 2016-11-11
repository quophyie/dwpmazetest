package uk.gov.dwp.maze.location;


import uk.gov.dwp.maze.enums.Orientation;
import uk.gov.dwp.maze.exception.InvalidMazeLocation;
import uk.gov.dwp.maze.gridbuilder.GridLocation;
import uk.gov.dwp.maze.gridbuilder.Point;
import uk.gov.dwp.maze.maze.Maze;

public class LocationEngineImpl implements LocationEngine {

  private Maze maze;
  public LocationEngineImpl(Maze maze){
    this.maze = maze;

  }
  /**
   * Moves the explorer one step in its current orientation
   * @param orientation
   * @param gridLocation
   * @return
   */
  @Override
  public GridLocation retrieveNextLocation(Orientation orientation, GridLocation gridLocation) {
    Point point;
    GridLocation mazeLocation, location;

    if (gridLocation == null)
      throw new IllegalArgumentException("gridLocation cannot be null");

    if (orientation == null)
      throw new IllegalArgumentException("orientation cannot be null");

    switch(orientation){

      case NORTH: {
        point = new Point(gridLocation.getPoint().getX(), gridLocation.getPoint().getY() - 1);
        break;
      }
      case EAST: {
        point = new Point(gridLocation.getPoint().getX() + 1, gridLocation.getPoint().getY());
        break;
      }

      case SOUTH: {
        point = new Point(gridLocation.getPoint().getX(), gridLocation.getPoint().getY() + 1);
        break;
      }

      case WEST: {
        point = new Point(gridLocation.getPoint().getX() - 1, gridLocation.getPoint().getY());
       break;
      }
      default:{
        point = new Point(gridLocation.getPoint().getX(), gridLocation.getPoint().getY());
      }
    }

    try{
      mazeLocation = maze.getLocationByCoordinate(point.getX(), point.getY());
    } catch (InvalidMazeLocation invalidMazeLocation){
     return null;
    }

    location =  new GridLocation(point , mazeLocation.getGridLocationType());
    return location;
  }
}
