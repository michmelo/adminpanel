package com.vitalconnect.adminpanel.assemblers;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.vitalconnect.adminpanel.controller.AdminControllerV2;
import com.vitalconnect.adminpanel.model.Admin;

@Component
public class AdminModelAssembler implements RepresentationModelAssembler<Admin, EntityModel<Admin>> {
    @Override
    public @org.springframework.lang.NonNull EntityModel<Admin> toModel(@org.springframework.lang.NonNull Admin admin) {
        return EntityModel.of(admin,
                linkTo(methodOn(AdminControllerV2.class).getAdminByRut(admin.getRut())).withSelfRel(),
                linkTo(methodOn(AdminControllerV2.class).getAllAdmins()).withRel("admins"));
    }


}
