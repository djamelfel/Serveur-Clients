package version_3_2;

import java.io.*;
import java.net.*;

	/*
	 * Ce programme est une amelioration du precedent
	 * Il a pour objectif de transmettre les messages recus a tous les clients connectes
	 * ATTENTION : seul les nouveaux elements seront commentés
	 */
public class ServeurV3 {
	
	int port;
	byte[] rep ;
	DatagramSocket sa;
	DatagramPacket pa;
	String message, messageIni;
	ChatV3 chat;
	int[] tab;
	String[] nom;			//tableau regroupant les noms des clients (ils auront le meme indice que le tableau de port)
	int indiqTab;

	ServeurV3() throws UnknownHostException, SocketException, IOException{
		tab = new int[100];
		indiqTab=0;
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
					tab[indiqTab]=pa.getPort();
					sa.send(pa);
					sa.receive(pa);	//recoit le nom du client
					nom[indiqTab]=new String(pa.getData(),0,pa.getLength() ); //enregistre le nom du client
					System.out.println("Le client "+nom[indiqTab++]+" est connecte");
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
		tab[y]=tab[indiqTab];
		nom[y]=nom[--indiqTab];
	}
    
	public static void main(String args[]) throws UnknownHostException, SocketException, IOException {
		ServeurV3 serveur = new ServeurV3();
		serveur.connexion();
	}
} 
