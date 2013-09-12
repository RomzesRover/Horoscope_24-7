package loader;

import java.io.IOException;
import java.util.Calendar;

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

import com.BBsRs.horoscopefree.R;

public class HoroMailRuLoader {
	
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
	
	String tag = "HoroMailRuLoader";
	
	int delayForThread;
	
	String[] months = {"������", "�������","�����", "������", "���", "����", "����", "�������", "��������", "�������", "������", "�������"};
	
	public HoroMailRuLoader (Context context, WebView[] webViews, RelativeLayout[] errorLayout, RelativeLayout[] allNormalLayout, ProgressBar[] progressBar ){
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
					LoadMonth();
				break;
			case 6:
					LoadYear();
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
					Document doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+"/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadMailRu)[1]+"/").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
					
					resultS[1]="<div style=\"float:left; width:70px; height:65px; margin:2px;> <span style=\" font-size:=\"\" 36pt\"=\"\" align=\"center\"><span style=\"font-size: 36pt\"><span style=\"color: rgb(255, 69, 0)\">"+
					doc.getElementById("tm_today").child(0).child(0).text()+		//����
					"</span></span> <span style=\"color: rgb(105, 105, 105)\"><span style=\"font-size: 10pt\">"+
					doc.getElementById("tm_today").child(0).text().replaceAll("[0-9]*", "").toUpperCase()+														//�����
					"</span></span></div><div>"+
					ifF(doc.getElementById("tm_today").child(1).text()+																														//����
							"</div><div><br></div><div>")																				//�����
					+
					doc.getElementById("tm_today").child(2).text()
					+					//����
					"</div><div><br></div><div>"+
					doc.getElementById("tm_today").child(3).text()+"</div>";		//����2
					
					horoscopes[1]=context.getResources().getStringArray(R.array.mail_ru_horoscopes)[1]+" : #"+context.getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+"\n\n"+doc.getElementById("tm_today").child(0).text()+"\n\n"+ifFf(doc.getElementById("tm_today").child(1).text()+"\n\n")+doc.getElementById("tm_today").child(2).text()+"\n\n"+doc.getElementById("tm_today").child(3).text();
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
					Document doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+"/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadMailRu)[2]+"/").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
					resultS[2]= "<div style=\"float:left; width:70px; height:65px; margin:2px;> <span style=\" font-size:=\"\" 36pt\"=\"\" align=\"center\"><span style=\"font-size: 36pt\"><span style=\"color: rgb(255, 69, 0)\">"+
							doc.getElementById("tm_tomorrow").child(0).child(0).text()+		//����
							"</span></span> <span style=\"color: rgb(105, 105, 105)\"><span style=\"font-size: 10pt\">"+
							doc.getElementById("tm_tomorrow").child(0).text().replaceAll("[0-9]*", "").toUpperCase()+														//�����
							"</span></span></div><div>"+
							ifF(doc.getElementById("tm_tomorrow").child(1).text()+																														//����
									"</div><div><br></div><div>")																				//�����
							+
							doc.getElementById("tm_tomorrow").child(2).text()+					//����
							"</div><div><br></div><div>"+
							doc.getElementById("tm_tomorrow").child(3).text()+"</div>";
					
					horoscopes[2]=context.getResources().getStringArray(R.array.mail_ru_horoscopes)[2]+" : #"+context.getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+"\n\n"+doc.getElementById("tm_tomorrow").child(0).text()+"\n\n"+ifFf(doc.getElementById("tm_tomorrow").child(1).text()+"\n\n")+doc.getElementById("tm_tomorrow").child(2).text()+"\n\n"+doc.getElementById("tm_tomorrow").child(3).text();
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
					Document doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+"/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadMailRu)[0]+"/").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
					resultS[0]= "<div style=\"float:left; width:70px; height:65px; margin:2px;> <span style=\" font-size:=\"\" 36pt\"=\"\" align=\"center\"><span style=\"font-size: 36pt\"><span style=\"color: rgb(255, 69, 0)\">"+
							doc.getElementById("tm_yesterday").child(0).child(0).text()+		//����
							"</span></span> <span style=\"color: rgb(105, 105, 105)\"><span style=\"font-size: 10pt\">"+
							doc.getElementById("tm_yesterday").child(0).text().replaceAll("[0-9]*", "").toUpperCase()+														//�����
							"</span></span></div><div>"+
							ifF(doc.getElementById("tm_yesterday").child(1).text()+																														//����
									"</div><div><br></div><div>")																				//�����
							+
							doc.getElementById("tm_yesterday").child(2).text()+					//����
							"</div><div><br></div><div>"+
							doc.getElementById("tm_yesterday").child(3).text()+"</div>"; 

					horoscopes[0]=context.getResources().getStringArray(R.array.mail_ru_horoscopes)[0]+" : #"+context.getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+"\n\n"+doc.getElementById("tm_yesterday").child(0).text()+"\n\n"+ifFf(doc.getElementById("tm_yesterday").child(1).text()+"\n\n")+doc.getElementById("tm_yesterday").child(2).text()+"\n\n"+doc.getElementById("tm_yesterday").child(3).text();				
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
					Calendar c = Calendar.getInstance(); 
					Document doc = Jsoup.connect("http://horo.mail.ru/numerology_calc.html?method=31&day1="+String.valueOf(sPref.getInt("dayBorn", 10))+"&month1="+String.valueOf(sPref.getInt("monthBorn", 4)+1)+"&year1="+String.valueOf(sPref.getInt("yearBorn", 1995))+"&day2="+String.valueOf(c.get(Calendar.DAY_OF_MONTH))+"&month2="+String.valueOf(c.get(Calendar.MONTH)+1)+"&year2="+String.valueOf(c.get(Calendar.YEAR))).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
					
					resultS[3]="<div style=\"float:left; width:70px; height:65px; margin:2px;> <span style=\" font-size:=\"\" 36pt\"=\"\" align=\"center\"><span style=\"font-size: 36pt\"><span style=\"color: rgb(255, 69, 0)\">"+
					String.valueOf(c.get(Calendar.DAY_OF_MONTH))+		//����
					"</span></span> <span style=\"color: rgb(105, 105, 105)\"><span style=\"font-size: 10pt\">"+
					months[c.get(Calendar.MONTH)].replaceAll("[0-9]*", "").toUpperCase()+														//�����
					"</span></span></div><div>"+																		//�����
					"��� ���� ��������: "+doc.getElementsByClass("content").first().child(1).child(0).text()+
					doc.getElementsByClass("content").first().child(3).html()+
					"</div>";		//����2
					
					 horoscopes[3]=context.getResources().getStringArray(R.array.mail_ru_horoscopes)[3]+" : #"+sPref.getString("name", context.getResources().getString(R.string.noname))+"\n\n"+"�������� ��: "+doc.getElementsByClass("content").first().child(1).child(2).text()+"\n��� ���� ��������: "+doc.getElementsByClass("content").first().child(1).child(0).text()+"\n"+doc.getElementsByClass("content").first().child(3).text();
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
					Document doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+"/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadMailRu)[3]+"/").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
					resultS[4]= "<div style=\"float:left; width:110px; height:65px; margin:2px;> <span style=\" font-size:=\"\" 36pt\"=\"\" align=\"center\"><span style=\"font-size: 36pt\"><span style=\"color: rgb(255, 69, 0)\">"+
							doc.getElementById("tm_week").child(0).child(0).text()+		//����
							"</span></span> <span style=\"color: rgb(105, 105, 105)\"><span style=\"font-size: 10pt\">"+
							doc.getElementById("tm_week").child(0).text().replaceAll("[0-9]*", "").toUpperCase().replaceAll("��", "")+	
							"</span></span></div><div>"+
							ifF(doc.getElementById("tm_week").child(1).text()+																														//����
									"</div><div><br></div><div>")																				//�����
							+
							doc.getElementById("tm_week").child(2).text()+"</div>"+
							"</div><div><br></div><div>"+
							doc.getElementById("tm_week").child(3).text()+"</div>"
							+
							"</div><div><br></div><div>"+
							doc.getElementById("tm_week").child(4).text()+"</div>"; 

					horoscopes[4]=context.getResources().getStringArray(R.array.mail_ru_horoscopes)[4]+" : #"+context.getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+"\n\n"+doc.getElementById("tm_week").child(0).text()+"\n\n"+ifFf(doc.getElementById("tm_week").child(1).text()+"\n\n")+doc.getElementById("tm_week").child(2).text()+"\n\n"+doc.getElementById("tm_week").child(3).text()+"\n\n"+doc.getElementById("tm_week").child(4).text();
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
	
	public void LoadMonth(){
		Thread thr=new Thread(new Runnable() {				//������ � ����� ������
	        public void run() {
	        	try {							//�������� �������
					Thread.sleep(delayForThread); 
					Document doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+"/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadMailRu)[4]+"/").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
					resultS[5]= "<div style=\"float:left; width:110px; height:65px; margin:2px;> <span style=\" font-size:=\"\" 36pt\"=\"\" align=\"center\"><span style=\"font-size: 36pt\"><span style=\"color: rgb(255, 69, 0)\">"+
							doc.getElementById("tm_month").child(0).child(0).text()+		//����
							"</span></span> <span style=\"color: rgb(105, 105, 105)\"><span style=\"font-size: 10pt\">"+
							doc.getElementById("tm_month").child(0).text().replaceAll("[0-9]*", "").toUpperCase().replaceAll("��", "")+														//�����
							"</span></span></div><div>"+
							ifF(doc.getElementById("tm_month").child(1).text()+																														//����
									"</div><div><br></div><div>")																				//�����
							+
							doc.getElementById("tm_month").child(2).text()+"</div>"+
							"</div><div><br></div><div>"+
							doc.getElementById("tm_month").child(3).text()+"</div>"
							+
							"</div><div><br></div><div>"+
							doc.getElementById("tm_month").child(4).text()+"</div>"; 

					horoscopes[5]=context.getResources().getStringArray(R.array.mail_ru_horoscopes)[5]+" : #"+context.getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+"\n\n"+doc.getElementById("tm_month").child(0).text()+"\n\n"+ifFf(doc.getElementById("tm_month").child(1).text()+"\n\n")+doc.getElementById("tm_month").child(2).text()+"\n\n"+doc.getElementById("tm_month").child(3).text()+"\n\n"+doc.getElementById("tm_month").child(4).text();
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
	
	public void LoadYear(){
		Thread thr=new Thread(new Runnable() {				//������ � ����� ������
	        public void run() {
	        	try {							//�������� �������
					Thread.sleep(delayForThread); 
					Document doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+"/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadMailRu)[5]+"/").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
					resultS[6]= "<div style=\"float:left; width:110px; height:65px; margin:2px;> <span style=\" font-size:=\"\" 36pt\"=\"\" align=\"center\"><span style=\"font-size: 36pt\"><span style=\"color: rgb(255, 69, 0)\">"+
							doc.getElementById("tm_year").child(0).child(0).text()+		//����
							"</span></span> <span style=\"color: rgb(105, 105, 105)\"><span style=\"font-size: 10pt\">"+
							doc.getElementById("tm_year").child(0).text().replaceAll("[0-9]*", "").toUpperCase()+														//�����
							"</span></span></div><div>"+
							ifF(doc.getElementById("tm_year").child(1).text()+																														//����
									"</div><div><br></div><div>")																				//�����
							+
							doc.getElementById("tm_year").child(2).text()+"</div>"+
							"</div><div><br></div><div>"+
							doc.getElementById("tm_year").child(3).text()+"</div>"+
									"</div><div><br></div><div>"+
									doc.getElementById("tm_year").child(4).text()+"</div>"+
											"</div><div><br></div><div>"+
											doc.getElementById("tm_year").child(5).text()+
											"</div><div><br></div><div>"+
											doc.getElementById("tm_year").child(6).text()+
											"</div><div><br></div><div>"+
											doc.getElementById("tm_year").child(7).text()+"</div>"; 

					horoscopes[6]=context.getResources().getStringArray(R.array.mail_ru_horoscopes)[6]+" : #"+context.getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+"\n\n"+doc.getElementById("tm_year").child(0).text()+"\n\n"+ifFf(doc.getElementById("tm_year").child(1).text()+"\n\n")+doc.getElementById("tm_year").child(2).text()+"\n\n"+doc.getElementById("tm_year").child(3).text()+"\n\n"+doc.getElementById("tm_year").child(4).text()+"\n\n"+doc.getElementById("tm_year").child(5).text()+"\n\n"+doc.getElementById("tm_year").child(6).text()+"\n\n"+doc.getElementById("tm_year").child(7).text();
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
	
	public String ifF(final String arg1){
		if (arg1.length()<30){
			return "";}
			else {return arg1;}
	}
	
	public String ifFf(final String arg1){
		if (arg1.length()<5){
			return "";}
			else {return arg1;}
	}
}
