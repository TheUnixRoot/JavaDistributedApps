package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
	public static void main(String[] args) {
		Scanner inCliente = null;
		PrintWriter outCliente = null;
		Socket cliente = null;
		ServerSocket servidor = null;
		try {
			int puerto = Integer.parseInt(args[0]);
			servidor = new ServerSocket(puerto, 1);
            System.out.println("Servidor conectado");
			while (true) {
				cliente = servidor.accept();
				boolean conected = true;
                System.out.println("Cliente conectado");
				inCliente = new Scanner(cliente.getInputStream());
				outCliente = new PrintWriter(cliente.getOutputStream());
				while (conected) {
					System.out.println("Conectado, esperando al cliente...");
					try {
						cliente.setSoTimeout(40000);
						String cadena = inCliente.nextLine();
						
						System.out.println("Recibido: "+cadena);
						
						if (cadena.compareTo("TERMINAR") == 0) {
							conected = false;
							outCliente.println("OK");
						} else {
							StringBuilder strb = new StringBuilder("");
							char[] arr = cadena.toCharArray();
							for (int i = arr.length-1; i >= 0; i--) {
								strb.append(arr[i]);
							}
							cadena = strb.toString();
							
							System.out.println("Enviando: "+cadena);
							
							outCliente.println(cadena);
							outCliente.flush();
						}
					} catch (SocketException sk) {
                        conected = false;
                        System.out.println("Cliente desconectado por timeout");
                    } catch (NoSuchElementException elem){
                        conected = false;
                        System.out.println("Cliente desconectado");
                    }
				}
				cliente.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			inCliente.close();
			outCliente.close();
		}
	}
}
