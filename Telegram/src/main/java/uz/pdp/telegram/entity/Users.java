package uz.pdp.telegram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Users extends BaseEntity {
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String password;


    public Users(String firstName, String lastName, String username, String email, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
