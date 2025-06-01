package com.backend.assetmanagement.model;

import com.backend.assetmanagement.enums.RequestStatus;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "asset_requests")
public class AssetRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Asset asset;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.pending;

    @Column(name = "request_date")
    private LocalDate requestDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
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
        return "AssetRequest [id=" + id + ", employee=" + employee + ", asset=" + asset + ", status=" + status +
               ", requestDate=" + requestDate + "]";
    }
}
