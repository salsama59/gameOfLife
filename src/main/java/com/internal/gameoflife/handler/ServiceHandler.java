package com.internal.gameoflife.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.internal.gameoflife.simulation.GameOfLifeSimulation;

/**
 * This class is responsible for executing the action for the server
 * It write the simulation grid data into the outputstream of the client socket,
 * when the simulation thread isn't sleeping
 * @author syeponde
 */
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