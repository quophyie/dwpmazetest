package uk.gov.dwp.maze.gridbuilder;

import java.io.IOException;
import java.util.List;

public interface GridBuilder {
  GridResult buildGrid(List<String> mazeFileLines);
  boolean isValidGridPosition(String[][] grid, int xCoord, int yCoord);
  List<String> loadMazeFile(String file) throws IOException;
}
