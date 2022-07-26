package com.pmhung2.jwtdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
    @NotBlank
    @Size(min = 3, max = 50)
    private String userName;
    private String password;
    private String image;
    private String role;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();

    public Users(@NotBlank
                 @Size(min = 3, max = 50) String name,
                 @NotBlank
                 @Size(min = 3, max = 50) String userName,
                 String encode,
    String role) {
        this.name = name;
        this.userName = userName;
        this.password = encode;
        this.role = role;
    }

    public Users(String name, String userName, String encode, String role, String imageName) {
        this.name = name;
        this.userName = userName;
        this.password = encode;
        this.role = role;
        this.image = imageName;
    }
}
