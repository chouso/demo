package com.chcodes.demo.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Command {

	@Id
	@GeneratedValue
	private int id;
	private LocalDate commandDate;
	@ManyToOne( cascade = CascadeType.ALL )
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;

	public Command(LocalDate commandDate, AppUser user) {
		super();
		this.commandDate = commandDate;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getCommandDate() {
		return commandDate;
	}

	public void setCommandDate(LocalDate commandDate) {
		this.commandDate = commandDate;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Command [commandDate=" + commandDate + ", user=" + user + "]";
	}

}
