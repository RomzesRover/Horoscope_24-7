package com.BBsRs.horoscopeNewEdition.Loader;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;

import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Base.HoroscopeCollection;

public class Loader {
	
	int listType;
    // preferences 
    SharedPreferences sPref;
    Context context;
    boolean cancelLoad = false;
    Handler handler;
    int femaleIndex=-1, maleIndex=-1;
    Proxy proxy = null;
	
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
	
	public void loadAndSetupProxyServer(String proxyTarget){
		String ip = sPref.getString(Constants.PREFERENCES_PROXY_SERVER_SAVED_IP, ""), port = sPref.getString(Constants.PREFERENCES_PROXY_SERVER_SAVED_PORT, "");
		Document doci;
		//check exist proxy to target
		if (ip.length()>0 && port.length()>0){
			try{
				proxy = new Proxy(
		                Proxy.Type.HTTP,
		                InetSocketAddress.createUnresolved(ip, Integer.valueOf(port))
		              );
				doci = Jsoup.connect(proxyTarget)
						.userAgent(context.getResources().getString(R.string.user_agent))
						.timeout(context.getResources().getInteger(R.integer.user_timeout))
						.proxy(proxy)
						.get();
				Log.e("IAM HERE", doci.title());
				return;
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		if (cancelLoad) return;
		//determine proxy server ip and port
		try {
			handler.post(new Runnable(){
				@Override
				public void run() {
					Toast.makeText(context, context.getString(R.string.proxy_setting_up),  Toast.LENGTH_LONG).show();
				}
			});
			
			doci = Jsoup.connect("https://free-proxy-list.net/uk-proxy.html")
			.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();  if (cancelLoad) return;
			Elements col = doci.select("table").get(0).select("tr");
			col.remove(0);
			col.remove(col.size()-1);
			for (Element one : col){
				if (cancelLoad) return;
				
				ip = one.select("td").get(0).text();
				port = one.select("td").get(1).text();
				Log.e("IAM HERE", ip+":"+port);
				//check each proxy to find one
				try{
					proxy = new Proxy(
			                Proxy.Type.HTTP,
			                InetSocketAddress.createUnresolved(ip, Integer.valueOf(port))
			              );
					doci = Jsoup.connect(proxyTarget)
							.userAgent(context.getResources().getString(R.string.user_agent))
							.timeout(context.getResources().getInteger(R.integer.user_timeout))
							.proxy(proxy)
							.get();
					sPref.edit().putString(Constants.PREFERENCES_PROXY_SERVER_SAVED_IP, ip).commit();
					sPref.edit().putString(Constants.PREFERENCES_PROXY_SERVER_SAVED_PORT, port).commit();
					final String ipF = ip, portF = port;
					handler.post(new Runnable(){
						@Override
						public void run() {
							Toast.makeText(context, String.format(context.getString(R.string.proxy_settet_up), ipF, portF),  Toast.LENGTH_LONG).show();
						}
					});
					break;
				} catch (Exception e){
					e.printStackTrace();
				}
				if (cancelLoad) return;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
