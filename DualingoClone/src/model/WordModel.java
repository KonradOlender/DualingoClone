package model;

public class WordModel {
	private int id;
    private String word;   //foreign
    private String translation;   //polish
    private int idLevel;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getTranslation() {
        return translation;
    }
    public void setTranslation(String translation) {
        this.translation = translation;
    }
    public int getIdLevel() {
        return idLevel;
    }
    public void setIdLevel(int id) {
        this.idLevel = id;
    }

    //public Word() { }
    public WordModel(int id, String word, String translation, int idLevel) {
        this.id = id;
        this.word = word;
        this.translation = translation;
        this.idLevel = idLevel;
    }

    @Override
    public String toString() {
        return "["+id+"] - "+translation+" "+translation+" - "+idLevel;
    }
}
