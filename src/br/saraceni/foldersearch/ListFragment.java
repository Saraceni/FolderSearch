package br.saraceni.foldersearch;

import java.io.File;
import java.util.ArrayList;

import br.saraceni.foldersearch.util.FolderFilesAdapter;
import br.saraceni.foldersearch.util.FolderSneaker;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListFragment extends Fragment {
	
	private FolderSneaker folderSneaker;
	private FolderFilesAdapter adapter;
	private ArrayList<File> items;
	private ActionBar actionBar;
	private Activity parent;
	//private Messenger serviceMessengerSender;
	//private boolean isBound = false;
	
	public static String FOLDER_KEY = "CURRENT_FOLDER_KEY";
	private static String TAG = "ListFragment";
	
	/* ------------------------- Fragment LifeCycle Methods ----------------------- */
	
	/* This methods are listed in the order they are called, according to the fragment
	 * lifecycle
	 * 
	 */
	
	// Called when the fragment is attached to it's parent activity
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		parent = activity;
		//parent.invalidateOptionsMenu();
	}
	
	//Called to do the initial creation of the fragment
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		folderSneaker = new FolderSneaker();
		actionBar = parent.getActionBar();
		items = new ArrayList<File>();
		adapter = new FolderFilesAdapter(parent, android.R.layout.simple_list_item_1, items);
		setRetainInstance(true);
	}
	
	// Called once the Fragment has been created in order to create its user interface.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		if(savedInstanceState != null && savedInstanceState.containsKey(FOLDER_KEY))
		{
			String folder = savedInstanceState.getString(FOLDER_KEY);
			folderSneaker.setCurrentFolder(folder);
			Log.i(TAG, "ListFragment onRestore: " + folder);
		}
		
		populateItems();
		
		View v = inflater.inflate(R.layout.show_files_layout, container, false);
		ListView listView = (ListView) v.findViewById(R.id.files_listview);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new FolderFileItemClickListener());
		
		return v;
	}
	
	@Override
	public void onDestroy()
	{
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}
	
	
	/* ---------------------- Methods to Persist Fragment State ----------------------- */
	
	// Called to save UI state changes at the end of the active lifecycle.
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		Log.i(TAG, "onSaveInstanceState");
		String currentFolderPath = folderSneaker.getCurrentFolderPath();
		savedInstanceState.putString(FOLDER_KEY, currentFolderPath);
		super.onSaveInstanceState(savedInstanceState);
	}
	
    /* ---------------------------- Parent's Action Bar Methods ---------------------------- */
	
	private void setActionBarTitle(String title)
	{
		actionBar.setTitle(title);
	}
	
    /* -------------------------- Folder Manager Methods ----------------------------- */
	
	private void populateItems()
	{
		File[] files = folderSneaker.getCurrentFolderElements();
		if(files != null && files.length > 0)
		{
			items.clear();
			for(File file : folderSneaker.getCurrentFolderElements())
			{
				items.add(file);
			}
			adapter.notifyDataSetChanged();
		}
		else
		{
			Toast.makeText(parent, folderSneaker.getCurrentFolderPath() + " is empty.", Toast.LENGTH_SHORT).show();
			folderSneaker.goToParentDirectory();
		}
		setActionBarTitle(folderSneaker.getCurrentFolderPath());
	}
	
	public void goToParentDirectory()
	{
		folderSneaker.goToParentDirectory();
		populateItems();
	}
	
    /* ------------------------ Custom OnItemClickListener --------------------------- */
	
	public class FolderFileItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String folderName = (String) ((TextView) view.findViewById(R.id.text)).getText();
			if(folderSneaker.setCurrentFolder(folderName))
			{
				populateItems();
			}
			
		}

	}
	
	
	/* ---------------------- Methods Responsible for Binding Service ------------------------ */
//	
//	private void doBindService()
//	{
		// Establish a connection with the service.  We use an explicit
	    // class name because there is no reason to be able to let other
	    // applications replace our component.
//		Intent intent = new Intent(parent, FolderSearchService.class);
//		parent.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
//		isBound = true;
//	}
//	
//	private void doUnbindService()
//	{
//		if(isBound)
//		{
//			parent.unbindService(serviceConnection);
//			isBound = false;
//		}
//	}
//	
//	/* ------------------- Class Responsible for Binding With the Service -------------------- */
//	
//	private ServiceConnection serviceConnection = new ServiceConnection()
//	{
//		public void onServiceConnected(ComponentName className, IBinder service)
//		{
//			serviceMessengerSender = new Messenger(service);
//			try
//			{
//				Message msg = Message.obtain(new ResultThreadHandler(), FolderSearchService.SET_MESSENGER_SENDER);
//				serviceMessengerSender.send(msg);
//			}
//			catch(RemoteException exc)
//			{
//				// In this case the service has crashed before we could even
//	            // do anything with it; we can count on soon being
//	            // disconnected (and then reconnected if it can be restarted)
//	            // so there is no need to do anything here.
//			}
//			Toast.makeText(parent, "Searching...", Toast.LENGTH_SHORT).show();
//		}
//		
//		public void onServiceDisconnected(ComponentName className)
//		{
//			// This is called when the connection with the service has been
//	        // unexpectedly disconnected -- that is, its process crashed.
//			serviceMessengerSender = null;
//			isBound = false;
//			Toast.makeText(parent, "Service Disconected", Toast.LENGTH_SHORT).show();
//		}
//	};
//	

}












