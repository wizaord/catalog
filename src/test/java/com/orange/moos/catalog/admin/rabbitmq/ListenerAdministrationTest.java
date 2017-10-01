package com.orange.moos.catalog.admin.rabbitmq;

import com.orange.moos.catalog.CatalogApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.AMQP;
import static com.orange.moos.catalog.listener.E_LISTENER.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApplication.class)
@ActiveProfiles(AMQP)
public class ListenerAdministrationTest {

    @Autowired
    private ListenerAdministration listenerAdministration;

    @Before
    public void startAllListener() {
        this.listenerAdministration.startAllListener();
        // check that listener are started
        assertThat(this.listenerAdministration.isRunning(DECOMPOSITION)).isTrue();
        assertThat(this.listenerAdministration.isRunning(VALIDATION)).isTrue();
        assertThat(this.listenerAdministration.isRunning(SEQUENCING)).isTrue();
    }

    @Test
    public void logListenerStatusTest() {
        //do nothing. Just test the listener are logged
        this.listenerAdministration.logListenerStatus();
    }

    @Test
    public void stopAllListernerTest() {
        this.listenerAdministration.stopAllListener();

        assertThat(this.listenerAdministration.isRunning(DECOMPOSITION)).isFalse();
        assertThat(this.listenerAdministration.isRunning(VALIDATION)).isFalse();
        assertThat(this.listenerAdministration.isRunning(SEQUENCING)).isFalse();
    }

    @Test
    public void startAllListenerTest() {
        this.listenerAdministration.stopAllListener();

        assertThat(this.listenerAdministration.isRunning(DECOMPOSITION)).isFalse();
        assertThat(this.listenerAdministration.isRunning(VALIDATION)).isFalse();
        assertThat(this.listenerAdministration.isRunning(SEQUENCING)).isFalse();

        this.listenerAdministration.startAllListener();
        assertThat(this.listenerAdministration.isRunning(DECOMPOSITION)).isTrue();
        assertThat(this.listenerAdministration.isRunning(VALIDATION)).isTrue();
        assertThat(this.listenerAdministration.isRunning(SEQUENCING)).isTrue();
    }

    @Test
    public void stopListener() {
        this.listenerAdministration.stopListener(SEQUENCING);
        assertThat(this.listenerAdministration.isRunning(SEQUENCING)).isFalse();
    }

    @Test
    public void startListener() {
        stopListener();

        this.listenerAdministration.startListener(SEQUENCING);
        assertThat(this.listenerAdministration.isRunning(SEQUENCING)).isTrue();
    }
}