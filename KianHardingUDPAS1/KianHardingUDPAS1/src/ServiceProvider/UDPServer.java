package ServiceProvider;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class UDPServer {
    //constants
    private static final int PORT = 47538;
    private static final int MAX_LEN = 1024;
    private static final String EXIT = "EXIT";
    
    //hashmap to store the contacts
    private static Map<String, String> contacts = new HashMap<>();
    
    public static void main(String[] args) {
        try {
            // create a datagram socket for the server to listen for incoming datagrams
            DatagramSocket serverSocket = new DatagramSocket(PORT);
            // create a byte array to store received data
            byte[] receiveData = new byte[MAX_LEN];
             // output a message to indicate the server has started on the specified port
            System.out.println("Server started on port " + PORT);
            
            while (true) {
                // create a datagram packet to receive data from the client
                DatagramPacket receivePacket = new DatagramPacket(receiveData, MAX_LEN);
                // receive the data
                serverSocket.receive(receivePacket);
                // convert the received data into a string
                String clientMessage = new String(receivePacket.getData());
                // get the IP address of the client
                InetAddress clientAddress = receivePacket.getAddress();
                // get the port number of the client
                int clientPort = receivePacket.getPort();
                // process the client's message
                String response = handleClientMessage(clientMessage.trim());
                // convert the response into a byte array
                byte[] sendData = response.getBytes();
                // create a datagram packet to send the response to the client
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                // send the response to the client
                serverSocket.send(sendPacket);
                if (clientMessage.trim().equals(EXIT)) {
                    break;
                }
            }
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String handleClientMessage(String message) {
        // split the message into an array of tokens
        String[] tokens = message.split(" ");
        
        
        if (tokens[0].equals("GET")) {
            String contactName = tokens[1];
            // check if the contact exists in the HashMap
            if (contacts.containsKey(contactName)) {
                return "PhoneNumber of: "+contactName+":"+contacts.get(contactName);
            } else {
                return "Contact not found";
            }
        } else if (tokens[0].equals("ADD")) {
            // get the name of the contact from the message
            String contactName = tokens[1];
            // get the Number of the contact from the message
            String contactNum = tokens[2];
            // check if the contact already exists in the HashMap
            if (contacts.containsKey(contactName)) {
                return "Contact already exists";
            } else {
                contacts.put(contactName, contactNum);
                return "Contact added";
            }
            // check if the client has sent the exit
        } else if (tokens[0].equals(EXIT)) {
            return "Exiting server...";
        } else {
            return "Invalid command";
        }
    }
}