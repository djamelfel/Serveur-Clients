//package version_2;

import java.io.*;
import java.net.*;
import java.util.*;

	/*
	 * Ce programme est le meme que ClientV2_1.java
	 * A l'exception du port du Client, il a du etre modifier
	 * Afin de simuler correctement l'arrivee d'un nouveau client
	 */
public class ClientV2_2{
	
	String mot;
	byte[] msg;
	DatagramSocket s;
	DatagramPacket p;
	boolean finProg;
	int portServ;
	Scanner sc;

	ClientV2_2(int portS) throws IOException, SocketException, UnknownHostException {
		finProg=false;
		mot = "Bonjour";
		msg = mot.getBytes();
		portServ=portS;
		sc = new Scanner(System.in);
		s = new DatagramSocket(33000);		
	}

	public void connexion() throws IOException, SocketException, UnknownHostException {
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
					System.out.println("chaine recue du serveur : " + mot );
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) throws IOException {
	
		ClientV2_2 client;
			if (args.length != 1)
				System.out.println("Aide : Veuillez entrer l'adresse du serveur : \"32000\"");
			else {
				client = new ClientV2_2(Integer.parseInt(args[0]));
				client.connexion();   
		 }
	}
}
