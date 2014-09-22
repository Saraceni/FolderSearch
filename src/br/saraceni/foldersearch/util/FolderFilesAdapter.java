package br.saraceni.foldersearch.util;

import java.io.File;
import java.util.ArrayList;

import br.saraceni.foldersearch.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FolderFilesAdapter extends ArrayAdapter<File> {
	
	private ArrayList<File> items;
	
	public FolderFilesAdapter(Context context, int resource, ArrayList<File> items) {
		super(context, resource, items);
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.items_layout, null);
		}
		File i = items.get(position);
		if(i != null)
		{
			((TextView) convertView.findViewById(R.id.text)).setText(i.getName());
			if(i.isDirectory())
			{
				((ImageView) convertView.findViewById(R.id.icon)).setImageResource(R.drawable.folder);
				if(i.list() != null)
					((TextView) convertView.findViewById(R.id.size)).setText("Files: " + String.valueOf(i.list().length));
				else
					((TextView) convertView.findViewById(R.id.size)).setText("Files: 0");
			}
			else
			{
				((ImageView) convertView.findViewById(R.id.icon)).setImageResource(R.drawable.file);
				((TextView) convertView.findViewById(R.id.size)).setText("Size: " + i.length() + " bytes");
			}
		}
		
		return convertView;
	}

}
