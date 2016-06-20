package udp.ifaces;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import udp.impl.DialogoPuerto.*;

/**
 * Interfaz del componente de comunicación. 
 * Este componente debe ser implementado por el alumno.
 * Los segmentos UDP que se intercambian las entidades tienen el siguiente formato:
 * - 1 byte: indica si es un segmento multicast o unicast. 0 unicat y 1 multicast
 * - Si el segmento es multicast, a continuación vienen 4 bytes con la IP del grupo en big endian.
 * - 1 byte indicando la longitud de los bytes que indican el alias.
 * - bytes con el alias del interlocutor
 * - por último, los bytes del mensaje.
 * @author Francisco Chicano
 * @date 12/05/2012
 *
 */

public interface Comunicacion
{
	/**
	 * Crea un socket UDP asociado al puerto indicado que se usará en toda la sesión de chat.
	 * @param puerto
	 */
	public void crearSocket(PuertoAlias pa);
	
	/**
	 * Establece el controlador para que sea posible avisar de la llegada de nuevos mensajes
	 * @param v
	 */
	public void setControlador(Controlador c);
	
	/**
	 * Ejecuta un preocso que se encarga de leer los mensajes que llegan por la red y
	 * avisar al controlador de su llegada para que muestre la información en la GUI.
	 */
	public void runReceptor();
	
	/**
	 * Envía un mensaje a una dirección de socket indicada.
	 * @param sa Dirección de socket a la que enviar el mensaje
	 * @param mensaje Mensaje a enviar
	 */
	public void envia(InetSocketAddress sa, String mensaje);
	
	/**
	 * Debe unirse al grupo multicast indicado.
	 * @param multi Dirección multicast.
	 */
	public void joinGroup(InetAddress multi);
	
	/**
	 * Debe desvincularse del grupo multicast indicado.
	 * @param multi Dirección multicast.
	 */
	public void leaveGroup(InetAddress multi);
}
