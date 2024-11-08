package org.ensa.serviceexamination.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-TestAnalyse", url = "http://localhost:8085", path = "/testAnalyse")
public interface TestAnalyseClient {

    @GetMapping("/exists/{id}")
    ResponseEntity<Boolean> testAnalyseExists(@PathVariable("id") long id);
}
