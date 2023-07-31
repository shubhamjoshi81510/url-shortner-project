package com.urlshort.repository;

import com.urlshort.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlMapping,String> {



}
