package FXML;

import company.GameController;
import company.Ship;
import company.UnitPosition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private GridPane gridPaneShips;
    @FXML
    private GridPane gridPaneTargets;
    @FXML
    private Button newGameButton;
    @FXML
    private Button horizontalButton;
    @FXML
    private ImageView backgroudPhoto;
    @FXML
    private Button visibleTargetsButton;
    @FXML
    private Button restartButton;

    private GameController gameController = new GameController();
    private List<Ship> playerShips = gameController.playerShips;
    private List<Ship> computerShips = gameController.computerShips;
    private List<Rectangle> shotRectanglesPlayer = new ArrayList<>();
    private List<Rectangle> shotRectanglesComputer = new ArrayList<>();
    private int numberOfLoops = 0;
    private boolean isHorizontal=true;
    private boolean areTargetsVisible=false;


    public void onMouseClickedVisibleTargetsButton() {
        Color color;
        if(areTargetsVisible){
            areTargetsVisible=false;
            color=Color.web("#00c5ff");
        } else {
            areTargetsVisible=true;
            color=Color.web("002AFFFF");
        }


        for (int i = 0; i < computerShips.size(); i++) {
            Ship shipComputer = computerShips.get(i);
            for (UnitPosition unitPosition : shipComputer.getPositions()) {        ////////////////POLA KOMPUTERA KOLOROWE
                int shipRow = unitPosition.getRow();
                int shipColumn = unitPosition.getColumn();

                ObservableList<Node> childrenComputer = gridPaneTargets.getChildren();    //Kolorowanie pól komputera
                    for (Node node : childrenComputer) {
                        Integer columnIndex = GridPane.getColumnIndex(node);
                        Integer rowIndex = GridPane.getRowIndex(node);

                        if (columnIndex == null)
                            columnIndex = 0;
                        if (rowIndex == null)
                            rowIndex = 0;

                        if (columnIndex == shipColumn && rowIndex == shipRow) {
                            try {
                                Rectangle rectangle = (Rectangle) node;
                                rectangle.setFill(color);
                            } catch (RuntimeException exception) {
                            }
                        }
                    }

            } ////////////////POLA KOMPUTERA KOLOROWE
        }
    }

    public void onMouseClickedHorizontal(){
        if (isHorizontal){
            isHorizontal=false;
            horizontalButton.setText("vertical");
        } else {
            isHorizontal=true;
            horizontalButton.setText("horizontal");
        }

    }

    public void onMouseClickedNewGame(MouseEvent e) {
        gameController.startFunctions();
        System.out.println("StartWork");
        numberOfLoops = 0;
        newGameButton.setVisible(false);
        gridPaneShips.disableProperty().set(false);
        gridPaneTargets.disableProperty().set(false);
        setEverythingVisible(true);
    }

    public void onMouseClickedRectangle(MouseEvent e) {
        int row, column;
        boolean testInterferencePlayer;

        Node source = (Node) e.getSource();

        try {
            row = GridPane.getRowIndex(source);
        } catch (RuntimeException exception) {
            //System.out.println("Błąd: " + exception);
            row = 0;
        }
        try {
            column = GridPane.getColumnIndex(source);
        } catch (RuntimeException exception) {
            column = 0;
        }

        if (numberOfLoops < playerShips.size()) {
            System.out.println("playerShip.size() = " + playerShips.size() + "\ncomputerShips.size() = " + computerShips.size());
            Ship shipPlayer = playerShips.get(numberOfLoops);
            Ship shipComputer = computerShips.get(numberOfLoops);

            testInterferencePlayer = gameController.placeShipsPlayer(shipPlayer, e, row, column, isHorizontal);
            gameController.placeShipComputer(shipComputer);

            if (testInterferencePlayer) {
                numberOfLoops--;
            } else {
                for (UnitPosition unitPosition : shipPlayer.getPositions()) {                     // Kolorowanie pól ze statkami
//                    System.out.println("Ship positions row and column = " + unitPosition.getRow() + "," + unitPosition.getColumn());
                    int shipRow = unitPosition.getRow();
                    int shipColumn = unitPosition.getColumn();

                    ObservableList<Node> childrenPlayer = gridPaneShips.getChildren();    //Kolorowanie pól gracza
                    for (Node node : childrenPlayer) {
                        Integer columnIndex = GridPane.getColumnIndex(node);
                        Integer rowIndex = GridPane.getRowIndex(node);

                        if (columnIndex == null)
                            columnIndex = 0;
                        if (rowIndex == null)
                            rowIndex = 0;

                        if (columnIndex == shipColumn && rowIndex == shipRow) {
                            try {
                                Rectangle rectangle = (Rectangle) node;
                                rectangle.setFill(Color.BLUE);
                            } catch (RuntimeException exception) {
                            }
                        }
                    }

                }
            }
            if(numberOfLoops==playerShips.size()-1){
                horizontalButton.visibleProperty().set(false);
                gridPaneShips.disableProperty().set(true);
                visibleTargetsButton.visibleProperty().set(true);
            }
            numberOfLoops++;
        }                   // Położenie statków i kolorowanie pól na których się znajdują

    }
    // Dodać akcje z gridPaneTarget + rysowanie strzałów

    public void onMouseClickedTarget(MouseEvent e){
        int row, column;
        boolean isHit;

        Node source = (Node) e.getSource();

        try {
            row = GridPane.getRowIndex(source);
        } catch (RuntimeException exception) {
            row = 0;
        }
        try {
            column = GridPane.getColumnIndex(source);
        } catch (RuntimeException exception) {
            column = 0;
        }

        if (numberOfLoops>=playerShips.size()) {
            gameController.RunGame(row, column);
            isHit = gameController.isHitPlayer();

            ObservableList<Node> childrenTarget = gridPaneTargets.getChildren();    //Kolorowanie pól w które strzelał gracz
            for (Node node : childrenTarget) {
                Integer columnIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);

                if (columnIndex == null)
                    columnIndex = 0;
                if (rowIndex == null)
                    rowIndex = 0;

                if (columnIndex == column && rowIndex == row) {
                    try {
                        Rectangle rectangle = (Rectangle) node;
                        shotRectanglesPlayer.add(rectangle);
                        if(isHit) {
                            rectangle.setFill(Color.RED);
                            rectangle.disableProperty().set(true);
                        } else {
                            rectangle.setFill(Color.BLACK);
                            rectangle.disableProperty().set(true);
                        }
                    } catch (RuntimeException exception) {
                    }
                }
            }

            isHit = gameController.isHitComputer();
            ObservableList<Node> childrenPlayer = gridPaneShips.getChildren();    //Kolorowanie pól gracza
            for (Node node : childrenPlayer) {
                Integer columnIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);

                if (columnIndex == null)
                    columnIndex = 0;
                if (rowIndex == null)
                    rowIndex = 0;

                if (columnIndex == gameController.getComputerShot().getColumn() && rowIndex == gameController.getComputerShot().getRow()) {
                    try {
                        Rectangle rectangle = (Rectangle) node;
                        shotRectanglesComputer.add(rectangle);
                        if(isHit) {
                            rectangle.setFill(Color.RED);
                        } else {
                            rectangle.setFill(Color.BLACK);
                        }
                    } catch (RuntimeException exception) {
                    }
                }
            }
        }
    }

    public void onMouseClickedRestartButton() {

       Color color = Color.web("#00c5ff");

        for (int i = 0; i < computerShips.size(); i++) {
            Ship shipComputer = computerShips.get(i);
            for (UnitPosition unitPosition : shipComputer.getPositions()) {        ////////////////POLA KOMPUTERA KOLOROWE
                int shipRow = unitPosition.getRow();
                int shipColumn = unitPosition.getColumn();

                ObservableList<Node> childrenComputer = gridPaneTargets.getChildren();    //Kolorowanie pól komputera
                for (Node node : childrenComputer) {
                    Integer columnIndex = GridPane.getColumnIndex(node);
                    Integer rowIndex = GridPane.getRowIndex(node);

                    if (columnIndex == null)
                        columnIndex = 0;
                    if (rowIndex == null)
                        rowIndex = 0;

                    if (columnIndex == shipColumn && rowIndex == shipRow) {
                        try {
                            Rectangle rectangle = (Rectangle) node;
                            rectangle.disableProperty().set(false);
                            rectangle.setFill(color);
                        } catch (RuntimeException exception) {
                        }
                    }
                }

            } ////////////////POLA KOMPUTERA KOLOROWE
        }

        for(Rectangle rectangle : shotRectanglesPlayer){
            rectangle.setFill(color);
            rectangle.disableProperty().set(false);
        }

        for (int i = 0; i < playerShips.size(); i++) {
            Ship shipPlayer = playerShips.get(i);
            for (UnitPosition unitPosition : shipPlayer.getPositions()) {        ////////////////POLA gracza
                int shipRow = unitPosition.getRow();
                int shipColumn = unitPosition.getColumn();

                ObservableList<Node> childrenComputer = gridPaneShips.getChildren();    //Kolorowanie pól komputera
                for (Node node : childrenComputer) {
                    Integer columnIndex = GridPane.getColumnIndex(node);
                    Integer rowIndex = GridPane.getRowIndex(node);

                    if (columnIndex == null)
                        columnIndex = 0;
                    if (rowIndex == null)
                        rowIndex = 0;

                    if (columnIndex == shipColumn && rowIndex == shipRow) {
                        try {
                            Rectangle rectangle = (Rectangle) node;
                            rectangle.disableProperty().set(false);
                            rectangle.setFill(color);
                        } catch (RuntimeException exception) {
                        }
                    }
                }

            } ////////////////POLA gracza
        }

        for(Rectangle rectangle : shotRectanglesComputer){
            rectangle.setFill(color);
            rectangle.disableProperty().set(false);
        }
//        shotRectanglesComputer=null;
//        shotRectanglesPlayer=null;

        gameController.restartGame();
        playerShips = gameController.playerShips;
        computerShips = gameController.computerShips;

        gameController.startFunctions();
        System.out.println("StartWork");
        numberOfLoops = 0;
        newGameButton.setVisible(false);
        gridPaneShips.disableProperty().set(false);
        gridPaneTargets.disableProperty().set(false);
        setEverythingVisible(true);
    }

    // Extra actions

    public void setEverythingVisible(boolean visible){
        backgroudPhoto.visibleProperty().set(visible);
        gridPaneShips.visibleProperty().set(visible);
        gridPaneTargets.visibleProperty().set(visible);
        horizontalButton.visibleProperty().set(visible);

        ObservableList<Node> gridPaneShipsChildren = gridPaneShips.getChildren();
        for (Node node : gridPaneShipsChildren) {
            try {
                Rectangle rectangle = (Rectangle) node;
                rectangle.visibleProperty().set(visible);
            } catch (RuntimeException e){
                //System.out.println("Błąd wyświetlania kwadratów gridPaneShipsChildren" + e);
            }

        }

        ObservableList<Node> gridPaneTargetChildren = gridPaneTargets.getChildren();
        for (Node node : gridPaneTargetChildren) {
            try {
                Rectangle rectangle = (Rectangle) node;
                rectangle.visibleProperty().set(visible);
            }catch (RuntimeException e) {
                //System.out.println("Błąd wyświetlania kwadratów gridPaneTargetChildren" + e);
            }
        }
    }
}
