package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.utils.NotificationsType;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Item;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static it.polimi.ingsw.client.view.utils.NotificationsType.*;
import static it.polimi.ingsw.util.Constants.*;

public class GameController implements GUIController {
    private GUI gui;

    //BOARD
    @FXML
    private ImageView board;
    @FXML
    private GridPane boardGrid;


    //PERSONAL BOOKSHELF
    @FXML
    private ImageView bookshelf;
    @FXML
    private GridPane bookshelfGrid;

    //HAND
    @FXML
    private ImageView hand1;
    @FXML
    private ImageView hand2;
    @FXML
    private ImageView hand3;

    //PERSONAL AND COMMON
    @FXML
    private ImageView common1;
    @FXML
    private ImageView common2;
    @FXML
    private ImageView personalGoalCard;

    //OTHER
    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private GridPane arrowGrid;
    @FXML
    private TextFlow currentPlayer;
    @FXML
    private TextFlow currentPoints;

    //NOTIFCATIONS
    @FXML
    private ListView<String> gameNotifications;
    private int notificationsLenght = 0;

    //CHAT
    @FXML
    private Button sendButton;
    @FXML
    private TextField chatMessage;
    @FXML
    private TextField receiver;
    @FXML
    private ListView<String> chatField;
    private int chatLenght = 0;

    //BOOKSHELF OF OTHER PLAYERS
    @FXML
    private ImageView bookshelf1;
    @FXML
    private GridPane bookshelf1Grid;
    @FXML
    private Text bookshelf1name;
    @FXML
    private ImageView bookshelf2;
    @FXML
    private GridPane bookshelf2Grid;
    @FXML
    private Text bookshelf2name;
    @FXML
    private ImageView bookshelf3;
    @FXML
    private GridPane bookshelf3Grid;
    @FXML
    private Text bookshelf3name;
    @FXML
    private Button orderConfirm;
    @FXML
    private ImageView arrow1;
    @FXML
    private ImageView arrow2;
    @FXML
    private ImageView arrow3;
    @FXML
    private ImageView arrow4;
    @FXML
    private ImageView arrow5;

    //ATTRIBUTES
    private ModelView modelView;
    private LocalBookshelf localBookshelf;
    private ArrayList<Integer> handOrder = new ArrayList<>();
    private ArrayList<Image> handImages = new ArrayList<>();
    private int handClickCount;
    private boolean HandVisible;

    //METHODS
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
    @Override
    public GUI getGui() {
        return gui;
    }

    /* Scene initializers */
    public void init() {
        this.modelView = gui.getClient().getModelView();
        showBoard();
        initPersonal(modelView.getLocalPersonalCard().num);
        initCommon(modelView.getCommonCards());
        initBookshelfs(modelView.getLocalBookshelfs());
    }
    public void initCommon(ArrayList<LocalCommonCard> commonCards) {
        if (gui.getClient().getModelView().getGameMode().equals(GameMode.EASY))
            return;
        int type1 = commonCards.get(0).getType();
        int type2 = commonCards.get(1).getType();

        URL url1 = getClass().getResource(getCommonPathByType(type1));
        if (url1 != null) {
            common1.setImage(new Image(url1.toString()));
            common1.getStyleClass().add("imageView");
        }

        URL url2 = getClass().getResource(getCommonPathByType(type2));
        if (url2 != null) {
            common2.setImage(new Image(url2.toString()));
            common2.getStyleClass().add("imageView");
        }
    }
    public void initPersonal(int num) {
        URL url = getClass().getResource(getPersonalByType(num));
        if (url != null) {
            personalGoalCard.setImage(new Image(url.toString()));
            personalGoalCard.getStyleClass().add("imageView");
        }
    }
    public void initBookshelfs(Map<String, LocalBookshelf> mappa){
        System.out.println("----");
        URL url = getClass().getResource("/Images/boards/bookshelf_orth.png");
        if (url != null) {
            if(mappa.size() >= 2){
                bookshelf1.setImage(new Image(url.toString()));
                if(mappa.size() >= 3){
                    bookshelf2.setImage(new Image(url.toString()));
                    if(mappa.size() >= 4){
                        bookshelf3.setImage(new Image(url.toString()));
                    }
                }
            }
        }
    }

    /* On click methods */
    public void onBoardClicked(MouseEvent event) {
        //Coordinates clickGridCoordinates = getGridCellsIndexes(event);
        Coordinates clickBoardCoordinates = getBoardCellsIndexes(event); //actual model coordinates
        if(isCatchable(clickBoardCoordinates)) {
            gui.pickItem(clickBoardCoordinates);
        }
    }
   /* public void onBookshelfClick(MouseEvent event) {
        int column = getBookshelfCellsColumn(event);
            gui.putItemList(column);
            setEffectNull();
            //removeArrows();
    }*/
    public void onArrowClicked(MouseEvent event){
        ImageView clickedArrow = (ImageView) event.getSource();
        int column = 0;
        if (clickedArrow.equals(arrow1))
            column = 0;
        else if (clickedArrow.equals(arrow2))
            column = 1;
        else if (clickedArrow.equals(arrow3))
            column = 2;
        else if (clickedArrow.equals(arrow4))
            column = 3;
        else if (clickedArrow.equals(arrow5))
            column = 4;

        gui.putItemList(column);
        setArrowsInvisible();
        setEffectNull();
    }
    public void onHandClick(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.YELLOW);
        dropShadow.setWidth(20);
        dropShadow.setHeight(20);

        if (clickedImageView.getEffect() == null ) {
            clickedImageView.setEffect(dropShadow);
            if (clickedImageView.equals(hand1)) {
                handOrder.add(0);
                System.out.println("Added 0");
            } else if (clickedImageView.equals(hand2)) {
                handOrder.add(1);
                System.out.println("Added 1");
            } else if (clickedImageView.equals(hand3)){
                handOrder.add(2);
                System.out.println("Added 2");
            }
        }

        else {
            clickedImageView.setEffect(null);
            if (clickedImageView.equals(hand1)) {
                handOrder.remove(Integer.valueOf(0));
                System.out.println("removed 0");
            }

            else if (clickedImageView.equals(hand2)) {
                handOrder.remove(Integer.valueOf(1));
                System.out.println("removed 1");
            }
            else if (clickedImageView.equals(hand3)) {
                handOrder.remove(Integer.valueOf(2));
                System.out.println("removed 2");
            }

        }

    }
    public void onTickClick() {
        int handSize = modelView.getLocalHand().coordinatesList.size();
        showArrows(handSize);
        gui.selectInsertOrder(handOrder);
        handOrder.clear();
    }

    /* Show methods */
    public void showBookshelf() {
        resetBookshelf();
        String name = gui.getClient().getName();
        LocalBookshelf localBookshelf = modelView.getLocalBookshelfs().get(name);
        for (int i = 0; i < nRowBookshelf; i++) {
            for (int j = 0; j < nColumnBookshelf; j++) {
                Item item = localBookshelf.bookshelf[i][j];
                if (item != null) {
                    URL url = getClass().getResource(item.getImagePath());
                    if (url != null) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(url.toString());
                        imageView.setImage(image);
                        imageView.setFitWidth(35.5);
                        imageView.setFitHeight(35);
                        bookshelfGrid.add(imageView, j, nRowBookshelf - 1 - i); //inserisci colonna, riga
                        //bisogna prima leggere correttamente la matrice, poi stamparla al contrario
                    }
                }
            }
        }
    }
    public void showPlayersBookshelfs(Map<String, LocalBookshelf> localBookshelfs, String nameBookshelfPlayer) {
        int index = 1;
        GridPane gridPane = new GridPane();
        String nameClientPlayer = gui.getClient().getName();

        for(String s : localBookshelfs.keySet()){
            if(!Objects.equals(nameClientPlayer, s)){
                if(Objects.equals(nameBookshelfPlayer, s)){
                    switch (index){
                        case 1 -> {
                            gridPane = bookshelf1Grid;
                            bookshelf1name.setText(s);
                        }
                        case 2 -> {
                            gridPane = bookshelf2Grid;
                            bookshelf2name.setText(s);
                        }
                        case 3 -> {
                            gridPane = bookshelf3Grid;
                            bookshelf3name.setText(s);
                        }
                    }
                    resetPlayersBookshelfs(gridPane);
                    for (int i=0; i<nRowBookshelf; i++){
                        for(int j=0; j<nColumnBookshelf; j++){
                            Item item = localBookshelfs.get(s).bookshelf[i][j];
                            if(item != null){
                                URL url = getClass().getResource(item.getImagePath());
                                if(url != null){
                                    ImageView imageView = new ImageView();
                                    Image image = new Image(url.toString());
                                    imageView.setImage(image);
                                    imageView.setFitWidth(23.5);
                                    imageView.setFitHeight(23.6);
                                    gridPane.add(imageView, j, nRowBookshelf - 1 - i);
                                }
                            }
                        }
                    }
                }
                index++;
            }
        }
    }
    public void showBoard() {
        LocalBoard localBoard = modelView.getLocalBoard();
        resetBoard();

        for (int i = 0; i < nRowBoard; i++) {
                for (int j = 0; j < nColumnBoard; j++) {
                    Item item = localBoard.board[i][j];
                    if (item != null) {
                        URL url = getClass().getResource(item.getImagePath());
                        if (url != null) {
                            ImageView imageView = new ImageView();
                            Image image = new Image(url.toString());
                            imageView.setImage(image);
                            imageView.setFitWidth(45);
                            imageView.setFitHeight(45);
                            boardGrid.add(imageView, j, nRowBoard-1-i); //inserisci colonna, riga
                            //bisogna prima leggere correttamente la matrice, poi stamparla al contrario
                        }
                    }
                }
        }
    }
    public void showHand() {
        removeHandImages();
        //handClickCount = 0;
        LocalHand localHand = modelView.getLocalHand();

        if (HandVisible) {
            if (localHand.size > 0) {
                URL url = getClass().getResource(localHand.hand[0].getImagePath());
                if (url != null) {
                    Image image = new Image(url.toString());
                    hand1.setImage(image);
                }
            }
            if (localHand.size > 1) {
                URL url = getClass().getResource(localHand.hand[1].getImagePath());
                if (url != null) {
                    Image image = new Image(url.toString());
                    hand2.setImage(image);
                }
            }
            if (localHand.size > 2) {
                URL url = getClass().getResource(localHand.hand[2].getImagePath());
                if (url != null) {
                    Image image = new Image(url.toString());
                    hand3.setImage(image);
                }
            }
        }
    }
    public void showCurrentPoints() {
        String playerName = gui.getClient().getName();
        for (LocalPlayer p : modelView.getLocalPlayerList()){
            if (Objects.equals(p.name, playerName)){
                Text text = new Text();
                text.setText("" + p.points);
                currentPoints.getChildren().clear();
                currentPoints.getChildren().add(text);
            }
        }
    }
    public void showArrows(int handSize){
        if (noSpaceLeft(1,handSize))
            arrow1.setVisible(true);
        if (noSpaceLeft(2,handSize))
            arrow2.setVisible(true);
        if (noSpaceLeft(3,handSize))
            arrow3.setVisible(true);
        if (noSpaceLeft(4,handSize))
            arrow4.setVisible(true);
        if (noSpaceLeft(5,handSize))
            arrow5.setVisible(true);
    }

    /* Coordinates getters */
    public Coordinates getGridCellsIndexes(MouseEvent event) {
        double cellWidth = boardGrid.getWidth() / nColumnBoard;
        double cellHeight = boardGrid.getHeight() / nRowBoard;
        int clickedRow = (int) (event.getY() / cellHeight);
        int clickedCol = (int) (event.getX() / cellWidth);
        //System.out.println("getGridCellsIndex ritorna:\n riga --> " + clickedRow + "\n colonna --> " + clickedCol + "\n");
        return new Coordinates(clickedRow, clickedCol);
    }
    public Coordinates getBoardCellsIndexes(MouseEvent event) {
        double cellWidth = boardGrid.getWidth() / nColumnBoard;
        double cellHeight = boardGrid.getHeight() / nRowBoard;
        int clickedRow = (int) (event.getY() / cellHeight);
        int clickedCol = (int) (event.getX() / cellWidth);
        //System.out.println("getBoardCellsIndex ritorna:\n riga --> " + (8-clickedRow) + "\n colonna --> " + clickedCol + "\n");
        return new Coordinates(8 - clickedRow, clickedCol);
    }
    public int getBookshelfCellsColumn(MouseEvent event) {
        double cellWidth = bookshelfGrid.getWidth() / nColumnBookshelf;
        int clickedCol = (int) (event.getX() / cellWidth);

        return clickedCol;
    }

    /* Updating  */
    public void resetBoard(){
        for (Node node : boardGrid.getChildren()) {
            ((ImageView) node).setImage(null);
        }
    }
    private void resetBookshelf() {
        for (Node node : bookshelfGrid.getChildren()) {
            if(node instanceof ImageView)
                ((ImageView) node).setImage(null);
        }
    }
    private void resetPlayersBookshelfs(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if(node instanceof ImageView)
                ((ImageView) node).setImage(null);
        }
    }
    public void removeHandImages(){
        hand1.setImage(null);
        hand2.setImage(null);
        hand3.setImage(null);
    }
    public void setTurn(String name){
        Text text = new Text(name);
        currentPlayer.getChildren().clear();
        currentPlayer.getChildren().add(text);
        if(! name.equals(gui.getClient().getName())){
            bookshelfGrid.setDisable(true);
            boardGrid.setDisable(true);
            HandVisible = false;
        }
        else if(name.equals(gui.getClient().getName())){
            bookshelfGrid.setDisable(false);
            boardGrid.setDisable(false);
            HandVisible = true;
            setArrows();
        }

    }

    /* Images */
    public boolean isImageViewEmpty(ImageView imageView) {
        return imageView.getImage() == null;
    }
    public String getCommonPathByType(int type) {
        return "/Images/common/" + (type + 1) + ".jpg";
    }
    public String getPersonalByType(int n) {
        return "/Images/personal/" + n + ".png";
    }

    /* Checking */
    public boolean isCatchable(Coordinates coordinates)  {
        int row = coordinates.getRow();
        int column = coordinates.getColumn();
        LocalBoard localBoard = modelView.getLocalBoard();
        LocalHand localHand = modelView.getLocalHand();

        if (localHand.coordinatesList.size() > 3) {
            showLocalNotification(TOOMANYITEMS);
            return false;
        }
        if ((row < 0 || row > 8) || (column < 0 || column > 8)) {
            showLocalNotification(OUTOFBOARD);
            return false;
        }
        if (localBoard.board[row][column] == null) {
            showLocalNotification(EMPTYSLOT);
            return false;
        }

        if (row == 0 || row == 8 || column == 0 || column == 8){
            return checkNewCoordinates(coordinates);
        }
        if (localBoard.board[row - 1][column] == null && !localHand.coordinatesList.contains(new Coordinates(row - 1, column))) {
            return checkNewCoordinates(coordinates);
        }
        else if (localBoard.board[row + 1][column] == null && !localHand.coordinatesList.contains(new Coordinates(row + 1, column))) {
            return checkNewCoordinates(coordinates);
        }
        else if (localBoard.board[row][column - 1] == null && !localHand.coordinatesList.contains(new Coordinates(row,column - 1))) {
            return checkNewCoordinates(coordinates);
        }
        else if (localBoard.board[row][column + 1] == null && !localHand.coordinatesList.contains(new Coordinates(row, column + 1))) {
            return checkNewCoordinates(coordinates);
        }
        else {
            showLocalNotification(NOTCATCHABLE);
            return false;
        }
    }
    public boolean checkNewCoordinates (Coordinates coordinates) {
        LocalHand localHand = modelView.getLocalHand();
        if (localHand.coordinatesList.isEmpty())
            return true;

        boolean checkNear = false;
        boolean checkSameColumn = true;
        boolean checkSameRow = true;
        int row = coordinates.getRow();
        int column = coordinates.getColumn();

        for (Coordinates c : localHand.coordinatesList) {
            if (c.equals(coordinates))
                return false;
            if (c.getRow() != row)
                checkSameRow = false;
            if (c.getColumn() != column)
                checkSameColumn = false;
            if (c.getColumn() == column + 1 || c.getColumn() == column - 1 || c.getRow() == row + 1 || c.getRow() == row - 1)
                checkNear = true;
            if (!(checkSameRow || checkSameColumn))
                break;
        }
        return checkNear && (checkSameRow || checkSameColumn);
    }
    public boolean noSpaceLeft(int column, int handsize) {
        String name = gui.getClient().getName();
        LocalBookshelf localBookshelf = modelView.getLocalBookshelfs().get(name);
        int counterSpace = 0;
        if (localBookshelf.bookshelf != null) {
            for (int i = (nRowBookshelf - 1); i >= 0; i--) { // (nRow-1) because we count also the row 0
                if (localBookshelf.bookshelf[i][column] == null)
                    counterSpace++;
            }
        }
        return (counterSpace >= handsize);
    }

    /* Notifications methods */
    public void showLocalNotification(NotificationsType notificationsType) {
        switch (notificationsType){
            case TOOMANYITEMS -> gameNotifications.getItems().add("❮ERROR❯ too many items selected!");
            case OUTOFBOARD -> gameNotifications.getItems().add("❮ERROR❯ out of Board slot!");
            case EMPTYSLOT -> gameNotifications.getItems().add("❮ERROR❯ empty slot!");
            case NOTCATCHABLE -> gameNotifications.getItems().add("❮ERROR❯ not catchable!");
        }
        notificationsLenght = notificationsLenght + 1;
        gameNotifications.scrollTo(notificationsLenght);
    }
    public void showGlobalNotification(NotificationsType notificationsType, String name, Coordinates coordinates, ArrayList<Integer> list, int column) {
        switch (notificationsType){
            case PICK -> gameNotifications.getItems().add("❮ACTION❯ " + name + ": PICK, coord. " + coordinates.toString());
            case UNDO -> gameNotifications.getItems().add("❮ACTION❯ " + name + ": UNDO ");
            case ORDER -> gameNotifications.getItems().add("❮ACTION❯ " + name + ": ORDER with " + list.toString());
            case PUT -> gameNotifications.getItems().add("❮ACTION❯ " + name + ": PUT, column " + column);
            case LASTROUND -> gameNotifications.getItems().add("❮INFO❯ " + name + " has finished his bookshelf!");
        }
        notificationsLenght = notificationsLenght + 1;
        gameNotifications.scrollTo(notificationsLenght);
    }

    /* Chat methods */
    public void sendMessage(ActionEvent event) {
        String message = chatMessage.getText();
        chatMessage.clear();
        if(Objects.equals(receiver.getText(), "")){
            gui.sendMessage(message, null);
        } else {
            String receiverName = receiver.getText();
            gui.sendMessage(message, receiverName);
        }
    }
    public void displayMessage(ChatMessage chatMessage, String name) {
        if (chatMessage.receiver == null && !Objects.equals(chatMessage.sender, name)){
            chatField.getItems().add("❮TO ALL❯ " + chatMessage.sender + ": " + chatMessage.message);
        } else if (chatMessage.receiver == null && Objects.equals(chatMessage.sender, name)) {
            chatField.getItems().add("❮YOU TO ALL❯ " + chatMessage.message);
        } else if (chatMessage.receiver != null && Objects.equals(chatMessage.sender, name)){
            chatField.getItems().add("❮YOU TO " + chatMessage.receiver + "❯ " + chatMessage.message);
        } else {
            chatField.getItems().add("❮TO YOU❯ " + chatMessage.sender + ": " + chatMessage.message);
        }

        chatLenght = chatLenght + 1;
        chatField.scrollTo(chatLenght);
    }

    /* Switch Buttons
    public void onChat(javafx.event.ActionEvent actionEvent){
        this.gui.switchStage(Command.CHAT);
    }
    public void onBookshelfs(javafx.event.ActionEvent actionEvent) {
        this.gui.switchStage(Command.SHOW_BOOKSHELFS);
    }
    */
    /* Other features */
    public void setEffectNull(){
        hand1.setEffect(null);
        hand2.setEffect(null);
        hand3.setEffect(null);
    }
    public void setArrows(){
        URL url = getClass().getResource("/Images/misc/arrow.png");
        Image arrow = new Image(url.toString());
        arrow1.setImage(arrow);
        arrow2.setImage(arrow);
        arrow3.setImage(arrow);
        arrow4.setImage(arrow);
        arrow5.setImage(arrow);
        setArrowsInvisible();
        setTransition(arrow1);
        setTransition(arrow2);
        setTransition(arrow3);
        setTransition(arrow4);
        setTransition(arrow5);

    }
    public void setArrowsInvisible(){
        arrow1.setVisible(false);
        arrow2.setVisible(false);
        arrow3.setVisible(false);
        arrow4.setVisible(false);
        arrow5.setVisible(false);
    }
    public void setTransition(ImageView arrowImageView){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), arrowImageView);
        translateTransition.setByY(20); // Sposta di 20 unità verticalmente
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        // Imposta il tipo di ripetizione dell'animazione (in avanti e indietro nel nostro caso)
        translateTransition.setAutoReverse(true);
        // Avvia l'animazione
        translateTransition.play();

    }
    public void showSelectedItem(ImageView imageview){
        if (isImageViewEmpty(hand1)){
            hand1.setImage(imageview.getImage());
        }
        else if (isImageViewEmpty(hand2)){
            hand2.setImage(imageview.getImage());
        }
        else hand3.setImage(imageview.getImage());
    }
    public ImageView getImageViewFromGridPane(GridPane gridPane, int rowIndex, int columnIndex) {
        ImageView imageView = null;

        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == rowIndex && GridPane.getColumnIndex(node) == columnIndex && node instanceof ImageView) {
                imageView = (ImageView) node;

                break;
            }
        }

        return imageView;
    }
}



