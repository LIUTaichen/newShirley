package com.dempseywood.service;

import com.dempseywood.FleetManagementApp;
import com.dempseywood.domain.Niggle;
import com.dempseywood.domain.enumeration.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
@Transactional
public class BlackhawkSessionIdServiceTest {

    @Autowired
    private BlackhawkSessionIdService blackhawkSessionIdService;

    @Test
    @Transactional
    public void assertThatSessionIdIsRetrievedOnInit() {
        assertThat(blackhawkSessionIdService.getSessionId()).isNotEmpty().isNotNull();
    }

}
