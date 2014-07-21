package version_3_2;

import java.io.*;
import java.net.*;

	/*
	 * Ce programme est une amelioration du precedent
	 * Il a pour objectif d'envoyer les messages recu a tout les clients
	 * ATTENTION : seul les nouveaux elements seront commentés
	 */
public class ChatV3 extends Thread{
	
	ServeurV3 serveur;
	DatagramSocket sa;
	DatagramPacket pa;
	String message;
	byte[] rep;
	
	public ChatV3(int portServ, int portClient, ServeurV3 serv) throws UnknownHostException, SocketException, IOException{
		serveur=serv;
		rep = new byte[3000];
		this.sa= new DatagramSocket(portServ);
		pa = new DatagramPacket(rep, rep.length, InetAddress.getByName("localhost"), portClient);
		sa.send(pa);
	}
	
	public void run() {
		while(true){
			try{
				sa.receive(pa);
				message = new String(pa.getData(),0,pa.getLength() );
				if(message.equals("/quit")) {
					System.out.println("Le client "+pa.getPort()+" a quite");
					serveur.fin(pa.getPort());
					this.stop();
				}
				else {
					//recoit port parcours tableau de port pour trouver identifiant
					int var=-1;
					for(int i=0;i<serveur.indiqTab;i++)
						if(pa.getPort()==serveur.tab[i])
							var=i;
					System.out.println("Chaine envoiee par le client "+ serveur.nom[var] +" : " + message );
					message = serveur.nom[var] + " : " + message ;
					rep = message.getBytes();
					pa.setData(rep);
					pa.setLength(rep.length);
					for(int i=0;i<serveur.indiqTab;i++) {
						pa.setPort(serveur.tab[i]+1);
						sa.send(pa);
					}
				}
			}
			catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
