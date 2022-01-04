package com.example.mainspingproject.service;

import com.example.mainspingproject.dto.CountMeetupsDTO;
import com.example.mainspingproject.dto.FilterDTO;
import com.example.mainspingproject.dto.ResponseMeetupDTO;
import com.example.mainspingproject.dto.SearchWithFilterDTO;
import com.example.mainspingproject.entity.Meetup;
import com.example.mainspingproject.exceptions.ResourceNotFoundException;
import com.example.mainspingproject.repository.MeetupRepository;
import com.example.mainspingproject.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.FloatControl;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MeetupServiceImpl implements MeetupService{
    private final MeetupRepository meetupRepository;

    @Autowired
    public MeetupServiceImpl(MeetupRepository meetupRepository) {
        this.meetupRepository = meetupRepository;
    }

    @Override
    public List<ResponseMeetupDTO> getAvailableMeetups(int page) {
       return Mapper.mapAll(meetupRepository.findEightMeetups(page), ResponseMeetupDTO.class);
    }

    @Override
    public void addNewMeetup(Meetup meetup) {
        meetupRepository.save(meetup);
    }

    @Override
    public void deleteMeetupById(Long id) {
        meetupRepository.deleteById(id);
    }

    @Override
    public void editMeetup(Meetup meetup, Long id) {
        meetup.setId(id);
        meetupRepository.save(meetup);
    }

    @Override
    public Meetup getById(Long id) throws ResourceNotFoundException {
        return meetupRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource not found"));
    }

    public CountMeetupsDTO getCountMeetups(){
        CountMeetupsDTO countMeetupsDTO = new CountMeetupsDTO();
        countMeetupsDTO.setCount(meetupRepository.countAllAvailableMeetups());
        return countMeetupsDTO;
    }

    public CountMeetupsDTO getCountMeetupsByDate(LocalDate date){
        CountMeetupsDTO countMeetupsDTO = new CountMeetupsDTO();
        countMeetupsDTO.setCount(meetupRepository.getCountMeetupsByDate(date));
        return countMeetupsDTO;
    }

    public List<ResponseMeetupDTO> getMeetupsByDate(Long page,LocalDate date){
        return Mapper.mapAll(meetupRepository.findAllByDate(page, date), ResponseMeetupDTO.class);
    }

    public CountMeetupsDTO getCountMeetupsByDateAndTime(SearchWithFilterDTO searchWithFilterDTO){
        CountMeetupsDTO countMeetupsDTO = new CountMeetupsDTO();
        countMeetupsDTO.setCount(meetupRepository.getCountMeetupByDateAndTime(searchWithFilterDTO.getDate(),
                searchWithFilterDTO.getStart_time(), searchWithFilterDTO.getEnd_time()));
        return  countMeetupsDTO;
    }

    public List<ResponseMeetupDTO> getMeetupsByDateAndTime(Long page,SearchWithFilterDTO searchWithFilterDTO){
        return Mapper.mapAll(meetupRepository.findAllByDateAndTime(
                page,
                searchWithFilterDTO.getDate(),
                searchWithFilterDTO.getStart_time(),
                searchWithFilterDTO.getEnd_time()
        ), ResponseMeetupDTO.class);
    }

    public CountMeetupsDTO getCountMeetupsByTime(FilterDTO filterDTO){
        CountMeetupsDTO countMeetupsDTO = new CountMeetupsDTO();
        countMeetupsDTO.setCount(meetupRepository.getCountMeetupsByTime(filterDTO.getStart(), filterDTO.getEnd()));
        return countMeetupsDTO;
    }

    public List<ResponseMeetupDTO> getMeetupsByTime(Long page, FilterDTO filterDTO){
        return Mapper.mapAll(meetupRepository.findAllByTimeBetween(page, filterDTO.getStart(), filterDTO.getEnd()), ResponseMeetupDTO.class);
    }
}
