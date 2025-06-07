package com.backend.assetmanagement.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.backend.assetmanagement.enums.AssetStatus;
import com.backend.assetmanagement.model.Asset;

@Component
public class AssetDTO {
    private int id;
    private String serialNumber;
    private String specs;
    private AssetStatus status;

    public AssetDTO() {}

    public AssetDTO(int id, String serialNumber, String specs, AssetStatus status) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.specs = specs;
        this.status = status;
    }

    public int getId() { 
    	return id; 
    	}
    public void setId(int id) { 
    	this.id = id; 
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

    public static List<AssetDTO> convertToDTOList(List<Asset> assets) {
        List<AssetDTO> dtoList = new ArrayList<>();
        for (Asset a : assets) {
            AssetDTO dto = new AssetDTO();
            dto.setId(a.getId());
            dto.setSerialNumber(a.getSerialNumber());
            dto.setSpecs(a.getSpecs());
            dto.setStatus(a.getStatus());
            dtoList.add(dto);
        }
        return dtoList;
    }
}
