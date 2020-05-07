package Usuaris;

import bikes.BikeUsed;

public class Client {
	
	private int id;
	private BikeUsed bike;
	private String mail;
	private String contrasenya;
	
	public Client() {
		bike = new BikeUsed(0);
	}
	/////////////////////////////////////////////////
	////////////////IDENTIFIERS//////////////////////
	public int getBikeId() {
		return bike.getId();
	}

	public void setBikeId(int id) {
		bike.setId(id);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	////////////////////////////////////////////////
	/////////////////LOG IN/////////////////////////
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}
	//////////////////////////////////////////////////

}
