package company;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Shooter {
    Scanner scanner = new Scanner(System.in);
    Utils utils = new Utils();

    public void shootPlayer(char[][] playerBoard ,char[][] computerBoard, List<Ship> playerShips, List<Ship> computerShips, Set<Ship> deadShipsPlayer, Set<Ship> deadShipsComputer){
        int row, column;

//        System.out.println("W który rząd oddać strzał?");
        row = scanner.nextInt();
//        System.out.println("W którą kolumnę oddać strzał");
        column = scanner.nextInt();
//        scanner.nextLine();
//        computerBoard[row][column] = 'X';
        checkIfHit(row, column, false, playerShips, computerShips, deadShipsPlayer, deadShipsComputer);
    } // Shotter

    public void shootComputer(char[][] playerBoard ,char[][] computerBoard, List<Ship> playerShips, List<Ship> computerShips, Set<Ship> deadShipsPlayer, Set<Ship> deadShipsComputer){
        int row, column;
        do {
            row = utils.getRandomNumberInRange(0, 9);
            column = utils.getRandomNumberInRange(0, 9);
        }while(playerBoard[row][column]=='X');
//        playerBoard[row][column]='X';
        checkIfHit(row, column, true, playerShips, computerShips, deadShipsPlayer, deadShipsComputer);
    } // Shotter

    public void checkIfHit(int row, int column, boolean isComputer, List<Ship> playerShips, List<Ship> computerShips, Set<Ship> deadShipsPlayer, Set<Ship> deadShipsComputer){
        if(isComputer) {
            checkIfHitCommon(playerShips, isComputer, row, column, deadShipsPlayer, deadShipsComputer);
            playerShips.removeIf(ship -> ship.getLife()==0);
            System.out.println("Graczowi pozostało " + playerShips.size() + " statków");
        } else {
            checkIfHitCommon(computerShips, isComputer, row, column, deadShipsPlayer, deadShipsComputer);
            computerShips.removeIf(ship -> ship.getLife()==0);
            System.out.println("Komputerowi pozostało " + computerShips.size() + " statków");
        }
    } // Shotter

    private void checkIfHitCommon(List<Ship> shipList, boolean isComputer, int row, int column, Set<Ship> deadShipsPlayer, Set<Ship> deadShipsComputer) {
        for (Ship ship : shipList) {
            Set<UnitPosition> positions = ship.getPositions();
            for (UnitPosition unitPosition : positions) {
                if (unitPosition.getColumn() == column && unitPosition.getRow() == row) {
                    ship.setLife(ship.getLife() - 1);
                    System.out.println("! ! ! HIT ! ! !");
                    System.out.println("Trafionemu statkowi potało: " + ship.getLife() + " żyć!");
                    if (ship.getLife() == 0 && isComputer==false) {
                        deadShipsPlayer.add(ship);
                    } else if (ship.getLife() == 0 && isComputer==true){
                        deadShipsComputer.add(ship);
                    }
                }
            }
        }
    }
}
