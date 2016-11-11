package uk.gov.dwp.maze.reporter;

import uk.gov.dwp.maze.gridbuilder.GridLocation;
import uk.gov.dwp.maze.maze.Maze;

import java.util.Stack;

public class ReporterImpl  implements  Reporter{

  private Maze maze;
  public ReporterImpl(Maze maze){
    if (maze == null)
      throw new IllegalArgumentException("maze cannot be null");
    this.maze = maze;
  }

  @Override
  public String generateVisualReport(Stack<GridLocation> route) {
    if (route == null)
       throw new IllegalArgumentException("route cannot be null");

    route.stream().forEach(gridLocation -> {
      if (!gridLocation.equals(maze.getStartLocation()) && !gridLocation.equals(maze.getFinishLocation()))
      this.maze.setMarkAtMazeLocation(".", gridLocation.getPoint().getX(), gridLocation.getPoint().getY());
    });


    return maze.toString().trim();
  }

  @Override
  public String generateCoordinateReport(Stack<GridLocation> route) {
    if (route == null)
      throw new IllegalArgumentException("route cannot be null");

    StringBuilder stringBuilder = new StringBuilder("");
    route.stream()
        .forEach(gridLocation -> stringBuilder.append(String.format("(x:%d, y:%d)\n",
            gridLocation.getPoint().getX(),gridLocation.getPoint().getY() )));

    return stringBuilder.toString();
  }
}
