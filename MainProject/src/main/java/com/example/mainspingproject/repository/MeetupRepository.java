package com.example.mainspingproject.repository;

import com.example.mainspingproject.entity.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MeetupRepository extends JpaRepository<Meetup, Long> {
    @Query(value="select * from Meetup m where upper(m.place) like upper(concat('%', :place, '%'))", nativeQuery = true)
    Collection<Meetup> findByPlaceContainsIgnoreCase(@PathParam("place") String place);

    @Query(value = "select count(*) from Meetups m where datediff(m.date,current_date) > 0 or ((datediff(m.date,current_date) = 0 and timediff(m.time, current_time) > 0));", nativeQuery = true)
    Long countAllAvailableMeetups();

    @Query(value = "select * from Meetups m where datediff(m.date,current_date) > 0 or ((datediff(m.date,current_date) = 0 and timediff(m.time, current_time) > 0));", nativeQuery = true)
    List<Meetup> findAllAvailableMeetups();

    @Query("select count(m) from Meetup m where m.date = :date")
    Long countMeetupsByDate(@PathParam("date")LocalDate date);

    @Modifying
    @Query("delete from Meetup m where m.id = :id")
    void deleteById(@PathParam("id") Long id);

    @Query(value = "select * from Meetups m where m.creator_email= :creator_email", nativeQuery = true)
    List<Meetup> findAllByCreatorEmail(@PathParam("creator_email") String creator_email);

    @Query(value = "select count(*) from Meetups m where m.creator_email= :creator_email", nativeQuery = true)
    int countMeetupsByCreatorEmail(@PathParam("creator_email") String creator_email);

    @Query(value = "CALL FIND_EIGHT_MEETUPS(:page);", nativeQuery = true)
    Collection<Meetup> findEightMeetups(@Param("page") long page);

    @Query(value = "CALL FIND_EIGHT_MEETUP_BY_DATE(?1 , ?2)", nativeQuery = true)
    List<Meetup> findAllByDate(Long page, LocalDate date);

    @Query(value = "CALL GET_COUNT_MEETUP_BY_DATE(?1)", nativeQuery = true)
    Long getCountMeetupsByDate(LocalDate date);

    @Query(value = "CALL GET_COUNT_MEETUP_BY_DATE_AND_TIME(?1, ?2, ?3)", nativeQuery = true)
    Long getCountMeetupByDateAndTime(LocalDate date , LocalTime start_time, LocalTime end_time);

    @Query(value = "CALL FIND_EIGHT_MEETUP_BY_DATE_AND_TIME(?1 , ?2, ?3, ?4)", nativeQuery = true)
    List<Meetup> findAllByDateAndTime(Long page, LocalDate date, LocalTime start_time, LocalTime end_time);

    @Query(value = "select count(*) from meetups m where (datediff(m.date,current_date) > 0 or ((datediff(m.date,current_date) = 0 " +
            "and timediff(m.time, current_time) > 0))) and time between ?1 and ?2;", nativeQuery = true)
    Long getCountMeetupsByTime(LocalTime start, LocalTime end);

    @Query(value = "CALL FIND_EIGHT_BY_TIME(?1 , ?2 , ?3)", nativeQuery = true)
    List<Meetup> findAllByTimeBetween(Long page, LocalTime start, LocalTime end);
}