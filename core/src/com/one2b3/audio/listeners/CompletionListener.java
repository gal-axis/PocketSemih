package com.one2b3.audio.listeners;

import com.one2b3.audio.Music;

/**
 * Interface definition for a callback to be invoked when playback of a music
 * stream has completed.
 */
public interface CompletionListener {
	/**
	 * Called when the end of a media source is reached during playback.
	 *
	 * @param music
	 *            the Music that reached the end of the file
	 */
	public abstract void onCompletion(Music music);
}