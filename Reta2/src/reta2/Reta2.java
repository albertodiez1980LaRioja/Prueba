/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reta2;

/**
 *
 * @author killito
 */


import java.net.*;
//importar la libreria java.net
 
import java.io.*;
//importar la libreria java.io
 
import java.util.*;
// declaramos la clase servidortcp

import java.awt.*;
import java.awt.event.*;
import java.nio.charset.Charset;
import javax.swing.*;



public class Reta2 implements actionListener{

    
    public static void ips2()  {
      try{
          Enumeration nets = NetworkInterface.getNetworkInterfaces();
          for ( Object netint : Collections.list(nets)) {
              
              //displayInterfaceInformation(netint);
            //  System.out.println("Display name: " + ((NetworkInterface)netint).getDisplayName());
              System.out.println("Hardware address: " + Arrays.toString(((NetworkInterface)netint).getHardwareAddress()));          
             // System.out.println("Hardware address: " + ((NetworkInterface)netint).);          
          }
          //System.out.println(Runtime.getRuntime().exec("dir").toString());
          
          URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));

String ip = in.readLine(); //you get the IP as a String
System.out.println(ip);       
          
      }
      catch ( SocketException e){
          e.printStackTrace();
      }
      catch ( IOException e){
          e.printStackTrace();
      }
 
    }
    
    
    
    
    
      public static void ips() {
          byte[] b;
            try{
             //Si se pasa un argumento obtenemos la direccion IP
  
              //Si no obtenemos la direccion IP de la maquina local
                InetAddress[] direcciones = InetAddress.getAllByName("debian.local");
                for(int i=0;i<direcciones.length;i++){
                    System.out.println(direcciones[i].getHostAddress());
                    
                }
             InetAddress direccion = InetAddress.getLocalHost();
                direccion = InetAddress.getByName("debian");
              String nombreDelHost = direccion.getHostName();//nombre host
              String IP_local = direccion.getHostAddress();//ip como String
              System.out.println("La IP de la maquina local es : " + IP_local);
             System.out.println("El nombre del host local es : " + nombreDelHost);
             System.out.println("El nombre del host local es : " + direccion.toString());
        
            }
            catch(Exception e) {
                 e.printStackTrace();
             }
      }
   
    static private JFrame ventana;
    static JMenuBar menuBar;
    static JMenu menuArchivo;
    static JMenuItem elementoAbrir;
    static JMenuItem elementoSalir;
    static JMenuItem elementoSubir;
    static Escuchador escuchador;
    static JButton Bbajar,Bsubir;
    static Subir subir;
    static Bajar bajar;
    
    
    
    static Vector<Object> lan;
    
    public static Vector<Object> escanearLAN(){
        Vector<Object> lanLocal = new Vector<Object>();
        InetAddress inAdd;
        try{
        for (int i=1; i<255; i++){
            inAdd = InetAddress.getByName("192.168.0."+i);
            if(inAdd.isReachable(1500)){
                System.out.println("IP: " + inAdd.getHostAddress());
                System.out.println("HOST: " + inAdd.getHostName());
            }
        }
        }
        catch (Exception e){
            System.out.println(e.getMessage().toString());
        }
        return lanLocal;
        
    }
    
    
    static Vector<Object> cadenas;
    public static String abrirArchivo(){
         //Crear un objeto FileChooser
        JFileChooser fc = new JFileChooser();
        //Mostrar la ventana para abrir archivo y recoger la respuesta
        //En el parámetro del showOpenDialog se indica la ventana
        //  al que estará asociado. Con el valor this se asocia a la
        //  ventana que la abre.
        int respuesta = fc.showOpenDialog(null);
        
        //Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION)
        {
            //Crear un objeto File con el archivo elegido
            File archivoElegido = fc.getSelectedFile();
            //ahora hay que poner cada linea del archivo en el vector cadenas
            System.out.println("Archivo elegido: " + archivoElegido);
            try{
                InputStream fis = new FileInputStream(archivoElegido);
                InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                BufferedReader br = new BufferedReader(isr);
                String line;
                cadenas = new Vector<Object>();
                while ((line = br.readLine()) != null) {
                    cadenas.add(line);
                    System.out.println(line);
                }
                fis.close();
            }
            catch (Exception e){}
            
            System.out.println("Número de cadenas: " + cadenas.size());
            //Mostrar el nombre del archvivo en un campo de texto
            return(archivoElegido.getAbsolutePath()+archivoElegido.getName());
        }
        return "";
    }
    
    
    public static void construirVentana(){
        Font fuente = new Font("Verdana", Font.BOLD, 18);
        escuchador = new Escuchador();
        Bbajar = new JButton("Bajar");
        Bsubir = new JButton("Subir");
        Bbajar.setFont(fuente);
        Bsubir.setFont(fuente);
        Bbajar.addActionListener(escuchador);
        Bsubir.addActionListener(escuchador);
        bajar = new Bajar(Bbajar);
        subir = new Subir(Bsubir,cadenas);
        
        
        ventana = new JFrame("Transferencia de datos");
        Container panelContenedor = ventana.getContentPane();
        panelContenedor.setLayout(new FlowLayout());
        
        
        JLabel etiqueta = new JLabel("Pulsa una opción:");
        etiqueta.setFont(fuente);
        panelContenedor.add(etiqueta);
        panelContenedor.add(Bbajar);
        panelContenedor.add(Bsubir);
        
        
        
        menuBar = new JMenuBar();
        
        ventana.setJMenuBar(menuBar);
        menuArchivo = new JMenu("Archivo");
        menuArchivo.setFont(fuente);
        menuBar.add(menuArchivo);
        
        elementoAbrir = new JMenuItem("Bajar");
        elementoAbrir.setFont(fuente);
        elementoAbrir.addActionListener(escuchador);
        
        elementoSubir = new JMenuItem("Subir");
        elementoSubir.setFont(fuente);
        elementoSubir.addActionListener(escuchador);
        //elementoAbrir.addActionListener(new Escuchador());
        
        elementoSalir = new JMenuItem("Salir");
        elementoSalir.setFont(fuente);
        elementoSalir.addActionListener(escuchador);
        menuArchivo.add(elementoAbrir);
        menuArchivo.add(elementoSubir);
        menuArchivo.add(elementoSalir);
        
    
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setSize(950, 600);
        ventana.setLocationRelativeTo(null); //pongo la ventana en el centro de la pantalla
        ventana.setVisible(true);
        
    }
    
    
 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // declaramos un objeto ServerSocket para realizar la comunicacióni
       // ips();
      //  ips2();
        EscanearRed escanear = new EscanearRed();
        escanear.start();
 
        construirVentana();
    
    }
    
    
    
    static class Escuchador implements ActionListener{
        public void actionPerformed(ActionEvent evento){
            String texto = evento.getActionCommand().toString();
            if ( texto.equals("Abrir") ){
                System.out.println("pulsado abrir " + evento.getActionCommand().toString());
            }
            else if ( texto.equals("Salir") ){
                System.exit(0); // 0 para salir sin error
            }
            else if ( texto.equals("Bajar") || texto.equals("Esperando a recibir datos")){
                if (bajar.getCompletado()==2 || bajar.getCompletado()==0){
                    //Bbajar.setEnabled(false);
                    Bbajar.setEnabled(false);
                    Bbajar.setText("Esperando a recibir datos");
                    bajar=new Bajar(Bbajar);    
                    bajar.start();
                    
                }
                
                System.out.println("pulsado " + evento.getActionCommand().toString());
            }
            else if ( texto.equals("Subir")){
                if (subir.getCompletado()==2 || subir.getCompletado()==0){
                    System.out.println("pulsado " + evento.getActionCommand().toString());
                    if(!abrirArchivo().equals("")){
                        Bsubir.setEnabled(false);
                        Bsubir.setText("Esperando a enviar datos");
                        //System.out.println(abrirArchivo());
                        subir=new Subir(Bsubir,cadenas);
                        subir.start();
                    }
                }
            }

        }
    }
    
    
}
