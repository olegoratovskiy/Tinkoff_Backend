package com.example.demo.service;


import com.example.demo.entity.EducationYear;
import com.example.demo.entity.Subject;
import com.example.demo.repository.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SubjectServiceTest {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;


    @Autowired
    private EducationYearService educationYearService;


    @Test
    void testCreateSubject() {
        Subject mockSubject = new Subject();
        mockSubject.setId(1L);
        mockSubject.setName("математика");

        Long mockYearId = 1L;

        EducationYear mockEducationYear = new EducationYear();
        mockEducationYear.setId(mockYearId);

        SubjectRepository subjectRepositoryMock = mock(SubjectRepository.class);
        when(subjectRepositoryMock.save(any(Subject.class))).thenReturn(mockSubject);


        EducationYearService educationYearServiceMock = mock(EducationYearService.class);
        when(educationYearServiceMock.getEducationYear(anyLong())).thenReturn(mockEducationYear);

        SubjectService subjectService = new SubjectService(subjectRepositoryMock, educationYearServiceMock);


        Subject createdSubject = subjectService.createSubject(mockSubject, mockYearId);


        assertNotNull(createdSubject);
        assertEquals(mockSubject.getId(), createdSubject.getId());
        assertEquals(mockSubject.getName(), createdSubject.getName());

    }


}
