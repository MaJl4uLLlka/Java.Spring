package com.example.mainspingproject.service;

import com.example.mainspingproject.dto.ResponseMeetupDTO;
import com.example.mainspingproject.entity.Meetup;
import com.example.mainspingproject.exceptions.ResourceNotFoundException;

import java.util.List;

public interface MeetupService {
    List<ResponseMeetupDTO> getAvailableMeetups(int page);
    void addNewMeetup(Meetup meetup);
    void deleteMeetupById(Long id);
    void editMeetup(Meetup meetup, Long id);
    Meetup getById(Long id) throws ResourceNotFoundException;
}
