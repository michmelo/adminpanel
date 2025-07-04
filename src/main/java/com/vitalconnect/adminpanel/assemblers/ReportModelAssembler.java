package com.vitalconnect.adminpanel.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.vitalconnect.adminpanel.controller.AdminControllerV2;
import com.vitalconnect.adminpanel.model.Report;

@Component
public class ReportModelAssembler implements RepresentationModelAssembler<Report, EntityModel<Report>> {

    @Override
    public @org.springframework.lang.NonNull EntityModel<Report> toModel(@org.springframework.lang.NonNull Report report) {
        return EntityModel.of(report,
                linkTo(methodOn(AdminControllerV2.class).getAllReports()).withRel("reports"),
                linkTo(methodOn(AdminControllerV2.class).getReportsByUsuario(report.getUsuario())).withRel("por-usuario"),
                linkTo(methodOn(AdminControllerV2.class).getReportsByTipo(report.getTipo())).withRel("por-tipo"),
                linkTo(methodOn(AdminControllerV2.class).deleteReport(report.getId())).withRel("eliminar"));
    }

}
