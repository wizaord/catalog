package com.orange.moos.catalog.admin.rabbitmq;

import com.orange.moos.catalog.CatalogApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private FanoutExchange fanoutValidation;


    @Before
    public void startAllListener() {
        this.listenerAdministration.startAllListener();
        this.listenerAdministration.purgeQueue(VALIDATION);
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

    @Test
    public void cleanQueueTest() throws InterruptedException {
        assertThat(this.listenerAdministration.countMessageInQueue(VALIDATION)).isZero();

        // stop listener
        this.listenerAdministration.stopListener(VALIDATION);

        // inject two messages
        this.template.convertAndSend(fanoutValidation.getName(), "", "hello");
        this.template.convertAndSend(fanoutValidation.getName(), "", "hello2");

        // wait 1 second. Let the AMQP statistics refresh the count value
        Thread.sleep(1000);

        // count message
        assertThat(this.listenerAdministration.countMessageInQueue(VALIDATION)).isEqualTo(2L);

        // clean queue
        this.listenerAdministration.purgeQueue(VALIDATION);

        assertThat(this.listenerAdministration.countMessageInQueue(VALIDATION)).isZero();
    }
}