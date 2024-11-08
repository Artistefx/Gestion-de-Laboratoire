package org.ensa.serviceexamination.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-Epreuve", url = "http://localhost:8085", path = "/epreuve")
public interface EpreuveClient {

    @GetMapping("/exists/{id}")
    ResponseEntity<Boolean> epreuveExists(@PathVariable("id") long id);
}
