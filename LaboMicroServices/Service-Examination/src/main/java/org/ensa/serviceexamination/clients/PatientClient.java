package org.ensa.serviceexamination.clients;


import org.ensa.serviceexamination.DTO.PatientDTO;
import org.ensa.serviceexamination.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-Patient", configuration = FeignClientConfig.class, url = "http://localhost:8088", path = "/patients")
public interface PatientClient {

    @GetMapping("/exists/{id}")
    ResponseEntity<Boolean> patientExists(@PathVariable("id") long id);

    @GetMapping("/{id}")
    ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id);

    @GetMapping("/public/byEmail/{email}")
    ResponseEntity<PatientDTO> getPatientByEmail(@PathVariable String email);

    @GetMapping("public/verify/{id}")
    ResponseEntity<String> verifyPatient(@PathVariable Long id);
}
