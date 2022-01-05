package com.example.mainspingproject.controller;

import com.example.mainspingproject.dto.EmailDTO;
import com.example.mainspingproject.dto.RequestReportDTO;
import com.example.mainspingproject.dto.ResponseReportDTO;
import com.example.mainspingproject.service.ReportsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Report", description = "API for reports")
@RestController
@RequestMapping("/reports")
public class ReportsRestController {
    public final ReportsService reportsService;

    @Autowired
    public ReportsRestController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @GetMapping("/myreports/{page}/{email}")
    @PreAuthorize("hasAuthority('reports:read')")
    public List<ResponseReportDTO> getAvailableReports(@PathVariable("email") String email,
                                                       @PathVariable("page") Long page){
        return reportsService.getUserReportsByEmail(email, page);
    }

    @PostMapping("/add/{meetup_id}")
    @PreAuthorize("hasAuthority('reports:write')")
    public void addNewReportRequest(@Valid @RequestBody RequestReportDTO reportDTO, @PathVariable Long meetup_id){
        reportsService.addNewRequestWithReport(reportDTO,meetup_id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('reports:write')")
    public void editReport(@Valid @RequestBody RequestReportDTO reportDTO, @PathVariable Long id){
        reportsService.updateReport(reportDTO, id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('reports:write')")
    public void deleteReport(@PathVariable Long id){
        reportsService.deleteReportById(id);
    }
}
