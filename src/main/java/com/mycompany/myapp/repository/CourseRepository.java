package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Course;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course/* entity class*/, Long/* entity class primary key type*/> {
    Optional<Course> findByCourseName(String courseName);
}
