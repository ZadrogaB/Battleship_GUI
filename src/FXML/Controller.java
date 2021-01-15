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
    private ImageView backgroudPhoto;

    private GameController gameController = new GameController();

    private List<Ship> playerShips = gameController.playerShips;

    public int numberOfLoops = 0;



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
        boolean testInterference;

        Node source = (Node) e.getSource();

        try {
            row = GridPane.getRowIndex(source);
        } catch (RuntimeException exception) {
            System.out.println("Błąd: " + exception);
            row = 0;
        }
        try {
            column = GridPane.getColumnIndex(source);
        } catch (RuntimeException exception) {
            System.out.println("Błąd: " + exception);
            column = 0;
        }

        if (numberOfLoops < playerShips.size()) {
            Ship ship = playerShips.get(numberOfLoops);
            testInterference = gameController.placeShipsPlayer(ship, e, row, column);
            if (testInterference) {
                numberOfLoops--;
            } else {
                for (UnitPosition unitPosition : ship.getPositions()) {                     // Kolorowanie pól ze statkami
                    System.out.println("Ship positions row and column = " + unitPosition.getRow() + "," + unitPosition.getColumn());
                    int shipRow = unitPosition.getRow();
                    int shipColumn = unitPosition.getColumn();
                    ObservableList<Node> children = gridPaneShips.getChildren();
                    for (Node node : children) {
                        Integer columnIndex = GridPane.getColumnIndex(node);
                        Integer rowIndex = GridPane.getRowIndex(node);

                        if (columnIndex == null)
                            columnIndex = 0;
                        if (rowIndex == null)
                            rowIndex = 0;

                        if (columnIndex == shipColumn && rowIndex == shipRow) {
                            Rectangle rectangle = (Rectangle) node;
                            rectangle.setFill(Color.BLUE);
                        }
                    }

                }
            }

            numberOfLoops++;
        }                   // Położenie statków i kolorowanie pól na których się znajdują
    }

    // Extra actions

    public void setEverythingVisible(boolean visible){
        backgroudPhoto.visibleProperty().set(visible);
        gridPaneShips.visibleProperty().set(visible);
        gridPaneTargets.visibleProperty().set(visible);

        ObservableList<Node> gridPaneShipsChildren = gridPaneShips.getChildren();
        for (Node node : gridPaneShipsChildren) {
            try {
                Rectangle rectangle = (Rectangle) node;
                rectangle.visibleProperty().set(visible);
                } catch (RuntimeException e){
                System.out.println("Błąd wyświetlania kwadratów gridPaneShipsChildren" + e);
                }

            }

    ObservableList<Node> gridPaneTargetChildren = gridPaneTargets.getChildren();
        for (Node node : gridPaneTargetChildren) {
            try {
                Rectangle rectangle = (Rectangle) node;
                rectangle.visibleProperty().set(visible);
            }catch (RuntimeException e) {
                System.out.println("Błąd wyświetlania kwadratów gridPaneTargetChildren" + e);
            }
        }
    }
}
