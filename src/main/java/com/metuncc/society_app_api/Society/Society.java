package com.metuncc.society_app_api.Society;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="societies")
@Data
public class Society {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
}
