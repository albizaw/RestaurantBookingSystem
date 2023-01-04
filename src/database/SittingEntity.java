package database;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sitting", schema = "", catalog = "")
public class SittingEntity {
//    private Integer idResTable;
//    private Integer numSeats;
//    private Boolean forSmokers;
//
//    private Slots slot;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idResTable")
    private int id;


    @Basic
    @Column(name = "seats")
    private int seats;

    @Basic
    @Column(name="forSmokers")
    private Boolean forSmokers;

    @Basic
    @Column(name="slot")
    private Slots slot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Boolean getForSmokers() {
        return forSmokers;
    }

    public void setForSmokers(Boolean forSmokers) {
        this.forSmokers = forSmokers;
    }

    public Slots getSlot() {
        return slot;
    }

    public void setSlot(Slots slot) {
        this.slot = slot;
    }

    public SittingEntity() {
    }

    public SittingEntity(int id, int seats, Boolean forSmokers, Slots slot) {
        this.id = id;
        this.seats = seats;
        this.forSmokers = forSmokers;
        this.slot = slot;
    }
}
