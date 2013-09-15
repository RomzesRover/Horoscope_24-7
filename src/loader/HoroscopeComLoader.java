package loader;

import java.io.IOException;

import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.ProgressBar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.BBsRs.horoscopenew.R;

public class HoroscopeComLoader {
	
	SharedPreferences sPref; 

	private WebView[] webViews;
	private RelativeLayout[] errorLayout, allNormalLayout;
	private ProgressBar[] progressBar;
	private Context context;
	private final Handler handler = new Handler();
	
	int zodiacNumber;
	
	String[] resultS = new String[7];			//��� ����� html
	String[] horoscopes = new String[7];		//��� ������ ����� ��� �������...
	
	boolean [] loaded = new boolean[7];
	
	String tag = "HoroscopeComLoader";
	
	int delayForThread;
	
	public HoroscopeComLoader (Context context, WebView[] webViews, RelativeLayout[] errorLayout, RelativeLayout[] allNormalLayout, ProgressBar[] progressBar ){
		this.context=context;
		this.webViews=webViews;
		this.errorLayout=errorLayout;
		this.allNormalLayout=allNormalLayout;
		this.progressBar=progressBar;
		sPref = this.context.getSharedPreferences("T", 1);
		zodiacNumber=sPref.getInt("zodiacNumber", 0);		//����� �������
		delayForThread=this.context.getResources().getInteger(R.integer.DelayForThreads);
	}
	
	public String[] getHoroscopesForShare(){
		return horoscopes;
	}
	
	public void setResult(final int id){
		final Runnable updaterText = new Runnable() {
	        public void run() {
	        	webViews[id].loadDataWithBaseURL(null,resultS[id]
	                    ,"text/html", "utf-8",null); // ��������� �������� ��������
	        	progressBar[id].setVisibility(View.GONE);
	        	webViews[id].setVisibility(View.VISIBLE);
	        	allNormalLayout[id].setVisibility(View.VISIBLE);
	        	errorLayout[id].setVisibility(View.GONE);
	        }
	    };
	    handler.post(updaterText);
	}
	
	public void update(final int id){
		Log.i(tag, "Update Pressed + id: "+String.valueOf(id));
		final Runnable updaterText = new Runnable() {
	        public void run() {
	        	for (int i=0; i<resultS.length; i++){
	        		loaded[i]=false;
			    	resultS[i]=null;
					progressBar[i].setVisibility(View.VISIBLE);
		        	webViews[i].setVisibility(View.GONE);
		        	allNormalLayout[i].setVisibility(View.VISIBLE);
		        	errorLayout[i].setVisibility(View.GONE);
	        	}
	        	load(id);
	        }
	    };
	    handler.post(updaterText);
	}
	
	public void Error(final int id){
		final Runnable updaterText = new Runnable() {
	        public void run() {
		progressBar[id].setVisibility(View.GONE);
    	webViews[id].setVisibility(View.GONE);
    	allNormalLayout[id].setVisibility(View.GONE);
    	errorLayout[id].setVisibility(View.VISIBLE);
    	
    	Button retry = (Button)errorLayout[id].findViewById(R.id.retry);
    	retry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loaded[id]=false;
		    	resultS[id]=null;
				progressBar[id].setVisibility(View.VISIBLE);
	        	webViews[id].setVisibility(View.GONE);
	        	allNormalLayout[id].setVisibility(View.VISIBLE);
	        	errorLayout[id].setVisibility(View.GONE);
				load(id);
			}
		});
	        }
	    };
	    handler.post(updaterText);
	}
	
	public void load(int id){
		if (resultS[id]==null && !loaded[id]){
			Log.i(tag, "Load id: "+String.valueOf(id)+" Confirmed");
			loaded[id]=true;
			switch (id){
			case 1:
					LoadToday();
				break;
			case 2:
					LoadTomorrow();
				break;
			case 0:
					LoadYesterday();
				break;
			case 3:
					LoadPersonal();
				break;
			case 4:
					LoadWeek();
				break;
			case 5:
					LoadMoney();
				break;
			case 6:
					LoadMonth();
				break;
			}
		} else {
			Log.i(tag, "Load id: "+String.valueOf(id)+" Declined");
		}
		
	}
	
	public void LoadToday(){
		Thread thr=new Thread(new Runnable() {				//������ � ����� ������
	        public void run() {
	        	try {							//�������� �������
					Thread.sleep(delayForThread);
					Document doc = Jsoup.connect("http://my.horoscope.com/astrology/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadHoroscopeCom)[1]+"-horoscope-"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+".html").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
					
					resultS[1]="<span style=\"color: rgb(255, 69, 0);\"><span style=\"font-size: 18pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 16pt;\">"+
							doc.getElementsByClass("col420").get(0).child(2).text().replaceAll("Share with friends:","")				//date
							+"</span></span></span></span></span></span></span></span><hr><p><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\">"+
							doc.getElementsByClass("fontdef1").get(1).text()		//horo
							+"</span></span></span></p>";
					horoscopes[1]=context.getResources().getStringArray(R.array.horoscope_com_horoscopes)[1]+" : #"+context.getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+"\n\n"+doc.getElementsByClass("col420").get(0).child(2).text().replaceAll("Share with friends:","")+"\n"+doc.getElementsByClass("fontdef1").get(1).text();
					setResult(1);
	        	} catch (NotFoundException e) {
					Log.e(tag, "data Error"); Error(1);
					e.printStackTrace();
				} catch (IOException e) {
					Log.e(tag, "Load Error"); Error(1); 
					e.printStackTrace();
				} catch (InterruptedException e) {
					Log.e(tag, "Interrupted Load Error"); Error(1);
					e.printStackTrace();
				} catch (NullPointerException e) {
	        		Log.e(tag, "null Load Error"); Error(1);
					e.printStackTrace();
				} catch (Exception e) {
	        		Log.e(tag, "other Load Error"); Error(1);
					e.printStackTrace();
				}}
        });
	thr.start();
	}
	
	public void LoadTomorrow(){
		Thread thr=new Thread(new Runnable() {				//������ � ����� ������
	        public void run() {
	        	try {							//�������� �������
					Thread.sleep(delayForThread); 
					Document doc = Jsoup.connect("http://my.horoscope.com/astrology/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadHoroscopeCom)[2]+"-horoscope-"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+".html").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
					
					resultS[2]="<span style=\"color: rgb(255, 69, 0);\"><span style=\"font-size: 18pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 16pt;\">"+
							doc.getElementsByClass("col420").get(0).child(2).text().replaceAll("Share with friends:","")				//date
							+"</span></span></span></span></span></span></span></span><hr><p><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\">"+
							doc.getElementsByClass("fontdef1").get(1).text()		//horo
							+"</span></span></span></p>";
					horoscopes[2]=context.getResources().getStringArray(R.array.horoscope_com_horoscopes)[2]+" : #"+context.getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+"\n\n"+doc.getElementsByClass("col420").get(0).child(2).text().replaceAll("Share with friends:","")+"\n"+doc.getElementsByClass("fontdef1").get(1).text();
					setResult(2);
	        	} catch (NotFoundException e) {
					Log.e(tag, "data Error"); Error(2);
					e.printStackTrace();
				} catch (IOException e) {
					Log.e(tag, "Load Error"); Error(2); 
					e.printStackTrace();
				} catch (InterruptedException e) {
					Log.e(tag, "Interrupted Load Error"); Error(2);
					e.printStackTrace();
				} catch (NullPointerException e) {
	        		Log.e(tag, "null Load Error"); Error(2);
					e.printStackTrace();
				} catch (Exception e) {
	        		Log.e(tag, "other Load Error"); Error(2);
					e.printStackTrace();
				}}
        });
	thr.start();
	}
	
	public void LoadYesterday(){
		Thread thr=new Thread(new Runnable() {				//������ � ����� ������
	        public void run() {
	        	try {							//�������� �������
					Thread.sleep(delayForThread); 
					Document doc = Jsoup.connect("http://my.horoscope.com/astrology/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadHoroscopeCom)[0]+"-horoscope-"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+".html").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
					
					resultS[0]="<span style=\"color: rgb(255, 69, 0);\"><span style=\"font-size: 18pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 16pt;\">"+
							doc.getElementsByClass("col420").get(0).child(2).text().replaceAll("Share with friends:","")				//date
							+"</span></span></span></span></span></span></span></span><hr><p><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\">"+
							doc.getElementsByClass("fontdef1").get(1).text()		//horo
							+"</span></span></span></p>";
					horoscopes[0]=context.getResources().getStringArray(R.array.horoscope_com_horoscopes)[0]+" : #"+context.getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+"\n\n"+doc.getElementsByClass("col420").get(0).child(2).text().replaceAll("Share with friends:","")+"\n"+doc.getElementsByClass("fontdef1").get(1).text();
					setResult(0);
	        	} catch (NotFoundException e) {
					Log.e(tag, "data Error"); Error(0);
					e.printStackTrace();
				} catch (IOException e) {
					Log.e(tag, "Load Error"); Error(0); 
					e.printStackTrace();
				} catch (InterruptedException e) {
					Log.e(tag, "Interrupted Load Error"); Error(0);
					e.printStackTrace();
				} catch (NullPointerException e) {
	        		Log.e(tag, "null Load Error"); Error(0);
					e.printStackTrace();
				} catch (Exception e) {
	        		Log.e(tag, "other Load Error"); Error(0);
					e.printStackTrace();
				}}
        });
	thr.start();
	}
	
	public void LoadPersonal(){
		Thread thr=new Thread(new Runnable() {				//������ � ����� ������
	        public void run() {
	        	try {							//�������� �������
					Thread.sleep(delayForThread); 
					Document doc = Jsoup.connect("http://horoscope.com/horoscope/numerology/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadHoroscopeCom)[3]+".aspx?nDate="+String.valueOf(sPref.getInt("dayBorn", 10))+"&nMonth="+String.valueOf(sPref.getInt("monthBorn", 4)+1)+"&nYear="+String.valueOf(sPref.getInt("yearBorn", 1995))).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
					
					resultS[3]="<span style=\"color: rgb(255, 69, 0);\"><span style=\"font-size: 18pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 16pt;\">"+
							doc.getElementsByClass("col420").get(0).child(2).text().replaceAll("Share with friends:","")				//date
							+"</span></span></span></span></span></span></span></span><hr><p><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\">"+
							doc.getElementsByClass("fontdef1").get(0).text()		//horo
							+"</span></span></span></p>";
					horoscopes[3]=context.getResources().getStringArray(R.array.horoscope_com_horoscopes)[3]+" : #"+sPref.getString("name", context.getResources().getString(R.string.noname))+"\n\n"+doc.getElementsByClass("col420").get(0).child(2).text().replaceAll("Share with friends:","")+"\n"+doc.getElementsByClass("fontdef1").get(0).text();
					setResult(3);
	        	} catch (NotFoundException e) {
					Log.e(tag, "data Error"); Error(3);
					e.printStackTrace();
				} catch (IOException e) {
					Log.e(tag, "Load Error"); Error(3); 
					e.printStackTrace();
				} catch (InterruptedException e) {
					Log.e(tag, "Interrupted Load Error"); Error(3);
					e.printStackTrace();
				} catch (NullPointerException e) {
	        		Log.e(tag, "null Load Error"); Error(3);
					e.printStackTrace();
				} catch (Exception e) {
	        		Log.e(tag, "other Load Error"); Error(3);
					e.printStackTrace();
				}}
        });
	thr.start();
	}
	
	public void LoadWeek(){
		Thread thr=new Thread(new Runnable() {				//������ � ����� ������
	        public void run() {
	        	try {							//�������� �������
					Thread.sleep(delayForThread); 
					Document doc = Jsoup.connect("http://my.horoscope.com/astrology/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadHoroscopeCom)[4]+"-horoscope-"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+".html").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
					
					resultS[4]="<span style=\"color: rgb(255, 69, 0);\"><span style=\"font-size: 18pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 16pt;\">"+
							doc.getElementsByClass("col420").get(0).child(2).text().replaceAll("Share with friends:","")				//date
							+"</span></span></span></span></span></span></span></span><hr><p><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\">"+
							doc.getElementsByClass("fontdef1").get(0).text()		//horo
							+"</span></span></span></p>";
					horoscopes[4]=context.getResources().getStringArray(R.array.horoscope_com_horoscopes)[4]+" : #"+context.getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+"\n\n"+doc.getElementsByClass("col420").get(0).child(2).text().replaceAll("Share with friends:","")+"\n"+doc.getElementsByClass("fontdef1").get(0).text();
					setResult(4);
	        	} catch (NotFoundException e) {
					Log.e(tag, "data Error"); Error(4);
					e.printStackTrace();
				} catch (IOException e) {
					Log.e(tag, "Load Error"); Error(4); 
					e.printStackTrace();
				} catch (InterruptedException e) {
					Log.e(tag, "Interrupted Load Error"); Error(4);
					e.printStackTrace();
				} catch (NullPointerException e) {
	        		Log.e(tag, "null Load Error"); Error(4);
					e.printStackTrace();
				} catch (Exception e) {
	        		Log.e(tag, "other Load Error"); Error(4);
					e.printStackTrace();
				}}
        });
	thr.start();
	}
	
	public void LoadMoney(){
		Thread thr=new Thread(new Runnable() {				//������ � ����� ������
	        public void run() {
	        	try {							//�������� �������
					Thread.sleep(delayForThread); 
					Document doc = Jsoup.connect("http://astrology.horoscope.com/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadHoroscopeCom)[5]+"-horoscope-"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+".html").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();

					resultS[5]="<span style=\"color: rgb(255, 69, 0);\"><span style=\"font-size: 18pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 16pt;\">"+
							doc.getElementsByClass("col420").get(0).child(2).text().replaceAll("Share with friends:","")				//date
							+"</span></span></span></span></span></span></span></span><hr><p><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\">"+
							doc.getElementsByClass("fontdef1").get(0).text()		//horo
							+"</span></span></span></p>";
					horoscopes[5]=context.getResources().getStringArray(R.array.horoscope_com_horoscopes)[5]+" : #"+context.getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+"\n\n"+doc.getElementsByClass("col420").get(0).child(2).text().replaceAll("Share with friends:","")+"\n"+doc.getElementsByClass("fontdef1").get(0).text();
					setResult(5);
	        	} catch (NotFoundException e) {
					Log.e(tag, "data Error"); Error(5);
					e.printStackTrace();
				} catch (IOException e) {
					Log.e(tag, "Load Error"); Error(5); 
					e.printStackTrace();
				} catch (InterruptedException e) {
					Log.e(tag, "Interrupted Load Error"); Error(5);
					e.printStackTrace();
				} catch (NullPointerException e) {
	        		Log.e(tag, "null Load Error"); Error(5);
					e.printStackTrace();
				} catch (Exception e) {
	        		Log.e(tag, "other Load Error"); Error(5);
					e.printStackTrace();
				}}
        });
	thr.start();
	}
	
	public void LoadMonth(){
		Thread thr=new Thread(new Runnable() {				//������ � ����� ������
	        public void run() {
	        	try {							//�������� �������
					Thread.sleep(delayForThread); 
					Document doc = Jsoup.connect("http://my.horoscope.com/astrology/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadHoroscopeCom)[6]+"-horoscope-"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+".html").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
					
					resultS[6]="<span style=\"color: rgb(255, 69, 0);\"><span style=\"font-size: 18pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 16pt;\">"+
							doc.getElementsByClass("col420").get(0).child(0).child(8).text().replaceAll("Share with friends:","")	+			//date
							"</span></span></span></span></span></span></span></span><hr><p><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\"><span style=\"font-size: 14pt;\">"+
							doc.getElementsByClass("fontdef1").get(0).text()		//horo
							+"</span></span></span></p>";
					horoscopes[6]=context.getResources().getStringArray(R.array.horoscope_com_horoscopes)[6]+" : #"+context.getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+"\n\n"+doc.getElementsByClass("col420").get(0).child(0).child(8).text().replaceAll("Share with friends:","")+"\n"+doc.getElementsByClass("fontdef1").get(0).text();
					setResult(6);
	        	} catch (NotFoundException e) {
					Log.e(tag, "data Error"); Error(6);
					e.printStackTrace();
				} catch (IOException e) {
					Log.e(tag, "Load Error"); Error(6); 
					e.printStackTrace();
				} catch (InterruptedException e) {
					Log.e(tag, "Interrupted Load Error"); Error(6);
					e.printStackTrace();
				} catch (NullPointerException e) {
	        		Log.e(tag, "null Load Error"); Error(6);
					e.printStackTrace();
				} catch (Exception e) {
	        		Log.e(tag, "other Load Error"); Error(6);
					e.printStackTrace();
				}}
        });
	thr.start();
	}
}
