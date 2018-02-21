package com.arkodestudio.framework.impl;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import com.arkodestudio.framework.Music;

public class AndroidMusic implements Music, OnCompletionListener{
	
	private MediaPlayer mediaPlayer;
	private boolean isPrepared = false;
	
	public AndroidMusic(AssetFileDescriptor assetDescriptor) {
		mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(), 
									  assetDescriptor.getStartOffset(), 
									  assetDescriptor.getLength()
									 );
//			mediaPlayer.prepare();
//			this.isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
		} catch (Exception e) {
			throw new RuntimeException("couldn't load Music");
		}
	}
	
	@Override
	public void play() {
		if (isPlaying()) {
			return;
		}
		try {
			synchronized (this) {
				if (!isPrepared) {
					mediaPlayer.prepare();
				}
				mediaPlayer.start();
			}
		} catch (IllegalStateException e) {
			Log.e("AndroidMusic.class","play()::IllegalStateExcepion");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("AndroidMusic.class","play()::IOException");
			e.printStackTrace();
		}
		 
	}

	@Override
	public void stop() {		
		mediaPlayer.stop();
		synchronized (this) {
			isPrepared = false;
		}
	}

	@Override
	public void pause() {
		if (isPlaying()) {
			mediaPlayer.pause();
		}
	}

	@Override
	public void setLooping(boolean isLooping) {
		mediaPlayer.setLooping(isLooping);
		
	}

	@Override
	public void setVolume(float volume) {
		mediaPlayer.setVolume(volume,volume);
		
	}

	@Override
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();		
	}

	@Override
	public boolean isStopped() {		
		return (!isPrepared) ? true : false;
	}

	@Override
	public boolean isLooping() {
		return mediaPlayer.isLooping();
	}

	@Override
	public void dispose() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
		mediaPlayer.release();
		
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		synchronized (this) {
			isPrepared = false;
		}
		
	}

}
