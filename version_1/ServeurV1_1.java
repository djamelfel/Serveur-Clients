//package version_1;

import java.io.*;
import java.net.*;

	/*
	 * L'objectif est qu'un client puisse se connecter au serveur
	 * Une fois le client connecter nous lui renverrons les messages recus
	 */

public class ServeurV1_1 {
	
	byte[] rep ;
	DatagramSocket sa;
	DatagramPacket pa;
	String message,messageIni;

	/*
	 * Constructeur qui initialise les variables
	 */
	ServeurV1_1() throws UnknownHostException, SocketException, IOException{
		rep = new byte[3000];				//initialisation d'un tableau de byte qui recevra des bytes
		pa = new DatagramPacket(rep, rep.length);	//initialisation d'un packet
		sa = new DatagramSocket(32000);			//ouverture d'un port le 32000
	}
    
	/*
	 * Methode qui initialise la connexion avec le serveur
	 */
	public void connexion() throws IOException {
		sa.receive(pa);					//attente d'un messagge provenant du serveur
		messageIni = new String(pa.getData(),0,pa.getLength() );	//transforme le message de byte en texte
		if(messageIni.equals("Bonjour")) {		//si le message est Bonjour alors
			System.out.println("Le client est connecte");		//on affiche qu'un client est connecter
			sa.send(pa);				//on envoie au client la chaine Bonjour pour confirmation
			message();				//appel de la methode message
		}
	}
	
	/*
	 * Methode qui permet l'envoi et la reception des messages avec le client
	 */
	public void message() throws IOException {
		while(true) {					//afin que le serveur tourne toujours
			sa.receive(pa);				//attente d'un message provenant du serveur
			message = new String(pa.getData(),0,pa.getLength() );	//transforme le message de byte en texte
			System.out.println("Chaine envoiee par le client : " + message );	//affiche le message
			sa.send(pa);				//renvoi la chaine recue au client
		}
	}
    
	public void fermeture() {
		sa.close();
	}
    
	public static void main(String args[]) throws UnknownHostException, SocketException, IOException {
		ServeurV1_1 serveur = new ServeurV1_1();
		serveur.connexion();
	}
}
