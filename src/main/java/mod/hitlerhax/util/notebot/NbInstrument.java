package mod.hitlerhax.util.notebot;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class NbInstrument {
	public static boolean Tunning = false;
	public static ArrayList<NbNote> ToTune;
	public static boolean CurrentTuneComplete = false;

	public static boolean Discovering = false;
	public static ArrayList<NbNote> ToDiscover;
	public static boolean CurrentDiscoveryComplete = false;

	public static int CurrentInstrument = -1;

	private static ArrayList<NbNote> notes;
	private static int id;

	public NbInstrument(int id) {
		notes = new ArrayList <>();
		NbInstrument.id = id;
	}

	public static void TuneInstrument() {
		ToTune = new ArrayList <>();

		for (NbNote note : notes) {
			if (note.GetKnownPitch() != note.GetPitch())
				ToTune.add(note);
		}

		Tunning = true;
		CurrentInstrument = id;
	}

	public static void DiscoverInstrument() {
		ToDiscover = new ArrayList <>();
		for (NbNote note : notes) {
			note.SetValidated(false);
			ToDiscover.add(note);
		}

		Discovering = true;
		CurrentInstrument = id;
	}

	public void AddNote(int pitch, BlockPos pos) {
		notes.add(new NbNote(pitch, pos));
	}

	public BlockPos GetNotePosition(int pitch) {
		for (NbNote note : notes) {
			if (note.GetPitch() == pitch)
				return note.GetPosition();
		}

		return null;
	}

}