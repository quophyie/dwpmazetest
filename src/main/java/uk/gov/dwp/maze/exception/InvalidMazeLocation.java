package uk.gov.dwp.maze.exception;


public class InvalidMazeLocation extends RuntimeException {
    public InvalidMazeLocation(){
        super();
    }
    public InvalidMazeLocation(String message){
        super(message);
    }
}
