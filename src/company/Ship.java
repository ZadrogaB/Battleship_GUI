package company;

import java.util.HashSet;
import java.util.Set;

public class Ship {
    private int life;
    private int numberOfSquares;
    private Set<UnitPosition> positions = new HashSet<>();
    private Set<UnitPosition> neighbours = new HashSet<>();
    private boolean horizontal;

    private Set<UnitPosition> allPositionsSet = new HashSet<>();


    public Ship(int number) {
        this.life = number;
        this.numberOfSquares = number;
    }

    public int getLife() {
        return life;
    }

    public Set<UnitPosition> getPositions() {
        return positions;
    }

    public int getNumberOfSquares() {
        return numberOfSquares;
    }

    public Set<UnitPosition> getNeighbours() {
        return neighbours;
    }

    public void setPositions(int row, int column) {
        UnitPosition unitPosition = new UnitPosition(row, column);
        positions.add(unitPosition);
    }

    public void setNeighbours(int row, int column){
        UnitPosition unitPosition = new UnitPosition(row, column);
        neighbours.add(unitPosition);
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void createAllPositionsSet(){
        for(int i=0; i<20; i++){
            for(int j=0; j<20; j++){
                UnitPosition unitPosition = new UnitPosition(i,j);
                allPositionsSet.add(unitPosition);
            }
        }
    }

    public void deleteAllPositions(){
        createAllPositionsSet();
        positions.removeAll(allPositionsSet);
        neighbours.removeAll(allPositionsSet);
    }

    public void printNeighbours(){
        int loopCount = 0;
        System.out.println("Statek: " + numberOfSquares );
        for (UnitPosition unitPosition : neighbours){
            loopCount++;
            System.out.println(loopCount + ") Row = " + unitPosition.getRow() + "\nColumn = " + unitPosition.getColumn());
        }
    }

}
