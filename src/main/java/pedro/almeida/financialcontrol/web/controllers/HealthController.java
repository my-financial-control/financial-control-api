package pedro.almeida.financialcontrol.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public HealthResponseDTO health() {
        return new HealthResponseDTO("up");
    }

    record HealthResponseDTO(String status) {
    }
}
