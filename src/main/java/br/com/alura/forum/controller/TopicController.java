package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TopicDto;
import br.com.alura.forum.controller.dto.TopicDtoDetails;
import br.com.alura.forum.controller.form.TopicForm;
import br.com.alura.forum.models.Course;
import br.com.alura.forum.models.Topic;
import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    CourseRepository courseRepository;

    @GetMapping()
    public List<TopicDto> index(String courseName) {
        List<Topic> topics;
        if (courseName == null) {
            topics = topicRepository.findAll();
        }
        else {
            topics = topicRepository.findByCourseName(courseName);
        }
        return TopicDto.map(topics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDtoDetails> show(@PathVariable Long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        return optionalTopic.map(topic -> {
            return ResponseEntity.ok(new TopicDtoDetails(topic));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<TopicDto> create(@Valid @RequestBody TopicForm topicForm, UriComponentsBuilder uriBuilder) {
        Topic topic = topicForm.map(courseRepository);
        topicRepository.save(topic);

        URI uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicDto(topic));
    }
}
