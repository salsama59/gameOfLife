package com.internal.gameoflife.server;

import java.io.IOException;
import java.net.ServerSocket;

import com.internal.gameoflife.handler.ServiceHandler;
import com.internal.gameoflife.simulation.GameOfLifeSimulation;

public class GameOfLifeTcpServer {

	public GameOfLifeTcpServer(GameOfLifeSimulation gameOfLifeSimulation) {
		try {
			ServerSocket serverSocket = new ServerSocket(4444);
			while (true) {
				new ServiceHandler(serverSocket.accept(), gameOfLifeSimulation).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
