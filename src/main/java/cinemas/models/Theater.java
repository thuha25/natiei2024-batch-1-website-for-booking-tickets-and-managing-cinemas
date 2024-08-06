package cinemas.models;

import cinemas.models.common.CreationUpdationAuditableEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "theaters")
public class Theater extends CreationUpdationAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @Column(name = "address_vn")
    private String addressVn;
    @Column(name = "address_en")
    private String addressEn;
    private String location; // Embedded link for Google Maps

    @ManyToOne
    @JoinColumn(name = "city_id", insertable = false, updatable = false)
    private City city;

    @OneToMany(mappedBy = "theater", orphanRemoval = true)
    private Set<Screen> screens;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressVn() {
        return addressVn;
    }

    public void setAddressVn(String addressVn) {
        this.addressVn = addressVn;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Screen> getScreens() {
        return screens;
    }

    public void setScreens(Set<Screen> screens) {
        this.screens = screens;
    }
}
