package uk.gov.dwp.maze.gridbuilder;


import uk.gov.dwp.maze.enums.GridLocationType;
import uk.gov.dwp.maze.exception.GridException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GridBuilderImpl implements GridBuilder {

  @Override
  public GridResult buildGrid(List<String> mazeFileLines) {
    if (mazeFileLines == null || mazeFileLines.isEmpty())
      throw new GridException("gridLines list cannot be null or empty");

    int rows = mazeFileLines.size();
    int cols = mazeFileLines.get(0).length();

    String [][] grid  = new String [rows][cols];

    int rowNum = 0, numOfStartPositionsCreated = 0, numOfFinishPositionsCreated = 0;
    int numOfWalls = 0, numOfSpaces = 0;
    GridLocation startLocation = null, finishLocation = null;

    for (String line: mazeFileLines) {
      Character[] lineAsCharacterArray = ArrayUtils.toObject(line.toCharArray());
      for(int colNum = 0; colNum < lineAsCharacterArray.length; colNum ++){

        String characterInLine  = lineAsCharacterArray[colNum].toString();
        grid[rowNum][colNum] = characterInLine;

        if (characterInLine.equalsIgnoreCase("X"))
            numOfWalls++;
        else
            numOfSpaces++;

        if ("S".equals(characterInLine)) {
          if (numOfStartPositionsCreated > 0 )
            throw new GridException("grid cannot contain multiple start points");
           Point point = new Point(colNum, rowNum);
           startLocation = new GridLocation(point, GridLocationType.SPACE);
           numOfStartPositionsCreated++;
         }
         else if ("F".equals(characterInLine)) {
          if (numOfFinishPositionsCreated > 0 )
            throw new GridException("grid cannot contain multiple finish points");
            Point point = new Point(colNum, rowNum);
            finishLocation = new GridLocation(point, GridLocationType.SPACE);
            numOfFinishPositionsCreated++;
          }
     }

      rowNum++;
    }

    GridResult gridResult = new GridResult(grid, startLocation, finishLocation, numOfSpaces, numOfWalls);
    return gridResult;
  }

  @Override
  public boolean isValidGridPosition(String[][] grid, int xCoord, int yCoord) {
    if (xCoord >= 0 && xCoord <= grid.length
        && yCoord >= 0 && yCoord <= grid[0].length)
      return true;
    return false;
  }

  @Override
  public List<String> loadMazeFile(String file) throws IOException {

    if (StringUtils.isBlank(file))
      throw new IllegalArgumentException("The argument 'mazeFile' is null or empty . The argument 'mazeFile' cannot be null or empty");

    URL url = this.getClass().getResource(file);
    Path filePath = Paths.get(url.getPath());


    if (!Files.exists(filePath))
      throw new FileNotFoundException("The maze file was not found");

    List<String> lines;

    try (Stream<String> stream = Files.lines(Paths.get(filePath.toString()))) {
      lines = stream.collect(Collectors.toList());
    }

    return lines;
  }
}
