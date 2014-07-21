//package version_1;

import java.io.*;
import java.net.*;

	/*
	 * Nous avons améliorer le serveur afin qu'il sache qu'un client deconnecte
	 * La connection afin qu'il puisse se mettre de nouveau a attendre
	 * ATTENTION : seul les nouveaux elements seront commentés
	*/


public class ServeurV1_2 {
	
	byte[] rep ;
	DatagramSocket sa;
	DatagramPacket pa;
	String message,messageIni;

	ServeurV1_2() throws UnknownHostException, SocketException, IOException{
		rep = new byte[3000];
		pa = new DatagramPacket(rep, rep.length);
		sa = new DatagramSocket(32000);
	}
    
	public void connexion() throws IOException {
		sa.receive(pa);
		messageIni = new String(pa.getData(),0,pa.getLength() );
		if(messageIni.equals("Bonjour")) {
			System.out.println("Le client est connecte");
			sa.send(pa);
			message();
		}
	}
    
	public void message() throws IOException {
		while(true) {
			sa.receive(pa);
			message = new String(pa.getData(),0,pa.getLength() );
			if(message.equals("/quit")){		//test du message recu si c'est /quit alors
				    System.out.println("le client a quitter");	//le serveur affiche que le client a quitter
				    connexion();		//appel de la fonction connexion()
			}
			else {
				    System.out.println("Chaine envoiee par le client : " + message );
				    sa.send(pa);
			}
		}
	}
    
	public void fermeture() {
		sa.close();
	}
    
	public static void main(String args[]) throws UnknownHostException, SocketException, IOException {
		ServeurV1_2 serveur = new ServeurV1_2();
		serveur.connexion();
	}
}
