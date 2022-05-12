package com.chcodes.demo.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Command {

	@Id
	@GeneratedValue
	private Long id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate commandDate;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;

	public Command(LocalDate commandDate, AppUser user) {
		super();
		this.commandDate = commandDate;
		this.user = user;
	}

	

	public Command() {
		super();
		// TODO Auto-generated constructor stub
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



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}

}
