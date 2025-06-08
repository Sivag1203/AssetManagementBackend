package com.backend.assetmanagement.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AdminDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String address;

    public AdminDTO() {}

    public AdminDTO(int id, String name, String email, String phone, String department, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.address = address;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static AdminDTO fromEntity(com.backend.assetmanagement.model.Admin admin) {
        return new AdminDTO(
            admin.getId(),
            admin.getName(),
            admin.getEmail(),
            admin.getPhone(),
            admin.getDepartment(),
            admin.getAddress()
        );
    }

    public static List<AdminDTO> fromEntityList(List<com.backend.assetmanagement.model.Admin> admins) {
        List<AdminDTO> dtos = new ArrayList<>();
        for (com.backend.assetmanagement.model.Admin admin : admins) {
            dtos.add(fromEntity(admin));
        }
        return dtos;
    }
}
