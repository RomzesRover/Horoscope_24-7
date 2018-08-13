package com.BBsRs.horoscopeNewEdition.Loader;

import java.util.ArrayList;

import org.holoeverywhere.preference.SharedPreferences;

import android.content.Context;

import com.BBsRs.horoscopeNewEdition.Base.HoroscopeCollection;

public class Loader {
	
	int listType;
    // preferences 
    SharedPreferences sPref;
    Context context;
    boolean cancelLoad = false;
	
	public Loader(int _listType, SharedPreferences _sPref, Context _context){
		listType = _listType;
		sPref = _sPref;
		context = _context;
	}
	
	public void abortLoad(){
		cancelLoad = true;
	}
	
	public ArrayList<HoroscopeCollection> loadCurrList(){
		return null;
	}

}
