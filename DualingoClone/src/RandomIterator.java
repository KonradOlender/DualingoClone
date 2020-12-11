import java.util.Iterator;

public class RandomIterator implements Iterator<Word>
{
	public boolean hasNext() 
	{
		return false;
	}
	
	public Word next()
	{
		return new Word();
	}
}
