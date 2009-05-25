package com.san.tmts.dataobjects;

/**
 * ProjectusersId generated by MyEclipse Persistence Tools
 */

public class ProjectusersId extends com.san.tmts.dataobjects.BaseDO implements
		java.io.Serializable {

	// Fields

	private Integer projectId;

	private Integer userId;

	// Constructors

	/** default constructor */
	public ProjectusersId() {
	}

	/** full constructor */
	public ProjectusersId(Integer projectId, Integer userId) {
		this.projectId = projectId;
		this.userId = userId;
	}

	// Property accessors

	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProjectusersId))
			return false;
		ProjectusersId castOther = (ProjectusersId) other;

		return ((this.getProjectId() == castOther.getProjectId()) || (this
				.getProjectId() != null
				&& castOther.getProjectId() != null && this.getProjectId()
				.equals(castOther.getProjectId())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null
						&& castOther.getUserId() != null && this.getUserId()
						.equals(castOther.getUserId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getProjectId() == null ? 0 : this.getProjectId().hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		return result;
	}

}