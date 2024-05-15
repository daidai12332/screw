package com.example.screw.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.screw.repository.ReceiveDataDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@Component
public class SocketServer {

	@Autowired
	private ReceiveDataDao receiveDataDao;

	// initialize socket and input stream
	private Socket client;
	private ServerSocket server;
	private BufferedReader in;
	private int port = 80;
	private String ip = "26.58.3.200";
	ObjectMapper mapper = new ObjectMapper();

	public SocketServer() {
		super();
	}

	@PostConstruct
	public void init() {
		try {
			// creates a server and connects it to the given port and ip
			// �ݭn�]�w�� VPN �� IP ��}
			InetAddress inetAddress = InetAddress.getByName(ip);
			// we start our server
			server = new ServerSocket(port, 10000, inetAddress);
			System.out.println("Server started");
			System.out.println("Waiting for a client ...");
			
			// we accept the client in the given port and create a socket
			// we now have an established connection between our client and our server on
			// the given socket
			
			new Thread() {
				@Override
				public void run() {
					while (true) {
						try {
							client = server.accept();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						System.out.println("Client accepted");
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
										for (HashMap<String, String> item : obj.get("machine api")) {
											// �����C 3.5 ��e�@���q�y�q�i�ӡA�G�����N�C����ƪ��q�y�q *3.5�A����[�`�q�y�~�|�O IT ���n
											// �N��Ʀs���Ʈw
											receiveDataDao.insertReceiveData(item.get("name"), item.get("status"),
													item.get("order number"),
													Double.parseDouble(item.get("current")) * 3.5,
													Integer.parseInt(item.get("pass")),
													Integer.parseInt(item.get("ng")), now);											
											// Test ��Ʀ��L�L�X
											System.out.println("******* START ********");
											System.out.println("name: " + item.get("name"));
											System.out.println("status: " + item.get("status"));
											System.out.println("current: " + item.get("current"));
											System.out.println("order number: " + item.get("order number"));
											System.out.println("pass: " + item.get("pass"));
											System.out.println("ng: " + item.get("ng"));
											System.out.println("******* END ********");
										}
										// �N��ƿ�X�쥻��
										String saveDataName = LocalDate.now().toString() + "_receive_data" + ".txt" ;
										try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(saveDataName, true));) {
											line = now.toString() + "\n" + line + "\n\n";
											bos.write(line.getBytes());   // �N temp ����� �g�� buffer �g�� �ت��ɤ�
										} catch (IOException e) {
											System.out.println(e);
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
					
				}
			}.start();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

}
