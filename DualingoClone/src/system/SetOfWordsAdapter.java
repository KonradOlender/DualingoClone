package system;

import javax.swing.table.AbstractTableModel;

public class SetOfWordsAdapter extends AbstractTableModel{
	SetOfWords words = new SetOfWords();
	
	@Override
	public int getRowCount()
	{
		if(words == null) return 0;
		return words.getSize();
	}
	
	@Override
	public Object getValueAt(int row, int column)
	{
		if(column == 0)
			return row + 1;
		else if(column == 1)
			return words.getDefinition(row);
		else
			return words.getTranslation(row);
	}
	
	@Override
	public int getColumnCount()
	{
		return 3;
	}
	
	@Override
	public String getColumnName(int column)
	{
		if(column == 0)
			return "Lp";
		if(column == 1)
			return "Obcy";
		return "Polski";
	}
	
	@Override
	public boolean isCellEditable(int row, int column)
	{
		if(column == 0)
			return false;
		else
			return true;
	}
	
	@Override
	public void setValueAt(Object value, int row, int column)
	{
		if(column == 1)
		{
			words.changeDefinition((String) value, row);
		}
		if(column == 2)
		{
			words.changeTranslation((String) value, row);
		}
			

	}
	
	@Override
	public Class<?> getColumnClass(int column)
	{
		if(column == 0)
			return Integer.class;
		else
			return String.class;
	}
	
	public void setNewSet(SetOfWords words)
	{
		this.words = words;
		this.fireTableDataChanged();
	}
}
