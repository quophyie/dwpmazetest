package uk.gov.dwp.maze.reporter;


import uk.gov.dwp.maze.gridbuilder.GridLocation;

import java.util.Stack;

public interface Reporter {
  String generateVisualReport(Stack<GridLocation> route);
  String generateCoordinateReport(Stack<GridLocation> route);
}
