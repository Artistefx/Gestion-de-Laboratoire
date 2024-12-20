package org.ensa.serviceanalyses.clients;

import org.ensa.serviceanalyses.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-laboratoire" , configuration = FeignClientConfig.class , url = "http://localhost:8087" , path = "/laboratoire")
public interface LaboratoireClient {

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable("id") Long id);
}
