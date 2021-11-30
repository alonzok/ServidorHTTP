/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tarea3_httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author AlonzoK
 */
public class EchoGetHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
    	
    	//Connect to the database
    	try {
    		String item = "0";
            String url = "jdbc:mysql://localhost:3306/sensor";
            Connection conn = DriverManager.getConnection(url,"root","secret");
            Statement stmt = conn.createStatement();
            ResultSet rs;
 
            rs = stmt.executeQuery("SELECT cantidad AS ITEM FROM TouchSensor;");
            while ( rs.next() ) {
                item = rs.getString("ITEM");
            }
            
        
                 // send response
                 String response = "Veces pulsados: " + item;
                 exchange.sendResponseHeaders(200, response.length());
                 
                 OutputStream os = exchange.getResponseBody();
                 os.write(response.toString().getBytes());

                 os.close();
                 conn.close();
    	} catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            System.err.println(e);
        }
    }
    
}
