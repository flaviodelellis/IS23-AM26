package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.TUI.OutputHandler;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.connection.message.EndCause;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Item;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * The ClientTUI class represents a text-based user interface for the client.
 */
public class ClientTUI extends Client {
    private final OutputHandler outputHandler;

    /**
     * Constructs a new ClientTUI instance.
     *
     * @throws RemoteException if there is a communication-related issue.
     */
    public ClientTUI(OutputHandler outputHandler) throws RemoteException {
        super();
        modelView = new ModelView();
        this.outputHandler = outputHandler;
        this.outputHandler.bindModelView(modelView);
    }

    /**
     * Retrieves the OutputHandler associated with the client.
     *
     * @return The OutputHandler instance.
     */
    public OutputHandler getOutputHandler() { return outputHandler; }

    /**
     * Receives the list of available games from the server.
     *
     * @param gameslist The list of available games.
     */
    public void receiveGamesList(ArrayList<LocalGame> gameslist){
        outputHandler.showList(gameslist);
        setState(ClientState.READY);
    }

    /**
     * Receives the client's ID from the server.
     *
     * @param ID The client's ID.
     */
    public void receiveID(String ID){
        setID(ID);
        if(getPhase().equals(ClientPhase.HOME))
            setPhase(ClientPhase.LOBBY);
        setState(ClientState.READY);
    }

    /**
     * Receives the client's name from the server.
     *
     * @param name The client's name.
     */
    public void receiveName(String name){
        setName(name);
        setPhase(ClientPhase.GAME);
        setState(ClientState.READY);
    }

    /**
     * Receives an exception message from the server.
     *
     * @param e The exception message.
     */
    public void receiveException(String e){
        if(getPhase().equals(ClientPhase.MATCH_RECONNECTION)) {
            outputHandler.showInformation("reconnection to game failed!");
            setPhase(ClientPhase.HOME);
            outputHandler.showHomeIntro();
        }
        else
            outputHandler.showError(e);
        setState(ClientState.READY);
    }

    /**
     * Receives a notification of no data from the server.
     */
    public void receiveNothing(){
        setState(ClientState.READY);
    }

    /**
     * Opens the chat interface.
     * Sets the client phase to "CHAT" and starts a new thread for the chat.
     */
    public synchronized void openChat(){
        setPhase(ClientPhase.CHAT);
        outputHandler.showChat();
    }

    /**
     * Closes the chat interface.
     * Sets the client phase back to "GAME" and stops the chat thread.
     */
    public synchronized void closeChat(){
        setPhase(ClientPhase.GAME);
        outputHandler.showGame();
    }

    /**
     * Ping method to check the client's connection to the server.
     *
     * @param ping The ping value sent to the server.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void ping(int ping) throws RemoteException{}

    /**
     * Creates a new game with the specified game ID, game mode, and number of players.
     *
     * @param gameID     The ID of the game.
     * @param gameMode   The game mode.
     * @param numPlayers The number of players in the game.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void createGame(int gameID, GameMode gameMode, int numPlayers) throws RemoteException {
        // se arriva prima l'ID, la fase sara gia a GAME
        setPhase(ClientPhase.LOBBY);
        modelView.init(gameID,gameMode,numPlayers,getName());
        outputHandler.showInformation("A game is created with gameID " + gameID );
    }

    /**
     * Notifies the client about a player joining the game.
     *
     * @param name The name of the joining player.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void playerJoin(String name) throws RemoteException {
        outputHandler.showInformation(name,"joined the game");
    }

    /**
     * Notifies the client about a player leaving the game.
     *
     * @param name The name of the leaving player.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void playerLeave(String name) throws RemoteException {
        outputHandler.showInformation(name,"left the game");
    }

    /**
     * Notifies the client about a player losing the connection.
     *
     * @param name The name of the player who lost the connection.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void playerDisconnect(String name) throws RemoteException {
        outputHandler.showInformation(name,"lost the connection");
    }

    /**
     * Notifies the client about a player reconnecting to the game.
     *
     * @param name The name of the player who reconnected.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void playerReconnect(String name) throws RemoteException {
        // se arriva prima il name, la fase sara gia a GAME
        setPhase(ClientPhase.GAME);
        outputHandler.showInformation(name," reconnected");
    }

    /**
     * Notifies the client that the game has started.
     *
     * @throws RemoteException if there is a communication-related issue.
     */
    public void startGame() throws RemoteException {
        setPhase(ClientPhase.GAME);
        modelView.loadPlayers();
        //chat = new Chat(this);
        //chat.bindOutputHandler(outputHandler);
        outputHandler.showGameIntro();
        outputHandler.showInformation("The game has begun!");
    }

    /**
     * Notifies the client about the start of a new turn.
     *
     * @param name The name of the player whose turn it is.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void newTurn(String name) throws RemoteException {
        modelView.setCurrentPlayer(name);
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showGame();
        outputHandler.showInformation(name,"'s turn!");
    }

    /**
     * Notifies the client that the current round is the last round.
     *
     * @param name The name of the player who finished their bookshelf.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void lastRound(String name) throws RemoteException {

        outputHandler.showInformation(name,"finished his bookshelf!!");
        outputHandler.showInformation(" one round left!");
    }

    /**
     * Notifies the client that the game has ended.
     *
     * @param name  The name of the player who triggered the end of the game.
     * @param cause The cause of the game ending.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void endGame(String name, EndCause cause) throws RemoteException {
        //if(getPhase().equals(ClientPhase.CHAT))
        //    chat.stopThread();
        setPhase(ClientPhase.HOME);
        outputHandler.showInformation("The game is finished!");
        outputHandler.showEndGame();
        outputHandler.showHomeIntro();
    }

    /**
     * Notifies the client about a player picking an item from the library.
     *
     * @param name       The name of the player who picked the item.
     * @param coordinates The coordinates of the item in the library.
     * @param item       The item that was picked.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showCurrentAction(name);
        outputHandler.showInformation(name,": PICK, coordinates " + coordinates.toString());
    }

    /**
     * Notifies the client about a player requesting an undo action.
     *
     * @param name The name of the player who requested the undo action.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void notifyUndo(String name) throws RemoteException{
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showCurrentAction(name);
        outputHandler.showInformation(name,": UNDO");
    }

    /**
     * Notifies the client about a player ordering a set of items.
     *
     * @param name The name of the player who ordered the items.
     * @param list The list of item indices in the order they were chosen.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showCurrentAction(name);
        outputHandler.showInformation(name,": ORDER with " + list.toString());
    }

    /**
     * Notifies the client about a player placing an item on their bookshelf.
     *
     * @param name   The name of the player who placed the item.
     * @param column The column where the item was placed.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void notifyPut(String name, int column) throws RemoteException{
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showBookshelf(name);
        outputHandler.showInformation(name,": PUT, column " + column);
    }

    public void updateChat(ChatMessage chatMessage) throws RemoteException {
        //System.out.println("--> chat message received");
        modelView.updateChat(chatMessage);
        if(getPhase().equals(ClientPhase.CHAT))
            outputHandler.showChatMessage(chatMessage);
        //chat.addChatMessage(chatMessage);
    }

    /**
     * Notifies the client that the connection to the server has been lost.
     * Handles the reconnection process.
     */
    public void lostConnection(){
        //if(getPhase().equals(ClientPhase.CHAT))
        //    chat.stopThread();
        System.out.println();
        outputHandler.showError("connection lost!");
        outputHandler.showInformation("you can close the application with EXIT command");
        if(getPhase().equals(ClientPhase.HOME))
            setPhase(ClientPhase.HOME_RECONNECTION);
        else
            setPhase(ClientPhase.MATCH_RECONNECTION);
        // to do: da stampare solo con TCP
        outputHandler.showInformation("attempting to reconnect...");
    }

    @Override
    public void forceCloseApp() {
        closeApp();
    }

    /**
     * Handles the reconnection process for the client in the home phase.
     */
    public void homeReconnection(){
        outputHandler.showInformation("reconnection to server done!");
        setPhase(ClientPhase.HOME);
        outputHandler.showHomeIntro();
    }

    /**
     * Handles the reconnection process for the client in the game phase.
     */
    public void gameReconnection(){
        outputHandler.showInformation("reconnection to server done!");
        outputHandler.showInformation("attempting to reconnect to the game...");
    }

    /**
     * Sets the client's phase to HOME and notifies the client that they have left the game.
     */
    public void leaveGame() {
        setPhase(ClientPhase.HOME);
        outputHandler.showInformation(name,"left the game!");
        outputHandler.showHomeIntro();
    }

    /**
     * Notifies the client about the remaining time in the game.
     *
     * @param seconds The number of seconds remaining in the game.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void timer(int seconds) throws RemoteException{
        seconds = seconds/1000;
        outputHandler.showInformation(name,seconds  +" seconds left before the end of the game!");
        outputHandler.showInformation(name,"the timer will stop if someone joins the game!");
    }

    public void closeApp(){
        outputHandler.showByeBye();
        System.exit(0);
    }

}
