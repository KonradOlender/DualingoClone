
  import java.sql.Connection;
  import java.sql.DriverManager;
  import java.sql.PreparedStatement;
  import java.sql.ResultSet;
  import java.sql.SQLException;
  import java.sql.Statement;
  import java.util.LinkedList;
  import java.util.List;

  import model.LevelModel;
  import model.StateModel;
  import model.UserModel;
  import model.WordModel;
  
  
public class Database {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:biblioteka.db";

    private static Connection conn;
    private Statement stat;

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

        createTables();
    }
    

    public boolean createTables()  {
    	
    	String createLevel = "CREATE TABLE IF NOT EXISTS level (id_level INTEGER PRIMARY KEY AUTOINCREMENT, name_level varchar(100))";
    	String createWord = "CREATE TABLE IF NOT EXISTS word (id_word INTEGER PRIMARY KEY AUTOINCREMENT, polish varchar(100), foreign varchar(100), idLevel int";
    	String createUser = "CREATE TABLE IF NOT EXISTS user (id_user INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(100))";
    	String createState = "CREATE TABLE IF NOT EXISTS state (id_state INTEGER PRIMARY KEY AUTOINCREMENT, currentUserLevel int, currentProgress int, idUser int)";
    	
    	try {
            stat.execute(createLevel);
            stat.execute(createWord);
            stat.execute(createUser);
            stat.execute(createState);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    //dodawanie danych
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

    public boolean insertWord(String polish, String foreign, int idLevel) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into word values (NULL, ?, ?, ?);");
            prepStmt.setString(1, polish);
            prepStmt.setString(2, foreign);
            prepStmt.setInt(3, idLevel);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu slowa do bazy");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
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
    
    //zwracanie list wszytkiego co jest w bazie 
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
    
    public List<WordModel> selectWords() {
        List<WordModel> words = new LinkedList<WordModel>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM word");
            int id, idLevel;
            String polish, foreign;
            while(result.next()) {
                id = result.getInt("id_word");
                polish = result.getString("polish");
                foreign = result.getString("foreign");
                idLevel = result.getInt("idLevel");
                words.add(new WordModel(id, polish, foreign, idLevel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return words;
    }
    
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
    
    public List<StateModel> selectStates() {
        List<StateModel> states = new LinkedList<StateModel>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM state");
            int id, idUser, currentUserLevel, currentProgress;
            while(result.next()) {
                id = result.getInt("id_word");
                idUser = result.getInt("idUser");
                currentUserLevel = result.getInt("currentUserLevel");
                currentProgress = result.getInt("currentProgress");
                states.add(new StateModel(id, currentUserLevel, currentProgress, idUser));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return states;
    }
    
    //wybieranie z warunkiem - zwracane s� listy 
    public List<WordModel> selectWordWhereLevel(int level) {
  	  List<WordModel> words = new LinkedList<WordModel>();
  	  try {
            ResultSet result = stat.executeQuery("SELECT * FROM word WHERE idLevel=\""+level+"\"");
            int id, idLevel;
            String polish, foreign;
            while(result.next()) {
                id = result.getInt("id_word");
                polish = result.getString("polish");
                foreign = result.getString("foreign");
                idLevel = result.getInt("idLevel");
                words.add(new WordModel(id, polish, foreign, idLevel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return words;
    }

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
    
    public List<StateModel> selectStateWhereUserId(int userId) {
  	  List<StateModel> states = new LinkedList<StateModel>();
  	  try {
            ResultSet result = stat.executeQuery("SELECT * FROM state WHERE idUser=\""+userId+"\"");
            int id, idUser, currentUserLevel, currentProgress;
            while(result.next()) {
                id = result.getInt("id_word");
                idUser = result.getInt("idUser");
                currentUserLevel = result.getInt("currentUserLevel");
                currentProgress = result.getInt("currentProgress");
                states.add(new StateModel(id, currentUserLevel, currentProgress, idUser));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return states;
    }

    //usuwanie po id
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
    
    //edytowanie s�owa
    public void updateWordWhereId(int id, String new_polish, String new_foreign) {
  	  try {
            stat.execute("UPDATE word SET polish=\""+new_polish+"\", foreign=\""+new_foreign+"\" WHERE id_ksiazki=\"" + id +"\"");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //zamykanie po��czenia z baz�
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }
    
}