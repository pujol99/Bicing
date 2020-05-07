package bikes;

public class BikeParked extends Bike{
	
	private Station estacio;
	
	public BikeParked(int id, Station estacio) {
		super(id);
		this.estacio = estacio;
	}

	public int getStationId() {
		return estacio.getId();
	}

	public void setOwner(Station station) {
		this.estacio = station;
	}

}
