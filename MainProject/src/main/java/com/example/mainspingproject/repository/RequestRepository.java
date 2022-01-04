package com.example.mainspingproject.repository;

import com.example.mainspingproject.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.websocket.server.PathParam;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(value = "CALL FIND_EIGHT_REQUESTS(:email , :page);", nativeQuery = true)
    List<Request> findAllByRequesterEmail(@PathParam("email") String email,@PathParam("page") Long page);

    @Query(value = "insert into requests(meetup_id, requester_email) values(:m_id,:req_email)", nativeQuery = true)
    void createNewRequest(@PathParam("m_id") Long m_id, @PathParam("req_email") String req_email);

    @Query(value = "select * from requests where meetup_id = :id and is_canceled = false and is_approved = false", nativeQuery = true)
    List<Request> findAllByMeetupId(@PathParam("id") Long id);

    @Query(value = "update requests set is_canceled = true where id = :id", nativeQuery = true)
    void rejectRequestById(@PathParam("id") Long id);

    @Query(value = "update requests set is_approved = true where id = :id", nativeQuery = true)
    void approveRequestById(@PathParam("id") Long id);
}