package br.com.alura.forum.controller.form;

import br.com.alura.forum.models.Topic;
import br.com.alura.forum.repository.TopicRepository;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TopicUpdateForm {
    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String title;
    @NotNull @NotEmpty @Length(min = 10)
    private String message;

    public Topic update(Long id, TopicRepository topicRepository) {
        Topic topic = topicRepository.getOne(id);
        topic.setTitle(this.title);
        topic.setMessage(this.message);

        return topic;
    }
}
