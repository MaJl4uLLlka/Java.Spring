package com.example.mainspingproject.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "topic", nullable = false, length = 100)
    private String topic;

    @Column(name = "reporter_email", nullable = false)
    private String reporter;

    @OneToOne
    @JoinColumn(name = "id", nullable = false)
    private Request request;
}