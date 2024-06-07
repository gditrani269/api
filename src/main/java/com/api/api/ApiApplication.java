package com.api.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ApiApplication.class, args);
		System.out.print ("holiss 2");

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
	}

}
