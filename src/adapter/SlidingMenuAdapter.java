package adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.BBsRs.horoscopenew.R;



public class SlidingMenuAdapter extends ArrayAdapter<String> {

	 private final Context context;
	    String[] names;
	    boolean[] checked;
	    boolean f1;
	    public SlidingMenuAdapter(Context context,  String[] names, boolean[] checked) {
	        super(context, R.layout.row, names);
	        this.context = context;
	        this.names=names;
	        this.checked=checked;
	    }
	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
	        LayoutInflater inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View rowView = inflater.inflate(R.layout.row, parent, false);
	        TextView textName = (TextView) rowView.findViewById(R.id.row_title);
	        
	        textName.setText(names[position]);					//������ �����
	        if (checked[position]){
	        	//rowView.setBackground(context.getResources().getDrawable(R.drawable.bg_drawer_selected));
	        	rowView.setBackgroundResource(R.drawable.bg_drawer_selected);
	        	//rowView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.list_selector_holo_checked));
	        	f1=true;
	        }
	        return rowView;
	    }
	    
	    public void onSetChecked(int position, int lastSelected){
	    	if (position!=lastSelected && position!=-1 && lastSelected!=-1){
	    	checked[position]=true;
	    	checked[lastSelected]=false;
	    	notifyDataSetChanged();
	    	}
	    }
	    
	 

}
