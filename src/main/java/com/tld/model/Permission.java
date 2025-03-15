package com.tld.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "permission")
public class Permission {
	
	@Id	
	@Column(name = "permission_id")
	private Integer permissionId;
	
	@Column(name = "permission_name")
	private String permissionName;
	
	@ManyToMany(mappedBy = "permissions")
	private Set<Role> role;

}
