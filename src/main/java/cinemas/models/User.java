package cinemas.models;

import cinemas.converters.ZonedDateTimeConverter;
import cinemas.enums.RoleEnum;
import cinemas.models.common.SoftDeletableEntity;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "users")
public class User extends SoftDeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String password;
    @Column(name = "full_name")
    private String fullName;
    private String phone;
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime birthday;
    @Column(name = "photo_url")
    private String photoUrl;
    @Enumerated(EnumType.ORDINAL)
    private RoleEnum role; // 0 = manager, 1 = customer
    @Column(name = "reward_points")
    private Integer rewardPoints = 0;
    @Convert(converter = ZonedDateTimeConverter.class)
    @Column(name = "hire_date")
    private ZonedDateTime hireDate;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public ZonedDateTime getHireDate() {
        return hireDate;
    }

    public void setHireDate(ZonedDateTime hireDate) {
        this.hireDate = hireDate;
    }
}
