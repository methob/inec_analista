package br.inec.com.inec_desafio_analista.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Repositories implements Serializable{

    private Integer id;

    private String name;

    private String description;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("owner")
    private User owner;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
