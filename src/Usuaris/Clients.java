package Usuaris;

import java.util.ArrayList;

public class Clients {
	
	private ArrayList<Client> clients;
	private Client actual;
	
	public Clients() {
		clients = new ArrayList<Client>();
		actual = null;
	}
	
	public ArrayList<Client> getClients() {
		return clients;
	}
	
	public Client getClientActual() {
		if(actual == null) {
			return null;
		}else {
			return actual;
		}
	}
	
	public void setClientActual(Client c) {
		actual = c;
	}
	
	public void printClients() {
		for(int i = 0; i < clients.size(); i++) {
			System.out.println(clients.get(i).getId());
		}
	}
	
	public boolean iniciSessio(String mail, String password) {
		for(int i = 0; i < clients.size(); i++) {
			if(clients.get(i).getMail().equals(mail)) {
				if(clients.get(i).getContrasenya().equals(password)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Client addClient(int n, String s, String m) {
		Client c = new Client();
		c.setId(n);
		c.setMail(s);
		c.setContrasenya(m);
		c.setBikeId(0);
		clients.add(c);
		return c;
	}
	
	public Client getClient(String correu) {
		
		for(int i = 0; i < clients.size(); i++) {
			if(clients.get(i).getMail().equals(correu)) {
				return clients.get(i);
			}
		}
		return null;
	}
}
	

