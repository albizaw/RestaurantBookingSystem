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

        int idCustomer = 0 ;
        out.println("CHECK_CUSTOMER_EXIST," + name + "," + surname);
        String response = in.readLine();

        String[] responseParts = response.split(",");
        String action = responseParts[0];

        if (action.equals("CUSTOMER_EXIST")) {
            // Customer with the given name and surname exists on the server
            idCustomer = Integer.parseInt(responseParts[1]);
            System.out.println("Customer exists with ID: " + idCustomer);
        } else if (action.equals("CUSTOMER_NOT_EXIST")) {
            // Customer with the given name and surname does not exist on the server
            System.out.println("Customer does not exist");

            // Create a new customer with the given name and surname
            CustomerEntity customer = new CustomerEntity(0,name, surname);

            // Send a request to the server to create the new customer
            out.println("CREATE_CUSTOMER," + name + "," + surname);

            // Read the response from the server
            response = in.readLine();
            responseParts = response.split(",");
            action = responseParts[0];
            idCustomer = Integer.parseInt(responseParts[1]);
            System.out.println("Id customer is " + idCustomer);
        }



        if (action.equals("Name and surname is ok") || action.equals("CUSTOMER_EXIST"))
        {

            Scanner scanner = new Scanner(System.in);
            while(true)
            {
                System.out.println("1. List All Tables in Restaurant");
                System.out.println("2. List Available Tables in Restaurant");
                System.out.println("3. Make reservation");
                System.out.println("4. Cancel reservation");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");

                int choice = scanner.nextInt();

                if (choice == 1)
                {
                    out.println("GET_TABLES");
                    System.out.println("I am here");
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
                else if(choice == 3)
                {


                    System.out.println(idCustomer);
                    scanner.nextLine();
                    System.out.println("Enter id of table");
                    int tableId = scanner.nextInt();

                    out.println("MAKE_RESERVATION," + idCustomer + ","+ tableId);

                }
                else if (choice == 4)
                {
//delete reservations
                    out.println("GET_RESERVATIONS,"+idCustomer);
                    //resonse from the server
                     response = in.readLine();
                     responseParts = response.split(",");
                     action = responseParts[0];

                     if (response.startsWith("CUSTOMER_HAVE_NO_RESERVATIONS"))
                     {
                         System.out.println("Customer has no reservations");
                     }
                     else if (response.startsWith("RESERVATION"))
                     {
                        while (!response.equals("END"))
                        {
                            int idReservation = Integer.parseInt(responseParts[1]);
                            int idTable = Integer.parseInt(responseParts[2]);
                            String slot = responseParts[3];


                            System.out.println("Rezerwacja id["+ idReservation +"] idTable["+idTable+"] slot "+slot);

                            response = in.readLine();
                            responseParts = response.split(",");
                        }

                        //tutaj prosi o wybor id rezerwacji do usuniecia
                         System.out.print("Enter reservation id: ");
                         int idReservation = scanner.nextInt();

                         out.println("DELETE_RESERVATION,"+idReservation+","+idCustomer);
                         response = in.readLine();
                         if (response.equals("RESERVATION_NOT_FOUND")) {
                             System.out.println("Reservation not found.");
                         } else if (response.equals("RESERVATION_DELETED")) {
                             System.out.println("Reservation deleted.");
                         }
                     }
                }
                else if (choice == 2)
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
                else if(choice == 5)
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
