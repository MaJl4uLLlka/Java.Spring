package com.example.mainspingproject.service;

import com.example.mainspingproject.dto.RequestReportDTO;
import com.example.mainspingproject.dto.ResponseReportDTO;
import com.example.mainspingproject.dto.ResponseRequestDTO;
import com.example.mainspingproject.entity.Meetup;
import com.example.mainspingproject.entity.Report;
import com.example.mainspingproject.entity.Request;
import com.example.mainspingproject.repository.MeetupRepository;
import com.example.mainspingproject.repository.ReportRepository;
import com.example.mainspingproject.repository.RequestRepository;
import com.example.mainspingproject.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportsService {
    private final ReportRepository reportRepository;
    private final MeetupRepository meetupRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public ReportsService(ReportRepository reportRepository, MeetupRepository meetupRepository, RequestRepository requestRepository) {
        this.reportRepository = reportRepository;
        this.meetupRepository = meetupRepository;
        this.requestRepository = requestRepository;
    }

    public List<ResponseReportDTO> getUserReportsByEmail(String email, Long page){
        return Mapper.mapAll(reportRepository.findAllByEmail(email, page),ResponseReportDTO.class);
    }

    public void addNewRequestWithReport(RequestReportDTO reportDTO, Long id){
        requestRepository.createNewRequest(id, reportDTO.getReporter());
        Report newReport = Mapper.map(reportDTO, Report.class);
       reportRepository.save(newReport);
    }

    public void updateReport(RequestReportDTO reportDTO, Long id){
        Report report = Mapper.map(reportDTO, Report.class);
        report.setId(id);
        reportRepository.save(report);
    }

    public void deleteReportById(Long id){
        reportRepository.deleteById(id);
    }
}
