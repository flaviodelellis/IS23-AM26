package it.polimi.ingsw.clientTest;

import it.polimi.ingsw.client.localModel.localBoard;
import it.polimi.ingsw.client.localModel.localBookshelf;
import it.polimi.ingsw.client.localModel.localHand;
import it.polimi.ingsw.client.localModel.localPlayer;
import it.polimi.ingsw.controller.ClientSkeleton;
import it.polimi.ingsw.model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Client extends UnicastRemoteObject implements ClientSkeleton {
    private String ID;
    private boolean state;

    public Client() throws RemoteException{
        state = false;
    }
    public void newChatMessage(String name, String message) throws RemoteException {
        System.out.println(">> "+name+": "+message);
    }

    public void playerJoin(String name) throws RemoteException {
        System.out.println(">> "+name+" si è unito alla partita");
    }

    public void playerLeave(String name) throws RemoteException {
        System.out.println(">> "+name+": ha lasciato la partita");
    }

    public void startGame(String name) throws RemoteException {
        System.out.println(">> partitia iniziata!");
        System.out.println(">> e' turno di "+name);
    }

    public void newTurn(String name) throws RemoteException {
        System.out.println(">> e' il turno di "+name);
    }

    public void lastRound(String name) throws RemoteException {
        System.out.println(">> "+name+" ha completato la sua libreria!");
    }

    public void endGame(String name) throws RemoteException {
        System.out.println(">> partitia terminata!");
        System.out.println(">> il vincitore e' "+name);
    }


    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{
        System.out.println(">> "+name+": PICK "+item.getType().getValue()+" in coords "+coordinates.toString());
    }
    public void notifyUndo(String name) throws RemoteException{
        System.out.println(">> "+name+": UNDO ");
    }
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{
        System.out.println(">> "+name+": ORDER with "+list.toString());
    }
    public void notifyPut(String name, int column) throws RemoteException{
        System.out.println(">> "+name+": PUT in column "+column);
    }

    public void updateBoard(localBoard board) throws RemoteException {
        int i,j;
        System.out.print("\n");
        for (i = -1; i < 9; i++) {
            for (j = -1; j < 9; j++) {
                if(i==-1 && j==-1)
                    System.out.print("/ ");
                else if(i==-1 )
                    System.out.print(j+" ");
                else if(j==-1 )
                    System.out.print(i+" ");
                else{
                    Item item = board.board[i][j];
                    if(item==null)
                        System.out.print("  ");
                    else
                        System.out.print(item.getType().getValue()+" ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public void updateBookshelf(localBookshelf bookshelf) throws RemoteException {
        int i,j;
        System.out.print("\n");
        System.out.println("libreria di "+bookshelf.name+":");
        for (i = 5; i > -2; i--) {
            if(i>=0)
                System.out.print("|");
            else
                System.out.print(" ");
            for (j = 0; j < 5; j++) {
                if(i>=0) {
                    Item item = bookshelf.bookshelf[i][j];
                    if (item==null)
                        System.out.print("  |");
                    else {
                        System.out.print(item.getType().getValue() + " |");
                    }
                }
                else
                    System.out.print(j+"  ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public void updateHand(localHand hand) throws RemoteException {
        System.out.print("\n");
        System.out.println("mano:");
        for(Item item: hand.hand)
            System.out.print(item.getType().getValue() + " ");
        System.out.print("\n");
    }

    public void updatePlayerList(ArrayList<localPlayer> playerList) throws RemoteException {
        System.out.print("\n");
        System.out.println("ecco i giocatori: ");
        for(localPlayer p:playerList) {
            System.out.print(p.name + " ");
            if(p.firstPlayerSeat)
                System.out.print("*\n");
            else
                System.out.print("\n");
        }
        System.out.print("\n");
    }

    public void updateCommonGoalCard(ArrayList<CommonGoalCard> commonGoalCardslist) throws RemoteException{
        System.out.println("... due common goal card con i relativi token ... ");
    }
    public void sendPersonalGoalCard(PersonalGoalCard personalGoalCard) throws RemoteException{
        System.out.println("... la tua personal goal card ... ");
    }

    public String getID() {
        return ID;
    }

    public void setId(String ID){
        this.ID=ID;
    }

    public boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state=state;
    }
}