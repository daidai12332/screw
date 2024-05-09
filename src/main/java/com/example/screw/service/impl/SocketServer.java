package com.example.screw.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.screw.repository.ScrewMaterialDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SocketServer {
		
//	@Autowired
//	private ScrewMaterialDao screwDao;
	
	// initialize socket and input stream
	private Socket client;
	private ServerSocket server;
	private BufferedReader in;
	ObjectMapper mapper = new ObjectMapper();
	
	public SocketServer() {
		super();
	}
	
	@PostConstruct
	public void init() {
	}

	// creates a server and connects it to the given port
	public SocketServer(int port) {
		// starts server and waits for a connection
		try {
			// 需要設定為 VPN 的 IP 位址
			InetAddress inetAddress = InetAddress.getByName("26.58.3.200");
			// we start our server
			server = new ServerSocket(port, 10000, inetAddress);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			// we accept the client in the given port and create a socket
			// we now have an established connection between our client and our server on
			// the given socket
			while (true) {
				client = server.accept();
				System.out.println("Client accepted");
				new Thread() { // 在後面加上大括號，即可重新定義 run 的方法，不用另外建一個 class，稱為匿名類別
					@Override
					public void run() {
						try {
							// takes input from the client socket
							LocalDateTime now = LocalDateTime.now();
							System.out.println(now.toString());
							in = new BufferedReader(new InputStreamReader(client.getInputStream()));
							String line = "";
							try {
								while ((line = in.readLine()) != null) {
									if (line.contains("machine api")) {
										HashMap<String, ArrayList<HashMap<String, String>>> obj = mapper.readValue(line,
												new TypeReference<HashMap<String, ArrayList<HashMap<String, String>>>>() {
												});
										for(HashMap<String, String> item : obj.get("machine api")) {
//											screwDao.insertReceiveData(item.get("name"), item.get("status"), item.get("order number"), Double.parseDouble(item.get("current")), Integer.parseInt(item.get("pass")), Integer.parseInt(item.get("ng")), now);
											System.out.println("******* START ********");
											System.out.println("name: " + item.get("name"));
											System.out.println("status: " + item.get("status"));
											System.out.println("current: " + item.get("current"));
											System.out.println("order number: " + item.get("order number"));											
											System.out.println("pass: " + item.get("pass"));
											System.out.println("ng: " + item.get("ng"));
											System.out.println("******* END ********");
										}
									}
								}
							} catch (IOException i) {
								System.out.println(i);
							}
							System.out.println("Closing connection");
							// close connection
							client.close();
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}.start();
			}
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public static void main(String args[]) {
		SocketServer server = new SocketServer(80);
	}

}
