package uk.gov.dwp.maze.tests.gridbuilder;

import uk.gov.dwp.maze.enums.GridLocationType;
import uk.gov.dwp.maze.exception.GridException;
import uk.gov.dwp.maze.gridbuilder.GridBuilder;
import uk.gov.dwp.maze.gridbuilder.GridBuilderImpl;
import uk.gov.dwp.maze.gridbuilder.GridResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class GridbuilderTests {
  private GridBuilder gridBuilder;
  private List<String> gridLines;

  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @Before
  public void  setUp(){
    gridBuilder = new GridBuilderImpl();

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
  }

  @Test
  public void shouldThrowGridExceptionGivenNullMazeLinesList(){
    String message = String.format( "gridLines list cannot be null or empty");
    thrown.expect(GridException.class);
    thrown.expectMessage(message);

    gridBuilder.buildGrid(null);
  }

  @Test
  public void shouldThrowGridExceptionGivenEmptyMazeLinesList(){
    String message = String.format( "gridLines list cannot be null or empty");
    thrown.expect(GridException.class);
    thrown.expectMessage(message);

    gridBuilder.buildGrid(Arrays.asList());
  }

  @Test
  public void shouldBuildAndReturnMazeGivenAListOfStringMazeLines(){
    GridResult result = gridBuilder.buildGrid(gridLines);
    assertEquals(15, result.getGrid().length);
    assertEquals(15, result.getGrid()[0].length);
  }

  @Test
  public void shouldReturnTheStartLocationOf(){
    GridResult result = gridBuilder.buildGrid(gridLines);
    assertEquals(3, result.getStartLocation().getPoint().getX());
    assertEquals(3, result.getStartLocation().getPoint().getY());
    assertEquals(GridLocationType.SPACE, result.getStartLocation().getGridLocationType());
  }

  @Test
  public void shouldReturnTheFinishLocationOnGrid(){
    GridResult result = gridBuilder.buildGrid(gridLines);
    assertEquals(1, result.getFinishLocation().getPoint().getX());
    assertEquals(14, result.getFinishLocation().getPoint().getY());
    assertEquals(GridLocationType.SPACE, result.getStartLocation().getGridLocationType());
  }

  @Test
  public void shouldReturnNumberOfWallsGivenGrid(){
    GridResult result = gridBuilder.buildGrid(gridLines);
    assertEquals(149, result.getNumberOfWalls());
  }

  @Test
  public void shouldReturnNumberOfSpacesGivenGrid(){
    GridResult result = gridBuilder.buildGrid(gridLines);
    assertEquals(76, result.getNumberOfSpaces());
  }

  @Test
  public void shouldThrowGridExceptionGivenGridWithMultipleStartPoints() {
    gridLines.add("SXXXXXXXXXXXXX");

    String message = "grid cannot contain multiple start points";
    thrown.expect(GridException.class);
    thrown.expectMessage(message);

   gridBuilder.buildGrid(gridLines);
  }

  @Test
  public void shouldThrowGridExceptionGivenGridWithMultipleFinishPoints() {
    gridLines.add("FXXXXXXXXXXXXX");

    String message = "grid cannot contain multiple finish points";
    thrown.expect(GridException.class);
    thrown.expectMessage(message);

    gridBuilder.buildGrid(gridLines);
  }

  @Test
  public void shouldThrowGridExceptionGivenGridWithStartPoint() {
    gridLines.clear();
    gridLines.add("XXXXXXXXXXXX X");
    gridLines.add("X            X");
    gridLines.add("XFXXXXXXXXXXXX");

    String message = "grid does not contain a start point. grid must contain a single start point";
    thrown.expect(GridException.class);
    thrown.expectMessage(message);

    gridBuilder.buildGrid(gridLines);
  }

  @Test
  public void shouldThrowGridExceptionGivenGridWithFinishPoint() {
    gridLines.clear();
    gridLines.add("XXXXXXXXXXXXSX");
    gridLines.add("X            X");
    gridLines.add("XXXXXXXXXXXXXX");

    String message = "grid does not contain a finish point. grid must contain a single finish point";
    thrown.expect(GridException.class);
    thrown.expectMessage(message);

    gridBuilder.buildGrid(gridLines);
  }


}
