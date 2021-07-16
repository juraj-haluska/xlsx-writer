package net.spacive.xlsx.core_config;

import java.util.Date;

public class CoreConfig {

    private String creator = "";
    private String lastModifiedBy = "";
    private Date created = new Date();
    private Date lastModified = new Date();

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getCreator() {
        return creator;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Date getCreated() {
        return created;
    }

    public Date getLastModified() {
        return lastModified;
    }
}
