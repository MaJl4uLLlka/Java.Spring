package com.example.mainspingproject.controller;


import com.example.mainspingproject.aop.LogAnnotation;
import com.example.mainspingproject.dto.EmailDTO;
import com.example.mainspingproject.dto.RegisterNewRequestDTO;
import com.example.mainspingproject.dto.ResponseRequestDTO;
import com.example.mainspingproject.email.DefaultEmailService;
import com.example.mainspingproject.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestsRestController {
    private final RequestService requestService;

    @Autowired
    public RequestsRestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/myrequests/{page}/{email}")
    @PreAuthorize("hasAuthority('requests:read')")
    public List<ResponseRequestDTO> getUserRequests(@PathVariable("page") Long page,@PathVariable("email") String email){
        return requestService.getRequestsByEmail(email, page);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('requests:write')")
    public void createRequest(@Valid @RequestBody RegisterNewRequestDTO requestDTO){
        requestService.createNewRequest(requestDTO.getMeetup_id(), requestDTO.getRequester_email());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('requests:write')")
    public void deleteRequest(@PathVariable Long id){
        requestService.removeRequest(id);
    }

    @GetMapping("/all/{page}/{email}")
    @PreAuthorize("hasAuthority('requests:solve')")
    public List<ResponseRequestDTO> getAllRequestsForAdmin(@PathVariable("page") Long page ,@PathVariable("email") String email){
        return requestService.getAllRequestForAdminByEmail(email, page);
    }

    @LogAnnotation
    @PutMapping("/update/reject/{id}")
    @PreAuthorize("hasAuthority('requests:solve')")
    public void rejectRequest(@PathVariable Long id){
        requestService.rejectRequestById(id);
    }

    @LogAnnotation
    @PutMapping("/update/approve/{id}")
    @PreAuthorize("hasAuthority('requests:solve')")
    public void approveRequest(@PathVariable Long id){
        requestService.approveRequestById(id)
        ;
    }
}