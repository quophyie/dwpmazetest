package uk.gov.dwp.maze.tests.location;

import uk.gov.dwp.maze.enums.GridLocationType;
import uk.gov.dwp.maze.enums.Orientation;
import uk.gov.dwp.maze.explorer.Explorer;
import uk.gov.dwp.maze.gridbuilder.*;
import uk.gov.dwp.maze.location.LocationEngine;
import uk.gov.dwp.maze.location.LocationEngineImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.gov.dwp.maze.maze.Maze;
import uk.gov.dwp.maze.maze.MazeImpl;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class LocationEngineTests {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private LocationEngine locationEngine;
  int xCoord, yCoord ;
  private GridLocation gridLocation;
  private Explorer explorer;
  private GridBuilder gridBuilder;
  private Maze maze;
  private List<String> gridLines;

  @Before
  public void setUp(){
    this.gridBuilder = new GridBuilderImpl();
    gridLines = new ArrayList<>();
    gridLines.add("XXXXXXXXXXXXXXX");
    gridLines.add("X             X");
    gridLines.add("X XXXXXXXXXXX X");
    gridLines.add("X XS        X X");
    gridLines.add("X XXXXXXXXX X X");
    gridLines.add("X XXXXXXXXX X X");
    gridLines.add("X XXXX      X X");
    gridLines.add("X XXXX XXXX X X");
    gridLines.add("X XXXX XXXX X X");
    gridLines.add("X X    XXXXXX X");
    gridLines.add("X X XXXXXXXXX X");
    gridLines.add("X X XXXXXXXXX X");
    gridLines.add("X X         X X");
    gridLines.add("X XXXXXXXXX   X");
    gridLines.add("XFXXXXXXXXXXXXX");

    GridResult gridResult = gridBuilder.buildGrid(gridLines);

    maze = new MazeImpl(gridResult);
    locationEngine = new LocationEngineImpl(maze);
    xCoord = 3;
    yCoord = 3;
    gridLocation = new GridLocation(new Point(xCoord, yCoord), GridLocationType.SPACE);
    explorer = new Explorer(gridLocation);
  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullGridLocation(){
    String message = "gridLocation cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    locationEngine.retrieveNextLocation(Orientation.EAST, null);
  }


  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullOrientation(){
    String message = "orientation cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    locationEngine.retrieveNextLocation(null, gridLocation);
  }

  @Test
  public void shouldReturnGridLocationWhereYCoordValueHasDecreasedByOneGivenOrientationOfNorth(){
    GridLocation result = locationEngine.retrieveNextLocation(Orientation.NORTH, gridLocation);
    assertEquals(yCoord  - 1, result.getPoint().getY());
  }

  @Test
  public void shouldReturnGridLocationWhereXCoordValueHasIncreasedByOneGivenOrientationOfEast(){
    GridLocation result = locationEngine.retrieveNextLocation(Orientation.EAST, gridLocation);
    assertEquals(xCoord +1, result.getPoint().getX());
  }

  @Test
  public void shouldReturnGridLocationWhereYCoordValueHasIncreasedByOneGivenOrientationOfSouth(){

    GridLocation result = locationEngine.retrieveNextLocation(Orientation.SOUTH, gridLocation);
    assertEquals(yCoord  + 1, result.getPoint().getY());
  }

  @Test
  public void shouldReturnGridLocationWhereXCoordValueHasDecreasedByOneGivenOrientationOfWest(){
    GridLocation result = locationEngine.retrieveNextLocation(Orientation.WEST, gridLocation);
    assertEquals(xCoord  - 1, result.getPoint().getX());
  }

}
