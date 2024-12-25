package com.metuncc.society_app_api.Society;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.metuncc.society_app_api.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "societies")
@Data
public class Society {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToOne
    @JoinColumn(name = "president_id", unique = true) // Ensures unique association
    private User president;

    @ManyToMany
    @JoinTable(
            name = "society_user", // Join table name
            joinColumns = @JoinColumn(name = "society_id"), // Foreign key for Society
            inverseJoinColumns = @JoinColumn(name = "user_id") // Foreign key for User
    )
    @EqualsAndHashCode.Exclude // Prevents cyclic references in equals/hashCode
    @ToString.Exclude // Prevents cyclic references in toString()
    @JsonManagedReference // Breaks the cyclic reference for JSON serialization
    @JsonIgnore
    private Set<User> users = new HashSet<>();
}
