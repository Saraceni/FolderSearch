package br.saraceni.foldersearch.util;

import java.io.File;
import java.util.ArrayList;

import br.saraceni.foldersearch.ResultActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class FolderSearchService extends Service {
	
	private static final String TAG = "FolderSearchService";
	public static final String FOLDER_NAME_EXTRA = "FOLDER_NAME_EXTRA";
	public static final String SET_MESSAGE_SENDER = "SET_MESSAGE_SENDER";
	public static final String SERVICE_ACTION = "br.saraceni.foldersearch.util.SERVICE_ACTION";
	public static final String SEARCH_RESULT_KEY = "br.saraceni.foldersearch.FolderSearchService.SEARCH_RESULT_KEY";
	
	public static final int SET_MESSENGER_SENDER = 33;
	public static final int MESSAGE_SENDER_SET_ACK = 31;
	//private Handler activityHandler;
	private Messenger serviceMessengerReceiver = new Messenger(new ServiceIncomingHandler());
	private boolean firstResult = true;
	
	/* ----------------------------- Lifecycle Methods ----------------------------- */
	
	@Override
	public void onCreate()
	{
		Log.i(TAG,"onCreate()");
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.i(TAG,"onStartCommand()");
		if(intent.hasExtra(FOLDER_NAME_EXTRA))
		{
			String s_folder = intent.getStringExtra(FOLDER_NAME_EXTRA);
			Log.i(TAG,"Folder Retrieved: " + s_folder);
			new SearchThread(new ServiceIncomingHandler(), s_folder).start();
		}
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public void onDestroy()
	{
		Log.i(TAG,"onDestroy()");
		super.onDestroy();
	}
	
	/* ---------------------- Binding Service Methods and Class ------------------------ */
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return serviceMessengerReceiver.getBinder();
	}
	
	/* ---------------------- Thread Communication Handler -------------------- */
	
	private class ServiceIncomingHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case SearchThread.FILES_RESULT_WHAT:
				Bundle bundle = msg.getData();
				if(bundle.containsKey(SearchThread.FILES_KEY))
				{
					ArrayList<File> result = (ArrayList<File>) bundle.getSerializable(SearchThread.FILES_KEY);
					if(firstResult)
					{
						firstResult = false;
						Intent intent = new Intent(ResultActivity.RESULT_ACTIVITY_ACTION);
						intent.putExtra(SEARCH_RESULT_KEY, result);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						getApplication().startActivity(intent);
					}
				}
				Log.i(TAG, "FolderSearchService FILES_RESULT_WHAT");
				break;
			case SET_MESSENGER_SENDER:
				/*activityHandler = msg.getTarget();
				Log.i(TAG,"activityHandler SET");
				Message activity_msg = new Message();
				activity_msg.what = MESSAGE_SENDER_SET_ACK;
				activityHandler.dispatchMessage(activity_msg);*/
				break;
			}
			
		}
	}

}
