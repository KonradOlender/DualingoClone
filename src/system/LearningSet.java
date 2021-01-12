package system;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

//import LearningSet.RandomIterator;
//import LearningSet.TestIterator;

public class LearningSet {
	private int size;
	private LevelOfWordsToLearn strategy;
	private List<Word> learningSet;
	public int points = 10;
	
	public LearningSet(int size)
	{
		this.size = size;
		learningSet = new ArrayList<Word>();
		/*for(int i=1; i<11; i++)
		{
			Word w = new Word();
			w.translation = "t³umaczenie" + i;
			w.word = "slowko" + i;
			learningSet.add(w);
		}*/
	}
	
	public Word getWordAt(int index)
	{
		return learningSet.get(index);
	}
	
	public int getSize()
	{
		if(learningSet == null)
			return size;
		return learningSet.size();
	}
	
	public void setLevel(LevelOfWordsToLearn level)
	{
		strategy = level;
	}

	public void setWords(List<Word> words)
	{
		this.learningSet=words;
	}

	public void setPoints(int p)
	{
		this.points=p;
	}
	
	public class PractiseIterator implements Iterator<Word>{

		private int index;
		private boolean a,b,c;
		public PractiseIterator() {
			this.index = 0;
			a = false;
			b = false;
			c = false;
		}
		@Override
		public boolean hasNext() {
			return index < learningSet.size();
		}

		@Override
		public Word next() {
			if(index == learningSet.size()/4 && a == false) {
				index = 0;
				a = true;
			}
			else if(index == learningSet.size()/2 && b==false) {
				index = 0;
				b = true;
			}
			else if(index == 3 * (learningSet.size()/4) && c == false) {
				index = 0;
				c = true;
			}
			return learningSet.get(index++);
		}
		
		public Word currentItem()
		{
			return learningSet.get(index);
		}
	}
	
	public class RandomIterator implements Iterator<Word>{

		private int index;
		private List<Word> learningSetcopy;
		public RandomIterator() {
			this.index = 0;
			learningSetcopy = new ArrayList<Word>();
			for(Word ob : learningSet) {
			learningSetcopy.add(ob);
			}
			Collections.shuffle(learningSetcopy);
		}
		@Override
		public boolean hasNext() {
			return index < learningSetcopy.size();
		}

		@Override
		public Word next() {
			
			return learningSetcopy.get(index++);
		}
		
		public Word currentItem()
		{
			return learningSet.get(index);
		}
	}
	
	public Iterator<Word> iterator(int i){
		if(i == 0) {
			return new PractiseIterator();
		}
		else {
			return new RandomIterator();
		}
	}
}
