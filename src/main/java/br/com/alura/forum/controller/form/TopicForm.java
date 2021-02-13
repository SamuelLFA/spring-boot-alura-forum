package br.com.alura.forum.controller.form;

import br.com.alura.forum.models.Course;
import br.com.alura.forum.models.Topic;
import br.com.alura.forum.repository.CourseRepository;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TopicForm {

    @NotNull @NotEmpty @Length(min = 5)
    private String title;
    @NotNull @NotEmpty @Length(min = 10)
    private String message;
    @NotNull @NotEmpty
    private String courseName;

    public Topic map(CourseRepository courseRepository) {
        Course course = courseRepository.findByName(courseName);
        return new Topic(title, message, course);
    }
}
