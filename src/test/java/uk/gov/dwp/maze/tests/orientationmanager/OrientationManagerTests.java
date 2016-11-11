package uk.gov.dwp.maze.tests.orientationmanager;

import uk.gov.dwp.maze.enums.GridLocationType;
import uk.gov.dwp.maze.enums.Orientation;
import uk.gov.dwp.maze.explorer.Explorer;
import uk.gov.dwp.maze.gridbuilder.GridLocation;
import uk.gov.dwp.maze.gridbuilder.Point;
import uk.gov.dwp.maze.orientationmanager.OrientationManager;
import uk.gov.dwp.maze.orientationmanager.OrientationManagerImpl;
import uk.gov.dwp.maze.enums.Instruction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class OrientationManagerTests {

  @Rule
  public ExpectedException thrown = ExpectedException.none();
  private OrientationManager orientationManager;
  private GridLocation gridLocation;
  private Explorer explorer;

  @Before
  public void setUp(){
    orientationManager = new OrientationManagerImpl();
    Point point = new Point(1, 1);
    gridLocation = new GridLocation(point, GridLocationType.SPACE);
    explorer = new Explorer(gridLocation);

  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullExplorerOnChangeOrientation(){
    String message = "explorer cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    orientationManager.changeOrientation(Instruction.RIGHT, null);
  }


  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullExplorerOrientationOnChangeOrientation(){
    String message = "explorer.orientation cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    explorer.setOrientation(null);
    orientationManager.changeOrientation(Instruction.FORWARD, explorer);
  }


  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullInstructionOnChangeOrientation(){
    String message = "instruction cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    orientationManager.changeOrientation(null, explorer);
  }

  @Test
  public void shouldReturnEastGivenExplorerOrientationOfNorthOnRightTurn() {

    orientationManager.changeOrientation(Instruction.RIGHT, explorer);
    assertEquals(Orientation.EAST, explorer.getOrientation());
  }

  @Test
  public void shouldReturnSouthGivenExplorerOrientationOfEastOnRightTurn() {
    explorer.setOrientation(Orientation.EAST);
    orientationManager.changeOrientation(Instruction.RIGHT, explorer);
    assertEquals(Orientation.SOUTH, explorer.getOrientation());
  }

  @Test
  public void shouldReturnWestGivenExplorerOrientationOfSouthOnRightTurn() {
    explorer.setOrientation(Orientation.SOUTH);
    orientationManager.changeOrientation(Instruction.RIGHT, explorer);
    assertEquals(Orientation.WEST, explorer.getOrientation());
  }

  @Test
  public void shouldReturnNorthGivenExplorerOrientationOfWestOnRightTurn() {
    explorer.setOrientation(Orientation.WEST);
    orientationManager.changeOrientation(Instruction.RIGHT, explorer);
    assertEquals(Orientation.NORTH, explorer.getOrientation());
  }


  @Test
  public void shouldReturnWestGivenExplorerOrientationOfNorthOnLeftTurn() {

    orientationManager.changeOrientation(Instruction.LEFT, explorer);
    assertEquals(Orientation.WEST, explorer.getOrientation());
  }

  @Test
  public void shouldReturnNorthGivenExplorerOrientationOfEastOnLeftTurn() {
    explorer.setOrientation(Orientation.EAST);
    orientationManager.changeOrientation(Instruction.LEFT, explorer);
    assertEquals(Orientation.NORTH, explorer.getOrientation());
  }

  @Test
  public void shouldReturnEastGivenExplorerOrientationOfSouthOnLeftTurn() {
    explorer.setOrientation(Orientation.SOUTH);
    orientationManager.changeOrientation(Instruction.LEFT, explorer);
    assertEquals(Orientation.EAST, explorer.getOrientation());
  }

  @Test
  public void shouldReturnSouthGivenExplorerOrientationOfWestOnLeftTurn() {
    explorer.setOrientation(Orientation.WEST);
    orientationManager.changeOrientation(Instruction.LEFT, explorer);
    assertEquals(Orientation.SOUTH, explorer.getOrientation());
  }
}
