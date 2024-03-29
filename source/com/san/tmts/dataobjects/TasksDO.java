package com.san.tmts.dataobjects;

import java.util.Date;

/**
 * Tasks generated by MyEclipse Persistence Tools
 */

public class TasksDO extends com.san.tmts.dataobjects.BaseDO implements
		java.io.Serializable {

	// Fields

	private Integer taskId;

	private UsersDO usersByAssignedPerson;

	private TasklistDO tasklist;

	private String taskName;

	private String description;

	private Date startDatetime;

	private Date expectedEndDatetime;
	
	// Constructors

	/** default constructor */
	public TasksDO() {
	}

	/** minimal constructor */
	public TasksDO(Integer taskId, UsersDO usersByAssignedPerson,
			TasklistDO tasklist, String taskName) {
		this.taskId = taskId;
		this.usersByAssignedPerson = usersByAssignedPerson;
		this.tasklist = tasklist;
		this.taskName = taskName;
	}

	/** full constructor */
	public TasksDO(Integer taskId, UsersDO usersByAssignedPerson,
			TasklistDO tasklist, UsersDO usersByCreationPersonId, String taskName,
			String description, Date startDatetime, Date expectedEndDatetime,
			Date creationDate, Date lastupdateDate, Integer lastupdatePerdonId) {
		this.taskId = taskId;
		this.usersByAssignedPerson = usersByAssignedPerson;
		this.tasklist = tasklist;
		this.taskName = taskName;
		this.description = description;
		this.startDatetime = startDatetime;
		this.expectedEndDatetime = expectedEndDatetime;
	}

	// Property accessors

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public UsersDO getUsersByAssignedPerson() {
		return this.usersByAssignedPerson;
	}

	public void setUsersByAssignedPerson(UsersDO usersByAssignedPerson) {
		this.usersByAssignedPerson = usersByAssignedPerson;
	}

	public TasklistDO getTasklist() {
		return this.tasklist;
	}

	public void setTasklist(TasklistDO tasklist) {
		this.tasklist = tasklist;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDatetime() {
		return this.startDatetime;
	}

	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = startDatetime;
	}

	public Date getExpectedEndDatetime() {
		return this.expectedEndDatetime;
	}

	public void setExpectedEndDatetime(Date expectedEndDatetime) {
		this.expectedEndDatetime = expectedEndDatetime;
	}

}