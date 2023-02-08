package com.one2b3.audio.listeners;

import com.one2b3.audio.Music;

/**
 * Interface definition for a callback to be invoked when playback of a music
 * stream has completed.
 */
public interface LoopListener {

	/**
	 * Called when the media source just looped.
	 *
	 * @param music
	 *            the Music that looped
	 */
	public abstract void onLoop(Music music);
}