package com.vitalconnect.adminpanel;

import com.vitalconnect.adminpanel.model.*;
import com.vitalconnect.adminpanel.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
//import java.util.List;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Generar administradores
        for (int i = 0; i < 5; i++) {
            Admin admin = new Admin();
            //admin.setId(i + 1);
            admin.setRut(faker.idNumber().valid());
            admin.setNombre(faker.name().fullName());
            admin.setApellido(faker.name().lastName());
            admin.setRol(faker.options().option("super_admin", "moderador", "auditor"));
            admin.setBaneado(random.nextBoolean()); // Asignar baneado aleatoriamente
            
            /*
            // Validar que el rut sea único

            while (adminRepository.existsByRut(admin.getRut())) {
                admin.setRut(faker.idNumber().valid());
            }

            // Validar que el nombre y apellido no estén vacíos

            if (admin.getNombre().isBlank() || admin.getApellido().isBlank()) {
                throw new IllegalArgumentException("El nombre y apellido no pueden estar vacíos.");
            }

            // Validar que el rol sea uno de los permitidos

            List<String> validRoles = List.of("super_admin", "moderador", "auditor");
            if (!validRoles.contains(admin.getRol())) {
                throw new IllegalArgumentException("El rol debe ser uno de los siguientes: " + validRoles);
            }

            // Guardar el administrador en la base de datos
            // (Se asume que el repositorio ya maneja la validación de campos únicos)
            // y que no se guarda si el rut ya existe

            if (adminRepository.existsByRut(admin.getRut())) {
                System.out.println("El rut " + admin.getRut() + " ya existe, no se guardará el administrador.");
                continue; // Saltar este ciclo si el rut ya existe
            }

            // Guardar el administrador
            // (Se asume que el repositorio ya maneja la validación de campos únicos)   
            // y que no se guarda si el rut ya existe
            */
            System.out.println("Guardando administrador: " + admin.getNombre() + " " + admin.getApellido());
            adminRepository.save(admin);
        }
        
        // Generar reportes
        for (int i = 0; i < 10; i++) {
            Report report = new Report();
            //report.setId(i + 1);
            report.setTipo(faker.options().option("Incidente", "Sugerencia", "Consulta"));
            report.setMensaje(faker.lorem().sentence());
            report.setUsuario(faker.name().fullName());
        
            /*
            // Validar que el tipo sea uno de los permitidos
            List<String> validTypes = List.of("Incidente", "Sugerencia", "Consulta");
            if (!validTypes.contains(report.getTipo())) {
                throw new IllegalArgumentException("El tipo debe ser uno de los siguientes: " + validTypes);
            }
            // Validar que el mensaje no esté vacío
            if (report.getMensaje().isBlank()) {
                throw new IllegalArgumentException("El mensaje no puede estar vacío.");
            }
            // Validar que el usuario no esté vacío
            if (report.getUsuario().isBlank()) {
                throw new IllegalArgumentException("El usuario no puede estar vacío.");
            }
            // Guardar el reporte en la base de datos
            System.out.println("Guardando reporte: " + report.getTipo() + " - " + report.getMensaje());
            // (Se asume que el repositorio ya maneja la validación de campos únicos)
            // y que no se guarda si el reporte ya existe
            if (reportRepository.existsByTipoAndMensajeAndUsuario(report.getTipo(), report.getMensaje(), report.getUsuario())) {
                System.out.println("El reporte ya existe, no se guardará.");
                continue; // Saltar este ciclo si el reporte ya existe
            }

            */
            reportRepository.save(report);
        }

    }

}
