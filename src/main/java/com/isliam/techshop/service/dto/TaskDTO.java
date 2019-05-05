package com.isliam.techshop.service.dto;

public class TaskDTO {

    private String id;
    private String name;

    public TaskDTO() {
    }

    public TaskDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

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

}
