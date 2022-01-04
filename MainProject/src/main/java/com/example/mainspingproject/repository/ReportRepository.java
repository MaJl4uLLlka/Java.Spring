package com.example.mainspingproject.repository;

import com.example.mainspingproject.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.websocket.server.PathParam;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query(value = "CALL FIND_EIGHT_REPORTS(?1, ?2)", nativeQuery = true)
    List<Report> findAllByEmail(String email, Long page);
}