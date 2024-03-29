package com.san.tmts.dataobjects;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Project generated by MyEclipse Persistence Tools
 */

public class ProjectDO extends com.san.tmts.dataobjects.BaseDO implements
		java.io.Serializable {

	// Fields

	private Integer projectId;

	private UsersDO users;

	private String projectName;

	private String description;	

	private Set tasklists = new HashSet(0);

	// Constructors

	/** default constructor */
	public ProjectDO() {
	}

	/** minimal constructor */
	public ProjectDO(Integer projectId, String projectName) {
		this.projectId = projectId;
		this.projectName = projectName;
	}

	/** full constructor */
	public ProjectDO(Integer projectId, UsersDO users, String projectName,
			String description, Date creationDate, Date lastupdateDate,
			Integer lastupdatePerdonId, Set tasklists) {
		this.projectId = projectId;
		this.users = users;
		this.projectName = projectName;
		this.description = description;		
		this.tasklists = tasklists;
	}

	// Property accessors

	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public UsersDO getUsers() {
		return this.users;
	}

	public void setUsers(UsersDO users) {
		this.users = users;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set getTasklists() {
		return this.tasklists;
	}

	public void setTasklists(Set tasklists) {
		this.tasklists = tasklists;
	}

}