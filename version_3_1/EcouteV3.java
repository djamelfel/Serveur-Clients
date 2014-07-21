//package version_3_1;

import java.io.*;
import java.net.*;

	/*
	 * La Classe EcouteV3 est utilise afin d'attendre la reception d'un message du serveur
	 */
public class EcouteV3 extends Thread{
	
	DatagramSocket s;
	DatagramPacket p;
	String message;
	byte[] msg;
	
	public EcouteV3(int portC) throws UnknownHostException, SocketException, IOException{
		int i = portC+1;
		msg = new byte[3000];
		s = new DatagramSocket(i);		//ouverture d'un nouveau port qui recevra
							//tous les messages des clients connectes
		p = new DatagramPacket(msg, msg.length);
	}
	
	/*
	 * Cette methode ferme le port ouvert par la classe
	 */
	public void fin() {
		s.close();
	}
	
	public void run() {
		while(true){
			try {
				s.receive(p);
				message = new String(p.getData(),0,p.getLength() );
				System.out.println( message );
			}
			catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
