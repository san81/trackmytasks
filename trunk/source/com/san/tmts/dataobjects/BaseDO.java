
package com.san.tmts.dataobjects;

import java.io.Serializable;
import java.util.Date;



public class BaseDO implements Serializable
{
	
    private Date creationDate;
    private Long creationPersonId;
    private Date lastUpdateDate;
    private Long lastUpdatePersonId;

    public Long getCreationPersonId()
    {
        return creationPersonId;
    }

    public void setCreationPersonId(Long creationPersonId)
    {
        this.creationPersonId = creationPersonId;
    }

    public Long getLastUpdatePersonId()
    {
        return lastUpdatePersonId;
    }

    public void setLastUpdatePersonId(Long lastUpdatePersonId)
    {
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
