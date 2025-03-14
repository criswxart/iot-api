package com.tld.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name="role")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
	private Integer roleId;	
	
	@Column(name="role_name", nullable = false, unique = true)
	private String roleName;
	
	public Role (Integer roleId) {
		this.roleId=roleId;
	}
	
	@ManyToMany(mappedBy = "role")
	private Set<Users> users;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	        name="role_permission",
	        joinColumns= @JoinColumn(name="role_id"),
	        inverseJoinColumns=
	            @JoinColumn(name="permission_id")
	    )
	private Set<Permission> permissions;

}
