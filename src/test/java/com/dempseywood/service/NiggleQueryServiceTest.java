package com.dempseywood.service;

import com.dempseywood.FleetManagementApp;
import com.dempseywood.domain.MaintenanceContractor;
import com.dempseywood.domain.Niggle;
import com.dempseywood.domain.enumeration.Status;
import com.dempseywood.repository.MaintenanceContractorRepository;
import com.dempseywood.repository.NiggleRepository;
import com.dempseywood.security.AuthoritiesConstants;
import com.dempseywood.service.dto.NiggleCriteria;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
@Transactional
public class NiggleQueryServiceTest {


    @Autowired
    private NiggleQueryService niggleQueryService;

    @Autowired
    private MaintenanceContractorRepository maintenanceContractorRepository;

    @Autowired
    private NiggleRepository niggleRepository;
    private Niggle quattraNiggle;
    private Niggle unassignedNiggle;
    private Niggle submittedNiggle;
    private Niggle closedNiggle;
    private Niggle tbrNiggle;
    private Niggle winterNiggle;

    @Before
    public void init() {
        quattraNiggle = new Niggle();
        quattraNiggle.setStatus(Status.OPEN);
        MaintenanceContractor quattra = maintenanceContractorRepository.findOneByName(NiggleQueryService.QUATTRA_NAME);
        if (quattra == null) {
            quattra = new MaintenanceContractor();
            quattra.setName(NiggleQueryService.QUATTRA_NAME);
            maintenanceContractorRepository.save(quattra);
        }


        quattraNiggle.setAssignedContractor(quattra);
        niggleRepository.save(quattraNiggle);

        unassignedNiggle = new Niggle();
        unassignedNiggle.setStatus(Status.IN_PROGRESS);
        niggleRepository.save(unassignedNiggle);


        submittedNiggle = new Niggle();
        submittedNiggle.setAssignedContractor(quattra);
        submittedNiggle.setStatus(Status.SUBMITTED);
        closedNiggle = new Niggle();
        closedNiggle.setAssignedContractor(quattra);
        closedNiggle.setStatus(Status.CLOSED);
        tbrNiggle = new Niggle();
        tbrNiggle.setAssignedContractor(quattra);
        tbrNiggle.setStatus(Status.TBR);
        winterNiggle = new Niggle();
        winterNiggle.setAssignedContractor(quattra);
        winterNiggle.setStatus(Status.WINTER_WORK);

        niggleRepository.save(submittedNiggle);
        niggleRepository.save(closedNiggle);
        niggleRepository.save(tbrNiggle);
        niggleRepository.save(winterNiggle);


    }


    @Test
    @Transactional
    @WithMockUser(username = "quattra", authorities = {"ROLE_QUATTRA"})
    public void assertThatOnlyAuthorizedNigglesAreRetrievedForUserWithOnlyQuattraRole() {
        List<Niggle> niggles = niggleQueryService.findByCriteria(new NiggleCriteria());
        assertThat(niggles).contains(quattraNiggle);
        assertThat(niggles).doesNotContain(unassignedNiggle, submittedNiggle, closedNiggle, tbrNiggle, winterNiggle);
    }

    @Test
    @Transactional
    @WithMockUser(username = "quattra", authorities = {"ROLE_QUATTRA", "ROLE_DW"})
    public void assertThatAllAuthorizedNigglesAreRetrievedForUserWithAnyRoleOtherThanQuattraRole() {
        List<Niggle> niggles = niggleQueryService.findByCriteria(new NiggleCriteria());
        assertThat(niggles).contains(quattraNiggle, unassignedNiggle, submittedNiggle, closedNiggle, tbrNiggle, winterNiggle);
    }

    private Boolean isAuthorizedForQuattra(Niggle niggle) {
        List<Status> allowedStatus = Arrays.asList(niggleQueryService.ALLOWED_STATUS);
        if (niggle == null) {
            return true;
        } else if (!allowedStatus.contains(niggle.getStatus())) {
            return false;
        } else if (niggle.getAssignedContractor() == null || NiggleQueryService.QUATTRA_NAME != niggle.getAssignedContractor().getName()) {
            return false;
        } else {
            return true;
        }
    }

}
