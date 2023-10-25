
package ServiceConsumer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author kian harding
 */
public class Client {
    private static int requesterListeningPort = 47538;
    
    private static int providerListeningPort = 1234;
    
    private static String SERVER_ADDRESS = "localhost";
    
    private static final int MAX_LEN = 150;
    
   
  

    public static void main(String[] args){
          try {
              // Create a new datagram socket to communicate with the server
            DatagramSocket clientSocket = new DatagramSocket();
            // Get the server address
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
            
            Scanner scanner = new Scanner(System.in);
            
             // Continuously prompt the user for commands until they exit the program
            while (true) {
                System.out.println("Enter command (GET/ADD : contactName : [PhoneNumber]/EXIT):");
                
                
                // Read the input
                String command = scanner.nextLine();
                
                 // Convert the command to bytes and create a datagram packet to send to the server
                byte[] sendData = command.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, requesterListeningPort);
                
                 // Send the packet to the server
                clientSocket.send(sendPacket);
                
                 // If the user entered the exit command, break out of the loop and close the socket
                    if (command.trim().equals("EXIT")) {
                sendData = command.getBytes();
                DatagramPacket exit = new DatagramPacket(sendData, sendData.length, serverAddress, requesterListeningPort);
                clientSocket.send(sendPacket);
               
                break;
                }
                    // Receive the server's response
                byte[] receiveData = new byte[MAX_LEN];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, MAX_LEN);
                clientSocket.receive(receivePacket);
                
                // Convert the response to a string and print it to the console
                String serverResponse = new String(receivePacket.getData()).trim();
                System.out.println("Server response: " + serverResponse);
            }
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        }
        }

    


