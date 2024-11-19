package org.ensa.serviceutilisateurs.clients;

import org.ensa.serviceutilisateurs.DTOs.LaboratoireDTO;
import org.ensa.serviceutilisateurs.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "service-laboratoire", configuration = FeignClientConfig.class, url = "http://localhost:8087", path = "/laboratoire")
public interface LaboratoireClient {

    @GetMapping("/{id}")
    LaboratoireDTO getLaboratoireById(@PathVariable("id") Long id);

    @GetMapping("/exists/{id}")
    ResponseEntity<Boolean> existsById(@PathVariable("id") Long id);
}

