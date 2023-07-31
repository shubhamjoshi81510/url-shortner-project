package com.urlshort.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "url_table")
@Data
@Entity
public class UrlMapping{
    @Id
    @Column(name ="url_key")
    private String urlKey;

    @Column(name ="url")
    private String url;
}
