package com.arkodestudio.framework.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.arkodestudio.framework.FileIO;

public class AndroidFileIO implements FileIO {
	private Context context;
	private AssetManager assetManager;
	private String externalStoragePath;
	
	public AndroidFileIO(Context context) {
		this.context = context;
		assetManager = context.getAssets();		
		if (context.getExternalFilesDir(null).isAbsolute()) {
			externalStoragePath = context.getExternalFilesDir(null).toString()+File.separator;
		}
		 //String c = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
		Log.i("[INFO]AndroidFileIO::30::AndroidFileIO()::*externalStoreagePathValue*",externalStoragePath);		
	}
	
	@Override
	final public InputStream readFile(String fileName) throws IOException {
		return new FileInputStream(externalStoragePath+fileName);
	}

	@Override
	final public OutputStream writeFile(String fileName) throws IOException {
		return new FileOutputStream(externalStoragePath+fileName);
	}

	@Override
	final public InputStream readAsset(String fileName) throws IOException {
		return assetManager.open(fileName);
	}

	@Override
	final public SharedPreferences getSharedPreference() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

}
