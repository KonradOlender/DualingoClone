package windows;

public enum UserLevelsNames {
	BEGGINER(1,"pocz�tkuj�cy"),
	INTERMEDIATE(2, "�redniozaawansowany"),
	ADVANCED(3, "zaawansowany"),
	PRO(4, "profesjonalista");
	
	int level;
	String name;
	
	UserLevelsNames(int i, String s) 
	{
		level = i;
		name = s;
	}
	
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
