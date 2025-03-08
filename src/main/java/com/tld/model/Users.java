package com.tld.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Entity
@Table (name="users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Integer userId;	
	
	@Column(name="user_name", nullable = false)
	private String userName;	
	
	@Column(name="user_password", nullable = false)
	private String userPassword;	
	
	@ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)	
	private Role rolId;
	
	@Column(name="user_created_at", nullable = false)
	private Long userCreatedAt;
	
	@Column(name="user_active", nullable = false)
	private Boolean userActive;

}
