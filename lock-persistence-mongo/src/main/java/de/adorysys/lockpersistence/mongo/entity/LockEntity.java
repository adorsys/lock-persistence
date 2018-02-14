package de.adorysys.lockpersistence.mongo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "lock_persistence")
public class LockEntity {

    @Id
    private String id;

    private String name;

    private String value;

    private Date expires;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}
