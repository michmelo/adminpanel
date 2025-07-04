package com.vitalconnect.adminpanel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.vitalconnect.adminpanel.model.Admin;
import com.vitalconnect.adminpanel.repository.AdminRepository;
import com.vitalconnect.adminpanel.service.AdminService;

import jakarta.inject.Inject;

@SpringBootTest
public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Test
    public void testGetAllAdmins() {
        Admin mockAdmin = new Admin(1, "12345678-9", "Camila", "Rojas", "Admin", false);
        when(adminRepository.findAll()).thenReturn(List.of(mockAdmin));

        List<Admin> admins = adminService.getAllAdmins();

        assertNotNull(admins);
        assertEquals(1, admins.size());
        assertEquals("Camila", admins.get(0).getNombre());
    }


    @Test
    public void testGetAdminByRut() {
        String rut = "12345678-9";
        Admin admin = new Admin(0, rut, "John", "Doe", "Administrator", false);
        when(adminRepository.findByRut(rut)).thenReturn(java.util.Optional.of(admin));

        Admin foundAdmin = adminService.getAdminByRut(rut);

        assertNotNull(foundAdmin);
        assertEquals(rut, foundAdmin.getRut());
    }

    @Test
    public void testSave() {
        Admin admin = new Admin(0, "12345678-9", "Jane", "Doe", "Administrator", false);
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin savedAdmin = adminService.save(admin);

        assertNotNull(savedAdmin);
        assertEquals("Jane", savedAdmin.getNombre());
    }

    @Test
    public void testDeleteAdmin() {
        String rut = "12345678-9";
        doNothing().when(adminRepository).deleteByRut(rut);
        adminService.deleteByRut(rut);
        
        verify(adminRepository, times(1)).deleteByRut(rut);
    }
}
