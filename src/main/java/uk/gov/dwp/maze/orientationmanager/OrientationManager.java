package uk.gov.dwp.maze.orientationmanager;

import uk.gov.dwp.maze.explorer.Explorer;
import uk.gov.dwp.maze.enums.Instruction;

public interface OrientationManager {
  /**
   * Changes the orientation of the explorer based on the given instruction
   * @param instruction
   * @param explorer
   * @return
   */
  void changeOrientation(Instruction instruction, Explorer explorer);
}
