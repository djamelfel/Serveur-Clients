//package version_3_1;

import java.io.*;
import java.net.*;
import java.util.*;
	
	/*
	 * Ce programme est le meme que ClientV3_1.java
	 * A l'exception du port du Client, il a du etre modifier
	 * Afin de simuler correctement l'arriver d'un nouveau client
	 */
public class ClientV3_2{
	
	String mot;
	byte[] msg;
	DatagramSocket s;
	DatagramPacket p;
	boolean finProg;
	int portClient, portServ;
	Scanner sc ;
	EcouteV3 ecoute;

	ClientV3_2(int portS) throws IOException, SocketException, UnknownHostException{
		finProg=false;
		portClient = 33000;
		mot = "Bonjour";
		msg = mot.getBytes();
		portServ=portS;
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
			ecoute();
			envoiMes();
		}
	}
    
	public void fin() {
		s.close();
		ecoute.stop();
		finProg=true;
		System.out.println("Vous etes bien deconnecte");
	}
    
	public void ecoute() throws IOException{
		ecoute = new EcouteV3(portClient) ;
		ecoute.start();
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
	
		ClientV3_2 client;
		if (args.length != 1)
			System.out.println("Aide : Veuillez entrer l'adresse du serveur : \"32000\"");
		else {
			client = new ClientV3_2(Integer.parseInt(args[0]));
			client.connexion();
		}
	}   
}
