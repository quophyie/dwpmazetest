package uk.gov.dwp.maze.gridbuilder;

public class GridResult {
  private String[][] grid;
  private GridLocation startLocation;
  private GridLocation finishLocation;
  private int numberOfWalls;
  private int numberOfSpaces;

  public GridResult(String[][] grid, GridLocation startLocation, GridLocation finishLocation, int numberOfSpaces, int numberOfWalls){
    this.grid = grid;
    this.startLocation = startLocation;
    this.finishLocation = finishLocation;
    this.numberOfSpaces = numberOfSpaces;
    this.numberOfWalls = numberOfWalls;
  }

  public String[][] getGrid() {
    return grid;
  }

  public void setGrid(String[][] grid) {
    this.grid = grid;
  }

  public GridLocation getStartLocation() {
    return startLocation;
  }

  public void setStartLocation(GridLocation startLocation) {
    this.startLocation = startLocation;
  }

  public GridLocation getFinishLocation() {
    return finishLocation;
  }

  public void setFinishLocation(GridLocation finishLocation) {
    this.finishLocation = finishLocation;
  }

  public int getNumberOfWalls() {
    return numberOfWalls;
  }

  public void setNumberOfWalls(int numberOfWalls) {
    this.numberOfWalls = numberOfWalls;
  }

  public int getNumberOfSpaces() {
    return numberOfSpaces;
  }

  public void setNumberOfSpaces(int numberOfSpaces) {
    this.numberOfSpaces = numberOfSpaces;
  }
}
