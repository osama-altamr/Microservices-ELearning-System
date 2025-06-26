package com.elearning.user_service.conroller;

import com.elearning.user_service.model.dto.request.AdminRequest;
import com.elearning.user_service.model.dto.request.UserRequest;
import com.elearning.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    private ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/register")
    private ResponseEntity<?> registerInstructor(@RequestBody AdminRequest request) {
        return ResponseEntity.ok(service.save(request));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getMe());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
