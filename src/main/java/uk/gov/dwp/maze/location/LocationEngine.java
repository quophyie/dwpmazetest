package uk.gov.dwp.maze.location;


import uk.gov.dwp.maze.enums.Orientation;
import uk.gov.dwp.maze.gridbuilder.GridLocation;

public interface LocationEngine {
  /**
   * Returns the next grid location in a forward direction
   * based on the orientatin Instruction and the supplied grid location
   * @param orientation
   * @param gridLocation
   * @return
   */
  GridLocation retrieveNextLocation(Orientation orientation, GridLocation gridLocation);
}
