package com.api.api.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ParserAcciones {

    public static int iTest () {
        System.out.println("dentro del ParserAcciones");
        return 8;
    }

    public static double iAccionValue (String sUrl) throws MalformedURLException {
//        System.out.println("dentro del ParserPage -> iAccionValue");
		String url = sUrl;//"https://es.investing.com/equities/microsoft-corp-ar";

		URL obj = new URL(url);
		HttpURLConnection con;
        double dArma = 0;
		try {
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			//add request header
			con.setRequestProperty("cache-control", "no-cache");
			con.setRequestProperty("X-API-KEY", "myApiKey");
			con.setRequestProperty("X-API-EMAIL", "myEmail@mail.com");

			int responseCode = con.getResponseCode();
			BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
			String inputLine;
	//		StringBuffer response = new StringBuffer();
			boolean bDolar = false;

			while ((inputLine = in.readLine()) != null) {
				
				int iStar = inputLine.indexOf("instrument-price-last");
//				int iEnd = inputLine.indexOf("1501");
//				System.out.print("////////////////////////////////////////");
				
				if (iStar != -1 && !bDolar) {
//					System.out.println(inputLine);
//					System.out.println("iStar es la que va: " + iStar);
					int iStarValue = inputLine.indexOf(">",iStar) + 1;
                    int iEndValue = inputLine.indexOf("<", iStarValue);
//					System.out.println("iStarValue: " + iStarValue);
//                  System.out.println("iEndValue: " + iEndValue);
  //                  double dTmp = Double.parseDouble(inputLine.substring(iStarValue, iStarValue+7));
     //               iDolarNow = (int) dTmp;
					//iDolarNow = Integer.valueOf(inputLine.substring(iStarValue+1, iStarValue+10));
                    String sAccionValue = inputLine.substring(iStarValue, iEndValue);
//					System.out.println ("sAccionValue: " +sAccionValue);

//                    System.out.println("---------------------------");
 //                   String sOrigin=sAccionValue;
                    String sOrigin2=sAccionValue;
    //                String sSub = "";
                    
                    if (sOrigin2.contains(",")) {
                        sOrigin2 = sOrigin2.replace(".", "");
                        sOrigin2 = sOrigin2.replace(",", ".");
                        dArma = Double.valueOf(sOrigin2);
                    }
//                    System.out.println("---------------------------");
                }
				if (iStar != -1) {
//					bDolar = true;
//					System.out.println("iStar: " + iStar);
				}
/* 				if (iEnd != -1) {
					System.out.println("iEnd: " + iEnd);
				}*/
			
	//			response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dArma;

    }

}
