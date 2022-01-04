package com.example.mainspingproject.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "requester_email", nullable = false)
    private String requester_email;

    @Column(name = "is_canceled")
    private Boolean isCanceled;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @ManyToOne(optional = false)
    @JoinColumn(name = "meetup_id", nullable = false)
    private Meetup meetup;

    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;

}