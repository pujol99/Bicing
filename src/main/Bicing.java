package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.awt.Font;
import java.awt.Label;
import java.awt.Button;
import java.awt.TextField;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import Usuaris.*;
import bikes.BikesData;

public class Bicing {

	private JFrame frame;
	
	public static void main(String[] args) {
		Clients clientsData = new Clients();
		  
		initializeDataBase(clientsData);
		  
		BikesData bikesData = new BikesData(clientsData);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bicing window = new Bicing(clientsData, bikesData);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	public Bicing(Clients clientsData, BikesData bikesData) {
		initialize(clientsData, bikesData);
	}

	private void initialize(Clients clientsData, BikesData bikesData) {
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblBank = new JLabel("BICING");
		lblBank.setForeground(Color.BLUE);
		lblBank.setBackground(Color.WHITE);
		lblBank.setIcon(new ImageIcon(Bicing.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		lblBank.setFont(new Font("OCR A Extended", Font.PLAIN, 17));
		lblBank.setHorizontalAlignment(SwingConstants.CENTER);
		lblBank.setBounds(64, 11, 146, 41);
		frame.getContentPane().add(lblBank);
		
		Label label = new Label("Client nou? Registra't");
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		label.setAlignment(Label.CENTER);
		label.setBounds(50, 58, 172, 24);
		frame.getContentPane().add(label);
		
		Button crearCompte = new Button("NOU COMPTE");
		crearCompte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				Registre registre = new Registre(clientsData, bikesData);
				registre.setVisible(true);
				frame.setVisible(false);
				
				
			}
		});
		crearCompte.setBounds(93, 88, 93, 41);
		frame.getContentPane().add(crearCompte);
		
		Label label_1 = new Label("Ja tens un compte?");
		label_1.setFont(new Font("Arial", Font.PLAIN, 14));
		label_1.setAlignment(Label.CENTER);
		label_1.setBounds(50, 178, 172, 24);
		frame.getContentPane().add(label_1);
		
		Label label_2 = new Label("Inicia Sessio!");
		label_2.setFont(new Font("Arial", Font.PLAIN, 14));
		label_2.setAlignment(Label.CENTER);
		label_2.setBounds(50, 198, 172, 24);
		frame.getContentPane().add(label_2);
		
		TextField mailBox = new TextField();
		mailBox.setBounds(102, 228, 172, 32);
		frame.getContentPane().add(mailBox);
		
		TextField passwordBox = new TextField();
		passwordBox.setBounds(102, 266, 172, 32);
		frame.getContentPane().add(passwordBox);
		
		Label label_3 = new Label("mail");
		label_3.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_3.setAlignment(Label.CENTER);
		label_3.setBounds(34, 228, 62, 32);
		frame.getContentPane().add(label_3);
		
		Label label_4 = new Label("password");
		label_4.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_4.setAlignment(Label.CENTER);
		label_4.setBounds(10, 266, 86, 32);
		frame.getContentPane().add(label_4);
		
		Button Entrar = new Button("ENTRA!");
		Entrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(clientsData.iniciSessio(mailBox.getText(), passwordBox.getText())) {
					clientsData.setClientActual(clientsData.getClient(mailBox.getText()));
					pantalla p = new pantalla(clientsData.getClientActual(), bikesData);
					p.setVisible(true);
					
				}
				
			}
		});
		Entrar.setForeground(Color.BLACK);
		Entrar.setBounds(93, 310, 93, 41);
		frame.getContentPane().add(Entrar);
		
		Label misstageError = new Label("");
		misstageError.setBounds(212, 200, 62, 22);
		frame.getContentPane().add(misstageError);
	}



private static void initializeDataBase(Clients data) {
		
		String filePath = "Clients.xml";
	    File xmlFile = new File(filePath);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder;
	    try {
	        dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(xmlFile);
	        doc.getDocumentElement().normalize();            
	        NodeList nodeList = doc.getElementsByTagName("Client");
	        for (int i = 0; i < nodeList.getLength(); i++) {
	            data.getClients().add(getClient(nodeList.item(i)));
	        }
	    } catch (SAXException | ParserConfigurationException | IOException e1) {
	        e1.printStackTrace();
	    }	
		
		
	}

   private static Client getClient(Node node) {
	    
	    Client c = new Client();
	    if (node.getNodeType() == Node.ELEMENT_NODE) {
	        Element element = (Element) node;
	        c.setId(Integer.parseInt(getTagValue("id", element)));
	        c.setMail(getTagValue("mail", element));
	        c.setContrasenya(getTagValue("contrasenya", element));
	        c.setBikeId(Integer.parseInt(getTagValue("bike", element)));
	    }
	    return c;
	}

   private static String getTagValue(String tag, Element element) {
	    NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
	    Node node = (Node) nodeList.item(0);
	    return node.getNodeValue();
	}


}