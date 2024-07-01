package com.api.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//9-6-2024
import com.api.api.parsers.*;
import com.api.api.tools.*;

import org.springframework.web.bind.annotation.CrossOrigin;
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
/*/		System.out.println (leerArchivo.muestraContenido2("C:\\Users\\papa\\n\\desa\\java\\S.LOG", "MSFT"));
		String [] aca= leerArchivo.listActions ("");
		System.out.println("aca: " + aca[0] + aca[1]);*/
/*		ArrayList<String> array = new ArrayList<>();
		System.out.println("track 1");
		array = leerArchivo.listActionsArray("C:\\Users\\papa\\n\\desa\\java\\lista-acciones.txt");
		System.out.println("track 2");
		for (int i = 0; i < array.size(); i++) {
			System.out.println(array.get(i));
			String sOption = array.get(i).substring (0,array.get(i).indexOf(":"));
			System.out.println("sOption: " + sOption);
			String sUrlInvest = array.get(i).substring (array.get(i).indexOf(":")+1);
			System.out.println("sUrlInvest: " + sUrlInvest);
		}*/

/*		String url = "https://www.lanacion.com.ar";

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

			
			response.append(inputLine);
		}
		in.close();

		//print result
		//System.out.println(response.toString());
		System.out.println("iDolar: " + ParserPage.iDolarLaNacion ());
		System.out.println("---------------------------");*/
	}

//------------------------------------
	@RequestMapping("/users")
	@CrossOrigin (originPatterns = "*")
	String home2() {
		int iDolarNow = 0;
		double iMola = 0;
		double dAccionGral = 0;
		double dTotal = 0;
		String sRta = "[";
		int iKey = 0;
		int iTotalPesos = 0;
		int iTotalUsd = 0;
		ArrayList<String> array = new ArrayList<>();

		try {
			iDolarNow = ParserPage.iDolarLaNacion(); //iDolar ();
			System.out.println("Dolar Blue de www.lanacion.com: " + iDolarNow);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try {
			array = leerArchivo.listActionsArray("C:\\Users\\papa\\n\\desa\\java\\lista-acciones.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < array.size(); i++) {
			String sOptionName = array.get(i).substring (0,array.get(i).indexOf(":"));
			iKey ++;
			sRta += "{\"accion\": \"" + sOptionName + "\",";
			sRta += "\"id\": " + iKey + ",";
			int iOptionQuantity = Integer.valueOf(array.get(i).substring (array.get(i).indexOf(":")+1,array.get(i).indexOf("!")));
			String sUrlInvest = array.get(i).substring (array.get(i).indexOf("!")+1);
			try {
				dAccionGral = ParserAcciones.iAccionValue(sUrlInvest);
				sRta += "\"valor\": " + dAccionGral + ",";
				sRta += "\"Cantidad\": " + iOptionQuantity + ",";
				sRta += "\"Saldo_pesos\": " + iOptionQuantity * dAccionGral + ",";
				iTotalPesos += iOptionQuantity * dAccionGral;
				sRta += "\"Saldo_dolares\": " + (double)Math.round (iOptionQuantity * dAccionGral / iDolarNow * 100d) / 100d + "}," + //
										"";
										
	//			iTotalUsd += iOptionQuantity * dAccionGral / iDolarNow;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		sRta += "{\"accion\": \"" + "Totales" + "\",";
		iKey ++;
		sRta += "\"id\": " + iKey + ",";
		sRta += "\"valor\": " + 1 + ",";
		sRta += "\"Cantidad\": " + 1 + ",";
		sRta += "\"Saldo_pesos\": " + 1 * iTotalPesos + ",";
		sRta += "\"Saldo_dolares\": " + (double)Math.round (1 * iTotalPesos / iDolarNow * 100d) / 100d + "}" + //
								"";

//		sRta += "\t\"dolar\": \"" + iDolarNow + "\",\r\n";
//		sRta += "\t\"total\": \"" + dTotal + "\"\r\n\t}";
		sRta += "]";
		System.out.println("sRta: " + sRta);

		
		
		return sRta;//"{ dolar: " + iDolarNow + "}";
	}

	@RequestMapping("/users2")
	@CrossOrigin (originPatterns = "*")
	String home3() {
		String sRta = "[\r\n"
						+ "\t{"
						+ "\r\n\t\"id\": 1,\r\n"
						+ "\t\"username\": \"pepe\",\r\n"
						+ "\t\"email\": \"pepe@a.com\"\r\n"
						+ "\t},\r\n"
						+ "\t{"
						+ "\r\n\t\"id\": 2,\r\n"
						+ "\t\"username\": \"yo2\",\r\n"
						+ "\t\"email\": \"yeah@a.com\"\r\n"
						+ "\t},\r\n"
						+ "\t{"
						+ "\r\n\t\"id\": 3,\r\n"
						+ "\t\"username\": \"papu\",\r\n"
						+ "\t\"email\": \"papu@a.com\"\r\n"
						+ "\t}\r\n";

						sRta += "]";
		System.out.println(sRta);
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
