package br.com.alura.forum.controller.dto;

import br.com.alura.forum.models.Topic;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Data
public class TopicDto {
    private Long id;
    private String title;
    private String message;
    private LocalDateTime createdDate;

    public TopicDto(Topic topic) {
        if (topic != null) {
            this.id = topic.getId();
            this.title = topic.getTitle();
            this.message = topic.getMessage();
            this.createdDate = topic.getCreatedDate();
        }
    }

    public static Page<TopicDto> map(Page<Topic> topics) {
        return topics
                .map(TopicDto::new);
    }
}
