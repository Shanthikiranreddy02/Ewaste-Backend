package com.ewaste.service;

import com.ewaste.entity.User;
import com.ewaste.enums.Role;
import com.ewaste.enums.Status;
import com.ewaste.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Check if requester is admin
    private void validateAdmin(String email) {

        User admin = userRepository.findByEmail(email).orElse(null);

        if(admin == null || admin.getRole() != Role.ADMIN){
            throw new RuntimeException("Access denied. Admin privileges required.");
        }
    }

    // Get Pending Users
    public List<User> getPendingUsers(String email) {

        validateAdmin(email);

        return userRepository.findByStatus(Status.PENDING);
    }

    // Approve User
    public User approveUser(Long id, String email) {

        validateAdmin(email);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(Status.APPROVED);

        return userRepository.save(user);
    }

    // Reject User
    public User rejectUser(Long id, String email) {

        validateAdmin(email);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(Status.REJECTED);

        return userRepository.save(user);
    }

    // Change Role USER → ADMIN
    public User changeUserRole(Long id, String email) {

        validateAdmin(email);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getRole() == Role.ADMIN){
            throw new RuntimeException("User is already ADMIN");
        }

        user.setRole(Role.ADMIN);

        return userRepository.save(user);
    }
    
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}




//package com.ewaste.service;
//
//import com.ewaste.entity.User;
//import com.ewaste.enums.Role;
//import com.ewaste.enums.Status;
//import com.ewaste.repository.UserRepository;
//
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AdminService {
//
//    private UserRepository userRepository;
//
//    public AdminService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    // Check if requester is admin
//    private boolean isAdmin(String email) {
//
//        User admin = userRepository.findByEmail(email).orElse(null);
//
//        return admin != null && admin.getRole() == Role.ADMIN;
//    }
//
//    // Get Pending Users
////    public List<User> getPendingUsers(String email) {
////
////        if (!isAdmin(email)) {
////            throw new RuntimeException("Access denied. Only admin can view pending users.");
////        }
////
////        return userRepository.findByStatus(Status.PENDING);
////    }
////    public List<User> getPendingUsers(String email) {
////
////        if (!isAdmin(email)) {
////            System.out.println("Access denied. Only admin can view pending users.");
////            return List.of();   // return empty list instead of exception
////        }
////
////        return userRepository.findByStatus(Status.PENDING);
////    }
//    
//    public Object getPendingUsers(String email) {
//
//        if (!isAdmin(email)) {
//            return "Access denied. Only admin can view pending users.";
//        }
//
//        return userRepository.findByStatus(Status.PENDING);
//    }
//
//    // Approve User
//    public String approveUser(Long id, String email) {
//
//        if (!isAdmin(email)) {
//            return "Access denied. Only admin can approve users.";
//        }
//
//        User user = userRepository.findById(id).orElse(null);
//
//        if (user == null) {
//            return "User not found";
//        }
//
//        user.setStatus(Status.APPROVED);
//        userRepository.save(user);
//
//        return "User approved successfully";
//    }
//
//    // Reject User
//    public String rejectUser(Long id, String email) {
//
//        if (!isAdmin(email)) {
//            return "Access denied. Only admin can reject users.";
//        }
//
//        User user = userRepository.findById(id).orElse(null);
//
//        if (user == null) {
//            return "User not found";
//        }
//
//        user.setStatus(Status.REJECTED);
//        userRepository.save(user);
//
//        return "User rejected successfully";
//    }
//
//    // Change Role USER → ADMIN
//    public String changeUserRole(Long id, String email) {
//
//        if (!isAdmin(email)) {
//            return "Access denied. Only admin can change roles.";
//        }
//
//        User user = userRepository.findById(id).orElse(null);
//
//        if (user == null) {
//            return "User not found";
//        }
//
//        if (user.getRole() == Role.ADMIN) {
//            return "User is already an ADMIN";
//        }
//
//        user.setRole(Role.ADMIN);
//        userRepository.save(user);
//
//        return "User role changed to ADMIN successfully";
//    }
//}