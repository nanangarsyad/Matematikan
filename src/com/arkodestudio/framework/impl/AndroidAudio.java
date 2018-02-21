package com.arkodestudio.framework.impl;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.arkodestudio.framework.Audio;
import com.arkodestudio.framework.Music;
import com.arkodestudio.framework.Sound;

public class AndroidAudio implements Audio{

	private SoundPool soundPool;
	private AssetManager assetManager;
	
	public AndroidAudio(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		assetManager = activity.getAssets();
		soundPool = new SoundPool(20,AudioManager.STREAM_MUSIC,0);		
	}
	
	@Override
	public Music createMusic(String fileName) {
		try {
			AssetFileDescriptor assetDescriptor = assetManager.openFd(fileName);
			return new AndroidMusic(assetDescriptor);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load music *" + fileName+"*");
		}		
	}

	@Override
	public Sound createSound(String fileName) {
		try {
			AssetFileDescriptor assetDescriptor = assetManager.openFd(fileName);
			int soundId = soundPool.load(assetDescriptor, 0);
			return new AndroidSound(soundPool, soundId);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load sound *" + fileName + "*");
		}
		
	}
	
}
