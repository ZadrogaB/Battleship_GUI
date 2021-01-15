package company;

public class ComputerAI {
    private Utils utils = new Utils();
    private  boolean wasHitLastTime=false;
    private  int numberOfLoops = 0;

    public UnitPosition AIEasy(){
        int row, column;
        row = utils.getRandomNumberInRange(0,9);
        column = utils.getRandomNumberInRange(0,9);
        UnitPosition unitPosition = new UnitPosition(row,column);
        return unitPosition;
    }

   /* public UnitPosition AIHard(){
        int row, column;

        if((numberOfLoops==0)||(numberOfLoops!=0 && wasHitLastTime==false)){
            row = utils.getRandomNumberInRange(0,9);
            column = utils.getRandomNumberInRange(0,9);
            UnitPosition unitPosition = new UnitPosition(row,column);
        } else if(numberOfLoops!=0 && wasHitLastTime==true){

        }
        numberOfLoops++;
    }*/
}
