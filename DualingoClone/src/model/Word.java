package model;

public class Word {
	private int id;
    private String polish;
    private String foreign;
    private int idLevel;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPolish() {
        return polish;
    }
    public void setPolish(String polish) {
        this.polish = polish;
    }
    public String getForeign() {
        return foreign;
    }
    public void setForeign(String foreign) {
        this.foreign = foreign;
    }
    public int getIdLevel() {
        return idLevel;
    }
    public void setIdLevel(int id) {
        this.idLevel = id;
    }

    //public Word() { }
    public Word(int id, String polish, String foreign, int idLevel) {
        this.id = id;
        this.polish = polish;
        this.foreign = foreign;
        this.idLevel = idLevel;
    }

    @Override
    public String toString() {
        return "["+id+"] - "+polish+" "+foreign+" - "+idLevel;
    }
}
