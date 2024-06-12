package com.api.api.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class leerArchivo {

    public static int iTestTools () {
        System.out.println("dentro del leerarchivo");
        return 8;
    }

    public static String muestraContenido2(String archivo, String sSigla) throws FileNotFoundException, IOException { 
    	String cadena; 
        String sRta="";
        FileReader f = new FileReader(archivo); 
        BufferedReader b = new BufferedReader(f); 
        while((cadena = b.readLine())!=null) { 
            if (cadena.contains(sSigla)) {
                System.out.println(cadena);
            //    sRta = cadena.substring(sSigla.length()+1, cadena.length());
                sRta = cadena.replace(sSigla+":", "");
            }
        	 
        } 
        b.close(); 
        return sRta;
	} 

    public static String [] listActions(String ruta) {
                
     	String [] array = new String [5];; 
        array [0] = "uno";
        array [1] = "dos";
        array [2] = "tres";
        array [3] = "cuatro";
        array [4] = "cinco";
        return array;
    }

    public static ArrayList <String> listActionsArray(String ruta) throws FileNotFoundException, IOException  {
        System.out.println("listActionsArray track 1. " + ruta);
        ArrayList<String> array = new ArrayList<>();
//        array.add ("uno dinamico");
  //      array.add ("dos dinamico");

    	String cadena; 
        FileReader f = new FileReader(ruta); 
        BufferedReader b = new BufferedReader(f); 
        while((cadena = b.readLine())!=null) { 
            array.add (cadena);
            System.out.println(cadena);
        } 
        b.close(); 

        return array;
    }
}
