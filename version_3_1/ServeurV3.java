//package version_3_1;

import java.io.*;
import java.net.*;

	/*
	 * Ce programme est une amelioration du precedent
	 * Il a pour objectif de transmettre les messages recu a tous les clients conencter
	 * ATTENTION : seul les nouveaux elements seront commentés
	 */
public class ServeurV3 {
	
	int port;
	byte[] rep ;
	DatagramSocket sa;
	DatagramPacket pa;
	String message, messageIni;
	ChatV3 chat;
	int[] tab;			//tableau regroupant les ports des clients connectes
	int indiqTab;			//nombre de client connecte

	ServeurV3() throws UnknownHostException, SocketException, IOException{
		tab = new int[100];	//initiation du tableau a 100 personnes
		indiqTab=0;		//0 personnes sont actuellement connectee
		port=40000;
		rep = new byte[3000];
		pa = new DatagramPacket(rep, rep.length);
		sa = new DatagramSocket(32000);
	}
    
	public void connexion() throws IOException {
		while(true){
			sa.receive(pa);
			messageIni = new String(pa.getData(),0,pa.getLength() );
				if(messageIni.equals("Bonjour")) {
					System.out.println("Le client "+pa.getPort()+" est connecte");
					tab[indiqTab++]=pa.getPort();		//lorsqu'un client se connecte sont numero de port est
										//tout de suite ajouter au tableau. Plus une incrementation
										//du nombre de client connecte.
					sa.send(pa);
					message();
				}
		}
	}
    
	public void message() throws IOException {
		chat = new ChatV3(port++, pa.getPort(), this);
		chat.start();
		connexion();
	}
    
	public void fermeture() {
		sa.close();
	}
    
	/*
	 * Cette methode enleve du tableau un client qui se deconnecte
	 */
	public void fin(int port) {
		int y=0;
		for(int i=0;i<indiqTab;i++)
			if(tab[i]==port)
				y=i;
		tab[y]=tab[--indiqTab];
	}
    
	public static void main(String args[]) throws UnknownHostException, SocketException, IOException {
		ServeurV3 serveur = new ServeurV3();
		serveur.connexion();
	}
} 
