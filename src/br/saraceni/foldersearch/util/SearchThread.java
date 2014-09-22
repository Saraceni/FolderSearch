package br.saraceni.foldersearch.util;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SearchThread extends Thread {
	
	private String s_search;
	private Handler handler;
	
	public static final String FILES_KEY = "FILES_KEY";
	public static final String FILES_RESULT = "FILES_RESULT";
	public static final int FILES_RESULT_WHAT = 42;
	
	public static final String TAG = "SearchThread";
	public static final String START_SEARCH = "br.saraceni.foldersearch.START_SEARCH";
	public static final String SEARCH_FILE_KEY = "br.saraceni.foldersearch.SEARCH_FILE_KEY";
	
	public SearchThread(Handler handler, String search)
	{
		this.s_search = search;
		this.handler = handler;
	}
	
	@Override
	public void run()
	{
		File dir = Environment.getExternalStorageDirectory();
		ArrayList<File> files = new ArrayList<File>();
		FolderSneaker.searchFile(dir, s_search, files);
		Log.i(TAG, "Found: " + files.size() + " files");
		Message msg = handler.obtainMessage();
		msg.what = FILES_RESULT_WHAT;
		Bundle bundle = new Bundle();
		bundle.putSerializable(FILES_KEY, files);
		msg.setData(bundle);
		handler.sendMessage(msg);
		dir = Environment.getDataDirectory();
		files = new ArrayList<File>();
		FolderSneaker.searchFile(dir, s_search, files);
		Log.i(TAG, "Found: " + files.size() + " files");
		msg = handler.obtainMessage();
		msg.what = FILES_RESULT_WHAT;
		bundle = new Bundle();
		bundle.putSerializable(FILES_KEY, files);
		msg.setData(bundle);
		handler.sendMessage(msg);
			
	}

}
