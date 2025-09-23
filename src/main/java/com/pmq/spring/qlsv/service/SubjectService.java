package com.pmq.spring.qlsv.service;

import com.pmq.spring.qlsv.dto.response.SubjectResponse;
import com.pmq.spring.qlsv.exception.AppException;
import com.pmq.spring.qlsv.exception.ErrorCode;
import com.pmq.spring.qlsv.model.Subject;
import com.pmq.spring.qlsv.model.SubjectList;
import com.pmq.spring.qlsv.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectService {
    private SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }

    public List<SubjectList> getAllSubjectList() {
        return subjectRepository.findAll()
                .stream()
                .map(sj -> new SubjectList(sj.getSubCode(), sj.getSubName(), sj.getSubNum()))
                .toList();
    }

    public SubjectResponse saveSubject(Subject subject){
        subject.setRatioProcess(0.3);
        subject.setRatioComponent(0.7);

        if(subjectRepository.existsById(subject.getSubCode()))
            throw  new AppException(ErrorCode.SUBJECT_EXISTED);
        if(subjectRepository.existsBySubName(subject.getSubName()))
            throw new AppException(ErrorCode.SUBJECT_EXISTED);
        Subject created = subjectRepository.save(subject);
        return new SubjectResponse(
                created.getSubCode(),
                created.getSubName(),
                created.getSubNum(),
                created.getRatioProcess(),
                created.getRatioComponent()
        );
    }
    public SubjectResponse updateSubject(String subCode, Subject subjectDetails){
        return subjectRepository.findById(subCode).map(subject -> {
            subject.setSubName(subjectDetails.getSubName());
            subject.setSubNum(subjectDetails.getSubNum());
            Subject updated = subjectRepository.save(subject);

            SubjectResponse response = new SubjectResponse();
            response.setSubName(updated.getSubName());
            response.setSubNum(updated.getSubNum());
            return response;
        }).orElseThrow(() -> new RuntimeException("Subject not found with code: " + subCode));
    }
    public void deleteSubject(String subCode){
        subjectRepository.deleteById(subCode);
    }
}
