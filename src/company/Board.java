package company;

public class Board {

    public void fillBoardWithWater(char[][] board){
        for (int row=0; row<board.length; row++){
            for (int column=0; column<board.length; column++){
                board[row][column]='W';
            }
        }
    }

    public void printPlayerBoard(char[][] playerBoard, char[][] computerBoard){
        printBoardCommon(false,playerBoard, computerBoard);
    }

    public void printComputerBoard(char[][] playerBoard, char[][] computerBoard){
        printBoardCommon(true ,playerBoard, computerBoard);
    }

    private void printBoardCommon(boolean isComputer, char[][] playerBoard, char[][] computerBoard){
        System.out.print("\t");
        for (int i=0; i< playerBoard.length; i++){ //drukuje nagłówek kolumn
            System.out.print(i+"\t");
        }
        System.out.println();
        for (int row=0; row< playerBoard.length;row++){
            System.out.print(row+":\t");
            for (int column=0; column< playerBoard.length; column++){
                if(isComputer==true) {
                    System.out.print(computerBoard[row][column] + "\t");
                } else {
                    System.out.print(playerBoard[row][column] + "\t");
                }
            }
            System.out.println();
        }
    }

}
