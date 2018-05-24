package com.dempseywood.service;

import com.dempseywood.FleetManagementApp;
import com.dempseywood.domain.Niggle;
import com.dempseywood.domain.PurchaseOrder;
import com.dempseywood.domain.enumeration.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
@Transactional
public class NiggleServiceTest {

    @Autowired
    private NiggleService niggleService;

    private Niggle niggle;

    @Before
    public void init() {
        niggle = new Niggle();
        niggle.setDescription("nothing to see here");
        niggle.setStatus(Status.SUBMITTED);

    }

    @Test
    @Transactional
    public void assertThatDateOpenedIsRecordedWhenNiggleIsOpenedForTheFirstTime() {
        niggle.setStatus(Status.OPEN);
        niggle.setDateOpened(null);
        Niggle dbNiggle = niggleService.save(niggle);
        assertThat(dbNiggle.getDateOpened()).isNotNull();
    }

    @Test
    @Transactional
    public void assertThatDateOpenedIsNotChangedWhenOpenNiggleIsSaved() {
        niggle.setStatus(Status.OPEN);
        Instant now = Instant.now();
        niggle.setDateOpened(now);
        Niggle dbNiggle = niggleService.save(niggle);
        assertThat(dbNiggle.getDateOpened()).isEqualTo(now);
    }


    @Test
    @Transactional
    public void assertThatDateClosedIsRecordedWhenNiggleIsClosedForTheFirstTime() {
        niggle.setStatus(Status.CLOSED);
        niggle.setDateClosed(null);
        Niggle dbNiggle = niggleService.save(niggle);
        assertThat(dbNiggle.getDateClosed()).isNotNull();
    }

    @Test
    @Transactional
    public void assertThatDateClosedIsNotChangedWhenClosedNiggleIsSaved() {
        niggle.setStatus(Status.CLOSED);
        Instant now = Instant.now();
        niggle.setDateClosed(now);
        Niggle dbNiggle = niggleService.save(niggle);
        assertThat(dbNiggle.getDateClosed()).isEqualTo(now);
    }

    @Test
    @Transactional
    public void assertThatNewPurchaseOrderIsGeneratedWhenNiggleIsOpenedForTheFirstTime() {
        niggle.setStatus(Status.OPEN);
        niggle.setDateOpened(null);
        niggle.setPurchaseOrder(null);
        Niggle dbNiggle = niggleService.save(niggle);
        assertThat(dbNiggle.getPurchaseOrder()).isNotNull();
        assertThat(dbNiggle.getPurchaseOrder().getOrderNumber()).isNotEmpty().isNotNull();
    }
    @Test
    @Transactional
    public void assertThatNewPurchaseOrderIsNotGeneratedWhenPreviouslyOpenedNiggleIsSaved() {
        niggle.setStatus(Status.OPEN);
        niggle.setDateOpened(null);


        Niggle dbNiggle = niggleService.save(niggle);
        PurchaseOrder purchaseOrder = dbNiggle.getPurchaseOrder();
        dbNiggle = niggleService.save(niggle);
        assertThat(dbNiggle.getPurchaseOrder()).isNotNull();
        assertThat(dbNiggle.getPurchaseOrder().getOrderNumber()).isEqualTo(purchaseOrder.getOrderNumber());
        assertThat(dbNiggle.getPurchaseOrder().getId()).isEqualTo(purchaseOrder.getId());

    }



}
