package uk.gov.dwp.maze.guidance;

import uk.gov.dwp.maze.enums.Instruction;
import uk.gov.dwp.maze.explorer.Explorer;
import uk.gov.dwp.maze.gridbuilder.GridLocation;

import java.util.List;

/**
 * The Guidance System Used by an explorer to move around the grid
 */
public interface GuidanceSystem {

    /**
     * Turns the explorer in the specified direction
     * @param explorer
     * @param explorer
     * @return
     */

    void turn(Instruction instruction , Explorer explorer);

    /**
     * Moves the explorer forward in the direction that the explorer is facing
     * if the movement forward is a valid move
     */
    void move(Explorer explorer);


    /**
     * Returns true  if the explorer can move to locationToTest. false otherwise
     * @param locationToTest
     * @return
     */
    boolean canMove(GridLocation locationToTest);

    /**
     * Returns the next location in the direction that the explorer
     * is facing without moving the explore to the said location
     * @return
     */
    GridLocation peekNextForwardLocation(Explorer explorer);

    /**
     * returns the valid moves that an explorer can make from their current position
     * @return
     */
    List<GridLocation> getValidMoves(Explorer explorer);

  /**
   * Returns the start position of the maze used by the guidance system
   * @return
   */
  GridLocation getStartLocation();

    /**
     * Returns the finish position of the maze used by the guidance system
     * @return
     */
    GridLocation getFinishLocation();

  void markMazeLocation(int xCoord, int yCoord);
  String getLocationMark(int xCoord, int yCoord);


}
