package com.backend.assetmanagement.model;

import com.backend.assetmanagement.enums.ReturnStatus;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "return_requests")
public class ReturnRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Asset asset;

    @ManyToOne
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private ReturnStatus status = ReturnStatus.pending;

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

    public ReturnStatus getStatus() {
        return status;
    }

    public void setStatus(ReturnStatus status) {
        this.status = status;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return "ReturnRequest [id=" + id + ", asset=" + asset + ", employee=" + employee + ", status=" + status +
               ", requestDate=" + requestDate + "]";
    }
}
