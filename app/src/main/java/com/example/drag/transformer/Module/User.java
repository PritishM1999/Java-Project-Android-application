package com.example.drag.transformer.Module;

public class User {
    private String id;
    private String usax;

    public User(String id,String usax)
    {
        this.id=id;
        this.usax=usax;

    }

    public User()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsax() {
        return usax;
    }

    public void setUsax(String usax) {
        this.usax = usax;
    }
}
