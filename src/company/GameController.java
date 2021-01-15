package company;

import FXML.Controller;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.*;

public class GameController {
    private char[][] playerBoard = new char[10][10];
    private char[][] computerBoard = new char[10][10];
    private Board boardClass = new Board();
    public List<Ship> playerShips = createShips();
    public List<Ship> computerShips = createShips();
    private List<Ship> allShips = createShips();
    private Set<Ship> deadShipsComputer = new HashSet<>();
    private Set<Ship> deadShipsPlayer = new HashSet<>();
    Scanner scanner = new Scanner(System.in);
    Utils utils = new Utils();
    Shooter shooter = new Shooter();


    public void startFunctions(){
        boardClass.fillBoardWithWater(playerBoard);
        boardClass.fillBoardWithWater(computerBoard);
        System.out.println("startFunctions work");

    }

//    public void test(MouseEvent e){
//        Controller.test(e);
//    }

    public void RunGame(){

//        for (int i=0;;i++){
            System.out.println("Player Board");
            boardClass.printPlayerBoard(playerBoard, computerBoard);
            shooter.shootPlayer(playerBoard, computerBoard, playerShips, computerShips, deadShipsPlayer, deadShipsComputer);
            isWin("Player");
            System.out.println("Computer Board");
            boardClass.printComputerBoard(playerBoard, computerBoard);
            shooter.shootComputer(playerBoard, computerBoard, playerShips, computerShips, deadShipsPlayer, deadShipsComputer);
            isWin("Computer");
//        }
    }


    public void setNeighbours (Ship ship, int row, int column, boolean horizontal){
        for (int i=0; i<ship.getNumberOfSquares(); i++) {
            //System.out.println("Liczba kratek statku = " + ship.getNumberOfSquares() + " Iteracja = " + i);
            if (horizontal) {
                ship.setNeighbours(row, column + i);//pozycja statku
                if (row != 9) {
                    ship.setNeighbours(row + 1, column + i);//rząd poniżej
                }
                if (row != 0) {
                    ship.setNeighbours(row - 1, column + i);//rząd powyżej
                }
                if (column+i != 9 && i == ship.getNumberOfSquares()-1 ) {
                    ship.setNeighbours(row, column + i + 1);//po prawej
                }
                if (column != 0 && i == 0) {
                    ship.setNeighbours(row, column - 1);//po lewej
                }
            } else {
                ship.setNeighbours(row + i, column);//pozycja statku
                if (column != 9) {
                    ship.setNeighbours(row + i, column + 1);//kolumna po prawej
                }
                if (column != 0) {
                    ship.setNeighbours(row + i, column - 1);//kolumna po lewej
                }
                if (row+i != 9 && i == ship.getNumberOfSquares()-1) {
                    ship.setNeighbours(row + i + 1, column); //poniżej statku
                }
                if (row != 0 && i == 0){
                    ship.setNeighbours(row - 1, column); //powyżej statkiem
                }
            }
        }
    }

    public void isWin(String player){
        boolean win = false;
        if(playerShips.size()==0||computerShips.size()==0){
            System.out.println("Zwycięzcą jest: " + player);
//            System.exit(0);
        }
    }

    public boolean placeShipsPlayer(Ship ship, MouseEvent e, int row, int column){

        String isHorizontal;
        boolean horizontal, testInterference;


        System.out.println("Wybierz położenie statku o wielkości " + ship.getNumberOfSquares());


//            do {
                ship.deleteAllPositions();


                UnitPosition unitPosition = new UnitPosition(row, column);

                //System.out.println("unit position in placeShipPlayer checked");
                //System.out.println("W którym rzędzie ma być statek?");
                //row = unitPosition.getRow();
                //System.out.println("W której kolumnie ma być statek?");
                //column = unitPosition.getColumn();
                //scanner.nextLine();
                //System.out.println("Statek ma być położony horyzontalnie? (T/N)");
//                isHorizontal = scanner.nextLine();
                isHorizontal = "N";

                if (isHorizontal.equals("N")) {
                    for (int i = 0; i < ship.getNumberOfSquares(); i++) {
                        ship.setHorizontal(false);
                        //board[row + i][column] = 'S';
                        ship.setPositions(row + i, column);
                        setNeighbours(ship, row, column, false); //Ship ship, int row, int column, boolean horizontal
                    }
                } else {
                    for (int i = 0; i < ship.getNumberOfSquares(); i++) {
                        ship.setHorizontal(true);
                        //board[row][column + i] = 'S';
                        ship.setPositions(row, column + i);
                        setNeighbours(ship, row, column, true);
                    }
                }
                testInterference=isShipInterfere(playerShips, ship, row, column, ship.isHorizontal());
                if(testInterference){
                    System.out.println("Statki są za blisko siebie!");
                } else {
                    System.out.println("Row and Column: "+row+","+column);
                    //drawShip(ship, false, playerBoard, computerBoard);
                }

//            }while(testInterference);

        return testInterference;

    }

    public void placeShipComputer(List<Ship> computerShips, char[][] playerBoard, char[][] computerBoard){
        int row, column;
        Random random = new Random();
        boolean testInterference;

        for (Ship ship : computerShips) {
            //System.out.println("Losowanie pozycji statku: " + ship.getNumberOfSquares());
            boolean horizontal = random.nextBoolean();
            do {
                ship.deleteAllPositions();
                if (horizontal) {
                    row = utils.getRandomNumberInRange(0, 9);
                    column = utils.getRandomNumberInRange(0, 9 - ship.getNumberOfSquares());
                    for (int i = 0; i < ship.getNumberOfSquares(); i++) {
                        ship.setPositions(row, column + i);
                        setNeighbours(ship, row, column, true);
                    }
                } else {
                    row = utils.getRandomNumberInRange(0, 9 - ship.getNumberOfSquares());
                    column = utils.getRandomNumberInRange(0, 9);
                    for (int i = 0; i < ship.getNumberOfSquares(); i++) {
                        ship.setPositions(row + i, column);
                        setNeighbours(ship, row, column, false);
                    }
                }
                testInterference=isShipInterfere(computerShips, ship,row, column, ship.isHorizontal());
            } while (testInterference);
            ship.setHorizontal(horizontal);
//            drawShip(ship, true, playerBoard, computerBoard);
        }
    }

    public void drawShip(Ship ship, boolean isComputer, char[][] playerBoard,char[][] computerBoard){
        int row, column;
        Set<UnitPosition> drawPositions = ship.getPositions();

        for (UnitPosition unitPosition : drawPositions){
            row = unitPosition.getRow();
            column = unitPosition.getColumn();
            if(isComputer==false) {
                playerBoard[row][column] = 'S';
            } else {
                computerBoard[row][column] = 'C';
            }
        }
    }

    public List<Ship> createShips(){
        List<Ship> shipList = new ArrayList<>();
        Ship shipOne = new Ship(1);
        Ship shipTwo = new Ship(2);
        Ship shipThree = new Ship(3);
        Ship shipFour = new Ship(4);
        Ship shipFive = new Ship(5);
        shipList.add(shipOne);
        shipList.add(shipTwo);
        shipList.add(shipThree);
        shipList.add(shipFour);
        shipList.add(shipFive);
        return shipList;
    }

    public boolean isShipInterfere(List<Ship> shipList, Ship newShip, int row, int column, boolean isHorizontal){
        boolean isShipInterfere = false;

        for (Ship ship : shipList) {
            if (ship.getNumberOfSquares() < newShip.getNumberOfSquares()) {
                for (UnitPosition newShipUnitPosition : newShip.getPositions()) {
                    for (UnitPosition shipNeighboursUnitPosition : ship.getNeighbours()) {
                        if (shipNeighboursUnitPosition.getRow() == newShipUnitPosition.getRow()
                                && shipNeighboursUnitPosition.getColumn() == newShipUnitPosition.getColumn()) {
                            isShipInterfere = true;
                        }
                    }
                }
            }
        }

        if(newShip.getNumberOfSquares() + column>10 && isHorizontal){
            isShipInterfere=true;
        } else if(newShip.getNumberOfSquares()+row>10 && !isHorizontal)
        {
            isShipInterfere=true;
        }
        return  isShipInterfere;
    }


    public List<Ship> getPlayerShips() {
        return playerShips;
    }
}
