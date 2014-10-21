package com.acel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class Scores{
	
	private List<Score> scores = new ArrayList<Score>();
	boolean mExternalStorageAvailable = false;
	boolean mExternalStorageWriteable = false;
	String state = Environment.getExternalStorageState();
	private Context context;

	public Scores(Context context) {
		mediaCheck();
		this.context = context;
		loadData();
	}

	public boolean isHighScore(int score) {
		if (scores.size() > 10) {
			for (int i = 0; (i < scores.size() && i < 10); i++) {
				if (score > scores.get(i).getScore())
					return true;
			}
		} else {
			return true;
		}
		
		return false;
	}

	public void addToList(String name, int score){
		scores.add(new Score(name,score));
		saveData();
	}
	
	public List<Score> getScoreList(){
		Collections.sort(scores, new ScoreComparator());
		return scores;
	}
	
	public boolean loadData(){
		if (mExternalStorageWriteable) {
			File file = new File(context.getExternalFilesDir(null), "scores.bin");

			try {
				FileInputStream inStream = new FileInputStream(file);
				ObjectInputStream objectInStream = new ObjectInputStream(inStream);
				int count = objectInStream.readInt(); // Get the number of regions
				for (int c=0; c < count; c++)
				    scores.add((Score) objectInStream.readObject());
				objectInStream.close();
				return true;
			} catch (IOException e) {
				Log.w("ExternalStorage", "Error reading " + file, e);
				return false;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.w("ExternalStorage", "Error reading " + file, e);
				return false;
			}
		}
		return false;
	}
	
	public boolean saveData() {
		System.out.println("got here");
		if (mExternalStorageWriteable) {
			System.out.println("storage writable");
			
			File file = new File(context.getExternalFilesDir(null),"scores.bin");

			try {
				FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeInt(scores.size());
				for(Score s:scores)
					oos.writeObject(s);
				oos.close();
				return true;
			} catch (IOException e) {
				Log.w("ExternalStorage", "Error writing " + file, e);
				return false;
			}
		}
		return false;
	}

	public void mediaCheck() {
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
	}
	
	class ScoreComparator implements Comparator<Score>{
		public int compare(Score lhs, Score rhs) {
	        int score1 = lhs.getScore();        
	        int score2 = rhs.getScore();
	       
	        if(score1 > score2)
	            return -1;
	        else if(score1 < score2)
	            return 1;
	        else
	            return 0;   
		}
	}
	
}
