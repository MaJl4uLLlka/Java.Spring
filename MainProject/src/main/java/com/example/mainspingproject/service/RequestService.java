package com.example.mainspingproject.service;

import com.example.mainspingproject.dto.ResponseMeetupDTO;
import com.example.mainspingproject.dto.ResponseRequestDTO;
import com.example.mainspingproject.email.DefaultEmailService;
import com.example.mainspingproject.entity.Meetup;
import com.example.mainspingproject.entity.Request;
import com.example.mainspingproject.repository.MeetupRepository;
import com.example.mainspingproject.repository.RequestRepository;
import com.example.mainspingproject.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final MeetupRepository meetupRepository;
    private final DefaultEmailService emailService;

    @Autowired
    public RequestService(RequestRepository requestRepository, MeetupRepository meetupRepository, DefaultEmailService emailService) {
        this.requestRepository = requestRepository;
        this.meetupRepository = meetupRepository;
        this.emailService = emailService;
    }

    public List<ResponseRequestDTO> getRequestsByEmail(String email, Long page){
        return Mapper.mapAll(requestRepository.findAllByRequesterEmail(email, page), ResponseRequestDTO.class);
    }

    public void createNewRequest(Long meetup_id, String requester_email){
        requestRepository.createNewRequest(meetup_id, requester_email);
    }

    public void removeRequest(Long id){
        requestRepository.deleteById(id);
    }

    public List<ResponseRequestDTO> getAllRequestForAdminByEmail(String email, Long page){
        ArrayList<Meetup> meetups = new ArrayList<>(meetupRepository.findAllByCreatorEmail(email));
        List<Request> requests = new ArrayList<>();
        List<Request> requestPart = new ArrayList<>();
        for (int i = 0; i < meetups.size(); i++) {
            requests.addAll(requestRepository.findAllByMeetupId(meetups.get(i).getId()));
        }

        for (long i = (page * 8); i < (page * 8 + 8) && i < requests.size(); i++){
            requestPart.add(requests.get((int)i));
        }

        return Mapper.mapAll(requestPart, ResponseRequestDTO.class);
    }

    public void rejectRequestById(Long id) {
        try {
            requestRepository.rejectRequestById(id);
            Request request = requestRepository.findById(id).stream().findFirst().orElse(null);
            emailService.sendRejectNotify(request.getRequester_email());
        }
        catch (Exception e){
        }

    }

    public void approveRequestById(Long id){
        try{
            requestRepository.approveRequestById(id);
            Request request = requestRepository.findById(id).stream().findFirst().orElse(null);
            emailService.sendApproveNotify(request.getRequester_email());
        }
        catch (Exception e){
        }

    }
}
