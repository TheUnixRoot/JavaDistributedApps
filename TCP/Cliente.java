package Client;

import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente {
	public static void main(String[] args) {
		System.out.println("Cliente conectado");
		Scanner inSocks = null, inConsole = null;
		PrintWriter outSocks = null;
		Socket socks = null;
		try {
			String ipServer = args[0];
			int puerto = Integer.parseInt(args[1]);

			socks = new Socket(InetAddress.getByName(ipServer), puerto);
            System.out.printf("Conectado a %s en el puerto %d\n", ipServer, puerto);
            outSocks = new PrintWriter(socks.getOutputStream());
			inSocks = new Scanner(socks.getInputStream());
			inConsole = new Scanner(System.in);

			boolean conected = true; // como cierro?
			String cadena;
            while (conected) {
                System.out.println("Introduzca texto para enviar:");
                cadena = inConsole.nextLine();
				if (cadena.compareTo("TERMINAR") == 0) {
					conected = false;
                    System.out.println("Enviado mensaje de fin de conexion, esperando servidor");
					outSocks.println(cadena);
                    outSocks.flush();
                } else {
                    System.out.println("Enviado mensaje, esperando servidor");
					outSocks.println(cadena); // peta??
					outSocks.flush();
					System.out.println(inSocks.nextLine());
                }

			}
			String oki = inSocks.nextLine();
			while(oki.compareTo("OK") != 0)	{
                oki = inSocks.nextLine();
                System.out.println(oki);
            }
			System.out.println("Conexion cerrada");
			socks.close();
			
		} catch(ConnectException coe){
            System.out.println("ERROR, SERVIDOR NO ENCONTRADO O CONEXION RECHAZADA");
        } catch (NoSuchElementException elem){
			try{
				socks.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
        }catch (UnknownHostException unk) {
			unk.printStackTrace();
		} catch (NumberFormatException num) {
			num.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			// TODO Close all
			inSocks.close();
			inConsole.close();
			outSocks.close();
			System.out.println("Cliente finalizado");
		}
	}

}
