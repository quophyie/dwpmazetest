package uk.gov.dwp.maze.tests.guidance;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.gov.dwp.maze.enums.GridLocationType;
import uk.gov.dwp.maze.enums.Instruction;
import uk.gov.dwp.maze.enums.Orientation;
import uk.gov.dwp.maze.exception.InvalidMazeLocation;
import uk.gov.dwp.maze.explorer.Explorer;
import uk.gov.dwp.maze.gridbuilder.*;
import uk.gov.dwp.maze.guidance.GuidanceSystemImpl;
import uk.gov.dwp.maze.guidance.GuidanceSystem;
import uk.gov.dwp.maze.location.LocationEngine;
import uk.gov.dwp.maze.location.LocationEngineImpl;
import uk.gov.dwp.maze.maze.Maze;
import uk.gov.dwp.maze.maze.MazeImpl;
import uk.gov.dwp.maze.orientationmanager.OrientationManager;
import uk.gov.dwp.maze.orientationmanager.OrientationManagerImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GuidanceSystemTests {

  @Rule
  public ExpectedException thrown  = ExpectedException.none();
  private GuidanceSystem guidanceSystem;
  private GridBuilder gridBuilder;
  private Maze maze;
  private List<String> gridLines;
  private OrientationManager orientationManager;
  private LocationEngine locationEngine;
  private Explorer explorer;

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
    orientationManager = new OrientationManagerImpl();
    locationEngine = new LocationEngineImpl(maze);
    guidanceSystem = new GuidanceSystemImpl(maze, orientationManager, locationEngine);

    GridLocation gridLocation = new GridLocation(new Point(3, 3), GridLocationType.SPACE);
    explorer = new Explorer(gridLocation);

  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullMazeInConstructor(){
   String errMsg = "The parameter 'maze' cannot be null";
   thrown.expectMessage(errMsg);
   thrown.expect(IllegalArgumentException.class);
   new GuidanceSystemImpl(null, orientationManager, locationEngine);
  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullOrientationManagerInConstructor(){
    String errMsg = "The parameter 'orientationManager' cannot be null";
    thrown.expectMessage(errMsg);
    thrown.expect(IllegalArgumentException.class);
    new GuidanceSystemImpl(maze, null, locationEngine);
  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullLocationEngineInConstructor(){
    String errMsg = "The parameter 'locationEngine' cannot be null";
    thrown.expectMessage(errMsg);
    thrown.expect(IllegalArgumentException.class);
    new GuidanceSystemImpl(maze, orientationManager, null);
  }

  @Test
  public void shouldChangeExplorerOrientationToEastGivenRightTurnInstructionOnCurrentExplorerOrientationOfNorthOnTurn(){
    guidanceSystem.turn(Instruction.RIGHT, explorer);
    assertEquals(Orientation.EAST, explorer.getOrientation());
  }

  @Test
  public void shouldChangeExplorerOrientationToWestGivenRightTurnInstructionOnCurrentExplorerOrientationOfNorthOnTurn(){
    guidanceSystem.turn(Instruction.LEFT, explorer);
    assertEquals(Orientation.WEST, explorer.getOrientation());
  }

  @Test
  public void shouldMoveExplorer1PositionToNorthGivenCurrentExplorerOrientationOfNorthOnMove(){
    Point point = new Point(1, 3);
    GridLocation explorerLocation = explorer.getLocation();
    explorerLocation.setPoint(point);
    explorer.setLocation(explorerLocation);
    explorer.setOrientation(Orientation.NORTH);
    guidanceSystem.move(explorer);
    assertEquals(1, explorer.getLocation().getPoint().getX());
    assertEquals(2, explorer.getLocation().getPoint().getY());
  }

  @Test
  public void shouldMoveExplorer1PositionToSouthGivenCurrentExplorerOrientationOfSouthOnMove(){
    Point point = new Point(1, 3);
    GridLocation explorerLocation = explorer.getLocation();
    explorerLocation.setPoint(point);
    explorer.setLocation(explorerLocation);
    explorer.setOrientation(Orientation.SOUTH);
    guidanceSystem.move(explorer);
    assertEquals(1, explorer.getLocation().getPoint().getX());
    assertEquals(4, explorer.getLocation().getPoint().getY());
  }
  @Test
  public void shouldMoveExplorer1PositionToWestGivenCurrentExplorerOrientationOfWestOnMove(){
    Point point = new Point(4, 3);
    GridLocation explorerLocation = explorer.getLocation();
    explorerLocation.setPoint(point);
    explorer.setLocation(explorerLocation);
    explorer.setOrientation(Orientation.WEST);
    guidanceSystem.move(explorer);
    assertEquals(3, explorer.getLocation().getPoint().getX());
    assertEquals(3, explorer.getLocation().getPoint().getY());
  }

  @Test
  public void shouldMoveExplorer1PositionToEastGivenCurrentExplorerOrientationOfEastOnMove(){
    explorer.setOrientation(Orientation.EAST);
    guidanceSystem.move(explorer);
    assertEquals(4, explorer.getLocation().getPoint().getX());
    assertEquals(3, explorer.getLocation().getPoint().getY());
  }

  @Test
  public void shouldThrowInvalidMazeLocationGivenCurrentExplorerOrientationOfWestOnMove(){
    String errMsg = "Cannot move to the requested location";
    thrown.expectMessage(errMsg);
    thrown.expect(InvalidMazeLocation.class);
    explorer.setOrientation(Orientation.WEST);
    guidanceSystem.move(explorer);
  }

  @Test
  public void shouldReturnTrueGivenCurrentExplorerOrientationOfEastOnCanMove(){
    Point point = new Point(4, 3);
    GridLocation locationToTest = new GridLocation(point, GridLocationType.SPACE);
    explorer.setOrientation(Orientation.EAST);
    boolean result = guidanceSystem.canMove(locationToTest);
    assertTrue(result);
  }

  @Test
  public void shouldReturnTrueGivenCurrentExplorerOrientationOfWestOnCanMove(){
    Point point = new Point(2, 3);
    GridLocation locationToTest = new GridLocation(point, GridLocationType.WALL);
    explorer.setOrientation(Orientation.WEST);
    boolean result = guidanceSystem.canMove(locationToTest);
    assertFalse(result);
  }

  @Test
  public void shouldReturnGridLocationOfTypeWALLGivenCurrentExplorerOrientationOfNorthOnPeekNextForwardMove(){
    explorer.setOrientation(Orientation.NORTH);
    GridLocation result = guidanceSystem.peekNextForwardLocation(explorer);
    assertEquals(3, result.getPoint().getX());
    assertEquals(2, result.getPoint().getY());
    assertEquals(GridLocationType.WALL, result.getGridLocationType());
  }

  @Test
  public void shouldReturnGridLocationOfTypeSPACEGivenCurrentExplorerOrientationOfEastOnPeekNextForwardMove(){
    explorer.setOrientation(Orientation.EAST);
    GridLocation result = guidanceSystem.peekNextForwardLocation(explorer);
    assertEquals(4, result.getPoint().getX());
    assertEquals(3, result.getPoint().getY());
    assertEquals(GridLocationType.SPACE, result.getGridLocationType());
  }

  @Test
  public void shouldReturnListOfGridLocationsOfSingleItemOfTypeSPACEGivenCurrentExplorerOrientationOfEastOnGetValidMoves(){
    explorer.setOrientation(Orientation.EAST);
    List<GridLocation> result = guidanceSystem.getValidMoves(explorer);
    assertEquals(1, result.size());
    assertEquals(4, result.get(0).getPoint().getX());
    assertEquals(3, result.get(0).getPoint().getY());
    assertEquals(GridLocationType.SPACE, result.get(0).getGridLocationType());
  }

  @Test
  public void shouldReturnListOfGridLocationsContaining2ItemOfTypeSPACEGivenCurrentExplorerOrientationOfSouthOnGetValidMoves(){
    Point point = new Point(1, 3);
    GridLocation explorerLocation = explorer.getLocation();
    explorerLocation.setPoint(point);
    explorer.setLocation(explorerLocation);
    explorer.setOrientation(Orientation.SOUTH);

    List<GridLocation> result = guidanceSystem.getValidMoves(explorer);
    assertEquals(2, result.size());

    assertEquals(1, result.get(0).getPoint().getX());
    assertEquals(4, result.get(0).getPoint().getY());
    assertEquals(GridLocationType.SPACE, result.get(0).getGridLocationType());

    assertEquals(1, result.get(1).getPoint().getX());
    assertEquals(2, result.get(1).getPoint().getY());
    assertEquals(GridLocationType.SPACE, result.get(1).getGridLocationType());
  }


}
