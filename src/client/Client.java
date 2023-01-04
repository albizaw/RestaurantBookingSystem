package client;

import database.CustomerEntity;
import database.Slots;

import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        // Connect to the server
        Socket socket = new Socket("localhost", 9000);

        // Read from and write to the socket as needed
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Read user input for the name and surname
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter name: ");
        String name = userIn.readLine();
        System.out.print("Enter surname: ");
        String surname = userIn.readLine();

        //check if a customer is in database


        // Create a new Customer object
        CustomerEntity customer = new CustomerEntity(0,name, surname);
        // Send a request to the server to create the new customer
        out.println("CREATE_CUSTOMER," + name + "," + surname);

//        String request = in.readLine();

        // Parse the request to extract the action and any relevant parameters
//        String[] requestParts = request.split(",");
//        String action = requestParts[0];

        // Read the response from the server
       String response = in.readLine();
       String[] responseParts = response.split(",");
        String action = responseParts[0];
        int idCustomer = Integer.parseInt(responseParts[1]);
        System.out.println("Id customer is " + idCustomer);

        if (action.equals("Name and surname is ok"))
        {

            Scanner scanner = new Scanner(System.in);
            while(true)
            {
                System.out.println("1. List All Tables in Restaurant");
                System.out.println("2. Make reservation");
                System.out.println("3. Update reservation");
                System.out.println("4. Cancel reservation");
                System.out.println("5. List Available Tables in Restaurant");
                System.out.println("6. Exit");
                System.out.println("Enter your choice: ");

                int choice = scanner.nextInt();

                if (choice == 1)
                {
                    out.println("GET_TABLES");
                    String line = in.readLine();
                    while (!line.equals("END")) {
                        String[] parts = line.split(",");
                        int idSeats = Integer.parseInt(parts[0]);
                        int numSeats = Integer.parseInt(parts[1]);
                        boolean forSmokers = Boolean.parseBoolean(parts[2]);
                        String slot = parts[3];

                        System.out.println("Table " + idSeats + ": " + numSeats + " seats, " + (forSmokers ? "for smokers" : "not for smokers" )+ " slot "+ slot);
                        line = in.readLine();

                    }
                    System.out.print("cos tam");

                }
                else if(choice == 2)
                {


                    System.out.println(idCustomer);
                    scanner.nextLine();
                    System.out.println("Enter id of table");
                    int tableId = scanner.nextInt();

                    out.println("MAKE_RESERVATION," + idCustomer + ","+ tableId);

                }
                else if (choice == 3)
                {
//                    update reservation
                }
                else if (choice == 4)
                {
//                    delete reservation
                }
                else if (choice == 5)
                {
                    out.println("GET_AVAILABLE_TABLES");
                    String line = in.readLine();
                    while (!line.equals("END")) {
                        String[] parts = line.split(",");
                        int idSeats = Integer.parseInt(parts[0]);
                        int numSeats = Integer.parseInt(parts[1]);
                        boolean forSmokers = Boolean.parseBoolean(parts[2]);
                        String slot = parts[3];

                        System.out.println("Table " + idSeats + ": " + numSeats + " seats, " + (forSmokers ? "for smokers" : "not for smokers" )+ " slot "+ slot);
                        line = in.readLine();

                    }
                    System.out.println("cos tam");
                }
                else if(choice == 6)
                {
                    break;
                }
            }

        }
       else
        {
            System.out.println("Error");
        }

        //socket.close();


    }
}
