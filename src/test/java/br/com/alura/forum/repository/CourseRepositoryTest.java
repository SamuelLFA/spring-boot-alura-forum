package br.com.alura.forum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.forum.models.Course;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CourseRepositoryTest {
    
    @Autowired
    private CourseRepository courseRepository;

    @Test
    void shouldBeLoadACourseWhenGetByName() {
        var courseName = "HTML 5";

        var html5 = new Course(courseName, "Web");
        courseRepository.save(html5);

        var course = courseRepository.findByName(courseName);
        assertNotNull(course);
        assertEquals(course.getName(), courseName);
    }

    @Test
    void shouldBeNotLoadACourseWhenNameIsInvalid() {
        var courseName = "JPA";

        var course = courseRepository.findByName(courseName);
        assertNull(course);
    }
}
