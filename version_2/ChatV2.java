//package version_2;

import java.io.*;
import java.net.*;

/*
 * La classe ChatV2 se lancera chaque fois qu'un client est connecte
 * Afin que le meme dialogue que precedement puisse se faire
 * Tout en laissant au serveur l'opportunite d'attendre un nouveau client
 */
public class ChatV2 extends Thread{
	
	ServeurV2 serveur;
	DatagramSocket sa;
	DatagramPacket pa;
	String message;
	byte[] rep;
	
	/*
	 * Constructeur initialisant les variables
	 */
	public ChatV2(ServeurV2 serv, int portServ, int portClient ) throws UnknownHostException, SocketException, IOException{
		serveur = serv;
		rep = new byte[3000];			//initialisation d'un tableau de byte qui recevra des bytes
		sa= new DatagramSocket(portServ);	//ouverture d'un nouveau port exclusif pour le client
		pa = new DatagramPacket(rep, rep.length, InetAddress.getByName("localhost"), portClient);	//initialisation d'un packet
		sa.send(pa);
	}
	
	/*
	 * Cette methode est appellee grace a la methode start()
	 * A la meme utilite que dans le programme Serveur1_2.java
	 */
	public void run() {
		while(true){
			try{
				sa.receive(pa);
				message = new String(pa.getData(),0,pa.getLength() );
				if(message.equals("/quit")){
					System.out.println("Le client "+pa.getPort()+" a quitte");
					this.stop();
				}
				else {
					System.out.println("Chaine envoiee par le client "+pa.getPort()+" : " + message );
					sa.send(pa);
				}
			}
			catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
