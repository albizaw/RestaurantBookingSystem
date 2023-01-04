package database;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Customers", schema = "", catalog = "")
public class CustomerEntity {
//    private Integer idCustomer;
//    private String name;
//    private String  surname;



    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idCustomer")
    private int idCustomer;

    @Basic
    @Column(name="name")
    private String name;

    @Basic
    @Column(name="surname")
    private String surname;

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public CustomerEntity(int idCustomer, String name, String surname) {
        this.idCustomer = idCustomer;
        this.name = name;
        this.surname = surname;
    }
}

