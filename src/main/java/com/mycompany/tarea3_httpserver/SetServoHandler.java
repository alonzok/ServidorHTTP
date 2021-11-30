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

public class SetServoHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // send response
        String response = "";
    	try {
    		//Se conecta a la base de datos.
        	String url = "jdbc:mysql://localhost:3306/sensor";
            Connection conn = DriverManager.getConnection(url,"root","secret");
            
            //Se crea el objeto statement.
            Statement stmt = conn.createStatement();
            ResultSet rs;
            String item = "1";
            
            //Se agrega la sentencia a ejecutar al statement.
            rs = stmt.executeQuery("SELECT active FROM ServoMotor;");
            while ( rs.next() ) {
            	
            	//Se ejecuta y se obtiene el resultado de forma de string.
                item = rs.getString("active");
                System.out.println(item);
            } 
            
            //Se ejecuta la sentencia para cambiar de valor al active de ServoMotor.
            String statementString;
            if(item.compareTo("1") == 0) {
            	statementString = "UPDATE ServoMotor SET active = 0 WHERE id = 1";
            	response = "Servo Desactivado";
            } else {
            	statementString = "UPDATE ServoMotor SET active = 1 WHERE id = 1";
            	response = "Servo Activado";
            }
            
            System.out.println(statementString);
            stmt.executeUpdate(statementString);
            
    	} catch(Exception e) {
    		System.err.println(e);
    	}
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
    }

}
