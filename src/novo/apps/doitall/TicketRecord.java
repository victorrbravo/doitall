package novo.apps.doitall;

import java.io.Serializable;

public class TicketRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String summary;
	private String description;
	private String project;
	private String tentativedate;
	private String assignto;
	private String assignfrom;
	private int notifyid;
	
	
	public int getNotifyid() {
		return notifyid;
	}
	public void setNotifyid(int notifyid) {
		this.notifyid = notifyid;
	}
	public String getAssignfrom() {
		return assignfrom;
	}
	public void setAssignfrom(String assignfrom) {
		this.assignfrom = assignfrom;
	}
	public String getAssignto() {
		return assignto;
	}
	public void setAssignto(String assignto) {
		this.assignto = assignto;
	}
	public Long getEpochtentativedate() {
		return epochtentativedate;
	}
	public void setEpochtentativedate(Long epochtentativedate) {
		this.epochtentativedate = epochtentativedate;
	}
	public Long getEpochfinishdate() {
		return epochfinishdate;
	}
	public void setEpochfinishdate(Long epochfinishdate) {
		this.epochfinishdate = epochfinishdate;
	}
	private Long epochtentativedate;
	private Long epochfinishdate;
	private String finishdate;
	private String owner;
	private String status;
	private String type;
	
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
		summary = "";
		description = "";
		project = "";
		tentativedate = "";
		assignto = "";
		owner = "";
		assignfrom = "";
		notifyid = 0;

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
	public String getFinishdate() {
		return finishdate;
	}
	public void setFinishdate(String finishdate) {
		this.finishdate = finishdate;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
