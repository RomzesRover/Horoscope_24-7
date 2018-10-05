package com.BBsRs.horoscopeNewEdition.Loader;

import java.util.ArrayList;

import org.holoeverywhere.preference.SharedPreferences;

import android.content.Context;
import android.os.Handler;

import com.BBsRs.horoscopeNewEdition.Base.HoroscopeCollection;

public class Loader {
	
	int listType;
    // preferences 
    SharedPreferences sPref;
    Context context;
    boolean cancelLoad = false;
    Handler handler;
    int femaleIndex=-1, maleIndex=-1;
	
	public Loader(int _listType, SharedPreferences _sPref, Context _context, Handler _handler, int _femaleIndex, int _maleIndex){
		listType = _listType;
		sPref = _sPref;
		context = _context;
		handler = _handler;
		femaleIndex = _femaleIndex;
		maleIndex = _maleIndex;
	}
	
	public void abortLoad(){
		cancelLoad = true;
	}
	
	public ArrayList<HoroscopeCollection> loadCurrList(){
		return null;
	}

}
