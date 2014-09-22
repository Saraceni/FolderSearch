package br.saraceni.foldersearch;

import java.io.File;
import java.util.ArrayList;

import br.saraceni.foldersearch.util.FolderSearchService;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ResultActivity extends Activity {
	
	public static final String TAG = "ResultActivity";
	public static final String RESULT_ACTIVITY_ACTION = "br.saraceni.foldersearch.RESULT_ACTIVITY_ACTION";
	
	private SearchResultFragment searchResultFragment;
	
	/* -------------------------------- Lifecycle Methods ------------------------------- */
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result_activity);
		
		if(savedInstanceState == null)
		{
			Log.i(TAG, "savedInstanceState == null");
			searchResultFragment = new SearchResultFragment();
			Intent intent = getIntent();
			if(intent != null)
			{
				ArrayList<File> result = (ArrayList<File>) intent.getExtras().get(FolderSearchService.SEARCH_RESULT_KEY);
				if(result != null)
				{
					FragmentTransaction ft = getFragmentManager().beginTransaction().add(R.id.search_result_container, searchResultFragment);
					ft.commit();
					searchResultFragment.setResultContent(result);
				}
			}
		}
	}
	
	@Override
	public void onDestroy()
	{
		Log.i(TAG, "onDestroy");
		super.onDestroy();
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
		super.onRestoreInstanceState(savedInstanceState);
		searchResultFragment = (SearchResultFragment) getFragmentManager().findFragmentById(R.id.search_result_container);
	}

}
	
	
	
	
	
	
	
	
	
	
	
	
	
