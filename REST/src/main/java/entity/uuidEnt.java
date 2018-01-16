package entity;

import java.util.List;


// @author Lasse
 
public class uuidEnt {
    
List<String> uuids;
String created;

    public List<String> getUuids()
    {
        return uuids;
    }

    public void setUuids(List<String> uuids)
    {
        this.uuids = uuids;
    }
String user;
String description;

    public String getCreated()
    {
        return created;
    }

    public void setCreated(String created)
    {
        this.created = created;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
