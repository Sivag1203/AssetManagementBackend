package com.backend.assetmanagement.model;

import com.backend.assetmanagement.enums.AssetStatus;
import com.backend.assetmanagement.enums.Level;

import jakarta.persistence.*;

@Entity
@Table(name = "asset")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private AssetCategory category;

    @Column(name = "serial_number")
    private String serialNumber;

    private String specs;

    @Enumerated(EnumType.STRING)
    private AssetStatus status = AssetStatus.available;
    
    @Column(name = "eligibility_level")
    @Enumerated(EnumType.STRING)
    private Level eligibilityLevel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AssetCategory getCategory() {
        return category;
    }

    public void setCategory(AssetCategory category) {
        this.category = category;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }
    
    public Level getEligibilityLevel() {
		return eligibilityLevel;
	}

	public void setEligibilityLevel(Level eligibilityLevel) {
		this.eligibilityLevel = eligibilityLevel;
	}

    @Override
    public String toString() {
        return "Asset [id=" + id + ", category=" + category + ", serialNumber=" + serialNumber +
                ", specs=" + specs + ", status=" + status + "]";
    }

}
