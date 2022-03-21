package mod.hitlerhax.sound;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import mod.hitlerhax.util.Globals;
import net.minecraft.client.audio.ISound;

public class SongManager implements Globals {

	private final List<ISound> songs = Collections.singletonList(Drippler.sound);

	private ISound menuSong;
	private final ISound currentSong;

	public SongManager() {
		this.menuSong = this.getRandomSong();
		this.currentSong = this.getRandomSong();
	}

	public ISound getMenuSong() {
		this.menuSong = this.getRandomSong();
		return this.menuSong;
	}

	public void play() {
		if (!this.isCurrentSongPlaying()) {
			mc.soundHandler.playSound(currentSong);
		}
	}

	private boolean isCurrentSongPlaying() {
		return mc.soundHandler.isSoundPlaying(currentSong);
	}

	private ISound getRandomSong() {
		Random random = new Random();
		return songs.get(random.nextInt(songs.size()));
	}
}