package uk.gov.dwp.maze.explorer;

import uk.gov.dwp.maze.gridbuilder.GridLocation;
import uk.gov.dwp.maze.guidance.GuidanceSystem;

import java.util.Stack;

public interface ExplorationEngine {

  Stack<GridLocation> explore(Explorer explorer, GuidanceSystem guidanceSystem);
}
