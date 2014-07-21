//package version_2;

import java.io.*;
import java.net.*;
import java.util.*;
import version_1.ClientV1_1;

	/*
	 * Ce programme est le meme que precedement (ClientV1_2.java)
	 */
public class ClientV2{
	
	String mot;
	byte[] msg;
	DatagramSocket s;
	DatagramPacket p;
	boolean finProg;
	int portServ, portClient;
	Scanner sc;

	ClientV2(int portS, int portC) throws IOException, SocketException, UnknownHostException{
		finProg=false;
		mot = "Bonjour";
		msg = mot.getBytes();
		portServ=portS;
		portClient=portC;
		sc = new Scanner(System.in);
		s = new DatagramSocket(portClient);		
	}

	public void connexion() throws IOException, SocketException, UnknownHostException{
		p = new DatagramPacket(msg, msg.length, InetAddress.getByName("localhost"), portServ);
		s.send(p);
		s.receive(p);
		mot = new String(p.getData(),0,p.getLength() );
		if(mot.equals("Bonjour")){
			s.receive(p);
			portServ = p.getPort();
			envoiMes();
		}
	}

	public void fin() {
		s.close();
		finProg=true;
		System.out.println("Vous etes bien deconnecté");
	}
    
	public void envoiMes(){
		while(finProg!=true){
			try {
				System.out.print("Saisissez votre message : ");
				mot = sc.nextLine();
				msg= mot.getBytes();
				p = new DatagramPacket(msg, msg.length, InetAddress.getByName("localhost"), portServ);
				s.send(p);
				if(mot.equals("/quit") )
					fin();
				else {
					s.receive(p);
					mot = new String(p.getData(),0,p.getLength() );
					System.out.println("Chaine recue du serveur : " + mot );
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) throws IOException{
	
		ClientV2 client;
		if (args.length != 2)
			System.out.println("Aide : Veuillez entrer l'adresse du serveur : \"32000\", ainsi que le port client");
		else {
			client = new ClientV2(Integer.parseInt(args[0]),Integer.parseInt(args[1]));	//appel du constructeur
			client.connexion();					//lancement du programme
		}
	}
}
