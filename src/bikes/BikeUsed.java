package bikes;

import Usuaris.*;

public class BikeUsed extends Bike{
	
	private Client owner;
	
	public BikeUsed(int id) {
		super(id);
	}
	
	public BikeUsed(int id, Client c) {
		super(id);
		owner = c;
	}
	
	public Client getOwner() {
		return owner;
	}

	public void setOwner(Client owner) {
		this.owner = owner;
	}
	
	
	
	
	

}
