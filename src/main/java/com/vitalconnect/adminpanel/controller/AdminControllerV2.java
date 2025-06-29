package com.vitalconnect.adminpanel.controller;

import com.vitalconnect.adminpanel.assemblers.AdminModelAssembler;
import com.vitalconnect.adminpanel.dto.UserDTO;
import com.vitalconnect.adminpanel.exception.ResourceNotFoundException;
import com.vitalconnect.adminpanel.model.Admin;
import com.vitalconnect.adminpanel.model.Report;
import com.vitalconnect.adminpanel.service.AdminService;
import com.vitalconnect.adminpanel.service.external.UserClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Reportes y Administradores", description = "Endpoints para gestión de reportes y administradores.")
@RestController
@RequestMapping("/api/v2/admin")

public class AdminControllerV2 {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminModelAssembler assembler;

    // CRUD de reports

    //Obtener todos los reportes
    @Operation(
        summary = "Obtener todos los reportes",
        description = "Retorna una lista de reportes disponibles en formato JSON"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de reportes obtenida exitosamente",
            content = @Content(mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
        ),
        @ApiResponse(
            responseCode = "204",
            description = "No hay reportes disponibles"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Solicitud incorrecta")
        ,
        @ApiResponse(
            responseCode = "404", 
            description = "Reporte no encontrado")
        ,
        @ApiResponse(
            responseCode = "405", 
            description = "Metodo no permitido")
        ,
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto de recursos")
        ,
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = adminService.getAllReports();
        if (reports.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reports);
    }

    //Crear reporte
    @Operation(
        summary = "Crear un nuevo reporte",
        description = "Crea un nuevo reporte y lo retorna en formato JSON"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Reporte creado exitosamente",
            content = @Content(mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Solicitud incorrecta")
        ,
        @ApiResponse(
            responseCode = "404", 
            description = "Reporte no encontrado")
        ,
        @ApiResponse(
            responseCode = "405", 
            description = "Metodo no permitido")
        ,
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto de recursos")
        ,
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    @PostMapping("/reports")
    public ResponseEntity<Report> createReport(@Valid @RequestBody Report report) {
        return ResponseEntity.ok(adminService.createReport(report));
    }

    //Borrar reporte por id
    @Operation(
        summary = "Borrar un reporte por ID",
        description = "Elimina un reporte existente por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Reporte eliminado exitosamente"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Solicitud incorrecta")
        ,
        @ApiResponse(
            responseCode = "404", 
            description = "Reporte no encontrado")
        ,
        @ApiResponse(
            responseCode = "405", 
            description = "Metodo no permitido")
        ,
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto de recursos")
        ,
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
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
    @Operation(
        summary = "Obtener reportes por usuario",
        description = "Retorna una lista de reportes disponibles por usuario en formato JSON"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de reportes obtenida exitosamente",
            content = @Content(mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Solicitud incorrecta")
        ,
        @ApiResponse(
            responseCode = "404", 
            description = "Usuario no encontrado")
        ,
        @ApiResponse(
            responseCode = "405", 
            description = "Metodo no permitido")
        ,
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto de recursos")
        ,
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    @GetMapping("/reports/usuario/{usuario}")
    public ResponseEntity<List<Report>> getReportsByUsuario(@PathVariable String usuario) {
        List<Report> reports = adminService.getReportsByUsuario(usuario);
        return reports.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reports);
    }

    //Obtener reporte por tipo
    @Operation(
        summary = "Obtener reportes por tipo",
        description = "Retorna una lista de reportes disponibles por tipo en formato JSON"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de reportes obtenida exitosamente",
            content = @Content(mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Solicitud incorrecta")
        ,
        @ApiResponse(
            responseCode = "404", 
            description = "Tipo de reporte no encontrado")
        ,
        @ApiResponse(
            responseCode = "405", 
            description = "Metodo no permitido")
        ,
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto de recursos")
        ,
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    @GetMapping("/reports/tipo/{tipo}")
    public ResponseEntity<List<Report>> getReportsByTipo(@PathVariable String tipo) {
        List<Report> reports = adminService.getReportsByTipo(tipo);
        return reports.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reports);
    }

    // CRUD de admins

    //Obtener todos los admins
    @Operation(
        summary = "Obtener todos los administradores",
        description = "Retorna una lista de los administradores disponibles en formato JSON"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de administradores obtenida exitosamente",
            content = @Content(mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Solicitud incorrecta")
        ,
        @ApiResponse(
            responseCode = "404", 
            description = "Administrador no encontrado")
        ,
        @ApiResponse(
            responseCode = "405", 
            description = "Metodo no permitido")
        ,
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto de recursos")
        ,
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    @GetMapping(value = "/admins", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Admin>> getAllAdmins() {
        List<EntityModel<Admin>> admins = adminService.getAllAdmins().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(admins, linkTo(methodOn(AdminControllerV2.class).getAllAdmins()).withSelfRel());
    }

    //Crear un admin
    @Operation(
        summary = "Crear un nuevo administrador",
        description = "Crea un nuevo administrador y lo retorna en formato JSON"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Administrador creado exitosamente",
            content = @Content(mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Solicitud incorrecta")
        ,
        @ApiResponse(
            responseCode = "404", 
            description = "Administrador no encontrado")
        ,
        @ApiResponse(
            responseCode = "405", 
            description = "Metodo no permitido")
        ,
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto de recursos")
        ,
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    @PostMapping("/admins")
    public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.createAdmin(admin));
    }

    //Buscar un admin por id
    @Operation(
        summary = "Obtener admins por ID",
        description = "Retorna administrador buscado en formato JSON"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Administrador obtenido exitosamente",
            content = @Content(mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Solicitud incorrecta")
        ,
        @ApiResponse(
            responseCode = "404", 
            description = "Administrador no encontrado")
        ,
        @ApiResponse(
            responseCode = "405", 
            description = "Metodo no permitido")
        ,
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto de recursos")
        ,
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
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
    @Operation(
        summary = "Actualizar un administrador por ID",
        description = "Actualiza un administrador existente y lo retorna en formato JSON"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Administrador actualizado exitosamente",
            content = @Content(mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Solicitud incorrecta")
        ,
        @ApiResponse(
            responseCode = "404", 
            description = "Administrador no encontrado")
        ,
        @ApiResponse(
            responseCode = "405", 
            description = "Metodo no permitido")
        ,
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto de recursos")
        ,
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
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
    @Operation(
        summary = "Borrar un administrador por ID",
        description = "Elimina un administrador existente por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Administrador eliminado exitosamente"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Solicitud incorrecta")
        ,
        @ApiResponse(
            responseCode = "404", 
            description = "Administrador no encontrado")
        ,
        @ApiResponse(
            responseCode = "405", 
            description = "Metodo no permitido")
        ,
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto de recursos")
        ,
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
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
@Operation(
        summary = "Obtener admins por rut",
        description = "Retorna administrador buscado en formato JSON"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Administrador obtenido exitosamente",
            content = @Content(mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Solicitud incorrecta")
        ,
        @ApiResponse(
            responseCode = "404", 
            description = "Administrador no encontrado")
        ,
        @ApiResponse(
            responseCode = "405", 
            description = "Metodo no permitido")
        ,
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto de recursos")
        ,
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })    @GetMapping("/rut/{rut}")
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

    public AdminControllerV2(UserClientService userClientService) {
        this.userClientService = userClientService;
    }

    @Operation(
        summary = "Obtener información de un usuario por ID",
        description = "Retorna la información del usuario en formato JSON"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Información del usuario obtenida exitosamente",
            content = @Content(mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Solicitud incorrecta"),
        @ApiResponse(
            responseCode = "404", 
            description = "Usuario no encontrado"),
        @ApiResponse(
            responseCode = "405", 
            description = "Metodo no permitido"),
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto de recursos"),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    @GetMapping("/user-info/{id}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable int id) {
        UserDTO user = userClientService.getUserByIdBlocking(id);
        return ResponseEntity.ok(user);
    }


}