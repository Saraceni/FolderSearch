package br.saraceni.foldersearch.util;

import java.io.File;
import java.util.ArrayList;

import android.util.Log;

public class FolderSneaker {
	
	private static File rootFolder = new File("/");
	private File currentFolder;
	private static String TAG = "FolderSneaker";
	
	public FolderSneaker()
	{
		try
		{
			currentFolder = rootFolder;
		}
		catch(Exception exc)
		{
			Log.e(TAG, "FolderSneaker(): " + exc.toString());
		}
		if(!rootFolder.exists())
		{
			Log.e(TAG, "Root Folder doesnt exists");
		}
	}
	
	public String getCurrentFolderPath()
	{
		return currentFolder.getAbsolutePath();
	}
	
	public boolean setCurrentFolder(String folderName)
	{
		boolean result = false;
		File[] files = getCurrentFolderElements();
		if(files == null || files.length < 1)
			return result;
		int index = 0;
		for(File file : files)
		{
			if(file.getName().equals(folderName))
			{
				result = true;
				break;
			}
			index++;
		}
		if(result)
		{
			File foundFile = files[index];
			Log.i(TAG, folderName + " found");
			if(foundFile.exists() && foundFile.isDirectory())
			{
				currentFolder = foundFile;
				result = true;
			}
			else
			{
				result = false;
			}
		}
		else
		{
			File file = new File(folderName);
			if(file.exists() && file.isDirectory())
			{
				Log.i(TAG, folderName + " is directory");
				currentFolder = file;
			}
		}
		return result;
	}
	
	public boolean setCurrentFolder(File file)
	{
		if(!file.exists() && !file.isDirectory())
		{
			currentFolder = file;
			return true;
		}
		else
			return false;
	}
	
	public File[] getCurrentFolderElements()
	{
		return currentFolder.listFiles();
	}
	
	public ArrayList<String> getCurrentFolderElementsString()
	{
		File[] files = getCurrentFolderElements();
		if(files.length < 1)
			Log.i(TAG, "getCurrentFolderElements size < 1");
		ArrayList<String> container = new ArrayList<String>();
		for(File file : files)
		{
			container.add(file.getName());
		}
		return container;
	}
	
	public void goToParentDirectory()
	{
		if(!currentFolder.equals(rootFolder))
			currentFolder = currentFolder.getParentFile();
	}
	
	public static ArrayList<File> getFile(String search)
	{
		ArrayList<File> result = new ArrayList<File>();
		searchFile(rootFolder, search, result);
		return result;	
	}
	
	public static void searchFile(File folder, String search, ArrayList<File> fileContainer)
	{
		File[] array = folder.listFiles();
		if(array == null)
			return;
		Log.i("FolderSneaker","Inside folder: " + folder.getAbsolutePath());
		for(File file : array)
		{
			//Log.i("FolderSneaker", "Current file evaluated: " + file.getName());
			if(file.isDirectory())
			{
				searchFile(file, search, fileContainer);
			}
			else if(file.getName().contains(search))
			{
				//Log.i("FolderSneaker", "File found " + file.getName());
				fileContainer.add(file);
			}
		}
	}

}
