package ru.gobetter.newswatcher.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "website")
public class Website {
    @Id
    private String url;
}
