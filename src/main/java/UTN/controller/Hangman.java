package UTN.controller;

import UTN.dao.DaoHangman;
import UTN.model.Player;
import UTN.model.ThreadColor;
import UTN.model.Word;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static UTN.model.ThreadColor.ANSI_CYAN;
import static UTN.model.ThreadColor.ANSI_RESET;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;


public class Hangman {
    private List<Character> alphabet;
    private Word word;
    private String theWord;
    private static int  lives = 20;
    private volatile boolean keepOnPlaying;
    private String letter;
    private int index;
    private String color;
    private String winnerName;
    private char[] hiddenWord;

    public Hangman() {
        this.alphabet = getAlphabet();
        this.word = retrieveWord();
        this.theWord = this.word.getWordName();
        this.keepOnPlaying = true;
        this.hiddenWord = hideWord(theWord);
        this.letter = "";
        this.index = -1;
        this.color = "";
        this.winnerName = "";
    }

    private Word retrieveWord(){
        DaoHangman daoGame = new DaoHangman();
        daoGame.connect();
        Word w = daoGame.obtainWord();
        return w;
    }
    public List<Character> getAlphabet(){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        List<Character> abc = new ArrayList<>();
        for (int i = 0; i < alphabet.length(); i++) {
            abc.add(alphabet.charAt(i));
        }
        return abc;
    }
    public char[] hideWord(String word){
        char[] hidden = word.toCharArray();
        for(int i = 0; i < hidden.length; i++){
            hidden[i] = '*';
        }
        return hidden;
    }
    public void waitingRoom(){
        while (!keepOnPlaying) {
            try {
                System.out.println("*/*/*/*/*/ EL THREAD " + currentThread().getName() + " ENTRA EN ESPERA */*/*/*/*/");
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setKeepOnPlaying(false);
        play();
    }

    public synchronized void play() {

        switch(currentThread().getName()){
            case "Player 1":
                color = ThreadColor.ANSI_GREEN;
                break;
            case "Player 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            case "Player 3":
                color = ThreadColor.ANSI_BLUE;
                break;
            default:
                color = ANSI_RESET;
        }

        if(!Player.isWinner() && lives > 0) {
            System.out.println( ANSI_RESET + "LIVES LEFT: " + Hangman.lives);
            System.out.println(color + "Esta jugando : " + currentThread().getName());
            Random indexLetter = new Random();
            index = indexLetter.nextInt(alphabet.size()); //alphabet random index
            letter = String.valueOf(alphabet.get(index)); //pick the letter with index
            System.out.println(color + "Alphabet untill now: " + alphabet);
            System.out.println(color + "Thread " + currentThread().getName() + " picks letter " + letter);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            alphabet.remove(index);
            if(!theWord.contains(letter)) {
                lives--; //one life less if player misses the letter
                System.out.println(ANSI_RESET + "The letter " + letter + "  is not in the word.");
            }
            while (theWord.contains(letter)) { //the letter is on the word, so we show it in the hidden word, and hide it in the real word
                index = theWord.indexOf(letter);
                hiddenWord[index] = letter.charAt(0); //show the guessed letter
                theWord = theWord.replaceFirst(letter, "*"); //if letter is a guess, replace it for '*'
            }
            System.out.println(ANSI_CYAN + "Hidden word is: " + String.valueOf(hiddenWord));
            if (theWord.matches("^(.)\\1*$")) { //regex: all characters of the word are the same ('*')
                //we have a winner here
                setWinnerName(currentThread().getName());
                System.out.println(ANSI_CYAN  + " --- "+ winnerName + " WINS!!!");
                Player.setWinner(true);
            }
        }

        setKeepOnPlaying(true);
        notify();
    }

    public Word getWord() {
        return word;
    }

    public static int getLives() {
        return lives;
    }

    public static void setLives(int lives) {
        Hangman.lives = lives;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public  boolean isKeepOnPlaying() {
        return keepOnPlaying;
    }

    public  void setKeepOnPlaying(boolean keepOnPlaying) {
        this.keepOnPlaying = keepOnPlaying;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }
}
