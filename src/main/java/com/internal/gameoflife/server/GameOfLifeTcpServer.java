package com.internal.gameoflife.server;

import java.io.IOException;
import java.net.ServerSocket;

import com.internal.gameoflife.handler.ServiceHandler;
import com.internal.gameoflife.simulation.GameOfLifeSimulation;

/**
 * This class represent the tcp server, it is responsible for initiate the connection then wait for the client to connect
 * Each time a client connect a new thread is created, to serve datas to the client. 
 * @author syeponde
 */
public class GameOfLifeTcpServer {

	public GameOfLifeTcpServer(GameOfLifeSimulation gameOfLifeSimulation) {
		try (ServerSocket serverSocket = new ServerSocket(4444);) {
			while (true) {
				new ServiceHandler(serverSocket.accept(), gameOfLifeSimulation).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
