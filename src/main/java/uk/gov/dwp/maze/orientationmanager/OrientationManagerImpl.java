package uk.gov.dwp.maze.orientationmanager;

import uk.gov.dwp.maze.explorer.Explorer;
import uk.gov.dwp.maze.enums.Orientation;
import uk.gov.dwp.maze.enums.Instruction;


public class OrientationManagerImpl implements OrientationManager {
  @Override
  public void changeOrientation(Instruction instruction, Explorer explorer) {
    if (instruction == null)
      throw new IllegalArgumentException("instruction cannot be null");

    if (explorer == null)
      throw new IllegalArgumentException("explorer cannot be null");

    if (explorer.getOrientation() == null)
      throw new IllegalArgumentException("explorer.orientation cannot be null");

    if (instruction.equals(Instruction.RIGHT)){
      this.changeOrientationClockwise(explorer);
    }
    else if (instruction.equals(Instruction.LEFT)){
      changeOrientationAntiClockwise(explorer);
    }
  }

  private void changeOrientationClockwise(Explorer explorer){
    switch (explorer.getOrientation()){
      case NORTH:{
        explorer.setOrientation(Orientation.EAST);
        break;
      }
      case EAST:{
        explorer.setOrientation(Orientation.SOUTH);
        break;
      }

      case WEST:{
        explorer.setOrientation(Orientation.NORTH);
        break;
      }
      case SOUTH:{
        explorer.setOrientation(Orientation.WEST);
        break;
      }
    }
  }

  private void changeOrientationAntiClockwise(Explorer explorer){
    switch (explorer.getOrientation()){
      case NORTH:{
        explorer.setOrientation(Orientation.WEST);
        break;
      }
      case EAST:{
        explorer.setOrientation(Orientation.NORTH);
        break;
      }
      case WEST:{
        explorer.setOrientation(Orientation.SOUTH);
        break;
      }
      case SOUTH:{
        explorer.setOrientation(Orientation.EAST);
        break;
      }
    }
  }
}
