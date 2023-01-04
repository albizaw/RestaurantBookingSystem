package database;

import javax.persistence.*;

@Entity
@Table(name = "testEnt", schema = "", catalog = "")
public class TestEntity {
    private Integer id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Basic
    @Column(name = "name")

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
