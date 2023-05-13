package it.polimi.ingsw.clientTest.commands;

import it.polimi.ingsw.clientTest.Client;
import it.polimi.ingsw.controller.ControllerSkeleton;

import java.util.Objects;
import java.util.Scanner;

public class JoinCommand implements Command{
    @Override
    public void execute(ControllerSkeleton controller, Scanner stdin, Client client) {
        String name = stdin.next();
        int num = stdin.nextInt();

        if(Objects.equals(name, "")){
            System.out.println("CLIENT:: nome non valido");
            return;
        }

        if(num<0){
            System.out.println("CLIENT:: numero partita non valida");
            return;
        }

        try {
            String id = controller.addPlayer(name,num,client);
            System.out.println("CLIENT: giocatore connesso");
            client.setId(id);
            client.setState(true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}