package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CourseService;
import com.mycompany.myapp.service.dto.CourseDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CourseController {

    private CourseService courseService;

    //2. 实现学生选课功能
    // http method - post
    // URL - "student/course"
    // request info? path variable? request body?
    //   - as a path variable
    // request header = jwt token (you are you)
    // http status - 204
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/student/course/{courseName}")
    public void enrollCourse(@PathVariable String courseName) {
        String username = getUsername();
        courseService.enrollCourse(username, courseName);
    }

    // 1. 列出所有已选课程
    @GetMapping("/all-courses")
    public List<CourseDTO> getAllCourse() {
        return courseService.getAllCourses();
    }

    // 3. drop
    // http method - delete
    // url - "/student/course"
    // Request info? Path variable? Request body? - path variable
    //response body? list of courseDTO
    // request header - JWT token(how are you)
    //HTTP status - 204
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/student/course/{courseName}")
    public void dropCourse(@PathVariable String courseName) {
        String username = getUsername();
        courseService.dropCourse(username, courseName);
    }

    //3. 列出一个学生选了那些课
    // http method - get
    // URL - "student/courses"
    // request info? path variable? request body?
    //   - as a path variable {student}
    // request header = jwt token (you are you)
    // response body list of courseDTO
    // http status - 200
    @GetMapping("/student/courses")
    public List<CourseDTO> getStudentCourse() {
        String username = getUsername();
        return courseService.getEnrolledCourses(username);
    }

    private String getUsername() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
