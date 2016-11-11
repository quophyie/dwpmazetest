package uk.gov.dwp.maze.gridbuilder;


import uk.gov.dwp.maze.enums.GridLocationType;

public class GridLocation {
    private Point point;


    private GridLocationType gridLocationType;
    public GridLocation(){}

    public GridLocation(Point position, GridLocationType gridLocType)  {
        this.point = position;
        this.gridLocationType = gridLocType;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public GridLocationType getGridLocationType() {
        return gridLocationType;
    }

    public void setGridLocationType(GridLocationType gridLocationType) {
        this.gridLocationType = gridLocationType;
    }

    @Override
    public boolean equals(Object obj){
        if ( obj!=null &&
            (this.getGridLocationType()!= null && ((GridLocation)obj).getGridLocationType()!= null)
            && this.getGridLocationType().equals(((GridLocation)obj).getGridLocationType())
            && this.getPoint().equals(((GridLocation)obj).getPoint()))
            return true;

        return false;
    }

    @Override
    public int hashCode(){
        return (String.valueOf(this.getPoint().hashCode())
            + String.valueOf(this.getGridLocationType().hashCode())).hashCode();
    }

}
