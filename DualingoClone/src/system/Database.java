package system;

  import java.sql.Connection;
  import java.sql.DriverManager;
  import java.sql.PreparedStatement;
  import java.sql.ResultSet;
  import java.sql.SQLException;
  import java.sql.Statement;
  import java.util.LinkedList;
  import java.util.List;

import model.LanguageModel;
import model.LevelModel;
  import model.StateModel;
  import model.UserModel;
  import model.WordModel;
  
  
public class Database {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:biblioteka.db";

    private static Connection conn;
    private Statement stat;

    //tworzenie polaczenia z baza w konstruktorze
    public Database() {
    	try {
            Class.forName(Database.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }

        //wywolanie funkcji tworzacej tabele
        createTables();
    }
    

    //tworzenie tabel w bazie - jesli juz wczesniej nie zostaly utworzone
    public boolean createTables()  {
    	
    	String createLevel = "CREATE TABLE IF NOT EXISTS level (id_level INTEGER PRIMARY KEY AUTOINCREMENT, name_level varchar(100))";
    	String createWord = "CREATE TABLE IF NOT EXISTS word (id_word INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR(100), translation VARCHAR(100), idLevel int, idLanguage int)";
    	String createUser = "CREATE TABLE IF NOT EXISTS user (id_user INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(100))";
    	String createState = "CREATE TABLE IF NOT EXISTS state (id_state INTEGER PRIMARY KEY AUTOINCREMENT, currentUserLevel int, currentProgress int, idUser int)";
    	String createLanguage = "CREATE TABLE IF NOT EXISTS language (id_language INTEGER PRIMARY KEY AUTOINCREMENT, name_language varchar(100))";
    	
    	try {
            stat.execute(createLevel);
            stat.execute(createWord);
            stat.execute(createUser);
            stat.execute(createState);
            stat.execute(createLanguage);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    //usuwanie wybranej tabeli
    public void dropTable(String name) {
  	  try {
            stat.execute("DROP TABLE "+ name);
            
        } catch (SQLException e) {
            System.err.println("Blad przy usuwaniu tabeli " + name);
            e.printStackTrace();
        }
    }
    
    //DODAWANIE DANYCH
    //dodawanie poziomu
    public boolean insertLevel(String name) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into level values (NULL, ?);");
            prepStmt.setString(1, name);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu poziomu do bazy");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //dodawanie slowa
    public boolean insertWord(String word, String translation, int idLevel, int idLanguage) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into word values (NULL, ?, ?, ?, ?);");
            prepStmt.setString(1, word);
            prepStmt.setString(2, translation);
            prepStmt.setInt(3, idLevel);
            prepStmt.setInt(4, idLanguage);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu slowa do bazy");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    //dodawanie uzytkownika
    public boolean insertUser(String name) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into user values (NULL, ?);");
            prepStmt.setString(1, name);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu uzytkowanika do bazy");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //dodawanie stanu
    public boolean insertState(int currentUserLevel, int currentProgress, int idUser) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into state values (NULL, ?, ?, ?);");
            prepStmt.setInt(1, currentUserLevel);
            prepStmt.setInt(2, currentProgress);
            prepStmt.setInt(3, idUser);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu stanu do bazy");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //dodanie jezyka
    public boolean insertLanguage(String name) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into language values (NULL, ?);");
            prepStmt.setString(1, name);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu jezyka do bazy");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    //FUNKCJE ZWRACAJACE LISTY - wszystkie wiersze, bez narzucowych warunkow
    //generowanie listy wszystkich poziomow
    public List<LevelModel> selectLevels() {
        List<LevelModel> levels = new LinkedList<LevelModel>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM level");
            int id;
            String name;
            while(result.next()) {
                id = result.getInt("id_level");
                name = result.getString("name_level");
                levels.add(new LevelModel(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return levels;
    }

    //generowanie listy wszystkich slow
    public List<WordModel> selectWords() {
        List<WordModel> words = new LinkedList<WordModel>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM word");
            int id, idLevel, idLanguage;
            String word, translation;
            while(result.next()) {
                id = result.getInt("id_word");
                word = result.getString("word");
                translation = result.getString("translation");
                idLevel = result.getInt("idLevel");
                idLanguage = result.getInt("idLanguage");
                words.add(new WordModel(id, word, translation, idLevel, idLanguage));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return words;
    }

    //generowanie listy wszystkich uzytkownikow
    public List<UserModel> selectUsers() {
        List<UserModel> users = new LinkedList<UserModel>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM user");
            int id;
            String name;
            while(result.next()) {
                id = result.getInt("id_user");
                name = result.getString("name");
                users.add(new UserModel(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }

    //generowanie listy wszystkich stanow
    public List<StateModel> selectStates() {
        List<StateModel> states = new LinkedList<StateModel>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM state");
            int id, idUser, currentUserLevel, currentProgress;
            while(result.next()) {
                id = result.getInt("id_state");
                currentUserLevel = result.getInt("currentUserLevel");
                currentProgress = result.getInt("currentProgress");
                idUser = result.getInt("idUser");
                states.add(new StateModel(id, currentUserLevel, currentProgress, idUser));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return states;
    }

    //generowanie listy wszystkich jezykow
    public List<LanguageModel> selectLanguages() {
        List<LanguageModel> languages = new LinkedList<LanguageModel>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM language");
            int id;
            String name;
            while(result.next()) {
                id = result.getInt("id_language");
                name = result.getString("name_language");
                languages.add(new LanguageModel(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return languages;
    }
    
    //FUNKCJE ZWRACAJACE Z BAZY LISTY - z podanymi warunkami w parametrach
    //generowanie listy wszystkich slow o wybranym poziomie
    public List<WordModel> selectWordsWhereLevel(int level) {
  	  List<WordModel> words = new LinkedList<WordModel>();
  	  try {
            ResultSet result = stat.executeQuery("SELECT * FROM word WHERE idLevel=\""+level+"\"");
            int id, idLevel, idLanguage;
            String word, translation;
            while(result.next()) {
                id = result.getInt("id_word");
                word = result.getString("word");
                translation = result.getString("translation");
                idLevel = result.getInt("idLevel");
                idLanguage = result.getInt("idLanguage");
                words.add(new WordModel(id, word, translation, idLevel, idLanguage));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return words;
    }

    //generowanie listy wszystkich slow na podanym poziomie, w podanym jezyku, gdzie slowo lub tlumaczenie zawiera podanych fragment
    public List<WordModel> selectWordsWhereConditions(int level, String searchedPhrase, int language) {
    	List<WordModel> words = new LinkedList<WordModel>();
    	  try {
    		  ResultSet result;
    		  if(searchedPhrase == null || searchedPhrase=="" ) {
    			  result = stat.executeQuery("SELECT * FROM word WHERE idLevel=\""+level+"\" AND idLanguage=\""+language+"\"");
    		  }
    		  else result = stat.executeQuery("SELECT * FROM word WHERE idLevel=\""+level+"\" AND idLanguage=\""+language+"\" AND ( word LIKE \"%"+searchedPhrase+"%\" OR translation LIKE \"%"+searchedPhrase+"%\")");
              
    		  int id, idLevel, idLanguage;
              String word, translation;
              while(result.next()) {
                  id = result.getInt("id_word");
                  word = result.getString("word");
                  translation = result.getString("translation");
                  idLevel = result.getInt("idLevel");
                  idLanguage = result.getInt("idLanguage");
                  words.add(new WordModel(id, word, translation, idLevel, idLanguage));
              }
          } catch (SQLException e) {
              e.printStackTrace();
              return null;
          }
          return words;
    }
    
    //generowanie listy wszytkich slow o podanych warunkach
    public List<WordModel> getWords(String newword, String newtranslation, int level, int language) {
    	List<WordModel> words = new LinkedList<WordModel>();
    	try {
  		  ResultSet result = stat.executeQuery("SELECT * FROM word WHERE idLevel=\""+level+"\" AND idLanguage=\""+language+"\" AND  word=\""+newword+"\" AND translation=\""+newtranslation+"\"");
            
  		  int id, idLevel, idLanguage;
            String word, translation;
            while(result.next()) {
                id = result.getInt("id_word");
                word = result.getString("word");
                translation = result.getString("translation");
                idLevel = result.getInt("idLevel");
                idLanguage = result.getInt("idLanguage");
                words.add(new WordModel(id, word, translation, idLevel, idLanguage));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return words;
    }

    //generowanie listy wszystkich uzytkownikow o podanej nazwie
    public List<UserModel> selectUserWhereName(String user_name) {
  	  List<UserModel> users = new LinkedList<UserModel>();
  	  try {
            ResultSet result = stat.executeQuery("SELECT * FROM user WHERE name=\""+user_name+"\"");
            int id;
            String name;
            while(result.next()) {
                id = result.getInt("id_user");
                name = result.getString("name");
                users.add(new UserModel(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }

    //generowanie listy wszystkich stanow konkretnego uzytkownika, ktorego id jest podane jako parametr
    public List<StateModel> selectStateWhereUserId(int userId) {
  	  List<StateModel> states = new LinkedList<StateModel>();
  	  try {
            ResultSet result = stat.executeQuery("SELECT * FROM state WHERE idUser=\""+userId+"\"");
            int id, idUser, currentUserLevel, currentProgress;
            while(result.next()) {
                id = result.getInt("id_state");
                currentUserLevel = result.getInt("currentUserLevel");
                currentProgress = result.getInt("currentProgress");
                idUser = result.getInt("idUser");
                states.add(new StateModel(id, currentUserLevel, currentProgress, idUser));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return states;
    }

    //generowanie listy wszystkich stanow konkretnego uzytkownika, ktorego id jest podane jako parametr - bez duplikatow
    public List<State> selectDistinctStateWhereUserId(int userId) {
  	  List<State> states = new LinkedList<State>();
  	  try {
            ResultSet result = stat.executeQuery("SELECT DISTINCT currentUserLevel, currentProgress FROM state WHERE idUser=\""+userId+"\"");
            int id, idUser, currentUserLevel, currentProgress;
            while(result.next()) {
                currentUserLevel = result.getInt("currentUserLevel");
                currentProgress = result.getInt("currentProgress");
                State s = new State();
    			s.setCurrentUserLevel(currentUserLevel);
    			s.setCurrentUserProgress(currentProgress);
    			states.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return states;
    }

    //generowanie listy wszystkich stanow konkretnego uzytkownika, z danego poziomu i z danym postepem
    public List<StateModel> selectStateWhereConditions(int cul, int cup, int userId) {
  	  List<StateModel> states = new LinkedList<StateModel>();
  	  try {
            ResultSet result = stat.executeQuery("SELECT * FROM state WHERE currentUserLevel=\"" +cul+"\" AND currentProgress=\"" +cup+"\" AND idUser=\""+userId+"\"");
            int id, idUser, currentUserLevel, currentProgress;
            while(result.next()) {
                id = result.getInt("id_state");
                currentUserLevel = result.getInt("currentUserLevel");
                currentProgress = result.getInt("currentProgress");
                idUser = result.getInt("idUser");
                states.add(new StateModel(id, currentUserLevel, currentProgress, idUser));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return states;
    }
    
    //generowanie listy wszystkich jezykow o podanej nazwie 
    public List<LanguageModel> selectLanguageWhereName(String nameL) {
  	  List<LanguageModel> languages = new LinkedList<LanguageModel>();
  	  try {
            ResultSet result = stat.executeQuery("SELECT * FROM language WHERE name_language=\""+nameL+"\"");
            int id;
            String name;
            while(result.next()) {
                id = result.getInt("id_language");
                name = result.getString("name_language");
                languages.add(new LanguageModel(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return languages;
    }

    //USUWANIE DANYCH Z BAZY
    //usuwanie poszczegolnych rzeczy o podanym id
    public void deleteLevelWhereId(int id) {
  	  try {
            stat.execute("DELETE FROM level WHERE id_level=\"" +id +"\"");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWordWhereId(int id) {
  	  try {
            stat.execute("DELETE FROM word WHERE id_word=\"" +id +"\"");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserWhereId(int id) {
  	  try {
            stat.execute("DELETE FROM user WHERE id_user=\"" +id +"\"");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStateWhereId(int id) {
  	  try {
            stat.execute("DELETE FROM state WHERE id_state=\"" +id +"\"");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLanguageWhereId(int id) {
  	  try {
            stat.execute("DELETE FROM language WHERE id_language=\"" +id +"\"");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //INNE
    //edytowanie s�owa o podanym id - zmiana jego definicji i tlumaczenia
    public void updateWordWhereId(int id, String word, String translation) {
  	  try {
            stat.execute("UPDATE word SET translation=\""+translation+"\", word=\""+word+"\" WHERE id_word=\"" + id +"\"");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //zwracanie indeksu stanu danego uzytkownika, ktora jako ostatni zostal dodany do bazy
    public int selectLastStateWhereUserId(int userId) {
    	int maxId = -1;
    	  try {
              ResultSet result = stat.executeQuery("SELECT MAX(id_state) AS Max FROM state WHERE idUser=\""+userId+"\"");
              maxId = result.getInt(1);
          } catch (SQLException e) {
              e.printStackTrace();
              return -1;
          }
          return maxId;
    }


    //ZAMYKANIE POLACZENIA Z BAZA
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }
    
}
