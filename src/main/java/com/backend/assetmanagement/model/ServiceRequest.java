package com.backend.assetmanagement.model;

import com.backend.assetmanagement.enums.ServiceStatus;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "service_requests")
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Asset asset;

    @ManyToOne
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private ServiceStatus status = ServiceStatus.pending;

    private String description;

    @Column(name = "request_date")
    private LocalDate requestDate;


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

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return "ServiceRequest [id=" + id + ", asset=" + asset + ", employee=" + employee + ", status=" + status +
               ", description=" + description + ", requestDate=" + requestDate + "]";
    }
}
