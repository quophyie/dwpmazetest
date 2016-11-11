package uk.gov.dwp.maze.tests.explorer;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.gov.dwp.maze.enums.GridLocationType;
import uk.gov.dwp.maze.explorer.ExplorationEngine;
import uk.gov.dwp.maze.explorer.ExplorationEngineImpl;
import uk.gov.dwp.maze.explorer.Explorer;
import uk.gov.dwp.maze.gridbuilder.*;
import uk.gov.dwp.maze.guidance.GuidanceSystem;
import uk.gov.dwp.maze.guidance.GuidanceSystemImpl;
import uk.gov.dwp.maze.location.LocationEngine;
import uk.gov.dwp.maze.location.LocationEngineImpl;
import uk.gov.dwp.maze.maze.Maze;
import uk.gov.dwp.maze.maze.MazeImpl;
import uk.gov.dwp.maze.orientationmanager.OrientationManager;
import uk.gov.dwp.maze.orientationmanager.OrientationManagerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.assertEquals;

public class ExplorationEngineTests {
  @Rule
  public ExpectedException thrown  = ExpectedException.none();
  private ExplorationEngine explorationEngine;
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
    explorationEngine = new ExplorationEngineImpl();
  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullGuidanceSystemOnExplore(){
    thrown.expectMessage("guidanceSystem cannot be null");
    thrown.expect(IllegalArgumentException.class);
    explorationEngine.explore(explorer, null);
  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullExplorerOnExplore(){
    thrown.expectMessage("explorer cannot be null");
    thrown.expect(IllegalArgumentException.class);
    explorationEngine.explore(null, guidanceSystem);
  }

  @Ignore
  @Test
  public void shouldReturnRouteTraversedGivenMazeContainingSingleLine(){
    gridLines = new ArrayList<>();
    gridLines.add("X XS  F        X");

    GridResult gridResult = gridBuilder.buildGrid(gridLines);

    maze = new MazeImpl(gridResult);
    locationEngine = new LocationEngineImpl(maze);
    guidanceSystem = new GuidanceSystemImpl(maze, orientationManager, locationEngine);
    GridLocation gridLocation = new GridLocation(new Point(3, 3), GridLocationType.SPACE);
    explorer = new Explorer(gridLocation);
    Stack<GridLocation> result = explorationEngine.explore(explorer, guidanceSystem);
    assertEquals(4, result.size());
    assertEquals(maze.getFinishLocation(), result.peek());
  }

  @Test
  public void shouldReturnRouteTraversedGivenMazeContainingPerpendicularRoute(){
    gridLines = new ArrayList<>();
    gridLines.add("XXXS           X");
    gridLines.add("XXXXXXXXXXXXXX X");
    gridLines.add("XXXXXXXXXXXXXX X");
    gridLines.add("XXXXXXXXXXXXXX X");
    gridLines.add("XXXXXXXXXXXXXX X");
    gridLines.add("XXXXXXXXXXXXXXFX");

    GridResult gridResult = gridBuilder.buildGrid(gridLines);

    maze = new MazeImpl(gridResult);
    locationEngine = new LocationEngineImpl(maze);
    guidanceSystem = new GuidanceSystemImpl(maze, orientationManager, locationEngine);
    GridLocation gridLocation = new GridLocation(new Point(3, 3), GridLocationType.SPACE);
    explorer = new Explorer(gridLocation);
    Stack<GridLocation> result = explorationEngine.explore(explorer, guidanceSystem);
    assertEquals(17, result.size());
    assertEquals(maze.getFinishLocation(), result.peek());
  }

  @Test
  public void shouldReturnRouteTraversedGivenRouteThatMakesA_C_OnLooMaze(){
    gridLines = new ArrayList<>();
    gridLines.add("XXXS           X");
    gridLines.add("XXXXXXXXXXXXXX X");
    gridLines.add("XXXXXXXXXXXXXX X");
    gridLines.add("XF             X");

    GridResult gridResult = gridBuilder.buildGrid(gridLines);

    maze = new MazeImpl(gridResult);
    locationEngine = new LocationEngineImpl(maze);
    guidanceSystem = new GuidanceSystemImpl(maze, orientationManager, locationEngine);
    GridLocation gridLocation = new GridLocation(new Point(3, 3), GridLocationType.SPACE);
    explorer = new Explorer(gridLocation);
    Stack<GridLocation> result = explorationEngine.explore(explorer, guidanceSystem);
    assertEquals(28, result.size());
    assertEquals(maze.getFinishLocation(), result.peek());
  }

  @Test
  public void shouldReturnRouteTraversedGivenMazeWhereExplorerHasToVisitStartLocationTwice(){
    gridLines = new ArrayList<>();
    gridLines.add("XF S           X");
    gridLines.add("XXXXXXXXXXXXXX X");
    gridLines.add("XX             X");

    GridResult gridResult = gridBuilder.buildGrid(gridLines);

    maze = new MazeImpl(gridResult);
    locationEngine = new LocationEngineImpl(maze);
    guidanceSystem = new GuidanceSystemImpl(maze, orientationManager, locationEngine);
    GridLocation gridLocation = new GridLocation(new Point(3, 3), GridLocationType.SPACE);
    explorer = new Explorer(gridLocation);
    Stack<GridLocation> result = explorationEngine.explore(explorer, guidanceSystem);
    assertEquals(53, result.size());
    assertEquals(maze.getFinishLocation(), result.peek());
  }

  @Test
  public void shouldReturnRouteTraversedGivenDefaultMaze(){
    Stack<GridLocation> result = explorationEngine.explore(explorer, guidanceSystem);
    assertEquals(78, result.size());
    assertEquals(maze.getFinishLocation(), result.peek());
  }

}
