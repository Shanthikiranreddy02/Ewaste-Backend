package com.ewaste.controller;

import com.ewaste.entity.User;
import com.ewaste.security.JwtService;
import com.ewaste.service.AdminService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;
    private JwtService jwtService;

    public AdminController(AdminService adminService, JwtService jwtService) {
        this.adminService = adminService;
        this.jwtService = jwtService;
    }
    
    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }
    
    // View pending users
    @GetMapping("/pending-users")
    public ResponseEntity<?> getPendingUsers(@RequestHeader("Authorization") String token) {

        try {

            String jwt = token.substring(7);
            String email = jwtService.extractEmail(jwt);

            List<User> users = adminService.getPendingUsers(email);

            return ResponseEntity.ok(users);

        } catch (RuntimeException e){

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }

    // Approve user
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveUser(@PathVariable Long id,
                                         @RequestHeader("Authorization") String token) {

        try {

            String jwt = token.substring(7);
            String email = jwtService.extractEmail(jwt);

            User user = adminService.approveUser(id, email);

            return ResponseEntity.ok(user);

        } catch (RuntimeException e){

            if(e.getMessage().equals("User not found")){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    // Reject user
    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectUser(@PathVariable Long id,
                                        @RequestHeader("Authorization") String token) {

        try {

            String jwt = token.substring(7);
            String email = jwtService.extractEmail(jwt);

            User user = adminService.rejectUser(id, email);

            return ResponseEntity.ok(user);

        } catch (RuntimeException e){

            if(e.getMessage().equals("User not found")){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    // Change role USER → ADMIN
    @PutMapping("/change-role/{id}")
    public ResponseEntity<?> changeUserRole(@PathVariable Long id,
                                            @RequestHeader("Authorization") String token) {

        try {

            String jwt = token.substring(7);
            String email = jwtService.extractEmail(jwt);

            User user = adminService.changeUserRole(id, email);

            return ResponseEntity.ok(user);

        } catch (RuntimeException e){

            if(e.getMessage().equals("User not found")){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}




//package com.ewaste.controller;
//
//import com.ewaste.security.JwtService;
//import com.ewaste.service.AdminService;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/admin")
//public class AdminController {
//
//    private AdminService adminService;
//    private JwtService jwtService;
//
//    public AdminController(AdminService adminService, JwtService jwtService) {
//        this.adminService = adminService;
//        this.jwtService = jwtService;
//    }
//
//    // View pending users
////    @GetMapping("/pending-users")
////    public List<User> getPendingUsers(@RequestHeader("Authorization") String token) {
////
////        String jwt = token.substring(7);
////        String email = jwtService.extractEmail(jwt);
////
////        return adminService.getPendingUsers(email);
////    }
//    
//    @GetMapping("/pending-users")
//    public Object getPendingUsers(@RequestHeader("Authorization") String token) {
//
//        String jwt = token.substring(7);
//        String email = jwtService.extractEmail(jwt);
//
//        return adminService.getPendingUsers(email);
//    }
//
//    // Approve user
//    @PutMapping("/approve/{id}")
//    public String approveUser(@PathVariable Long id,
//                              @RequestHeader("Authorization") String token) {
//
//        String jwt = token.substring(7);
//        String email = jwtService.extractEmail(jwt);
//
//        return adminService.approveUser(id, email);
//    }
//
//    // Reject user
//    @PutMapping("/reject/{id}")
//    public String rejectUser(@PathVariable Long id,
//                             @RequestHeader("Authorization") String token) {
//
//        String jwt = token.substring(7);
//        String email = jwtService.extractEmail(jwt);
//
//        return adminService.rejectUser(id, email);
//    }
//
//    // Change role USER → ADMIN
//    @PutMapping("/change-role/{id}")
//    public String changeUserRole(@PathVariable Long id,
//                                 @RequestHeader("Authorization") String token) {
//
//        String jwt = token.substring(7);
//        String email = jwtService.extractEmail(jwt);
//
//        return adminService.changeUserRole(id, email);
//    }
//}