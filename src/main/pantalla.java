package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Usuaris.Client;
import Usuaris.Clients;
import bikes.BikesData;

import java.awt.Choice;
import java.awt.Label;
import java.awt.Font;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class pantalla extends JFrame {

	private JPanel contentPane;

	
	public pantalla(Client clientsData, BikesData bikesData) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 514, 313);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Choice choice = new Choice();
		choice.setBounds(49, 70, 406, 45);
		contentPane.add(choice);
		
		for(int i = 0; i < bikesData.getEstacions().size(); i++) {
			choice.add(Integer.toString(bikesData.getEstacions().get(i).getId()));
		}
		
		JLabel errorMsg = new JLabel("");
		errorMsg.setForeground(Color.RED);
		errorMsg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		errorMsg.setHorizontalAlignment(SwingConstants.CENTER);
		errorMsg.setBounds(115, 205, 284, 41);
		contentPane.add(errorMsg);
		
		Label label = new Label("ESCULL ESTACIO:");
		label.setFont(new Font("Century", Font.PLAIN, 18));
		label.setAlignment(Label.CENTER);
		label.setBounds(173, 10, 153, 54);
		contentPane.add(label);
		
		JButton btnDacord = new JButton("D'ACORD");
		btnDacord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(clientsData.getBikeId() == 0) {
					int n = bikesData
					.getEstacions()
					.get(choice.getSelectedIndex())
					.takeBike();
					if(n == -1) {
						errorMsg.setText("ESTACIO BUIDA PROVA UNA ALTRE");
					}else {
						bikesData.agafaBici(clientsData, bikesData, choice.getSelectedIndex(), n);
					}
				}else{
					int n = bikesData
					.getEstacions()
					.get(choice.getSelectedIndex())
					.parkBike();
					if(n == -1) {
						errorMsg.setText("ESTACIO PLENA PROVA UNA ALTRE");
					}else {
						bikesData.guardaBici(clientsData, bikesData, choice.getSelectedIndex(), n);
					}
				}
				
			}
		});
		btnDacord.setBounds(158, 121, 190, 41);
		contentPane.add(btnDacord);
		
		
		
		
			
		
		
		
	}
}
