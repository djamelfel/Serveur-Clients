//package version_2;

import java.io.*;
import java.net.*;

	/*
	 * Ce programme est une amelioration du precedent
	 * Il a pour objectif de gerer plusieurs clients a la fois
	 * ATTENTION : seul les nouveaux elements seront commentés
	 */
public class ServeurV2 {
	
	int port;
	byte[] rep ;
	DatagramSocket sa;
	DatagramPacket pa;
	String message,messageIni;
	ChatV2 chat;

	ServeurV2() throws UnknownHostException, SocketException, IOException{
		port=40000;					//initialisation d'un port destine au futur client
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
					sa.send(pa);
					message();
				}
		}
	}
    
	public void message() throws IOException {
		chat = new ChatV2(this, port++, pa.getPort() );	//nous instancions un nouveau thread qui s'occupera
								//de recevoir et renvoyer les messages du client sur un nouveau port
		chat.start();					//execution du thread
		connexion();					//le thread maintenant lancé, nous attendons qu'un nouveau client se connecte
	}
    
	public void fermeture() {
		sa.close();
	}
    
	public static void main(String args[]) throws UnknownHostException, SocketException, IOException {
		ServeurV2 serveur = new ServeurV2();
		serveur.connexion();
	}
}
