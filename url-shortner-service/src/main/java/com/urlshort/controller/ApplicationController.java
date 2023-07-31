package com.urlshort.controller;

import com.urlshort.entity.UrlMapping;
import com.urlshort.model.UrlReq;
import com.urlshort.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class ApplicationController {
@Autowired
private UrlService urlService;
    @CrossOrigin(origins = "*")
@PostMapping("/url")
 public UrlMapping getKey(@RequestBody UrlReq urlReq){
        UrlMapping urlMapping=urlService.genrateUrl(urlReq);
        return urlMapping;
}

   @GetMapping("/{key}")
   public RedirectView getUrlByKey(@PathVariable String key) {
        UrlMapping urlMapping = urlService.getUrlByKey(key);
        String redirectUrl = urlMapping.getUrl();
        return new RedirectView(redirectUrl);

    }

}
