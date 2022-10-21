package com.telefonica.movistar.planfs.configurations;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;


@Component
public class JsonXml {
	
    public static String xmltoJSON(String msg) {
        try {
          return JSONObject.valueToString(XML.toJSONObject(msg));
        } catch (Exception e) {
          return "";
        } 
      }
      
      public static String jsonToXML(String msg) {
        try {
          return XML.toString(new JSONObject(msg));
        } catch (Exception e) {
          return "";
        } 
      }
}
