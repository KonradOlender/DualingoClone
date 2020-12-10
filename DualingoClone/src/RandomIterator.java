import java.util.Iterator;

public class RandomIterator implements Iterator<Object>
{
	public boolean hasNext() 
	{
		return false;
	}
	
	public Object next()
	{
		return new Object();
	}
}
