
package com.san.tmts.dataobjects;

import java.io.Serializable;
import java.util.Date;



public class BaseDO implements Serializable
{
	
    private Date creationDate;
    private UsersDO creationPersonId;
    private Date lastUpdateDate;
    private UsersDO lastUpdatePersonId;

   
    public UsersDO getCreationPersonId() {
		return creationPersonId;
	}

	public void setCreationPersonId(UsersDO creationPersonId) {
		this.creationPersonId = creationPersonId;
	}

	public UsersDO getLastUpdatePersonId() {
		return lastUpdatePersonId;
	}

	public void setLastUpdatePersonId(UsersDO lastUpdatePersonId) {
		this.lastUpdatePersonId = lastUpdatePersonId;
	}

	public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate()
    {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate)
    {
        this.lastUpdateDate = lastUpdateDate;
    }

}
