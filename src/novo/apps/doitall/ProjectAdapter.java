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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.*;


public class ProjectAdapter extends BaseAdapter {
    private final Activity context;
    private  ArrayList<ProjectRecord> projects;

    public ProjectAdapter(Activity context) {        
       this.context = context;   
    }

    static class ViewContainer1 {
        public TextView txtProject;
        public CheckBox selected;
    }
    public void setProjects(ArrayList<ProjectRecord> t) {
    	projects = t;
    }
    
    
    

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewContainer1 viewContainer;
        View rowView = view;

        //---print the index of the row to examine---
        Log.d("CustomArrayAdapter",String.valueOf(position));

        //---if the row is displayed for the first time---
        if (rowView == null) {

            Log.d("CustomArrayAdapter", "New");
            
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.checkrow, null, true);

            //---create a view container object---
            viewContainer = new ViewContainer1();        

            //---get the references to all the views in the row---
            viewContainer.txtProject = (TextView) 
                rowView.findViewById(R.id.labelproject); 
            viewContainer.selected = (CheckBox) 
                rowView.findViewById(R.id.checkproject); 
            
            //---assign the view container to the rowView---
            rowView.setTag(viewContainer);
        } else { 

            //---view was previously created; can recycle---            
            Log.d("CustomArrayAdapter", "Recycling");
            //---retrieve the previously assigned tag to get
            // a reference to all the views; bypass the findViewByID() process,
            // which is computationally expensive---
            viewContainer = (ViewContainer1) rowView.getTag();
        }

        
        //---customize the content of each row based on position---
        viewContainer.txtProject.setText(projects.get(position).getTitle());
        
        viewContainer.selected.setChecked(projects.get(position).isSelected());
       
       // viewContainer.txtDescription.setText(presidents[position] + 
       //     " ...Some descriptions here...");
        //viewContainer.imageView.setImageResource(imageIds[position]);
        return rowView;
    }




	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (projects == null ) 
			return 0;
		
		return projects.size();
	}




	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (projects == null ) 
			return null;

		return projects.get(position);
	}




	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}


