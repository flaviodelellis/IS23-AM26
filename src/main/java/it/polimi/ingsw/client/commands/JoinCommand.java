package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import java.util.Objects;
import java.util.Scanner;

public class JoinCommand implements Command {

    public void execute(Sender sender, Scanner stdin, Client client) {
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

        sender.addPlayer(name, num);
    }
}
