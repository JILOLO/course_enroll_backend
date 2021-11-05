package com.mycompany.myapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_course")
@Data
@NoArgsConstructor // used by Hibernate
public class UserCourse {

    public UserCourse(User user, Course course) {
        this.user = user;
        this.course = course;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    // 有foreign key
    @JoinColumn(name = "user_id"/* column in user_course */, referencedColumnName = "id"/* id column in user table */)
    @ManyToOne // This class to User class.
    private User user; //user id

    @JoinColumn(name = "course_id"/* column in user_course */, referencedColumnName = "id"/* id column in course table */)
    @ManyToOne // This class to Course class.
    private Course course; //course id
}
