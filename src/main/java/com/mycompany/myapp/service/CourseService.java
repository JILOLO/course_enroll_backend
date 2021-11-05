package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.UserCourse;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.repository.UserCourseRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.mapper.CourseMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {

    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private UserCourseRepository userCourseRepository;
    private CourseMapper courseMapper;

    /**
     * 1. User exists
     * 2. Course exists
     * 3. UserCourse combination not exists(course not enrolled by the user yet)
     * 4. Save UserCourse to DB
     * @param username
     * @param courseName
     */

    public void enrollCourse(String username, String courseName) {
        UserCourse userCourse = getUserCourse(username, courseName);
        userCourseRepository
            .findFirstByCourseAndUser(userCourse.getCourse(), userCourse.getUser())
            .ifPresent(
                existingCourse -> {
                    throw new IllegalArgumentException("Course already enrolled");
                }
            );
        userCourseRepository.save(userCourse);
    }

    private UserCourse getUserCourse(String username, String courseName) {
        Optional<User> optionalUser = userRepository.findOneByLogin(username);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("No such user!"));

        Course course = courseRepository.findByCourseName(courseName).orElseThrow(() -> new IllegalArgumentException("No such course!"));

        return new UserCourse(user, course);
    }

    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(course -> courseMapper.convert(course)).collect(Collectors.toList());
    }

    /**
     * 1. user exists
     * 2. course exists
     * 3. drop
     * @param username
     * @param courseName
     */
    public void dropCourse(String username, String courseName) {
        UserCourse userCourse = getUserCourse(username, courseName);
        // userCourseRepository.delete(userCourse) will not work here
        // As it is deleting by primary key which is absent in the variable 'userCourse'
        userCourseRepository.deleteByCourseAndUser(userCourse.getCourse(), userCourse.getUser());
    }

    /**
     * 1. user exists
     * 2. Find UserCourse by user
     * 3. Convert to list of CourseDTO
     * @param username
     * @return
     */
    public List<CourseDTO> getEnrolledCourses(String username) {
        User user = userRepository.findOneByLogin(username).orElseThrow(() -> new UsernameNotFoundException("No such user!"));
        List<UserCourse> userCourses = userCourseRepository.findAllByUser(user);
        return userCourses
            .stream()
            .map(userCourse -> userCourse.getCourse())
            .map(course -> courseMapper.convert(course))
            .collect(Collectors.toList());
    }
}
