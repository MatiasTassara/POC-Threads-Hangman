package UTN.dao;

import UTN.model.Word;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoHangman {
    static final String DB_URL = "jdbc:mysql://localhost:3306/hangman";
    static final String USER = "root";
    static final String PASS = "atila";
    Connection conn = null;

    public void connect() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to DB...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.err.println("Connection failed.");
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public Word obtainWord(){
        System.out.println("Selecting random word...");
        int idWord ;
        String wordString;
        Word word = new Word();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select id_word, word from words order by rand() limit 1");
            if(rs.next()) {
                idWord = rs.getInt("id_word");
                wordString = rs.getString("word");
                word = new Word(idWord, wordString);
            }
        }
        catch (SQLException e){
            System.err.println("Couldn't get word from DB.");
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            }
            catch (SQLException e){
                System.err.println("Couldn't close database");
                e.printStackTrace();
            }
        }
        return word;
    }

    public void insertWinner(String winner,int idWord, String date){

        try {
            String query = "insert into winners (name,id_word,date_win) values (?, ?, ?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, winner);
            preparedStmt.setInt(2, idWord);
            preparedStmt.setString(3, date);
            preparedStmt.execute();
        }
        catch (SQLException e){
            System.err.println("Couldn't insert" + winner + " into database.");
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            }
            catch (SQLException e){
                System.err.println("Couldn't close database");
                e.printStackTrace();
            }
        }
    }

}
