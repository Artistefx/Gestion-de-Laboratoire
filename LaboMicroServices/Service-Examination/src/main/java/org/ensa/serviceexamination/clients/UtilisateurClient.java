package org.ensa.serviceexamination.clients;


import org.ensa.serviceexamination.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-Utilisateur", configuration = FeignClientConfig.class, url = "http://localhost:8088", path = "/utilisateurs")
public interface UtilisateurClient {

    @GetMapping("/exists/{email}")
    ResponseEntity<Boolean> utilisateurExists(@PathVariable("email") String email);
}
