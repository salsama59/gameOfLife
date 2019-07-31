package com.internal.gameoflife.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.internal.gameoflife.simulation.GameOfLifeSimulation;

public class ServiceHandler extends Thread implements Runnable {
	private Socket clientSocket;
	private GameOfLifeSimulation simulation;
	public ServiceHandler(Socket socket, GameOfLifeSimulation simulation) {
		this.clientSocket = socket;
		this.simulation = simulation;
	}

	@Override
	public void run() {
		try {
			if(!simulation.isSleeping()) {
				PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
				printWriter.println(simulation.getSimulationBuffer());
				printWriter.flush();
				printWriter.close();
			}
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}