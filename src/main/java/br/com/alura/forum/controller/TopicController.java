package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TopicDto;
import br.com.alura.forum.controller.dto.TopicDtoDetails;
import br.com.alura.forum.controller.form.TopicForm;
import br.com.alura.forum.controller.form.TopicUpdateForm;
import br.com.alura.forum.models.Topic;
import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    CourseRepository courseRepository;

    @GetMapping()
    public Page<TopicDto> index(@RequestParam(required = false) String courseName,
                                @PageableDefault(sort = "id",
                                        direction = Sort.Direction.ASC,
                                        page = 0,
                                        size = 10) Pageable pagination) {
        Page<Topic> topics;
        if (courseName == null) {
            topics = topicRepository.findAll(pagination);
        }
        else {
            topics = topicRepository.findByCourseName(courseName, pagination);
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
    @Transactional
    public ResponseEntity<TopicDto> create(@Valid @RequestBody TopicForm topicForm, UriComponentsBuilder uriBuilder) {
        Topic topic = topicForm.map(courseRepository);
        topicRepository.save(topic);

        URI uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicDto(topic));
    }
    
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicDto> update(@PathVariable Long id, @Valid @RequestBody TopicUpdateForm topicForm) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        return optionalTopic.map(topic -> {
            Topic updatedTopic = topicForm.update(id, topicRepository);
            return ResponseEntity.ok(new TopicDto(updatedTopic));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        return optionalTopic.map(topic -> {
            topicRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
