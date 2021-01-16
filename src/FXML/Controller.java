package FXML;

import company.GameController;
import company.Ship;
import company.Shooter;
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

import java.awt.*;
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

    private GameController gameController = new GameController();
    private List<Ship> playerShips = gameController.playerShips;
    private List<Ship> computerShips = gameController.computerShips;
    private int numberOfLoops = 0;
    private boolean isHorizontal=true;



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
            Ship shipPlayer = playerShips.get(numberOfLoops);
            Ship shipComputer = computerShips.get(numberOfLoops);

            testInterferencePlayer = gameController.placeShipsPlayer(shipPlayer, e, row, column, isHorizontal);
            gameController.placeShipComputer(shipComputer);

            if (testInterferencePlayer) {
                numberOfLoops--;
            } else {
                for (UnitPosition unitPosition : shipPlayer.getPositions()) {                     // Kolorowanie pól ze statkami
                    System.out.println("Ship positions row and column = " + unitPosition.getRow() + "," + unitPosition.getColumn());
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
                                rectangle.setFill(Color.BLUE);
                            } catch (RuntimeException exception) {
                            }
                        }
                    }
                } ////////////////POLA KOMPUTERA KOLOROWE
            }
            if(numberOfLoops==playerShips.size()-1){
                horizontalButton.visibleProperty().set(false);
                gridPaneShips.disableProperty().set(true);
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
                            if(isHit) {
                                rectangle.setFill(Color.RED);
                            } else {
                                rectangle.setFill(Color.BLACK);
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
