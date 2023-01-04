package server;

import java.net.*;
import java.io.*;

import database.CustomerEntity;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.ReservationEntity;
import database.SittingEntity;
import database.Slots;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class Server {
    public static void main(String[] args) throws IOException {
        // Create a server socket and bind it to a port
        ServerSocket serverSocket = new ServerSocket(9000);

        System.out.println("Server listening on port 9000");
        //Map<Integer, ReservationEntity> reservations = new HashMap<>();

        // Listen for incoming connections
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New client connected");

            // Create a new thread for each incoming connection
            new Thread(() -> handleRequest(socket)).start();
        }
    }

    private static void handleRequest(Socket socket) {
        try {

            while(true) {


                SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
                Session session = sessionFactory.openSession();

                // Read from and write to the socket as needed
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Read the request from the client
                String request = in.readLine();

                int idCust;
                // Parse the request to extract the action and any relevant parameters
                String[] requestParts = request.split(",");
                String action = requestParts[0];
                    if (action.equals("CHECK_CUSTOMER_EXIST"))
                    {
                        session.beginTransaction();
                        Query query = session.createQuery("from database.CustomerEntity");
                        List<CustomerEntity> customers = query.list();
                        String name = requestParts[1];
                        String surname = requestParts[2];

                        for (CustomerEntity customer : customers)
                        {

                            if (customer.getName().equals(name) && customer.getSurname().equals(surname))
                            {
                                idCust = customer.getIdCustomer();
                                System.out.println("CUSTOMER_EXIST,"+idCust);
                                out.println("CUSTOMER_EXIST,"+idCust);
                            }
                        }

                    }
                else if (action.equals("CREATE_CUSTOMER")) {
                    // Create a new customer with the given name and surname
                    session.beginTransaction();
                    String name = requestParts[1];
                    String surname = requestParts[2];
                    CustomerEntity customer = new CustomerEntity(0, name, surname);


                   // int idCust = customer.getIdCustomer();


                    // Save the customer to the database
                   idCust = (int) session.save(customer);
                    session.getTransaction().commit();
                    session.close();
                    System.out.println("Id cust = " + idCust);
                    // Send a response to the client
                    out.println("Name and surname is ok,"+idCust);
                }
                else if (action.equals("GET_TABLES"))
                {
                    session.beginTransaction();
                    Query query = session.createQuery("from database.SittingEntity");
                    List<SittingEntity> tables = query.list();

                    System.out.println(tables);

                    for (SittingEntity table: tables)
                    {
                       // System.out.println(table.getSlot());
                        Slots slot = table.getSlot();
                        String slotString = "";
                        switch(slot)
                        {
                            case first:
                                slotString = "first";
                                break;
                            case second:
                                slotString = "second";
                                break;
                            case third:
                                slotString = "third";
                                break;
                        }
                        out.println(table.getId() + ","+ table.getSeats()+","+table.getForSmokers()+","+slotString);
                    }
                    out.println("END");
                    session.getTransaction().commit();
                    System.out.println("tables");
                }
                else if(action.equals("GET_AVAILABLE_TABLES"))
                {
                    session.beginTransaction();
                   // List<SittingEntity> availableTables = new ArrayList<>();
                    Query query = session.createQuery("from database.SittingEntity");
                    List<SittingEntity> tables = query.list();

                    for (SittingEntity table : tables)
                    {
                        if (!isTableBooked(table.getId()))
                        {
                            Slots slot = table.getSlot();
                            String slotString = "";
                            switch (slot)
                            {
                                case first:
                                    slotString = "first";
                                    break;
                                case second:
                                    slotString = "second";
                                    break;
                                case third:
                                    slotString = "third";
                                    break;
                            }
                            out.println(table.getId() + ","+ table.getSeats()+","+table.getForSmokers()+","+slotString);
                        }

                    }
                    out.println("END");
                    session.getTransaction().commit();
                    System.out.println("Available Tables");


                }
                else if (action.equals("MAKE_RESERVATION"))
                {
                    int customerId = Integer.parseInt(requestParts[1]);
                    int tableId = Integer.parseInt(requestParts[2]);

                    session.beginTransaction();

                        System.out.println("Jestem tutaj");
                    System.out.println(customerId);
                        ReservationEntity reservation = new ReservationEntity(0,customerId, tableId, Slots.first);
                        session.save(reservation);


                }
                else {
                    // Invalid action
                    System.out.println("Invalid action: " + action);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveCustomer(CustomerEntity customer) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();
        session.close();
    }

    private static boolean isTableBooked(int tableId)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from database.ReservationEntity");
        List<ReservationEntity> reservations = query.list();

        for (ReservationEntity reservation : reservations)
        {
            if (reservation.getIdSitting() == tableId)
            {
                return true;
            }
        }
        return false;
    }


}