/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reta2;

import java.io.DataInputStream;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javax.swing.JButton;

/**
 *
 * @author killito
 */
public class Bajar extends Thread{
    // baja los datos del movil
    private int completado;
    private JButton boton;
    public Bajar(JButton boton){
        super();
        completado=0;
        this.boton = boton;
    }
    
    public synchronized int getCompletado() {
        return completado;
    }
    
    public synchronized void setCompletado(int c){
        completado = c;
    }
    
    
    private int numCadenas=0;
    private String textoRetornar="";
    public String formateaTexto(String entrada){
        //retorna el texto para la base de datos
        String retorno = "";
        
        if( (numCadenas % 10) == 0){ //es la primera, la mah, lo pongo a cero
            textoRetornar = "('" + entrada + "', '";
        }
        
        if( (numCadenas % 10) == 1){ //es la segunda, el nombre
            textoRetornar += entrada + "', '";
        }
        if( (numCadenas % 10) == 2){ //es la tercera, la localidad
            textoRetornar += entrada + "', '";
        }
        if( (numCadenas % 10) == 3){ //es la cuarta, la longitud
            textoRetornar += entrada + "', '";
        }
        if( (numCadenas % 10) == 4){ //es la quinta, la latitud
            textoRetornar += entrada + "', '";
        }
        if( (numCadenas % 10) == 5){ //es la 
            textoRetornar += entrada + "', '";
        }
        if( (numCadenas % 10) == 6){ //es la 
            textoRetornar += entrada + "', '";
        }
        if( (numCadenas % 10) == 7){ //es la 
            textoRetornar += entrada + "', '";
        }
        if( (numCadenas % 10) == 8){ //es la 
            textoRetornar += entrada + "', '";
        }
        if( (numCadenas % 10) == 9){ //es la 
            textoRetornar += entrada + "')";
            numCadenas++;
            return textoRetornar;
        }

        numCadenas++;
        return "";
    }
    
    public void run(){
        ServerSocket socket; 
        FileWriter fichero = null;
        PrintWriter pw = null;
        completado=1;
        java.util.Date fecha = new Date();
        System.out.println (fecha.toString());
        String nombreFichero = fecha.toString();
        // Declaramos un bloque try y catch para controlar la ejecución del subprograma
        try {
            fichero = new FileWriter("/home/killito/RetaCopias/bajadas/"+fecha.toString()+".txt");
            pw = new PrintWriter(fichero);
            //InetAddress localHost = InetAddress.getLocalHost();
            //InetAddress localHost = InetAddress.getByName("192.168.0.13");
            InetAddress localHost = InetAddress.getByName("debian.local");
            System.out.println(localHost.getHostName());
            System.out.println(localHost.getHostAddress());
      
            boton.setText(boton.getText().toString() +" IP: " + localHost.getHostAddress());
            // Instanciamos un ServerSocket con la dirección del destino y el
            // puerto que vamos a utilizar para la comunicación

            socket = new ServerSocket(6000);

            // Creamos un socket_cli al que le pasamos el contenido del objeto socket después
            // de ejecutar la función accept que nos permitirá aceptar conexiones de clientes
            Socket socket_cli = socket.accept();

            // Declaramos e instanciamos el objeto DataInputStream
            // que nos valdrá para recibir datos del cliente

            DataInputStream in = new DataInputStream(socket_cli.getInputStream());

            // Creamos un bucle do while en el que recogemos el mensaje
            // que nos ha enviado el cliente y después lo mostramos
            // por consola
            String mensaje;
            String mensajeAux;
            pw.println("INSERT INTO mah (Id, Nombre, Localidad, Direccion, Longitud, Latitud, DiaCerrado, MañanaCerrado, TardeCerrado, IP) VALUES");
            int numLineas=0,numLineasTotal=0;
            do {
                mensaje ="";
                mensaje = in.readUTF();
                System.out.println(mensaje);
                if(mensaje.startsWith("inicio")){
                    mensaje = in.readUTF();
                    System.out.println(mensaje);
                    numLineasTotal = Integer.parseInt(mensaje)/10;
                    mensaje = "inicio ";
                }
                if(!mensaje.startsWith("fin") && !mensaje.startsWith("inicio")){
                    //aqui hay que formatear la salida
                    mensajeAux = this.formateaTexto(mensaje);
                    if(!mensajeAux.equals("")){
                        numLineas++;
                        if(numLineas<numLineasTotal)
                            pw.println(mensajeAux+",");
                        else
                            pw.println(mensajeAux+";");
                        
                    }
                }
                //aquí hay que guardarlo en un fichero
            } while ( !mensaje.startsWith("fin"));
            System.out.println(socket_cli.getInetAddress().toString());
            //así sabemos la ip del cliente, en este caso el del movil
            socket.close();
            completado=2;
            fichero.close();
        }
       // utilizamos el catch para capturar los errores que puedan surgir
        catch (Exception e) {

        // si existen errores los mostrará en la consola y después saldrá del
        // programa
        //aqui habria que eliminar el fichero
         System.err.println(e.getMessage());
         System.exit(1);
        }
        System.out.println("Datos traspasados!");
        boton.setText("Bajar");
        boton.setEnabled(true);
    }
    
}
