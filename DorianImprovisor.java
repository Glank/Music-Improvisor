import javax.sound.midi.*;
import java.awt.event.*;
import javax.swing.*;

public class DorianImprovisor extends JFrame
{
    private static MidiChannel channel;
    private static int volume = 127;
    private static int tone = 12*4;
    private static MyMusic component = new MyMusic();
    private static Synthesizer synthesizer;

    public static void main(String[] args) throws MidiUnavailableException
    {
		Synthesizer synthesizer = MidiSystem.getSynthesizer();
		synthesizer.open();
		channel = synthesizer.getChannels()[0];
		int[] scale = component.getDorianScale(tone,5);
		int chord = 0;
		int note = 7;
		double length = 2;
		int beatsPerMeasure = 3;
		while (true)
		{
			on(component.getChord(scale, chord));
			on(scale[note]);
			for(int times = 0; times < beatsPerMeasure; times++)
			{
				try { Thread.sleep((int)(length*200)); } //sleep
				catch (InterruptedException e) {}
				if((int)(Math.random()*3) <= 0) //33% chance
				{
					off(scale[note]);
					note = component.getNextNote(scale, note, component.getChord(scale, chord), 7, 24);
					on(scale[note]);
				}
				try { Thread.sleep((int)(length*200)); } //sleep
				catch (InterruptedException e) {}
				if((int)(Math.random()*2) <= 0) //50% chance
				{
					off(component.getChord(scale, chord));
					off(scale[note]);
					chord = component.getNextChordNumber(chord);
					note = component.getNextNote(scale, note, component.getChord(scale, chord), 7, 24);
					on(component.getChord(scale, chord));
					on(scale[note]);
				}
				else if((int)(Math.random()*3) <= 0)
				{
					off(scale[note]);
					note = component.getNextNote(scale, note, component.getChord(scale, chord), 7, 24);
					on(scale[note]);
				}
			}
			off(component.getChord(scale, chord));
			off(scale[note]);
			chord = component.getNextChordNumber(chord);
			note = component.getNextNote(scale, note, component.getChord(scale, chord), 7, 24);
			on(component.getChord(scale, chord));
			on(scale[note]);
		}
	}

	private static void on(int note)
	{
		channel.noteOn(note, volume);
	}

	private static void off(int note)
	{
		channel.noteOff(note, volume);
	}

	private static void on(int[] chord)
	{
		for (int count = 0; count < chord.length; count++)
			channel.noteOn(chord[count], volume-40);
	}

	private static void off(int[] chord)
	{
		for (int count = 0; count < chord.length; count++)
			channel.noteOff(chord[count], volume-40);
	}
}
