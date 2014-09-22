package br.saraceni.foldersearch.test;

import java.io.File;
import java.util.ArrayList;

import br.saraceni.foldersearch.R;
import br.saraceni.foldersearch.util.FolderFilesAdapter;
import br.saraceni.foldersearch.util.FolderSneaker;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TestActivity extends Activity {
	
	private FolderSneaker folderSneaker;
	private FolderFilesAdapter adapter;
	private ArrayList<File> items;
	private ActionBar actionBar;
	
	public static String FOLDER_KEY = "CURRENT_FOLDER_KEY";
	private static String TAG = "ListView";
	
	/* ------------------------- Activity Lifecycle Methods -------------------------- */
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		folderSneaker = new FolderSneaker();
		if(savedInstanceState != null && savedInstanceState.containsKey(FOLDER_KEY))
		{
			String folder = savedInstanceState.getString(FOLDER_KEY);
			folderSneaker.setCurrentFolder(folder);
			Log.i(TAG, folder);
		}
		ListView listView = new ListView(this);
		items = new ArrayList<File>();
		adapter = new FolderFilesAdapter(this, android.R.layout.simple_list_item_1, items);
		populateItems();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new FolderFileItemClickListener());
		setActionBar();
		Log.i(TAG, "on create");
		setContentView(listView);
	}
	
	/* ----------------------- Persisting Activity State Methods ------------------------- */
	
	@Override
	public void onSaveInstanceState(Bundle saveInstanceState)
	{
		String currentFolderPath = folderSneaker.getCurrentFolderPath();
		saveInstanceState.putString(FOLDER_KEY, currentFolderPath);
		super.onSaveInstanceState(saveInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		if(savedInstanceState != null && savedInstanceState.containsKey(FOLDER_KEY))
		{
			String folder = savedInstanceState.getString(FOLDER_KEY);
			folderSneaker.setCurrentFolder(folder);
			populateItems();
			setActionBarTitle(folderSneaker.getCurrentFolderPath());
			Log.i(TAG, "on restore: " + folder);
		}
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	/* ------------------------------ Action Bar Methods --------------------------------- */
	
	private void setActionBar()
	{
		actionBar = getActionBar();
		actionBar.setTitle("/");
		actionBar.setHomeButtonEnabled(true);
		actionBar.show();
	}
	
	private void setActionBarTitle(String title)
	{
		actionBar.setTitle(title);
	}
	
	/* ------------------------------ Menu Methods --------------------------------- */
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home:
				folderSneaker.goToParentDirectory();
				populateItems();
				adapter.notifyDataSetChanged();
				setActionBarTitle(folderSneaker.getCurrentFolderPath());
				return true;
				default:
					return super.onOptionsItemSelected(item);
			
		}
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
			Toast.makeText(this, folderSneaker.getCurrentFolderPath() + " is empty.", Toast.LENGTH_SHORT).show();
			folderSneaker.goToParentDirectory();
		}
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
				setActionBarTitle(folderSneaker.getCurrentFolderPath());
			}
			
		}

	}
	

}
