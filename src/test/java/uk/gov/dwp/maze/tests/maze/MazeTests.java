package uk.gov.dwp.maze.tests.maze;


import uk.gov.dwp.maze.exception.InvalidMazeLocation;
import uk.gov.dwp.maze.gridbuilder.GridBuilder;
import uk.gov.dwp.maze.gridbuilder.GridBuilderImpl;
import uk.gov.dwp.maze.gridbuilder.GridLocation;
import uk.gov.dwp.maze.gridbuilder.GridResult;
import uk.gov.dwp.maze.maze.Maze;
import uk.gov.dwp.maze.maze.MazeImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MazeTests {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

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

  }

  @Test
  public void shouldReturnStartPositionGivenAMaze(){
    GridLocation result = maze.getStartLocation();
    assertEquals(3, result.getPoint().getX());
    assertEquals(3, result.getPoint().getY());
  }

  @Test
  public void shouldReturnFinishPositionGivenAMaze(){
    GridLocation result = maze.getFinishLocation();
    assertEquals(1, result.getPoint().getX());
    assertEquals(14, result.getPoint().getY());
  }

  @Test
  public void shouldReturnNumberOfWallsGivenGrid(){
    assertEquals(149, maze.getNumberOfWalls());
  }

  @Test
  public void shouldReturnNumberOfSpacesGivenGrid(){
    assertEquals(76, maze.getNumberOfSpaces());
  }

  @Test
  public void shouldReturnMazeLocationGivenXCoordAndYCoord(){
    int xCoord = 3, yCoord = 3;
    GridLocation result = maze.getLocationByCoordinate(xCoord, yCoord);
    assertEquals(xCoord, result.getPoint().getX());
    assertEquals(yCoord, result.getPoint().getY());
  }

  @Test
  public void shouldThrowInvalidMazeLocationGivenInvalidMazeCoordinates(){
    int xCoord = 10000, yCoord = 30000;
    String message = String.format("The requested maze location is invalid: XCoord: %d, YCoord: %d", xCoord, yCoord);
    thrown.expect(InvalidMazeLocation.class);
    thrown.expectMessage(message);
    maze.getLocationByCoordinate(xCoord, yCoord);

  }
}
