package uk.gov.dwp.maze.maze;

import uk.gov.dwp.maze.gridbuilder.GridLocation;

public interface Maze {
  GridLocation getLocationByCoordinate(int xCoord, int yCoord);
  GridLocation getStartLocation();
  GridLocation getFinishLocation();
  boolean isValidMazeLocation(int xCoord, int yCoord);
  int getNumberOfWalls();
  int getNumberOfSpaces();
  void markVistedMazeLocation(int xCoord, int yCoord);
  String getMarkAtVisitedLocation(int xCoord, int yCoord);
  void setMarkAtMazeLocation(String mark, int xCoord, int yCoord);
}
