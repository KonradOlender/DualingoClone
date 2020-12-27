package model;

public class State {
	private int id;
    private int currentUserLevel;
    private int currentProgress;
    private int idUser;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCurrentUserLevel() {
        return currentUserLevel;
    }
    public void setCurrentUserLevel(int currentUserLevel) {
        this.currentUserLevel = currentUserLevel;
    }
    public int getCurrentProgress() {
        return currentProgress;
    }
    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }
    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int id) {
        this.idUser = id;
    }

    //public State() { }
    public State(int id,int currentUserLevel,int currentProgress,int idUser) {
        this.id = id;
        this.currentUserLevel = currentUserLevel;
        this.currentProgress = currentProgress;
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "["+id+"] - "+currentUserLevel+" "+currentProgress+" - "+idUser;
    }
}
