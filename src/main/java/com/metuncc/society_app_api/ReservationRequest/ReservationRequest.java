package com.metuncc.society_app_api.ReservationRequest;

import com.metuncc.society_app_api.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reservation_requests")
@Getter
@Setter
public class ReservationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The president who sends the request
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "president_id", nullable = false)
    private User president;

    // The admins who can see the request
    @ManyToMany
    @JoinTable(
            name = "reservation_request_admins",
            joinColumns = @JoinColumn(name = "reservation_request_id"),
            inverseJoinColumns = @JoinColumn(name = "admin_id")
    )
    private Set<User> admins = new HashSet<>();

    // Status of the request (default is "pending")
    @Column(nullable = false)
    private String status = "pending";

    // The date and time the request was created
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Event name
    @Column(nullable = false)
    private String eventName;

    // Event date
    @Column(nullable = false)
    private LocalDate eventDate;

    // Event time
    @Column(nullable = false)
    private LocalTime eventTime;

    // Event location
    @Column(nullable = false)
    private String location;

    // Society name
    @Column(nullable = false)
    private String societyName;
}
