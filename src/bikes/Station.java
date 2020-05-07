package bikes;

import java.util.ArrayList;
import java.util.Random;

public class Station {
	
	private int size;
	private int id;
	private ArrayList<Integer> sites;
	
	public Station() {
		sites = new ArrayList<Integer>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int i) {
		id = i;
	}

	public int getSize() {
		return size;
	}
	
	public boolean isEmpty() {
		for(int i = 0; i < sites.size(); i++) {
			if(sites.get(i) != 0){
				return false;
			}
		}
		return true;
	}
	
	public boolean isFull() {
		for(int i = 0; i < sites.size(); i++) {
			if(sites.get(i) == 0){
				return false;
			}
		}
		return true;
	}

	public ArrayList<Integer> getSites() {
		return sites;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setSites(ArrayList<Integer> sites) {
		this.sites = sites;
	}
	
	public int takeBike() {
		if(isEmpty()) {
			return -1;
		}
		Random rand = new Random();
		int n = rand.nextInt(sites.size());
		if(sites.get(n) == 0) {
			takeBike();
		}
		return n;
	}
	
	public int parkBike() {
		if(isFull()) {
			return -1;
		}
		Random rand = new Random();
		int n = rand.nextInt(sites.size());
		if(sites.get(n) != 0) {
			parkBike();
		}	
		return n;
	}
	
	
	
	
	

}
