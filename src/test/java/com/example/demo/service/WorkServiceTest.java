package com.example.demo.service;

import com.example.demo.entity.Subject;
import com.example.demo.entity.Work;
import com.example.demo.repository.WorkRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WorkServiceTest {

    @Mock
    private WorkRepository workRepository;

    @Autowired
    private SubjectService subjectService;

    @InjectMocks
    private WorkService workService;



    @Test
    void testCreateWork() {

        Work mockWork = new Work();
        mockWork.setId(1L);
        mockWork.setTypeOfWork("Abstracts");

        Long mockSubjectId = 1L;

        Subject mockSubject = new Subject();
        mockSubject.setId(mockSubjectId);

        WorkRepository workRepositoryMock = mock(WorkRepository.class);
        when(workRepositoryMock.save(any(Work.class))).thenReturn(mockWork);


        SubjectService subjectServiceMock = mock(SubjectService.class);
        when(subjectServiceMock.getSubject(anyLong())).thenReturn(mockSubject);

        WorkService workService = new WorkService(workRepositoryMock, subjectServiceMock);

        Work createdWork = workService.createWork(mockWork, mockSubjectId);


        assertNotNull(createdWork);
        assertEquals(mockWork.getId(), createdWork.getId());
        assertEquals(mockWork.getTypeOfWork(), createdWork.getTypeOfWork());
    }




}
