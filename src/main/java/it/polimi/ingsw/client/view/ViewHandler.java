package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.localModel.localBoard;
import it.polimi.ingsw.client.localModel.localBookshelf;
import it.polimi.ingsw.client.localModel.localHand;
import it.polimi.ingsw.client.localModel.localPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewHandler {
    private CLI cli = new CLI();
    public void showGame(localBoard board, HashMap<String, localBookshelf> bookshelfmap, localHand hand, ArrayList<localPlayer> localPlayerList) {
        cli.showBoard(board);
        for(localPlayer p: localPlayerList){
            cli.showBookshelf(bookshelfmap.get(p));
        }
        //cli.showHand(hand);
        //cli.showPersonalCard();
        //cli.showCommonCard();
    }

    public void showBoad(localBoard board) {
        cli.showBoard(board);
    }
    public void showBookshelf(localBookshelf bookshelf) {
        cli.showBookshelf(bookshelf);
    }

    public void showHand(localHand hand) {
        cli.showHand(hand);
    }
}