package uk.gov.dwp.maze.tests.reporter;


import org.junit.Before;
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
import uk.gov.dwp.maze.reporter.Reporter;
import uk.gov.dwp.maze.reporter.ReporterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReporterTests {

  private Reporter reporter;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private GridBuilder gridBuilder;
  private Maze maze;
  private List<String> gridLines;

  private GuidanceSystem guidanceSystem;
  private OrientationManager orientationManager;
  private LocationEngine locationEngine;
  private Explorer explorer;
  private ExplorationEngine explorationEngine;

  @Before
  public void serUp(){
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
    this.reporter = new ReporterImpl(maze);
    orientationManager = new OrientationManagerImpl();
    locationEngine = new LocationEngineImpl(maze);
    guidanceSystem = new GuidanceSystemImpl(maze, orientationManager, locationEngine);

    GridLocation gridLocation = new GridLocation(new Point(3, 3), GridLocationType.SPACE);

    explorer = new Explorer(gridLocation);

    explorationEngine = new ExplorationEngineImpl();
  }
  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullReporterOnGenerateVisualReport(){
    String message = "route cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    reporter.generateVisualReport(null);
  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullReporterOnGenerateCoordinateReport(){
    String message = "route cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    reporter.generateCoordinateReport(null);
  }

  @Test
  public void shouldReturnAVisualReportGivenARoute(){
    GridResult gridResult = gridBuilder.buildGrid(gridLines);

    maze = new MazeImpl(gridResult);
    guidanceSystem = new GuidanceSystemImpl(maze, orientationManager, locationEngine);
    Stack<GridLocation> route = explorationEngine.explore(explorer, guidanceSystem);
    String report = reporter.generateVisualReport(route);
    String expected =
        "XXXXXXXXXXXXXXX\n" +
        "X.............X\n" +
        "X.XXXXXXXXXXX.X\n" +
        "X.XS........X.X\n" +
        "X.XXXXXXXXX.X.X\n" +
        "X.XXXXXXXXX.X.X\n" +
        "X.XXXX......X.X\n" +
        "X.XXXX.XXXX.X.X\n" +
        "X.XXXX.XXXX.X.X\n" +
        "X.X....XXXXXX.X\n" +
        "X.X.XXXXXXXXX.X\n" +
        "X.X.XXXXXXXXX.X\n" +
        "X.X.........X.X\n" +
        "X.XXXXXXXXX...X\n" +
        "XFXXXXXXXXXXXXX";
    assertEquals(expected.trim(), report.trim());
  }

  @Test
  public void shouldReturnACoordinateReportGivenARoute(){

    gridLines = new ArrayList<>();
    gridLines.add("XXXXXXXXXXXXXXX");
    gridLines.add("X             X");
    gridLines.add("X XXXXXXXXXXX X");
    gridLines.add("X XS        X X");
    gridLines.add("X XXXXXXXXXFX X");
    GridResult gridResult = gridBuilder.buildGrid(gridLines);

    maze = new MazeImpl(gridResult);
    locationEngine = new LocationEngineImpl(maze);
    guidanceSystem = new GuidanceSystemImpl(maze, orientationManager, locationEngine);
    Stack<GridLocation> route = explorationEngine.explore(explorer, guidanceSystem);
    String report = reporter.generateCoordinateReport(route);
    String expected =
            "(x:3, y:3)\n" +
            "(x:4, y:3)\n" +
            "(x:5, y:3)\n" +
            "(x:6, y:3)\n" +
            "(x:7, y:3)\n" +
            "(x:8, y:3)\n" +
            "(x:9, y:3)\n" +
            "(x:10, y:3)\n" +
            "(x:11, y:3)\n" +
            "(x:11, y:4)";
    assertEquals(expected, report.trim());
  }
}
