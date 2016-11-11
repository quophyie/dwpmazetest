package uk.gov.dwp.maze.guidance;


import uk.gov.dwp.maze.enums.GridLocationType;
import uk.gov.dwp.maze.enums.Instruction;
import uk.gov.dwp.maze.enums.Orientation;
import uk.gov.dwp.maze.exception.InvalidMazeLocation;
import uk.gov.dwp.maze.explorer.Explorer;
import uk.gov.dwp.maze.gridbuilder.GridLocation;
import uk.gov.dwp.maze.maze.Maze;
import uk.gov.dwp.maze.location.LocationEngine;
import uk.gov.dwp.maze.orientationmanager.OrientationManager;

import java.util.ArrayList;
import java.util.List;


public class GuidanceSystemImpl implements GuidanceSystem {

    private OrientationManager orientationManager;
    private LocationEngine locationEngine;
    private Maze maze;

    public GuidanceSystemImpl(Maze maze, OrientationManager orientationManager, LocationEngine locationEngine){
        if (maze == null)
            throw new IllegalArgumentException("The parameter 'maze' cannot be null");

        if (orientationManager == null)
            throw new IllegalArgumentException("The parameter 'orientationManager' cannot be null");

        if (locationEngine == null)
            throw new IllegalArgumentException("The parameter 'locationEngine' cannot be null");

        this.maze = maze;
        this.orientationManager = orientationManager;
        this.locationEngine = locationEngine;
    }

    @Override
    public void turn(Instruction instruction , Explorer explorer) {
     orientationManager.changeOrientation(instruction ,explorer);
    }

    @Override
    public void move(Explorer explorer){
        GridLocation locationToMoveTo = this.peekNextForwardLocation(explorer);

        if(this.canMove(locationToMoveTo)){
            GridLocation location = locationEngine.retrieveNextLocation(explorer.getOrientation(), explorer.getLocation());
            explorer.setLocation(location);
        }
        else {
            throw new InvalidMazeLocation("Cannot move to the requested location");
        }

    }

    @Override
    public List<GridLocation> getValidMoves(Explorer explorer) {
        List<GridLocation> validMoves = new ArrayList<>();

        if (explorer!=null){
            Orientation initialOrientation = Orientation.valueOf(explorer.getOrientation().name());

            boolean turnsExhausted = false;
            while (!turnsExhausted){
                GridLocation move = peekNextForwardLocation(explorer);
                if(move != null && !move.equals( explorer.getLocation())
                    && move.getGridLocationType().equals(GridLocationType.SPACE))
                    validMoves.add(move);
                this.turn(Instruction.RIGHT, explorer);

                if(explorer.getOrientation().equals(initialOrientation))
                    turnsExhausted = true;
            }

        }
        return validMoves;
    }

    @Override
    public GridLocation getStartLocation() {
        return this.maze.getStartLocation();
    }

    @Override
    public GridLocation getFinishLocation() {
        return this.maze.getFinishLocation();
    }

    @Override
    public void markMazeLocation(int xCoord, int yCoord) {
        this.maze.markVistedMazeLocation(xCoord,yCoord);
    }

    @Override
    public String getLocationMark(int xCoord, int yCoord) {
        return this.maze.getMarkAtVisitedLocation(xCoord, yCoord);
    }

    @Override
    public boolean canMove(GridLocation locationToTest) {
        if (locationToTest !=null
            && locationToTest.getGridLocationType().equals(GridLocationType.SPACE)
            && maze.isValidMazeLocation(locationToTest.getPoint().getX() , locationToTest.getPoint().getY()))
            return true;
        return false;
    }

    @Override
    public GridLocation peekNextForwardLocation(Explorer explorer) {
        return this.locationEngine.retrieveNextLocation(explorer.getOrientation(), explorer.getLocation());
    }

}
