/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tarea3_httpserver;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 * @author AlonzoK
 */
public class Http {

	static int port = 8000;

	public static void main(String[] args) throws IOException {

		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

		System.out.println("server started at " + port);
		server.createContext("/echoGet", new EchoGetHandler());
		server.createContext("/servoPost", new SetServoHandler());
		server.setExecutor(null);
		server.start();
	}
}
