package com.vitalconnect.adminpanel.controller;

import com.vitalconnect.adminpanel.dto.UserDTO;
import com.vitalconnect.adminpanel.exception.ResourceNotFoundException;
import com.vitalconnect.adminpanel.model.Admin;
import com.vitalconnect.adminpanel.model.Report;
import com.vitalconnect.adminpanel.service.AdminService;
import com.vitalconnect.adminpanel.service.external.UserClientService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")

public class AdminController {

    @Autowired
    private AdminService adminService;

    // CRUD de reports

    //Obtener todos los reportes
    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = adminService.getAllReports();
        if (reports.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reports);
    }

    //Crear reporte
    @PostMapping("/reports")
    public ResponseEntity<Report> createReport(@Valid @RequestBody Report report) {
        return ResponseEntity.ok(adminService.createReport(report));
    }

    //Borrar reporte por id
    @DeleteMapping("/reports/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable int id) {
        try {
            adminService.deleteReport(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Obtener reporte por usuario
    @GetMapping("/reports/usuario/{usuario}")
    public ResponseEntity<List<Report>> getReportsByUsuario(@PathVariable String usuario) {
        List<Report> reports = adminService.getReportsByUsuario(usuario);
        return reports.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reports);
    }

    //Obtener reporte por tipo
    @GetMapping("/reports/tipo/{tipo}")
    public ResponseEntity<List<Report>> getReportsByTipo(@PathVariable String tipo) {
        List<Report> reports = adminService.getReportsByTipo(tipo);
        return reports.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reports);
    }

    // CRUD de admins

    //Obtener todos los admins
    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        if (admins.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(admins);
    }

    //Crear un admin
    @PostMapping("/admins")
    public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.createAdmin(admin));
    }

    //Buscar un admin por id
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable int id) {
        try {
            Admin admin = adminService.getAdminById(id);
            return ResponseEntity.ok(admin);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Actualizar un admin por id
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @Valid @RequestBody Admin admin) {
        try {
            Admin updatedAdmin = adminService.updateAdmin(id, admin);
            return ResponseEntity.ok(updatedAdmin);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Borrar un admin por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable int id) {
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar un admin por rut
    @GetMapping("/rut/{rut}")
    public ResponseEntity<Admin> getAdminByRut(@PathVariable String rut) {
        try {
            Admin admin = adminService.getAdminByRut(rut);
            return ResponseEntity.ok(admin);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para obtener información de un usuario específico
    
    private final UserClientService userClientService;

    public AdminController(UserClientService userClientService) {
        this.userClientService = userClientService;
    }

    @GetMapping("/user-info/{id}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable int id) {
        UserDTO user = userClientService.getUserByIdBlocking(id);
        return ResponseEntity.ok(user);
    }


}