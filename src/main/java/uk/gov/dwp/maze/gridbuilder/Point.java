package uk.gov.dwp.maze.gridbuilder;


public class Point {

  private int x;
  private int y;

  public Point(int xCoord, int yCoord){
    this.x = xCoord;
    this.y = yCoord;

  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public boolean equals(Object obj){
    if ( obj!=null
        && this.getX() == ((Point)obj).getX()
        && this.getY() == ((Point)obj).getY())
      return true;

    return false;
  }

  @Override
  public int hashCode(){
    return (String.valueOf(this.x)
        + String.valueOf(this.y)).hashCode();
  }
}
