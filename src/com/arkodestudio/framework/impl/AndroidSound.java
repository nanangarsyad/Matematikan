package com.arkodestudio.framework.impl;

import android.media.SoundPool;

import com.arkodestudio.framework.Sound;

public class AndroidSound implements Sound {
	private SoundPool soundPool;
	private int soundId;

	public AndroidSound(SoundPool soundPool, int soundId) {
		this.soundPool = soundPool;
		this.soundId = soundId;
	}
	
	@Override
	/**
	 * @author NaFa Putra Anugrah
	 * @param volume range 0.0 - 1.0
	 *  
	 */
	final public void play(float volume) {		
		soundPool.play(soundId, volume, volume, 0, 0, 1);
	}

	@Override
	final public void dispose() {
		soundPool.unload(soundId);		
	}

}
