package br.saraceni.foldersearch;


import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	private FragmentManager fragmentManager;
	private SearchFragment searchFragment;
	private ListFragment listFragment;
	private SearchResultFragment searchResultFragment;
	private ActionBar actionBar;
	
	private static final String LIST_FRAGMENT_TAG = "LIST_FRAGMENT_TAG";
	private static final String SEARCH_FRAGMENT_TAG = "SEARCH_FRAGMENT_TAG";
	private static final String SEARCH_RESULT_FRAGMENT_TAG = "SEARCH_RESULT_FRAGMENT_TAG";
	private static final String TAG = "MainActivity";
	
	
	/* -------------------------------- Lifecycle Methods ------------------------------- */
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.i(TAG, "Activity onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		setActionBar();
		
		fragmentManager = getFragmentManager();
		
		if(savedInstanceState == null)
		{
			Log.i(TAG, "savedInstanceState == null");
			listFragment = new ListFragment();
			fragmentManager.beginTransaction().add(R.id.container, listFragment, LIST_FRAGMENT_TAG).commit();
		}
	}
	
	
	/* --------------------- Menu Creation and Handling Methods -------------------------- */
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch(item.getItemId())
		{
			case android.R.id.home:
				searchFragment = new SearchFragment();
				FragmentTransaction ft = fragmentManager.beginTransaction().add(R.id.search_menu_container, searchFragment, SEARCH_FRAGMENT_TAG);
				ft.addToBackStack(null);
				ft.commit();
				return true;
			case R.id.folder_up:
				listFragment.goToParentDirectory();
				return true;
				default:
					return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onPrepareOptionsMenu (Menu menu)
	{
		if(listFragment == null)
			Log.i(TAG, "listFragment == null");
		if(listFragment.isAdded() && menu.findItem(R.id.folder_up) == null)
		{
			menu.add(R.id.folder_up);
		}
		else if(searchResultFragment != null && searchResultFragment.isAdded() && menu.findItem(R.id.folder_up) != null)
		{
			menu.removeItem(R.id.folder_up);
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	/* --------------------- Action Bar Methods -------------------------- */
	
	private void setActionBar()
	{
		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.show();
	}
	
	/* ------------------------ Persisting Activity State ------------------------ */
	
	@Override
	public void onSaveInstanceState(Bundle saveInstanceState)
	{
		Log.i(TAG, "onSaveInstanceState");
		super.onSaveInstanceState(saveInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		Log.i(TAG, "onRestoreInstanceState");
		//listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.container);
		listFragment = (ListFragment) fragmentManager.findFragmentByTag(LIST_FRAGMENT_TAG);
		searchResultFragment = (SearchResultFragment) fragmentManager.findFragmentByTag(SEARCH_RESULT_FRAGMENT_TAG);
		searchFragment = (SearchFragment) fragmentManager.findFragmentById(R.id.search_menu_container);
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	@Override
	public void onStop()
	{
		Log.i(TAG, "onStop");
		super.onStop();
	}
	
	@Override
	public void onDestroy()
	{
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}

}
