/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reta2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author killito
 */
public class Subir extends Thread{
    private int completado;
    private JButton boton;
    Vector<Object> cadenas;
    public Subir(JButton boton,Vector<Object> cadenas){
        super();
        completado=0;
        this.boton = boton;
        this.cadenas = cadenas;
    }
    
        public synchronized int getCompletado() {
        return completado;
    }
    
    public synchronized void setCompletado(int c){
        completado = c;
    }
    
    
    private String IpMovil(){
        ServerSocket socket; 
        String retorna = "", retornaAux="";
        System.out.println("Se busca la ip del movil");
        try {
            InetAddress localHost = InetAddress.getByName("debian.local");
            System.out.println(localHost.getHostName());
            System.out.println(localHost.getHostAddress());
            socket = new ServerSocket(6000);
            System.out.println("Esperando a que el movil envie señal, la ip del pc es: "
                        + localHost.getHostAddress());
            Socket socket_cli = socket.accept();
            System.out.println("Señal recibida del movil");
            retorna = socket_cli.getInetAddress().toString();
            socket_cli.close();
            socket.close();
        }
        catch (Exception e) {

        // si existen errores los mostrará en la consola y después saldrá del
        // programa
        //aqui habria que eliminar el fichero
         System.err.println(e.getMessage());
         System.exit(1);
         return "";
        }
        for(int i=1;i<retorna.length();i++){
            retornaAux += retorna.charAt(i);
        }
        System.out.println("La ip del movil es: " + retornaAux);
        return retornaAux;
    }
    
    /*
    Aqui hay que hacer varias acciones. El móvil tiene que recibir datos del PC,
    por lo tanto primero hay que saber la ip del movil en la red, para esto la 
    mejor forma es hacer que el pc sea servidor y detectar la ip del movil 
    (es complicado saber la ip en un móvil). Luego el móvil hace de servidor
    y el pc ya tiene la ip del movil para mandarle los datos
    
    
    */
    public void run(){
        try {
            String IpMovil;
            completado=1;
            try{
                InetAddress localHost = InetAddress.getByName("debian.local");
                boton.setText("Esperando al móvil, la IP del PC es: " + localHost.getHostAddress());
            }
            catch (Exception e){}
            IpMovil = IpMovil();
            //ahora que tenemos la ip hay que enviar los datos, conviene esperar
            //un poco a que el móvil inicie el serversocket
            sleep(500);
            //aqui hay que rellenar cadenas, con los datos del fichero
            
            enviar(IpMovil);
            completado=2;
            
            System.out.println(IpMovil);
            boton.setText("Subir");
            boton.setEnabled(true);
        } catch (InterruptedException ex) {
            Logger.getLogger(Subir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean enviar(String ipMovil){
        Socket socket;			 
	// Declaramos un bloque try y catch para controlar la ejecución del subprograma
	try {
            InetAddress serverAddr = InetAddress.getByName(ipMovil);
            //asi tambien vale un nombre
            socket = new Socket(serverAddr.getHostAddress(), /*SERVERPORT*/ 6000);
            //mandar por el puerto 6000
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            // declaramos un objeto socket para realizar la comunicación
            //Socket socket;
 
            // declaramos e instanciamos un objeto de tipo byte
            byte[] mensaje_bytes = new byte[256];
			 
            // declaramos una variable de tipo string
            String mensaje="";
				 
            // Instanciamos un socket con la dirección del destino y el
            // puerto que vamos a utilizar para la comunicación
            // socket = new Socket("192.168.0.13",6006);
			 
            // Declaramos e instanciamos el objeto DataOutputStream
            // que nos valdrá para enviar datos al servidor destino
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				 
            // Creamos un bucle do while en el que enviamos al servidor el mensaje
            // los datos que hemos obtenido despues de ejecutar la función
            // "readLine" en la instancia "in"
            mensaje = "inicio " + Integer.toString(cadenas.size());
            out.writeUTF(mensaje);
            mensaje = Integer.toString(cadenas.size());
            out.writeUTF(mensaje);
            for ( int i=0; i<cadenas.size();i++ ) {
                //mensaje = in.readLine();
		mensaje = (String)cadenas.elementAt(i);
		// enviamos el mensaje codificado en UTF
                    out.writeUTF(mensaje);
		// out.writeUTF(mensaje);
		// mientras el mensaje no encuentre la cadena fin, seguiremos ejecutando
		// el bucle do-while
            }
            mensaje = "fin";
            // enviamos el mensaje codificado en UTF
            out.writeUTF(mensaje);
            socket.close();
	}
	// utilizamos el catch para capturar los errores que puedan surgir
	catch (Exception e) {
            // si existen errores los mostrará en la consola y después saldrá del
            // programa
            System.err.println(e.getMessage());
            System.out.println("maolllllllll");
            return false;
            //return e.getMessage();
            //System.exit(1);
	}
        
        return true;
    }
}
