package novo.apps.doitall;

public class TicketRecord {
	private String summary;
	private String description;
	private String project;
	private String tentativedate;
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getTentativedate() {
		return tentativedate;
	}
	public void setTentativedate(String tentativedate) {
		this.tentativedate = tentativedate;
	}
	private int projectid;
	private String id;
	TicketRecord() {
		
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getProjectid() {
		return projectid;
	}
	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	

}
