package UTN.model;

import UTN.controller.Hangman;
import UTN.dao.DaoHangman;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Player implements Runnable{
    private String name;
    private Hangman hangman;
    public static volatile boolean winner = false;

    public Player(String name, Hangman hangman) {
        this.name = name;
        this.hangman = hangman;
    }

    @Override
    public void run() {
        while(!winner && Hangman.lives > 0) {
            System.out.println(Thread.currentThread().getName() + " EJECUTA EL METODO RUN");
            hangman.waitingRoom();
        }
        if(winner && getHangman().getWinnerName().equals(name)){
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = sdf.format(date);
            DaoHangman daoGame = new DaoHangman();
            daoGame.connect();
            daoGame.insertWinner(name,getHangman().getWord().getId(),currentDateTime);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hangman getHangman() {
        return hangman;
    }

    public void setHangman(Hangman hangman) {
        this.hangman = hangman;
    }
}
