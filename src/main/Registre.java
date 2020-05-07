package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Usuaris.Client;
import Usuaris.Clients;
import bikes.BikesData;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.awt.event.ActionEvent;

public class Registre extends JFrame {
	
	private JPanel contentPane;
	private JTextField mailBox;
	private JTextField passBox;
	private JTextField nTargetaBox;
	private JTextField pinBox;

	public Registre(Clients clientsData, BikesData bikesData) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		mailBox = new JTextField();
		mailBox.setColumns(10);
		mailBox.setBounds(321, 77, 153, 26);
		contentPane.add(mailBox);
		
		passBox = new JTextField();
		passBox.setColumns(10);
		passBox.setBounds(321, 114, 153, 26);
		contentPane.add(passBox);
		
		JLabel error = new JLabel("");
		error.setBounds(101, 183, 164, 85);
		contentPane.add(error);
		
		
		JLabel lblMail = new JLabel("mail");
		lblMail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMail.setBounds(274, 75, 37, 26);
		contentPane.add(lblMail);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(242, 114, 69, 26);
		contentPane.add(lblPassword);
		
		JButton btnResgistrarte = new JButton("RESGISTRAR'TE");
		btnResgistrarte.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {

				if(clientsData.getClient(mailBox.getText()) != null) {
					error.setText("aquest mail ja s'ha utilitzat");
				}else {
					
					Random rand = new Random();

					int n = rand.nextInt(100000) + 1;
				
					Client nou = clientsData.addClient(n, mailBox.getText(), passBox.getText());
					try {
						createClient(nou);
					} catch (Exception e1) {
					
						e1.printStackTrace();
					}
					pantalla p = new pantalla(clientsData.getClient(mailBox.getText()), bikesData);
					p.setVisible(true);
				}

		}
		});
		btnResgistrarte.setBounds(334, 297, 140, 53);
		contentPane.add(btnResgistrarte);
		
		
	}
	
public static void createClient(Client c) throws Exception{
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("Clients.xml");
        Element root = document.getDocumentElement();

        Collection<Client> servers = new ArrayList<Client>();
        servers.add(c);

        for (Client server : servers) {
            // server elements
            Element newServer = document.createElement("Client");

            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(Integer.toString(server.getId())));
            newServer.appendChild(id);
            
            Element mail = document.createElement("mail");
            mail.appendChild(document.createTextNode(server.getMail()));
            newServer.appendChild(mail);
            
            Element contrasenya = document.createElement("contrasenya");
            contrasenya.appendChild(document.createTextNode(server.getContrasenya()));
            newServer.appendChild(contrasenya);
            
            Element bikeId = document.createElement("bike");
            bikeId.appendChild(document.createTextNode(Integer.toString(server.getBikeId())));
            newServer.appendChild(bikeId);

            

            root.appendChild(newServer);
        }

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult("Clients.xml");
        transformer.transform(source, result);
		
		
		
	}
}