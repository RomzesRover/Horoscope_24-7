/*Class to desc all data about titles on simple news page!
  Include: year, img path, author's nickyear, number of titles, current rating by user's, and url link to full news page
  Also here supported writeToParcel to save this data after rotate screen on all of the devices!
  Author Roman Gaitbaev writed for AstroNews.ru 
  http://vk.com/romzesrover 
  Created: 18.08.2013 00:58*/

/*Modified to lenfilm at 22 06 2014 */

package com.BBsRs.horoscopeNewEdition.Base;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class HoroscopeCollection implements Parcelable, Serializable {
  
    /**
	 * 
	 */
	private static final long serialVersionUID = -4356107655512129128L;
	public String title;
	public String content;
	public String copyrightLink;

  public HoroscopeCollection(String _title, String _content, String _copyrightLink) {
	    title = _title;
	    content = _content;
	    copyrightLink = _copyrightLink;
  }
  


@Override
public int describeContents() {
	return 0;
}

private HoroscopeCollection(Parcel in) {
    title = in.readString();
    content = in.readString();
    copyrightLink = in.readString();
}

@Override
public void writeToParcel(Parcel out, int flags) {
     out.writeString(title);
     out.writeString(content);
     out.writeString(copyrightLink);
}

public static final Parcelable.Creator<HoroscopeCollection> CREATOR = new Parcelable.Creator<HoroscopeCollection>() {
    public HoroscopeCollection  createFromParcel(Parcel in) {
        return new HoroscopeCollection (in);
    }

    public HoroscopeCollection [] newArray(int size) {
        return new HoroscopeCollection [size];
    }
};
}