package mod.hitlerhax.util;

public class Timer {
	private long time;
	private long current;

	public Timer() {
		this.time = -1L;
		this.current = System.currentTimeMillis();
	}

	public boolean getPassedMillis(final long ms) {
		return this.getPassedNanos(this.convertToNano(ms));
	}

	public boolean getPassedNanos(final long ns) {
		return System.nanoTime() - this.time >= ns;
	}

	public long getPassedTimeMs() {
		return this.convertToMillis(System.nanoTime() - this.time);
	}

	public void reset() {
		this.time = System.nanoTime();
	}

	public void resetCurrent() {
		this.current = System.currentTimeMillis();
	}

	public long convertToMillis(final long time) {
		return time / 1000000L;
	}

	public long convertToNano(final long time) {
		return time * 1000000L;
	}

	public long getTimePassed() {
		return System.currentTimeMillis() - this.current;
	}
}
