package com.backend.assetmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "assigned_assets")
public class AssignedAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Asset asset;

    @ManyToOne
    private Employee employee;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "AssignedAsset [id=" + id + ", asset=" + asset + ", employee=" + employee + "]";
    }
}
