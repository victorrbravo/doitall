package novo.apps.doitall;

import java.io.Serializable;

import android.util.Log;


@SuppressWarnings("serial") //with this annotation we are going to hide compiler warning
public class ProjectRecord implements Serializable {
	private String projectid;
	private String title;
	private String description;
	private String type;
	private boolean selected;
	
	
	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	ProjectRecord() {
		Log.d("ProjectRecord 1","ProjectRecord 1");
	}


	public String getProjectid() {
		return projectid;
	}


	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

}
