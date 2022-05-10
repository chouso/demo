package com.chcodes.demo.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String userName;

	private String password;
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
	private List<Command> commands = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles = new ArrayList<>();
//onetoone avec  3 eme entite
	@OneToOne
	@JoinTable(name = "Users_Info_Associations", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idUserInformations"))
	private UserInformations userInformations;

	public void addRole(Role role) {
		this.roles.add(role);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Collection<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	public UserInformations getUserInformations() {
		return userInformations;
	}

	public void setUserInformations(UserInformations userInfos) {
		this.userInformations = userInfos;
	}

	public AppUser(String name, String userName, String password, List<Command> commands, List<Role> roles,
			UserInformations userInformations) {
		super();
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.commands = commands;
		this.roles = roles;
		this.userInformations = userInformations;
	}

	public AppUser() {
		super();
	}

}