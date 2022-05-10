package com.chcodes.demo.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
@Entity
public class UserInformations {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idInformations;

	private String address;

	private String city;

	private String email;
//onetoone avec 3 eme entite
	@OneToOne( cascade = CascadeType.ALL )
	@JoinTable(name = "Users_Info_Associations", joinColumns = @JoinColumn(name = "idUserInformations"), inverseJoinColumns = @JoinColumn(name = "idUser"))
	private AppUser user;

	
	
	public int getIdInformations() {
		return idInformations;
	}

	public void setIdInformations(int idInformations) {
		this.idInformations = idInformations;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserInformations [idInformations=" + idInformations + ", address=" + address + ", city=" + city
				+ ", email=" + email + ", user=" + user + "]";
	}
	
	
}
