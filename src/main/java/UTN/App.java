package UTN;

import UTN.model.Hangman;
import UTN.model.Player;

public class App 
{
    public static void main( String[] args ) {
        Hangman hangman = new Hangman();
        Player p1 = new Player("Player 1",hangman);
        Player p2 = new Player("Player 2",hangman);
        Player p3 = new Player("Player 3",hangman);
        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p2);
        Thread t3 = new Thread(p3);
        t1.setName(p1.getName());
        t2.setName(p2.getName());
        t3.setName(p3.getName());


        t2.start();
        t1.start();
        t3.start();

    }
}
