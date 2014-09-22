package br.saraceni.foldersearch;

import br.saraceni.foldersearch.util.FolderSearchService;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class SearchFragment extends Fragment {
	
	private Activity parent;
	private SearchFragmentInterface searchFragmentInterface;
	private EditText editText;
	private ImageButton button;
	
	private static final String TAG = "SearchFragment";
	
	
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
		}
		
		//Called to do the initial creation of the fragment
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
		}
		
		// Called once the Fragment has been created in order to create its user interface.
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View v = inflater.inflate(R.layout.search_menu, container, false);	
			button = (ImageButton) v.findViewById(R.id.search_bt);
			editText = (EditText) v.findViewById(R.id.edit_text_search);
			setUIElements();
			return v;
		}
		
		@Override
		public void onDestroy()
		{
			Log.i(TAG, "onDestroy");
			super.onDestroy();
		}
		
		/* -------------------------- Persisting State Methods ----------------------------- */
		
		@Override
	    public void onSaveInstanceState(Bundle outState)
		{
			Log.i(TAG, "onSaveInstanceState");
			super.onSaveInstanceState(outState);
		}
		
		/* -------------------------- UI Interaction Methods ----------------------------- */
		
		private void setUIElements()
		{
			button.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					String s_file = editText.getText().toString();
					Log.i("SearchFragment", "EditText string = " + s_file);
					/*if(searchFragmentInterface != null)
						searchFragmentInterface.onSearchClick(s_file);*/
					Intent searchIntent = new Intent(FolderSearchService.SERVICE_ACTION);
					searchIntent.putExtra(FolderSearchService.FOLDER_NAME_EXTRA, s_file);
					parent.startService(searchIntent);
				}
				
			});
		}
		
		/* --------------- Interface for Communication With Parent Activity -------------- */
		
		public void setSearchFragmentInterface(SearchFragmentInterface interf)
		{
			this.searchFragmentInterface = interf;
		}
		
		public interface SearchFragmentInterface
		{
			public void onSearchClick(String str);
		}

}
