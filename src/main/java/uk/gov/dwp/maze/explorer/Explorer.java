package uk.gov.dwp.maze.explorer;

import uk.gov.dwp.maze.enums.Orientation;
import uk.gov.dwp.maze.gridbuilder.GridLocation;

public class Explorer {
  private GridLocation location;
  private Orientation orientation;

  public Explorer(GridLocation location) {
    this.location = location;
    this.orientation = Orientation.NORTH;
  }
  public GridLocation getLocation() {
    return location;
  }

  public void setLocation(GridLocation location) {
    this.location = location;
  }

  public Orientation getOrientation() {
    return orientation;
  }

  public void setOrientation(Orientation orientation) {
    this.orientation = orientation;
  }
}
