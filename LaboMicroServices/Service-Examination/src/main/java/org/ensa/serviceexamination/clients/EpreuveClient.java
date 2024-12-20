package org.ensa.serviceexamination.clients;


import org.ensa.serviceexamination.DTO.EpreuveDTO;
import org.ensa.serviceexamination.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-Epreuve", configuration = FeignClientConfig.class, url = "http://localhost:8085", path = "/epreuve")
public interface EpreuveClient {

    @GetMapping("/exists/{id}")
    ResponseEntity<Boolean> epreuveExists(@PathVariable("id") long id);

    @GetMapping("/{id}")
    ResponseEntity<EpreuveDTO> getEpreuveById(@PathVariable Long id);
}
