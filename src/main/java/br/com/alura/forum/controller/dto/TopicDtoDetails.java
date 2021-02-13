package br.com.alura.forum.controller.dto;

import br.com.alura.forum.models.StatusTopic;
import br.com.alura.forum.models.Topic;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TopicDtoDetails {
    private Long id;
    private String title;
    private String message;
    private LocalDateTime createdDate;
    private String authorName;
    private StatusTopic status;
    private List<AnswerDto> answers;

    public TopicDtoDetails(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.message = topic.getMessage();
        this.createdDate = topic.getCreatedDate();
        this.authorName = topic.getAuthor().getName();
        this.status = topic.getStatus();
        this.answers = new ArrayList<>();
        this.answers.addAll(
                topic
                        .getAnswers()
                        .stream()
                        .map(AnswerDto::new)
                        .collect(Collectors.toList())
        );
    }
}
