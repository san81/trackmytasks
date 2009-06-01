package com.san.tmts.dataobjects;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Tasklist generated by MyEclipse Persistence Tools
 */

public class TasklistDO extends com.san.tmts.dataobjects.BaseDO implements
		java.io.Serializable {

	// Fields

	private Integer tasklistId;

	private ProjectDO project;

	private UsersDO users;

	private String tasklistName;

	private String description;

	private Set taskses = new HashSet(0);

	// Constructors

	/** default constructor */
	public TasklistDO() {
	}

	/** minimal constructor */
	public TasklistDO(Integer tasklistId, ProjectDO project, String tasklistName) {
		this.tasklistId = tasklistId;
		this.project = project;
		this.tasklistName = tasklistName;
	}

	/** full constructor */
	public TasklistDO(Integer tasklistId, ProjectDO project, UsersDO users,
			String tasklistName, String description, Date creationDate,
			Date lastupdateDate, Integer lastupdatePerdonId, Set taskses) {
		this.tasklistId = tasklistId;
		this.project = project;
		this.users = users;
		this.tasklistName = tasklistName;
		this.description = description;
		this.taskses = taskses;
	}

	// Property accessors

	public Integer getTasklistId() {
		return this.tasklistId;
	}

	public void setTasklistId(Integer tasklistId) {
		this.tasklistId = tasklistId;
	}

	public ProjectDO getProject() {
		return this.project;
	}

	public void setProject(ProjectDO project) {
		this.project = project;
	}

	public UsersDO getUsers() {
		return this.users;
	}

	public void setUsers(UsersDO users) {
		this.users = users;
	}

	public String getTasklistName() {
		return this.tasklistName;
	}

	public void setTasklistName(String tasklistName) {
		this.tasklistName = tasklistName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getTaskses() {
		return this.taskses;
	}

	public void setTaskses(Set taskses) {
		this.taskses = taskses;
	}

}