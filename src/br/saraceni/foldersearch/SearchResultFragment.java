package br.saraceni.foldersearch;

import java.io.File;
import java.util.ArrayList;

import br.saraceni.foldersearch.util.FolderFilesAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SearchResultFragment extends Fragment {
	
	private ArrayList<File> resultItems;
	private FolderFilesAdapter resultAdapter;
	
	private static final String TAG = "SearchResultFragment";
	
	/* ------------------------- Fragment LifeCycle Methods ----------------------- */
	
	/* This methods are listed in the order they are called, according to the fragment
	 * lifecycle
	 * 
	 */
	
	public SearchResultFragment()
	{
		this.resultItems = new ArrayList<File>();
	}
	
	// Called when the fragment is attached to it's parent activity
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
	}
	
	//Called to do the initial creation of the fragment
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		resultAdapter = new FolderFilesAdapter(getActivity(), android.R.layout.simple_list_item_1, resultItems);
		setRetainInstance(true);
	}
	
	// Called once the Fragment has been created in order to create its user interface.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.i(TAG, "onCreateView");
		if(savedInstanceState != null)
		{
			//retain instance state
		}
		
		View v = inflater.inflate(R.layout.search_result_fragment_layout, container, false);
		ListView listView = (ListView) v.findViewById(R.id.result_listView);
		listView.setAdapter(resultAdapter);
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
		super.onSaveInstanceState(savedInstanceState);
	}
	
	/* ------------------------- Methods to Set Fragment Content ----------------------- */
	
	public void setResultContent(ArrayList<File> result)
	{
		if(resultItems == null)
			Log.i(TAG, "resultItems == null");
		else
			resultItems.addAll(result);
		if(resultAdapter == null)
			Log.i(TAG, "resultAdapter == null");
		else
			resultAdapter.notifyDataSetChanged();
	}

}











