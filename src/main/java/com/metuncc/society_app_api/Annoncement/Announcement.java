package com.metuncc.society_app_api.Annoncement;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.metuncc.society_app_api.Society.Society;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name="announcements")
@Data
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String content;
    LocalDateTime date;
    String location;
    String posterUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="society_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Society society;
}
