package novo.apps.doitall;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.*;
public class AdvancedCustomArrayAdapter extends BaseAdapter {
    private final Activity context;
    private  ArrayList<TicketRecord> tickets;

    public AdvancedCustomArrayAdapter(Activity context) {        
       this.context = context;   
    }

    static class ViewContainer {
        public ImageView imageView;
        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtProject;
        public TextView txtTentativeDate;
    }
    public void setTickets(ArrayList<TicketRecord> t) {
    	tickets = t;
    }
    
    
    

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewContainer viewContainer;
        View rowView = view;

        //---print the index of the row to examine---
        Log.d("CustomArrayAdapter",String.valueOf(position));

        //---if the row is displayed for the first time---
        if (rowView == null) {

            Log.d("CustomArrayAdapter", "New");
            
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.lvrowlayout2, null, true);

            //---create a view container object---
            viewContainer = new ViewContainer();        

            //---get the references to all the views in the row---
            viewContainer.txtTitle = (TextView) 
                rowView.findViewById(R.id.txtPresidentName); 
            viewContainer.txtDescription = (TextView) 
                rowView.findViewById(R.id.txtDescription); 
            viewContainer.imageView = (ImageView) rowView.findViewById(R.id.icon);
            viewContainer.txtTentativeDate = (TextView) 
                    rowView.findViewById(R.id.tentativedatename); 
            

            viewContainer.txtProject = (TextView) 
                    rowView.findViewById(R.id.projectname); 

            //---assign the view container to the rowView---
            rowView.setTag(viewContainer);
        } else { 

            //---view was previously created; can recycle---            
            Log.d("CustomArrayAdapter", "Recycling");
            //---retrieve the previously assigned tag to get
            // a reference to all the views; bypass the findViewByID() process,
            // which is computationally expensive---
            viewContainer = (ViewContainer) rowView.getTag();
        }

        //---customize the content of each row based on position---
        viewContainer.txtTitle.setText(tickets.get(position).getSummary());
        viewContainer.txtDescription.setText(tickets.get(position).getDescription());
        viewContainer.txtProject.setText(tickets.get(position).getTentativedate()+" - " + 
        tickets.get(position).getFinishdate());
        viewContainer.txtTentativeDate.setText(tickets.get(position).getProject());

       // viewContainer.txtDescription.setText(presidents[position] + 
       //     " ...Some descriptions here...");
        //viewContainer.imageView.setImageResource(imageIds[position]);
        return rowView;
    }




	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (tickets == null ) 
			return 0;
		
		return tickets.size();
	}




	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (tickets == null ) 
			return null;

		return tickets.get(position);
	}




	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}


