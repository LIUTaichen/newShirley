package com.dempseywood.service;

import com.dempseywood.FleetManagementApp;
import com.dempseywood.domain.Niggle;
import com.dempseywood.domain.NiggleSnapshot;
import com.dempseywood.domain.enumeration.Priority;
import com.dempseywood.domain.enumeration.Status;
import com.dempseywood.repository.NiggleRepository;
import com.dempseywood.repository.NiggleSnapshotRepository;
import com.dempseywood.web.rest.NiggleResourceIntTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
@Transactional
public class SnapshotServiceTest {

    private final Logger log = LoggerFactory.getLogger(SnapshotServiceTest.class);

    @Autowired
    private SnapshotService snapshotService;
    @Autowired
    private NiggleRepository niggleRepository;

    @Autowired
    private NiggleSnapshotRepository niggleSnapshotRepository;


    @Autowired
    private EntityManager em;

    @Test
    public void assertThatWeekEndingIsNotNull() throws Exception {

        LocalDate localDate = snapshotService.getWeekEndingOn();
        log.info(localDate.toString());
        assertThat(localDate).isNotNull();
        niggleSnapshotRepository.deleteAll();
    }

    @Test
    @Transactional
    public void assertThatGeneratedSnapshotListIsNotEmpty() throws Exception {
        int i = 0;
        for(Status status: Status.values()){
            for (Priority priority: Priority.values()){
                i++;
                for(int j = 0; j<i; j++) {
                    Niggle openHighNiggle = NiggleResourceIntTest.createEntity(em);
                    openHighNiggle.setStatus(status);
                    openHighNiggle.setPriority(priority);
                    openHighNiggle.setDateOpened(Instant.now().minus(i, ChronoUnit.DAYS));
                    openHighNiggle.setDateCompleted(null);
                    openHighNiggle.setDateClosed(null);
                    niggleRepository.save(openHighNiggle);
                }
            }
        }
        Niggle openHighNiggle = NiggleResourceIntTest.createEntity(em);
        openHighNiggle.setStatus(Status.OPEN);
        openHighNiggle.setPriority(Priority.HIGH);
        openHighNiggle.setDateOpened(Instant.now().minus(1, ChronoUnit.DAYS));
        openHighNiggle.setDateCompleted(null);
        openHighNiggle.setDateClosed(null);
        niggleRepository.save(openHighNiggle);
        List<NiggleSnapshot> snapshots  = snapshotService.generateSnapshots();
        assertThat(snapshots).isNotEmpty();

        for(NiggleSnapshot snapshot : snapshots){
            switch (snapshot.getStatus()) {
                case OPEN:
                case IN_PROGRESS:
                case ON_HOLD:
                    assertThat(snapshot.getAgeOfOldest()).isGreaterThan(0);
                    break;
                case SUBMITTED:
                case TBR:
                case COMPLETED:
                case CLOSED:
                case WINTER_WORK:
                    assertThat(snapshot.getAgeOfOldest()).isZero();
                    break;
            }

        }
    }

}
