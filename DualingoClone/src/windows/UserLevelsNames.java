package windows;

//klasa, ktora definiuje odpowiednie nazwy poziomow
public enum UserLevelsNames {
	BEGGINER(1,"początkujący"),
	INTERMEDIATE(2, "średniozaawansowany"),
	ADVANCED(3, "zaawansowany"),
	PRO(4, "profesjonalista");
	
	int level;
	String name;
	
	UserLevelsNames(int i, String s) 
	{
		level = i;
		name = s;
	}
	
	//metoda pozwalajaca na pobranie odpowiedniej nazwy poziomu uzytkownika
	public static UserLevelsNames getLevelsName(int searchedLevel)
	{
		UserLevelsNames[] levels = UserLevelsNames.values();
		for(UserLevelsNames lev: levels)
		{
			if(lev.getLevel() == searchedLevel)
				return lev;
		}
		return BEGGINER;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	@Override
	public String toString()
	{
		return name;
	}

}
