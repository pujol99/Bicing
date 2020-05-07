package bikes;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Usuaris.*;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class BikesData {
		
		private ArrayList<Station> estacions;
		private ArrayList<BikeParked> bikesParked;
		private ArrayList<BikeUsed> bikesMoving;
		private Clients clients;
		
		public BikesData(Clients clients) {
			estacions = new ArrayList<Station>();
			bikesParked = new ArrayList<BikeParked>();
			bikesMoving = new ArrayList<BikeUsed>();
			this.clients = clients;
			initData();
		}	
		
		public ArrayList<Station> getEstacions() {
			return estacions;
		}

		public ArrayList<BikeParked> getBikesParked() {
			return bikesParked;
		}
		
		public ArrayList<BikeUsed> getBikesMoving() {
			return bikesMoving;
		}
		
		public Clients getClients() {
			return clients;
		}

		public void initData() {
			try {
				File inputFile = new File("stations.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(inputFile);
				doc.getDocumentElement().normalize();
	         
				//llista d'estacions
				NodeList nList = doc.getElementsByTagName("Estacio");
	         
				//recorrem llista
				for (int temp = 0; temp < nList.getLength(); temp++) {
	        	 
					//creem node de cada element
					Node nNode = nList.item(temp);
	            
					//si es te un node continuem
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	            	Station s = new Station();
	            	//creem un element d'aques node
						Element eElement = (Element) nNode;
	               
						//busquem si te un atribut index i l'imprimim
						s.setId(Integer.parseInt(eElement.getAttribute("index")));
	               
						//creem una llista amb els subelements d'aquest element
						NodeList nList2 = eElement.getElementsByTagName("pos");
	               
						//la recorrem
						for (int temp2 = 0; temp2 < nList2.getLength(); temp2++) {
							s.setSize(nList2.getLength());
							
							//creem un node de cada subelemet
							Node nNode2 = nList2.item(temp2);
							
	            	   
							//si te node continuem
							if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
								
								//creem un element d'aquest node
								Element eElement2 = (Element) nNode2;
								int id = Integer.parseInt(eElement2
	    	                       .getElementsByTagName("bikeIndex")
	    	                       .item(0)
	    	                       .getTextContent());
	    	                    BikeParked b = new BikeParked(id, s);
								s.getSites().add(b.getId());

								bikesParked.add(b);
							}
	            	   
						}
						estacions.add(s);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			/////////////////////////////////////////////////////////////////////////////
			try {
				File inputFile = new File("BikesUsed.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(inputFile);
				doc.getDocumentElement().normalize();
	         
				//llista d'estacions
				NodeList nList = doc.getElementsByTagName("bike");
	         
				//recorrem llista
				for (int temp = 0; temp < nList.getLength(); temp++) {
	        	 
					//creem node de cada element
					Node nNode = nList.item(temp);
	            
					//si es te un node continuem
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	            	
						Element eElement = (Element) nNode;
						
						int idBike = Integer.parseInt(eElement
	    	                       .getElementsByTagName("bikeIndex")
	    	                       .item(0)
	    	                       .getTextContent());
						
						int idClient = Integer.parseInt(eElement
	    	                       .getElementsByTagName("id")
	    	                       .item(0)
	    	                       .getTextContent());
						
						BikeUsed b = new BikeUsed(idBike, getClientById(idClient));
						bikesMoving.add(b);

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
		public void printStations() {
			
			for(int i = 0; i < estacions.size(); i++) {
				System.out.println("\nEstacio " + i + ":");
				
				for(int j = 0; j < estacions.get(i).getSites().size(); j++) {
					System.out.println(estacions.get(i).getSites().get(j));
				}
			}
			
			for(int i = 0; i < bikesMoving.size(); i++) {
					System.out.println(bikesMoving.get(i).getId());
			}	
		}
		
		public boolean takeBike(Client c, int estacio, int position) {
			if(c.getBikeId() != 0) {
				System.out.println("Ja tens una bici en us");
				return false;
			}else {
			try {
				
				String filepath = "stations.xml";
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filepath);
				
				NodeList nList = doc.getElementsByTagName("Estacio");
		         
				Node nNode = nList.item(estacio);
	            
				Element eElement = (Element) nNode;
               
				NodeList nList2 = eElement.getElementsByTagName("pos");
				
				Node nNode2 = nList2.item(position);
				
				Element eElement2 = (Element) nNode2;
				int bikeRemoved = estacions.get(estacio).getSites().get(position);
				newBikeUsed(c.getId(), bikeRemoved);
				eElement2
                   .getElementsByTagName("bikeIndex")
                   .item(0)
                   .setTextContent(Integer.toString(0));
				
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(filepath));
				transformer.transform(source, result);
				estacions.get(estacio).getSites().set(position, 0);

			   } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			   } catch (TransformerException tfe) {
				tfe.printStackTrace();
			   } catch (IOException ioe) {
				ioe.printStackTrace();
			   } catch (SAXException sae) {
				sae.printStackTrace();
			   }
			return true;}
			
		}
		
		public void newBikeUsed(int idClient, int idBike) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, SAXException, IOException {
			File inputFile = new File("BikesUsed.xml");
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        Document document = db.parse(inputFile);
	        Element root = document.getDocumentElement();
	        Element bike = document.createElement("bike");

	        root.appendChild(bike);
	        Element bikeId = document.createElement("bikeIndex");
	        bikeId.appendChild(document.createTextNode(Integer.toString(idBike)));
	        bike.appendChild(bikeId);
	        
	        Element clientId = document.createElement("id");
	        clientId.appendChild(document.createTextNode(Integer.toString(idClient)));
	        bike.appendChild(clientId);
	  

	        TransformerFactory factory = TransformerFactory.newInstance();
	        Transformer transformer = factory.newTransformer();
	        DOMSource domSource = new DOMSource(document);
	        StreamResult streamResult = new StreamResult(new File("BikesUsed.xml"));
	        transformer.transform(domSource, streamResult);
	    }
		
		public boolean storeBike(Client c, int estacio, int position) {
			
			if(c.getBikeId() == 0) {
				System.out.println("No tens cap bici per deixar");
				return false;
			}else {
				
			
			try {
				
				String filepath = "stations.xml";
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filepath);
				
				NodeList nList = doc.getElementsByTagName("Estacio");
		         
				Node nNode = nList.item(estacio);
	            
				Element eElement = (Element) nNode;
               
				NodeList nList2 = eElement.getElementsByTagName("pos");
				
				Node nNode2 = nList2.item(position);
				
				Element eElement2 = (Element) nNode2;
				
				deleteIdBikeUsed(c.getBikeId());
				
				eElement2
                   .getElementsByTagName("bikeIndex")
                   .item(0)
                   .setTextContent(Integer.toString(c.getBikeId()));
				
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(filepath));
				transformer.transform(source, result);

			   } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			   } catch (TransformerException tfe) {
				tfe.printStackTrace();
			   } catch (IOException ioe) {
				ioe.printStackTrace();
			   } catch (SAXException sae) {
				sae.printStackTrace();
			   }
			estacions.get(estacio).getSites().set(position, 0);
			return true;
			}
		}
		
		public void deleteBikeUsed(int i) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, SAXException, IOException {
			File inputFile = new File("BikesUsed.xml");
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        Document document = db.parse(inputFile);
	        Element root = document.getDocumentElement();
	        
	        NodeList list = document.getElementsByTagName("bike");
	        Node node = list.item(i);
	        document.getDocumentElement().removeChild(node);

	        TransformerFactory factory = TransformerFactory.newInstance();
	        Transformer transformer = factory.newTransformer();
	        DOMSource domSource = new DOMSource(document);
	        StreamResult streamResult = new StreamResult(new File("BikesUsed.xml"));
	        transformer.transform(domSource, streamResult);
	    }
		
		public void deleteIdBikeUsed(int idBike) throws TransformerConfigurationException, ParserConfigurationException, TransformerException, SAXException, IOException {
			for(int i = 0; i < bikesMoving.size(); i++) {
				
				if(bikesMoving.get(i).getId() == idBike) {
					deleteBikeUsed(i);
				}
			}
		}
		
		public Client getClientById(int n) {
			
			for(int i = 0; i < clients.getClients().size(); i++) {
				if(clients.getClients().get(i).getId() == n) {
					return clients.getClients().get(i);
				}
			}
			return null;
		}
		
		public void agafaBici(Client clientsData, BikesData bikesData, int estacio, int posicio) {
			  
			  int n = bikesData.getEstacions().get(estacio).getSites().get(posicio);
			  if(bikesData.takeBike(clientsData, estacio, posicio)) {
				  changeUserBike(clientsData.getId(), n);
			  }
			  
		  }
		  
		
		public void guardaBici(Client clientsData, BikesData bikesData, int estacio, int posicio) {
			  
			if(bikesData.storeBike(clientsData, estacio, posicio)) {
				  changeUserBike(clientsData.getId(), 0);
			  }
			  
		}
		
		public void changeUserBike(int nClient, int idBike) {
			try {
				
				String filepath = "Clients.xml";
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filepath);
				
				NodeList nList = doc.getElementsByTagName("Client");
				
				for(int i = 0; i < nList.getLength(); i++) {
					Node n = nList.item(i);
					Element eElement = (Element) n;
					if(eElement.
							getElementsByTagName("id").
							item(0).
							getTextContent().equals(Integer.toString(nClient))) {
						eElement
			               .getElementsByTagName("bike")
			               .item(0)
			               .setTextContent(Integer.toString(idBike));
					}					
				}
				
				
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(filepath));
				transformer.transform(source, result);

			   } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			   } catch (TransformerException tfe) {
				tfe.printStackTrace();
			   } catch (IOException ioe) {
				ioe.printStackTrace();
			   } catch (SAXException sae) {
				sae.printStackTrace();
			   }
			
		}
		
		
		
			
		

}

