package com.example.security.models.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.PrePersist;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;


public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    protected ObjectId id;

    @Version
    private Long version;

    protected Date lastUpdated = new Date();

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    @PrePersist
    private void prePersist() { // NOSONAR
        lastUpdated = new Date();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BaseEntity other = (BaseEntity) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}

