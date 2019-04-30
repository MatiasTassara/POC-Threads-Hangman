package UTN.model;

public class Word {

    private int id;
    private String wordName;

    public Word(){
    }

    public Word(int id, String wordName) {
        this.id = id;
        this.wordName = wordName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    @Override
    public String toString() {
        return "Id: " + getId() + " Word: " + getWordName();
    }
}
