package com.example.demo.service;

import com.example.demo.entity.EducationYear;
import com.example.demo.repository.EducationYearRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class YearServiceTest {

    @Autowired
    private EducationYearService educationYearService;

    @MockBean
    private EducationYearRepository educationYearRepository;

    @Test
    void testGetEducationYear() {
        Long educationYear = 1L;
        EducationYear expectedYear = new EducationYear();
        expectedYear.setId(educationYear);

        when(educationYearRepository.findById(educationYear)).thenReturn(Optional.of(expectedYear));

        EducationYear actualYear = educationYearService.getEducationYear(educationYear);

        verify(educationYearRepository, times(1)).findById(educationYear);
    }

    @Test
    void testGetEducationYearsNotEmpty() {
        var educationYearFirst = new EducationYear();
        var educationYearSecond = new EducationYear();

        List<EducationYear> expectedYears = new ArrayList<>();
        expectedYears.add(educationYearFirst);
        expectedYears.add(educationYearSecond);
        when(educationYearRepository.findAll()).thenReturn(expectedYears);
        List<EducationYear> actualYears = educationYearService.getAllEducationYear();
        assertEquals(expectedYears, actualYears);
    }

    @Test
    void testGetEducationYearIsEmpty() {
        when(educationYearRepository.findAll()).thenReturn(Collections.emptyList());
        List<EducationYear> actualYears = educationYearService.getAllEducationYear();
        assertEquals(Collections.emptyList(), actualYears);
    }


}


