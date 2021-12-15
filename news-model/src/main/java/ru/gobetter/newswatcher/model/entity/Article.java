package ru.gobetter.newswatcher.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Data
@Entity
@Table(name = "article")
public class Article {
    private String headline;
    private String content;
    private LocalDateTime articleDate = now();
}
