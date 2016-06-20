package udp.impl;

import udp.ifaces.Comunicacion;
import udp.ifaces.Controlador;
import udp.impl.DialogoPuerto.*;

import java.io.IOException;
import java.net.*;
import java.nio.channels.MulticastChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Implementación de prueba del componente de comunicación.
 *
 * @author Francisco Chicano
 * @date 15/05/2015
 */

public class ComunicacionImpl implements Comunicacion {
    private Controlador c;
    private DatagramSocket socks;
    private DatagramPacket packetIn, packetOut;
    private PuertoAlias puertoAlias;
    private MulticastSocket multicastSocks;


    @Override
    public void crearSocket(PuertoAlias pa) {
        puertoAlias = pa;
        try {
            socks = new DatagramSocket(puertoAlias.puerto);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setControlador(Controlador c) {
        this.c = c;
    }

    @Override
    public void runReceptor() {
        try {
            packetIn = new DatagramPacket(new byte[1500], 1500);
            socks.receive(packetIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cadena = new String(packetIn.getData(), Charset.forName("UTF-8"));
        c.mostrarMensaje(socks.getLocalSocketAddress(), puertoAlias.alias, cadena);

        // Ejemplo de uso de los métodos para transformar cadenas de caracteres en arrays de bytes y viceversa
        /*
        byte [] data = cadena.getBytes(Charset.forName("UTF-8"));
		String msg = new String(data, Charset.forName("UTF-8"));
		assert cadena.equals(msg);
		 */

    }

    @Override
    public void envia(InetSocketAddress sa, String mensaje) {
        byte[] cadena = mensaje.getBytes(Charset.forName("UTF-8"));
        packetOut = new DatagramPacket(cadena, cadena.length, sa.getAddress(), sa.getPort());
        try {
            socks.send(packetOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void joinGroup(InetAddress multi) {
        try {
            multicastSocks = new MulticastSocket(puertoAlias.puerto);
            multicastSocks.joinGroup(multi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void leaveGroup(InetAddress multi) {
        try {
            multicastSocks.leaveGroup(multi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
