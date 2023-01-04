package app;

import database.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main
{

    public static void main(String [] args)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();


       // CustomerEntity customer = new CustomerEntity(0,"Jankos","Ziarkowski");
      //  session.save(customer);

        SittingEntity sitting = new SittingEntity(0,2,false,Slots.first);
        SittingEntity sitting1 = new SittingEntity(0,4,true,Slots.third);
        session.save(sitting);
        session.save(sitting1);
        //ReservationEntity reservation = new ReservationEntity(0,customer.getIdCustomer(),sitting.getId(),sitting.getSlot());
       // session.save(reservation);

        session.getTransaction().commit();
        session.close();


    }
}