//package version_1;

import java.io.*;
import java.net.*;
import java.util.*;

	/*
	 * Nous reprendrons ici le meme programme que precedement
	 * Auquel nous ajouterons la possibilite au client de 
	 * Quitter la connexion avec le serveur
	 * ATTENTION : seul les nouveaux elements seront commentés
	 */
	
public class ClientV1_2{
	
	String mot;
	byte[] msg;
	DatagramSocket s;
	DatagramPacket p;
	boolean finProg;
	int portServ;
	Scanner sc;

	ClientV1_2(int portS, int portC) throws IOException, SocketException, UnknownHostException {
		finProg=false;
		mot = "Bonjour";
		msg = mot.getBytes();
		portServ=portS;
		s = new DatagramSocket(31000);		
		sc = new Scanner(System.in);
	}

	public void connexion() throws IOException, SocketException, UnknownHostException{
		p = new DatagramPacket(msg, msg.length, InetAddress.getByName("localhost"), portServ);
		s.send(p);
		s.receive(p);
		mot = new String(p.getData(),0,p.getLength() );
		if(mot.equals("Bonjour"))
			envoiMes();
		else
			System.out.print("La connexion a echoue");
	}
	/*
	 * Nouvelle méthode qui fermera la connexion avec le serveur
	 */
	public void fin() {
		s.close();						//ferme le port ouvert
		finProg=true;						//change le statut de la variable test
		System.out.println("Vous etes bien deconnecté");	//Informe le client de la deconnection
	}
    
	public void envoiMes(){
		while(finProg!=true){	//Tant que la variable test sera faux alors on reste dans la boucle
			try {
				System.out.print("Saisissez votre message : ");
				mot = sc.nextLine();
				msg= mot.getBytes();
				p = new DatagramPacket(msg, msg.length, InetAddress.getByName("localhost"), portServ);
				s.send(p);
				if(mot.equals("/quit") )		//test de la chaine entree par le client si celle ci est : /quit alors
					fin();				//on appel la methode fin()
				else {					//sinon nous attendons la reponse du serveur
					s.receive(p);
					mot = new String(p.getData(),0,p.getLength() );
					System.out.println("chaine recu du serveur : " + mot );
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    
	public static void main(String args[]) throws IOException{
	
		ClientV1_2 client;
		if (args.length != 2)
			System.out.println("Aide : Veuillez entrer l'adresse du serveur : \"32000\", ainsi que le port client");
		else {
			client = new ClientV1_2(Integer.parseInt(args[0]),Integer.parseInt(args[1]));	//appel du constructeur
			client.connexion();					//lancement du programme
		}
	}
}
