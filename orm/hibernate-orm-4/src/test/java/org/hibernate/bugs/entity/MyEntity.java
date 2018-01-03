package org.hibernate.bugs.entity;


import java.io.Serializable;

// @Audited
public class MyEntity implements Serializable {


    private Long id;


    private String details;


    private String title;


    public MyEntity() {
    }

    public MyEntity(String details, String title) {
        this.details = details;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

