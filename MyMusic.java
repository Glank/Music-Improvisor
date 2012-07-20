import java.util.*;

public class MyMusic
{
	public static final int OCTIVE = 12;

	public MyMusic()
	{
	}

	public int[] getMajorScale(int firstNote, int octives)
	{
		int[] scale = new int[8*octives];
		for (int count = 0; count < octives; count++)
		{
			scale[(count*7)+0] = (count*OCTIVE)+firstNote;
			scale[(count*7)+1] = (count*OCTIVE)+2+firstNote;
			scale[(count*7)+2] = (count*OCTIVE)+2+2+firstNote;
			scale[(count*7)+3] = (count*OCTIVE)+2+2+1+firstNote;
			scale[(count*7)+4] = (count*OCTIVE)+2+2+1+2+firstNote;
			scale[(count*7)+5] = (count*OCTIVE)+2+2+1+2+2+firstNote;
			scale[(count*7)+6] = (count*OCTIVE)+2+2+1+2+2+2+firstNote;
		}
		return scale;
	}

	public int[] getDorianScale(int firstNote, int octives)
	{
		int[] scale = new int[8*octives];
		for (int count = 0; count < octives; count++)
		{
			scale[(count*7)+0] = (count*OCTIVE)+firstNote;
			scale[(count*7)+1] = (count*OCTIVE)+2+firstNote;
			scale[(count*7)+2] = (count*OCTIVE)+2+1+firstNote;
			scale[(count*7)+3] = (count*OCTIVE)+2+1+2+firstNote;
			scale[(count*7)+4] = (count*OCTIVE)+2+1+2+2+firstNote;
			scale[(count*7)+5] = (count*OCTIVE)+2+1+2+2+2+firstNote;
			scale[(count*7)+6] = (count*OCTIVE)+2+1+2+2+2+1+firstNote;
		}
		return scale;
	}

	public int[] getChord(int[] scale, int number)
	{
		int[] chord = new int[3];
		chord[0] = scale[getBaseNote(number)];
		chord[1] = scale[getBaseNote(number+2)];
		chord[2] = scale[getBaseNote(number+4)];
		return chord;
	}

	public int getNextChordNumber(int oldNumber)
	{
		int newNumber = 0;
		switch (oldNumber)
		{
			case 2:
				newNumber = 5;
				break;
			case 5:
				if ((int)(Math.random()*5) <= 1)
					newNumber = 3;
				else
					newNumber = 1;
				break;
			case 3:
				if((int)(Math.random()*4) <= 0)
					newNumber = 1;
				else
				{
					if((int)(Math.random()*4) <= 0)
						newNumber = 0;
					else
						newNumber = 4;
				}
				break;
			case 1:
				newNumber = 4;
				break;
			case 4:
				if ((int)(Math.random()*3) <= 0)
					newNumber = 5;
				else
					newNumber = 0;
				break;
			case 0:
				newNumber = (int)(Math.random()*6)+1;
				break;
			default:
				newNumber = 0;
		}
		return newNumber;
	}

	public int getBaseNote(int note)
	{
		while (note >= 7)
			note-=7;
		return note;
	}

	public int getNextNote(int[] scale, int oldNote, int[] chord, int lowNoteBounds, int highNoteBounds)
	{
		int lowestPosibility = oldNote - 3;
		if (lowestPosibility < lowNoteBounds)
			lowestPosibility = lowNoteBounds;

		int highestPosibility = oldNote + 3;
		if (highestPosibility > highNoteBounds)
			highestPosibility = highNoteBounds;

		int[] posibilities = new int[7];
		int numOfPosibilities = 0;
		for(int count = lowestPosibility; count <= highestPosibility; count++)
		{
			if (chordContainsNote(chord, scale, count))
			{
				posibilities[numOfPosibilities] = count;
				numOfPosibilities++;
			}
		}
		int newNote = posibilities[(int)(Math.random()*numOfPosibilities)];
		int tries = 0;
		while ((newNote == oldNote) && (tries < 5))
		{
			newNote = posibilities[(int)(Math.random()*numOfPosibilities)];
			tries++;
		}
		return newNote;
	}

	public boolean chordContainsNote(int[] chord, int[] scale, int note)
	{
		boolean tf = false;
		for (int count = 0; count < chord.length; count++)
		{
			if (chord[count] == scale[getBaseNote(note)])
				tf = true;
		}
		return tf;
	}
}