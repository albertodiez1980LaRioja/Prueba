/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reta2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static reta2.Reta2.elementoAbrir;
/**
 *
 * @author killito
 */





public class Ventana implements actionListener {
    private JFrame ventana;
    JMenuBar menuBar;
    JMenu menuArchivo;
    JMenuItem elementoAbrir = new JMenuItem("Abrir");
    JMenuItem elementoSalir = new JMenuItem("Abrir");
    
    
    
    public void construirVentana(){
        ventana = new JFrame("Transferencia de datos");
        Container panelContenedor = ventana.getContentPane();
        
        JLabel etiqueta = new JLabel("Soy una etiqueta");
        etiqueta.setFont(new Font("Verdana", Font.BOLD, 18));
        panelContenedor.add(etiqueta);
        
        
        
        menuBar = new JMenuBar();
        
        ventana.setJMenuBar(menuBar);
        menuArchivo = new JMenu("Archivo");
        menuArchivo.setFont(new Font("Verdana", Font.BOLD, 18));
        menuBar.add(menuArchivo);
        
        elementoAbrir = new JMenuItem("Abrir");
        elementoAbrir.setFont(new Font("Verdana", Font.BOLD, 18));
      //  elementoAbrir.addActionListener();
        elementoSalir = new JMenuItem("Salir");
        elementoSalir.setFont(new Font("Verdana", Font.BOLD, 18));
        menuArchivo.add(elementoAbrir);
        menuArchivo.add(elementoSalir);
    
        
        ventana.pack();
        ventana.setSize(800, 600);
        ventana.setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent evento){}
}
