package adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BBsRs.horoscopenew.R;
				//������� ��� ������ :), � ����������...
public class ListViewAdapter extends ArrayAdapter<String> {

	 private final Context context;
	    private final String[] signs;
	    SharedPreferences sPref;    // ��� ��� �������� ��
	    LayoutInflater inflater;
	    boolean[] checked;

	    
	    public ListViewAdapter(Context context, String[] signs) {
	        super(context, R.layout.simple_element_listview, signs);
	        this.context = context;
	        this.signs = signs;
	        checked = new boolean[this.signs.length];
	        inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        
	    }
	    
	    static class ViewHolder {
	        public ImageView imageSign;
	        public TextView textSign;
	        public RelativeLayout rlLt;
	    }
	    
	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
	    	
	    	final ViewHolder holder;
	        View rowView = convertView;
	        if (rowView == null) {
	            rowView = inflater.inflate(R.layout.simple_element_listview, parent, false);
	            holder = new ViewHolder();
	            holder.textSign = (TextView) rowView.findViewById(R.id.textSign);
	            holder.imageSign = (ImageView)rowView.findViewById(R.id.imageSign);
	            holder.rlLt = (RelativeLayout) rowView.findViewById(R.id.relativeLayout1);
	            rowView.setTag(holder);
	        } else {
	            holder = (ViewHolder) rowView.getTag();
	        }
	        
	        if(checked[position])
	        	holder.rlLt.setBackgroundResource(R.drawable.ic_background_element_checked);
	        else 
	        	holder.rlLt.setBackgroundResource(R.drawable.list_selector_normal);
	        
	        holder.textSign.setText(signs[position]);					//����� ����� �������
	        
	        													//��������� ��������
	        switch(position){
	        case 0:
	        	holder.imageSign.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sign__01));
	        	break;
	        case 1:
	        	holder.imageSign.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sign__02));
	        	break;
	        case 2:
	        	holder.imageSign.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sign__03));
	        	break;
	        case 3:
	        	holder.imageSign.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sign__04));
	        	break;
	        case 4:
	        	holder.imageSign.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sign__05));
	        	break;
	        case 5:
	        	holder.imageSign.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sign__06));
	        	break;
	        case 6:
	        	holder.imageSign.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sign__07));
	        	break;
	        case 7:
	        	holder.imageSign.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sign__08));
	        	break;
	        case 8:
	        	holder.imageSign.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sign__09));
	        	break;
	        case 9:
	        	holder.imageSign.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sign__10));
	        	break;
	        case 10:
	        	holder.imageSign.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sign__11));
	        	break;
	        case 11:
	        	holder.imageSign.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sign__12));
	        	break;
	        }
	        //���������� ����� � ������*/
	        return rowView;
	    }
	    
	    public void onSetChecked(int position){
	    	for (int i=0; i<checked.length; i++)
	    		checked[i]=false;
	    	checked[position]=true;
	    	notifyDataSetChanged();
	    }

}
