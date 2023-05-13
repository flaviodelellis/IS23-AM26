package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControllerTest {

    @Test
    void joinGameTest() throws Exception {
        Controller controller = new Controller();
        controller.addFirstPlayer("marco", GameMode.EASY, 4);
        controller.addPlayer("paolo", 0,null);
        ArrayList<String> list = controller.getGameList();
        list.forEach(System.out:: println);
        Assertions.assertEquals(list.size(),1+1);

        try{
            controller.addPlayer("marco", 0);
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            controller.addPlayer("marco", 1);
        }catch(Exception e){
            e.printStackTrace();
        }
        list = controller.getGameList();
        list.forEach(System.out:: println);
        Assertions.assertEquals(list.size(),1+1);

        controller.addFirstPlayer("marco", GameMode.EASY, 4);
        controller.addPlayer("paolo", 1);
        controller.addPlayer("a", 0);
        list = controller.getGameList();
        list.forEach(System.out:: println);
        Assertions.assertEquals(list.size(),2+1);

        boolean b = false;
        controller.addPlayer("b", 0);
        try{
            controller.addPlayer("c", 0);
        }catch(Exception e){
            e.printStackTrace();
            b = true;
        }
        Assertions.assertTrue(b);
        list = controller.getGameList();
        list.forEach(System.out:: println);
        Assertions.assertEquals(list.size(),1+1);

        controller.addFirstPlayer("marco", GameMode.EASY, 4);
        controller.addFirstPlayer("marco", GameMode.EXPERT, 3);
        controller.addFirstPlayer("marco", GameMode.EASY, 2);
        b = false;
        try {
            controller.addFirstPlayer("marco", GameMode.EXPERT, 1);
        } catch (Exception e){
            e.printStackTrace();
            b = true;
        }
        Assertions.assertTrue(b);
        b = false;
        try {
            controller.addFirstPlayer("marco", GameMode.EXPERT, 5);
        } catch (Exception e){
            e.printStackTrace();
            b = true;
        }
        Assertions.assertTrue(b);
        list = controller.getGameList();
        list.forEach(System.out:: println);
        Assertions.assertEquals(list.size(),4+1);
    }

    @Test
    void SingleGameplayTest() throws RemoteException {
        boolean b=true;
        String ID1 = null;
        String ID2 = null;
        String ID3 = null;
        String ID4 = null;
        Controller controller=null;
        try {
            controller = new Controller();
            ID1=controller.addFirstPlayer("marco", GameMode.EASY, 4);
            ID2=controller.addPlayer("luca", 0);
            ID3=controller.addPlayer("paolo", 0);
            ID4= controller.addPlayer("andrea", 0);
        } catch (Exception e)
        {
            e.printStackTrace();
            b=false;
        }
        Assertions.assertTrue(b);
        ArrayList<String> list = controller.getGameList();
        Assertions.assertEquals(list.size(),0+1);

        b=true;
        try {
            controller.pickItem(new Coordinates(0,0),ID2);
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertFalse(b);

        b=true;
        try {
            controller.pickItem(new Coordinates(0,0),ID3);
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertFalse(b);

        b=true;
        try {
            controller.pickItem(new Coordinates(0,0),ID4);
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertFalse(b);

        b=true;
        try {
            controller.pickItem(new Coordinates(0,0),"salmone");
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertFalse(b);

        b=true;
        try {
            controller.pickItem(new Coordinates(0,4),ID1);
            controller.pickItem(new Coordinates(0,3),ID1);
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertTrue(b);

        ArrayList<Integer> ar=new ArrayList<Integer>();
        ar.add(1);
        ar.add(0);
        b=true;
        try {
            controller.selectInsertOrder(ar,ID2);
        }
        catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertFalse(b);
        b=true;
        try {
            controller.selectInsertOrder(ar,ID1);
        }
        catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertTrue(b);

        b=true;
        try {
            controller.putItemList(1,ID1);
        }
        catch (Exception e){ e.printStackTrace();b=false;}
        Assertions.assertTrue(b);

        b=true;
        try {
            controller.pickItem(new Coordinates(0,4),ID2);
            controller.pickItem(new Coordinates(0,3),ID2);
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertFalse(b);

        b=true;
        try {
            controller.pickItem(new Coordinates(0,4),ID1);
            controller.pickItem(new Coordinates(0,3),ID1);
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertFalse(b);

        b=true;
        try {
            controller.pickItem(new Coordinates(1,4),ID2);
            controller.pickItem(new Coordinates(1,3),ID2);
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertTrue(b);

        b=true;
        try {
            controller.undoPick(ID1);
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertFalse(b);

        b=true;
        try {
            controller.undoPick(ID2);
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertTrue(b);

        b=true;
        try {
            controller.pickItem(new Coordinates(1,4),ID2);
            controller.pickItem(new Coordinates(1,3),ID2);
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertTrue(b);

        b=true;
        try {
            controller.pickItem(new Coordinates(2,4),ID2);
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertFalse(b);

        b=false;
        try {
            controller.pickItem(new Coordinates(0,5),ID2);
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertFalse(b);

        b=false;
        try {
            controller.pickItem(new Coordinates(-1,-1),ID2);
        } catch (Exception e) { e.printStackTrace();b=false;}
        Assertions.assertFalse(b);

        b=true;
        try {
            controller.putItemList(2,ID1);
        }
        catch (Exception e){ e.printStackTrace();b=false;}
        Assertions.assertFalse(b);

        b=true;
        try {
            controller.putItemList(2,ID2);
        }
        catch (Exception e){ e.printStackTrace();b=false;}
        Assertions.assertTrue(b);

        b=true;
        try{
            controller.pickItem(new Coordinates(2,4),ID3);
            controller.pickItem(new Coordinates(2,3),ID3);
        } catch (Exception e){ e.printStackTrace();b=false;}
        Assertions.assertTrue(b);

        b=true;
        try{
            controller.pickItem(new Coordinates(2,5),ID3);
        }catch (Exception e){ e.printStackTrace();b=false;}
        Assertions.assertFalse(b);

        b=true;
        try{
            controller.putItemList(1,ID3);
            controller.pickItem(new Coordinates(4,0),ID4);
            controller.pickItem(new Coordinates(5,0),ID4);
            controller.putItemList(2,ID4);
        } catch (Exception e){ e.printStackTrace();b=false;}
        Assertions.assertTrue(b);

        b=true;
        try {
            controller.pickItem(new Coordinates(4, 1), ID1);
            controller.pickItem(new Coordinates(5, 1), ID1);
            controller.pickItem(new Coordinates(6, 2), ID1);
        } catch (Exception e){ e.printStackTrace();b=false;}
        Assertions.assertFalse(b);

        b=true;
        try{
            controller.undoPick(ID1);
            controller.pickItem(new Coordinates(4,1),ID1);
            controller.pickItem(new Coordinates(5,1),ID1);
            //controller.putItemList(1,ID1);
        } catch (Exception e){ e.printStackTrace();b=false;}
        Assertions.assertTrue(b);
    }

    @Test
    void MultiGameplayTest() throws GameModeException, GameFullException, NumPlayersException, NameAlreadyExistentException, InvalidGameIdException, RemoteException {
        boolean b=true;
        String ID1 = null;
        String ID2 = null;
        String ID3 = null;
        String ID4 = null;
        String ID5 = null;
        String ID6 = null;
        Controller controller=new Controller();

        ID1=controller.addFirstPlayer("marco", GameMode.EASY, 2);
        ID2=controller.addPlayer("luca", 0);
        ID3=controller.addFirstPlayer("marco", GameMode.EASY, 2);
        ID4=controller.addPlayer("luca", 1);
        ID5=controller.addFirstPlayer("marco", GameMode.EASY, 2);
        ID6=controller.addPlayer("luca", 2);

        try{
            controller.pickItem(new Coordinates(1,3),ID1);
            controller.pickItem(new Coordinates(1,3),ID3);
            controller.pickItem(new Coordinates(1,3),ID5);
            controller.pickItem(new Coordinates(1,4),ID1);
            controller.pickItem(new Coordinates(1,4),ID3);
            controller.pickItem(new Coordinates(1,4),ID5);
            controller.putItemList(0,ID1);
            ArrayList<Integer> a = new ArrayList<Integer>();
            a.add(1);a.add(0);
            controller.selectInsertOrder(a,ID3);
            controller.undoPick(ID5);
            controller.pickItem(new Coordinates(2,3),ID2);
            controller.pickItem(new Coordinates(2,4),ID2);
            controller.putItemList(0,ID2);
            controller.putItemList(0,ID3);
            controller.pickItem(new Coordinates(2,3),ID4);
            controller.pickItem(new Coordinates(2,4),ID4);
            controller.pickItem(new Coordinates(5,1),ID1);
            controller.pickItem(new Coordinates(1,3),ID5);
            controller.putItemList(0,ID1);
            controller.putItemList(0,ID4);
            controller.putItemList(0,ID5);
            controller.pickItem(new Coordinates(5,1),ID6);
            controller.putItemList(0,ID6);
        } catch(Exception e){
            e.printStackTrace();
            b=false;
        }
        Assertions.assertTrue(b);
    }
}