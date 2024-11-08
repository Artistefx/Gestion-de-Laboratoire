package org.ensa.serviceexamination.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-Patient", url = "http://localhost:8088", path = "/patients")
public interface PatientClient {

    @GetMapping("/exists/{id}")
    ResponseEntity<Boolean> patientExists(@PathVariable("id") long id);
}