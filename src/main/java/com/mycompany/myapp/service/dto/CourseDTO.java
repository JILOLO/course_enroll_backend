package com.mycompany.myapp.service.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO: data transfer object
 * define a model to communicate between frontend and backend
 */

@Data
@Builder
public class CourseDTO {

    private String courseName;
    private String courseLocation;
    private String courseContent;
    private Long teacherId;
}
