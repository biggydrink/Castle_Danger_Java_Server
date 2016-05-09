package com.andrew;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/** Server for Castle Danger */

// TODO build a protocol to handle communication
// DONE look into building a multiple client server
       // a server like this can handle many requests, just sequentially, so it may be slow
       // let's try making a multiple client server later if things don't seem to be working fast enough
       // FYI I think each connection will have to close ("bye") before letting the new one in
// TODO see what parts of the DB info need to go here - is it it's own DB thing, or do we just need to be able to run some queries in the protocol?

public class CDServer {

    public static void main(String[] args) {

        int portNum = 8888;

        try (
                ServerSocket serverSocket = new ServerSocket(portNum); // Server socket listens on portNum
                Socket clientSocket = serverSocket.accept(); // Creates a new socket with a connection to the client so that the server socket can continue listening
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                ) {
            String inputLine;
            String outputLine;

            // Create protocol object
            // outputline = protocol object method that returns String
            // out.println(outputLine);

            // Processes input and sends output until client sends bye to close
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equalsIgnoreCase("Bye")) {
                    break;
                }
                // outputLine = protocol String method
                outputLine = "I hear you! You say '" + inputLine.toUpperCase() + "'.";
                out.println(outputLine);
            }

        } catch (IOException ioe) {
            System.out.println("Exception when trying to listen on port " + portNum + " or listening for a connection");
            System.out.println(ioe.getMessage());
        }
    }
}
