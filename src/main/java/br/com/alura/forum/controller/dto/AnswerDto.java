package br.com.alura.forum.controller.dto;

import br.com.alura.forum.models.Answer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerDto {
    private Long id;
    private String message;
    private LocalDateTime createdDate;
    private String authorName;

    public AnswerDto(Answer answer) {
        this.id = answer.getId();
        this.message = answer.getMessage();
        this.createdDate = answer.getCreatedDate();
        this.authorName = answer.getAuthor().getName();
    }
}
