package novo.apps.doitall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
public class AdvancedCustomArrayAdapter extends BaseAdapter {
    private final Activity context;
    private  ArrayList<TicketRecord> tickets;
    private final String currentuser;
    
    Comparator<TicketRecord> ticketComparator; 

    public AdvancedCustomArrayAdapter(Activity context, String user) {
    	
       this.context = context;
       this.currentuser = user;
       Log.d("AdvancedCustomArrayAdapter",this.currentuser);
       ticketComparator  = new Comparator<TicketRecord>() {
           public int compare(TicketRecord obj1,TicketRecord obj2) {
           	Long first = obj1.getEpochtentativedate();
           	Long last = obj2.getEpochtentativedate();
           	int result = 0;
           	if (first > last ) {
           		result = 1;
           	}
           	else {
           		result =  -1;
           	}
           	
               return result;
           }
        };
        
       // Collections.sort(tickets, ticketComparator);
        
    }

    static class ViewContainer {
        public ImageView imageView;
        public ImageView tagView;
        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtProject;
        public TextView txtTentativeDate;
    }
    
    public void setTickets(ArrayList<TicketRecord> t) {
    	tickets = t;
    	Collections.sort(tickets, ticketComparator);
    }
    @Override
    public void notifyDataSetChanged() {
    	Collections.sort(tickets, ticketComparator);
    	super.notifyDataSetChanged();
    	
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
            viewContainer.tagView = (ImageView) rowView.findViewById(R.id.tagmessage);
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

		String fulltext = tickets.get(position).getSummary();
		viewContainer.txtTitle.setText(fulltext);

		Log.d("fulltext",fulltext);
    	String owner = tickets.get(position).getOwner();
    	String whoassign = tickets.get(position).getAssignto();

        if ( tickets.get(position).getStatus().contentEquals("Finished")) {
        if (!owner.contentEquals(this.currentuser) ) {
        	String assignfrom = tickets.get(position).getAssignfrom();
        	if (assignfrom.isEmpty() || assignfrom.contentEquals("n/a")) {
        		String actionassign = "(" + owner + ")";
        		SpannableString spanString = new SpannableString(fulltext + actionassign);
        		spanString.setSpan(new RelativeSizeSpan(0.65f), fulltext.length(), fulltext.length()+actionassign.length(), 0);
        		viewContainer.txtTitle.setText(spanString);
        		
        	}
        	else {
        		String actionassign = "(" + assignfrom + "->" + whoassign + ")";
        		SpannableString spanString = new SpannableString(fulltext + actionassign);
        		spanString.setSpan(new RelativeSizeSpan(0.65f), fulltext.length(), fulltext.length()+actionassign.length(), 0);
        		viewContainer.txtTitle.setText(spanString);
        	}
			        	
        }
        
        	viewContainer.imageView.setImageResource(R.drawable.closefolder);
        
        
        	
        }
        else if ( tickets.get(position).getStatus().contentEquals("Postponed")) {
        	viewContainer.imageView.setImageResource(R.drawable.tasknext32);        	
        }
        else if ( tickets.get(position).getStatus().contentEquals("AssignTo") ||
        		( !owner.contentEquals(this.currentuser) )  ) {
        	String assignfrom = tickets.get(position).getAssignfrom();
        	if (assignfrom.isEmpty() || assignfrom.contentEquals("n/a")) {
        		String actionassign = "(" + owner + ")";
        		SpannableString spanString = new SpannableString(fulltext + actionassign);
        		spanString.setSpan(new RelativeSizeSpan(0.65f), fulltext.length(), fulltext.length()+actionassign.length(), 0);
        		viewContainer.txtTitle.setText(spanString);
        		
        	}
        	else {
        		String actionassign = "(" + assignfrom + "->" + whoassign + ")";
        		SpannableString spanString = new SpannableString(fulltext + actionassign);
        		spanString.setSpan(new RelativeSizeSpan(0.65f), fulltext.length(), fulltext.length()+actionassign.length(), 0);
        		viewContainer.txtTitle.setText(spanString);
        	}
    					

    		Log.d("assignaccept","ASSIGN");
    		if (tickets.get(position).getStatus().contentEquals("AssignTo")) {
    			Log.d("assignaccept","assignto");
    			viewContainer.imageView.setImageResource(R.drawable.assignto);
    		}
    		else {
    			Log.d("assignaccept","assignaccept");
    			viewContainer.imageView.setImageResource(R.drawable.assignaccept);
    		}

    		
        }
        else {
    		if (tickets.get(position).getAssignto().contentEquals(currentuser) ) {
    			String assignfrom = tickets.get(position).getAssignfrom();
            	if (assignfrom.isEmpty() || assignfrom.contentEquals("n/a")) {
            		String actionassign = "(" + owner + ")";
            		SpannableString spanString = new SpannableString(fulltext + actionassign);
            		spanString.setSpan(new RelativeSizeSpan(0.65f), fulltext.length(), fulltext.length()+actionassign.length(), 0);
            		viewContainer.txtTitle.setText(spanString);
            		
            	}
            	else {
            		String actionassign = "(" + assignfrom + "->" + whoassign + ")";
            		SpannableString spanString = new SpannableString(fulltext + actionassign);
            		spanString.setSpan(new RelativeSizeSpan(0.65f), fulltext.length(), fulltext.length()+actionassign.length(), 0);
            		viewContainer.txtTitle.setText(spanString);
            	}
    			viewContainer.imageView.setImageResource(R.drawable.assignaccept);
    			//viewContainer.txtTitle.setText(viewContainer.txtTitle.getText().toString()+ "<-here");
    			
    		}
    		else {
    			viewContainer.imageView.setImageResource(R.drawable.yellowsoftware);
    		}
        	
        	
        }
        //---customize the content of each row based on position---


        if ( tickets.get(position).getStatus().contentEquals("ToDo")) {
			
	        Long tomorrow = Utils.lastSecondTomorrow()+60;
			
			Long now = Utils.now();
			Long etentative = tickets.get(position).getEpochtentativedate();
	
			Log.d("tentativedate","tentative:"  +String.valueOf(etentative));
			Log.d("tomorrow","tomorrow:"  +String.valueOf(tomorrow));
	
			
			if (etentative < tomorrow) {
				if (etentative < now ) {
					Log.d("tomorrow","Yes atrasada");
					viewContainer.tagView.setImageResource(R.drawable.atrasada);
				}
				else {
					Log.d("tomorrow","Yes manana");
					viewContainer.tagView.setImageResource(R.drawable.manana);	
				}
			}
			else {
				viewContainer.tagView.invalidate();
				viewContainer.tagView.setImageBitmap(null);
			
			}
        }
        else {
        	viewContainer.tagView.invalidate();
			viewContainer.tagView.setImageBitmap(null);
        }
        		
        viewContainer.txtDescription.setText(tickets.get(position).getDescription());
        viewContainer.txtTentativeDate.setText(tickets.get(position).getTentativedate()+" - " + 
        tickets.get(position).getFinishdate());
        viewContainer.txtProject.setText(tickets.get(position).getProject());

       // viewContainer.txtDescription.setText(presidents[position] + 
       //     " ...Some descriptions here...");
        //viewContainer.imageView.setImageResource(imageIds[position]);

//        if (tickets.get(position).getEpochtentativedate() < tomorrow) {
//        	rowView.setBackgroundColor(Color.parseColor("#f2f07e"));        
//        } else {
//        	rowView.setBackgroundColor(Color.WHITE);  
//        }

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







