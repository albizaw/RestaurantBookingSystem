package database;

import javax.persistence.*;

@Entity
@Table(name = "Reservations", schema = "", catalog = "")
public class ReservationEntity {
//    private int idReservation;
//se
//
//    private CustomerEntity customer;
//
//    private int Slot;



    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idReservation")
    private int id;

    @Basic
    @Column(name="idCustomer")
    private Integer idClient;

    @Basic
    @Column(name="idSitting")
    private Integer idSitting;



    @Basic
    @Column(name="slot")
    private Slots slot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Integer getIdSitting() {
        return idSitting;
    }

    public void setIdSitting(Integer idSitting) {
        this.idSitting = idSitting;
    }

    public Slots getSlot() {
        return slot;
    }

    public void setSlot(Slots slot) {
        this.slot = slot;
    }

    public  ReservationEntity()
    {

    }

    public ReservationEntity(int id, Integer idClient, Integer idSitting, Slots slot) {
        this.id = id;
        this.idClient = idClient;
        this.idSitting = idSitting;
        this.slot = slot;
    }
}
