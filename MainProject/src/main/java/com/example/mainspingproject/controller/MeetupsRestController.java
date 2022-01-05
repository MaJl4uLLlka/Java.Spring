package com.example.mainspingproject.controller;

import com.example.mainspingproject.aop.LogAnnotation;
import com.example.mainspingproject.dto.*;
import com.example.mainspingproject.entity.Meetup;
import com.example.mainspingproject.service.MeetupServiceImpl;
import com.example.mainspingproject.util.Mapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Tag(name = "Meetups", description = "API for meetups")
@RestController
@RequestMapping("/meetups")
public class MeetupsRestController {

    private final MeetupServiceImpl meetupService;

    @Autowired
    public MeetupsRestController(MeetupServiceImpl meetupService) {
        this.meetupService = meetupService;
    }

    @GetMapping("/{page}")
    @PreAuthorize("hasAuthority('meetups:read')")
    public List<ResponseMeetupDTO> getMeetups(@PathVariable int page){
        return meetupService.getAvailableMeetups(page);
    }

    @GetMapping("/count")
    @PreAuthorize("hasAuthority('meetups:read')")
    public CountMeetupsDTO getCountMeetups(){
        return meetupService.getCountMeetups();
    }

    @PostMapping("/search/{page}")
    @PreAuthorize("hasAuthority('meetups:read')")
    public List<ResponseMeetupDTO> getMeetupsByDate(@PathVariable("page") Long page, @RequestBody DateDTO date){
        return meetupService.getMeetupsByDate(page, date.getDate());
    }

    @PostMapping("/search/count")
    @PreAuthorize("hasAuthority('meetups:read')")
    public CountMeetupsDTO getCountMeetupsByDate(@RequestBody DateDTO date){
        return meetupService.getCountMeetupsByDate(date.getDate());
    }

    @PostMapping("/search/filter/count")
    @PreAuthorize("hasAuthority('meetups:read')")
    public CountMeetupsDTO getCountMeetupByDateAndTime(@RequestBody SearchWithFilterDTO searchWithFilterDTO){
        return meetupService.getCountMeetupsByDateAndTime(searchWithFilterDTO);
    }

    @PostMapping("/search/filter/{page}")
    @PreAuthorize("hasAuthority('meetups:read')")
    public List<ResponseMeetupDTO> getMeetupsByDateAndTime(@PathVariable Long page, @RequestBody SearchWithFilterDTO searchWithFilterDTO){
        return meetupService.getMeetupsByDateAndTime(page, searchWithFilterDTO);
    }

    @PostMapping("/filter/count")
    @PreAuthorize("hasAuthority('meetups:read')")
    public CountMeetupsDTO getMeetupsTime(@RequestBody FilterDTO filterDTO){
        return meetupService.getCountMeetupsByTime(filterDTO);
    }

    @PostMapping("/filter/{page}")
    @PreAuthorize("hasAuthority('meetups:read')")
    public List<ResponseMeetupDTO> getMeetupsTime(@PathVariable Long page,@RequestBody FilterDTO filterDTO){
        return meetupService.getMeetupsByTime(page, filterDTO);
    }

    @LogAnnotation
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('meetups:write')")
    public void createMeetup(@Valid @RequestBody RequestMeetupDTO meetupDTO){
        meetupService.addNewMeetup(Mapper.map(meetupDTO, Meetup.class));
    }

    @LogAnnotation
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('meetups:write')")
    public void updateMeetup(@Valid @RequestBody RequestMeetupDTO meetupDTO, @PathVariable Long id){
        meetupService.editMeetup(Mapper.map(meetupDTO, Meetup.class),id);
    }

    @LogAnnotation
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('meetups:write')")
    public void removeMeetup(@PathVariable  Long id){
        meetupService.deleteMeetupById(id);
    }
}
