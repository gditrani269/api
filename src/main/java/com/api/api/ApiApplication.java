package com.api.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//9-6-2024
import com.api.api.parsers.*;
import com.api.api.tools.*;

//-----------------------------------------
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController


//--------------------------------------

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ApiApplication.class, args);
		System.out.println ("holiss 2");
		System.out.println (leerArchivo.muestraContenido2("C:\\Users\\papa\\n\\desa\\java\\S.LOG", "MSFT"));
		String [] aca= leerArchivo.listActions ("");
		System.out.println("aca: " + aca[0] + aca[1]);
		ArrayList<String> array = new ArrayList<>();
		System.out.println("track 1");
		array = leerArchivo.listActionsArray("C:\\Users\\papa\\n\\desa\\java\\lista-acciones.txt");
		System.out.println("track 2");
		for (int i = 0; i < array.size(); i++) {
			System.out.println(array.get(i));
			String sOption = array.get(i).substring (0,array.get(i).indexOf(":"));
			System.out.println("sOption: " + sOption);
			String sUrlInvest = array.get(i).substring (array.get(i).indexOf(":")+1);
			System.out.println("sUrlInvest: " + sUrlInvest);
		}

		String url = "https://www.lanacion.com.ar";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET

		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("cache-control", "no-cache");
		con.setRequestProperty("X-API-KEY", "myApiKey");
		con.setRequestProperty("X-API-EMAIL", "myEmail@mail.com");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		boolean bDolar = false;
		while ((inputLine = in.readLine()) != null) {
				
				int iStar = inputLine.indexOf("lar blue");
				int iEnd = inputLine.indexOf("1501");
//				System.out.print("////////////////////////////////////////");
				
				if (iStar != -1 && !bDolar) {
//					System.out.println(inputLine);
					System.out.println("iStar es la que va: " + iStar);
					int iStarValue = inputLine.indexOf("$",iStar);
					System.out.println("iStarValue: " + iStarValue);
					System.out.println ("DOLAR: " + inputLine.substring(iStarValue+1, iStarValue+5));
				}
				if (iStar != -1) {
					bDolar = true;
					System.out.println("iStar: " + iStar);
				}
/* 				if (iEnd != -1) {
					System.out.println("iEnd: " + iEnd);
				}*/
			
			response.append(inputLine);
		}
		in.close();

		//print result
		//System.out.println(response.toString());
		System.out.println("iDolar: " + ParserPage.iDolarLaNacion ());
		System.out.println("---------------------------");
		String sOrigin="18.625,5";
		String sOrigin2="18.625,5";
		String sSub = "";
		double dArma = 0;
		if (sOrigin.contains(",")) {
			int iComa = sOrigin.indexOf(",");
			sSub = sOrigin.substring(0, iComa);
			System.out.println ("sSub: " + sSub);
			System.out.println ("sOrigin2: " + sOrigin2);
			sOrigin2 = sOrigin.replace(".", "");
			System.out.println ("sOrigin2: " + sOrigin2);
			sOrigin2 = sOrigin2.replace(",", ".");
			System.out.println ("sOrigin2: " + sOrigin2);
			dArma = Double.valueOf(sOrigin2);
			System.out.println ("dArma: " + dArma);
		}
		System.out.println("---------------------------");

	}

//------------------------------------
	@RequestMapping("/sasa")
	String home2() {
		int iDolarNow = 0;
		double iMola = 0;
		double dAccionGral = 0;
		double dTotal = 0;
		String sRta = "{\r\n" + //
						"";
		ArrayList<String> array = new ArrayList<>();
		System.out.println("track 1");
		try {
			array = leerArchivo.listActionsArray("C:\\Users\\papa\\n\\desa\\java\\lista-acciones.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("track 2");
		for (int i = 0; i < array.size(); i++) {
			System.out.print("--------------------------------------------");
			System.out.println(array.get(i));
			String sOptionName = array.get(i).substring (0,array.get(i).indexOf(":"));
			System.out.println("sOptionName: " + sOptionName);
			sRta += "\t" + sOptionName + ": {\r\n";
			int iOptionQuantity = Integer.valueOf(array.get(i).substring (array.get(i).indexOf(":")+1,array.get(i).indexOf("!")));
			System.out.println("iOptionQuantity: " + iOptionQuantity);
			String sUrlInvest = array.get(i).substring (array.get(i).indexOf("!")+1);
			System.out.println("sUrlInvest: " + sUrlInvest);

			try {
				dAccionGral = ParserAcciones.iAccionValue(sUrlInvest);
				System.out.println("dAccionGral: " + dAccionGral);
				sRta += "\t\tvalor: " + dAccionGral + ",\r\n";
				sRta += "\t\tCantidad: " + iOptionQuantity + "\r\n";
				sRta += "\t\tSaldo: " + iOptionQuantity * dAccionGral / 1245+ "\r\n\t},\r\n" + //
										"";
				dTotal += iOptionQuantity * dAccionGral / 1245;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			

		}
		sRta += "\ttotal: " + dTotal + "\r\n}";
		System.out.println("sRta: " + sRta);

		
		try {
			iDolarNow = ParserPage.iDolarLaNacion(); //iDolar ();
			System.out.println("iDolar desde sasa: " + iDolarNow);
			iMola = ParserAcciones.iAccionValue("https://es.investing.com/equities/microsoft-corp-ar");
			System.out.println("MSFT: " + iMola);
			iMola = ParserAcciones.iAccionValue("https://es.investing.com/equities/molinos-agro");
			System.out.println("MOLA: " + iMola);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sRta;//"{ dolar: " + iDolarNow + "}";
	}

//----------------------------------
    public static String muestraContenido(String archivo) throws FileNotFoundException, IOException { 
    	String cadena; 
        FileReader f = new FileReader(archivo); 
        BufferedReader b = new BufferedReader(f); 
        while((cadena = b.readLine())!=null) { 
        	System.out.println(cadena); 
        } 
        b.close(); 
		return cadena;
	} 
}
