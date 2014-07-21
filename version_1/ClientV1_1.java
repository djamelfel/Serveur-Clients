//package version_1;

import java.io.*;
import java.net.*;
import java.util.*;

	/*
	 * L'objectif est que le client puisse se connecter au serveur
	 * Il ecrira des messages au serveur
	 * Qui lui lui renverra
	 */

public class ClientV1_1{
	
	String mot;
	byte[] msg;
	DatagramSocket s;
	DatagramPacket p;
	int portServ,portClient;
	Scanner sc ;

	/*
	 * Constructeur qui initialise les variables
	 */
	ClientV1_1(int portS, int portC) throws IOException, SocketException, UnknownHostException{
		mot = "Bonjour";	//Chaine envoyee au serveur pour initialiser la connexion
		msg = mot.getBytes();	//convertir la chaine Bonjour en byte
		portClient=portC;
		portServ=portS;		//L'adresse du serveur entree en argument est stocker dans la variable
		s = new DatagramSocket(portClient);		//ouverture du port 31000
		sc = new Scanner(System.in);		//Preparation a la saisie de caractere
	}

	/*
	 * Gere la connexion au serveur
	 */
	public void connexion() throws IOException, SocketException, UnknownHostException{
		//preparation du packet destiner au serveur contenant la chaine d'initialisation
		p = new DatagramPacket(msg, msg.length, InetAddress.getByName("localhost"), portServ);
		s.send(p);		//envoie de la chaine
		s.receive(p);		//reception du message d'initialisation du serveur
		mot = new String(p.getData(),0,p.getLength() );	//Converti en texte l'element recu
		if(mot.equals("Bonjour"))	//Si le serveur nous a renvoye notre message alors on continue
			envoiMes();
		else			//sinon message d'erreur
			System.out.print("La connexion a echoue");
	}
	
	/*
	 * La connexon maintenant etabli, nous pouvons envoyer nos messages
	 */
	public void envoiMes(){
		while(true){
			try {
				System.out.print("Saisissez votre message : ");	//Demande de saisi du message
				mot = sc.nextLine();		//enregistrement de la saisie dans mot
				msg= mot.getBytes();		//convertir en byte le message precedement saisi
				//preparation du nouveau packet
				p = new DatagramPacket(msg, msg.length, InetAddress.getByName("localhost"), portServ);
				s.send(p);			//envoi du packet par notre port ouvert
				s.receive(p);			//attente de la reception de la reponse du serveur
				mot = new String(p.getData(),0,p.getLength() );	//convertir en texte l'element recu
				System.out.println("chaine recue du serveur : " + mot );		//affiche la chaine recue
			} catch (IOException e) {		//gere les erreurs
				e.printStackTrace();
			}
		}
	}
    
	public static void main(String args[]) throws IOException{
	
		ClientV1_1 client;
		if (args.length != 2)
			System.out.println("Aide : Veuillez entrer l'adresse du serveur : \"32000\", ainsi que le port client");
		else {
			client = new ClientV1_1(Integer.parseInt(args[0]),Integer.parseInt(args[1]));	//appel du constructeur
			client.connexion();					//lancement du programme
		}
	}
}
