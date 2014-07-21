//package version_3_1;

import java.io.*;
import java.net.*;
import java.util.*;
import version_1.ClientV1_1;

	/*
	 * Nous reprendrons ici le meme programme que precedement
	 * Auquel nous ajouterons la possibilite au client d'être 
	 * constament en ecoute et pouvoir ecrire en meme temps.
	 * ATTENTION : seul les nouveaux elements seront commentés
	 */
public class ClientV3{
	
	String mot;
	byte[] msg;
	DatagramSocket s;
	DatagramPacket p;
	boolean finProg;
	Scanner sc ;
	int portServ, portClient;
	EcouteV3 ecoute;

	ClientV3(int portS, int portC) throws IOException, SocketException, UnknownHostException{
		finProg=false;
		mot = "Bonjour";
		portClient=portC;			//nouvelle variabe qui sera utiliser par la classe Ecoute
		msg = mot.getBytes();
		portServ=portS;
		sc = new Scanner(System.in) ;
		s = new DatagramSocket(portClient);
	}

	public void connexion() throws IOException {
		p = new DatagramPacket(msg, msg.length, InetAddress.getByName("localhost"), portServ);
		s.send(p);
		s.receive(p);
		mot = new String(p.getData(),0,p.getLength() );
		if(mot.equals("Bonjour")){
			s.receive(p);
			portServ = p.getPort();
			ecoute();			//la connexion maintenant etabli nous fesons appel a la methode ecoute.
			envoiMes();
		}
	}
	
	public void fin() {
		s.close();
		ecoute.fin();				//fermeture du port ouvert par le thread
		ecoute.stop();				//fermeture de thread lance
		finProg=true;
		System.out.println("Vous etes bien deconnecté");
	}
    
	/*
	 * La methode ecoute a pour objectif de creer et lancer un thread qui sera a l'ecoute du serveur
	 * Laissant l'utilisateur la possibilite d'envoyer un message
	 */
	public void ecoute() throws IOException{
		ecoute = new EcouteV3(portClient) ;	//nous instancions un nouveau thread
		ecoute.start();				//le thread maintenant lancé, nous attendons que le client saisisse son message
	}
    
	public void envoiMes(){
		while(finProg!=true){
			try {
				mot = sc.nextLine();
				msg= mot.getBytes();
				p = new DatagramPacket(msg, msg.length, InetAddress.getByName("localhost"), portServ);
				s.send(p);
				if(mot.equals("/quit") )
					fin();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    
	public static void main(String args[]) throws IOException{
	
		ClientV3 client;
		if (args.length != 2)
			System.out.println("Aide : Veuillez entrer l'adresse du serveur : \"32000\", ainsi que le port client");
		else {
			client = new ClientV3(Integer.parseInt(args[0]),Integer.parseInt(args[1]));	//appel du constructeur
			client.connexion();					//lancement du programme
		}
	}
}
