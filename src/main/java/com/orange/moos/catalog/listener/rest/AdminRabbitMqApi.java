package com.orange.moos.catalog.listener.rest;

import com.orange.moos.catalog.admin.rabbitmq.ListenerAdministration;
import com.orange.moos.catalog.listener.E_LISTENER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.REST;

@RestController
@RequestMapping("/admin/rabbitmq")
@Profile(REST)
public class AdminRabbitMqApi {

    private final Logger log = LoggerFactory.getLogger(AdminRabbitMqApi.class);

    @Autowired
    private ListenerAdministration listenerAdministration;

    @PostMapping("/stop")
    public ResponseEntity<Void> stopAllReceiver() {
        log.info("REST stoping all amqp listeners");
        this.listenerAdministration.stopAllListener();
        return ResponseEntity.ok().build();

    }

    @PostMapping("/start")
    public ResponseEntity<Void> startAllReceiver() {
        log.info("REST starting all amqp listeners");
        this.listenerAdministration.startAllListener();
        return ResponseEntity.ok().build();

    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> listReceiverStatus() {
        final Map<String, String> listenerStatus = new HashMap<>();
        for (E_LISTENER e_listener : E_LISTENER.values()) {
            listenerStatus.put(e_listener.name(), String.valueOf(this.listenerAdministration.isRunning(e_listener)));
        }
        return ResponseEntity.ok(listenerStatus);
    }

    @GetMapping("/status/{listenername}")
    public ResponseEntity<Boolean> isReceiverRunning(@PathVariable String listenername) {
        log.info("REST islitener running: {}", listenername);
        final E_LISTENER e_listener;
        try {
            e_listener = E_LISTENER.valueOf(listenername);
        } catch (IllegalArgumentException e) {
            log.error("The listener with the name {} does not exist", listenername);
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(this.listenerAdministration.isRunning(e_listener));
    }

    @PostMapping("/stop/{listenername}")
    public ResponseEntity<Void> stopReceiver(@PathVariable String listenername) {
        log.info("REST stopping listener : {}", listenername);
        final E_LISTENER e_listener;
        try {
            e_listener = E_LISTENER.valueOf(listenername);
        } catch (IllegalArgumentException e) {
            log.error("The listener with the name {} does not exist", listenername);
            return ResponseEntity.badRequest().build();
        }

        //stop listener
        this.listenerAdministration.stopListener(e_listener);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/start/{listenername}")
    public ResponseEntity<Void> startReceiver(@PathVariable String listenername) {
        log.info("REST starting listener : {}", listenername);
        final E_LISTENER e_listener;
        try {
            e_listener = E_LISTENER.valueOf(listenername);
        } catch (IllegalArgumentException e) {
            log.error("The listener with the name {} does not exist", listenername);
            return ResponseEntity.badRequest().build();
        }

        //start listener
        this.listenerAdministration.startListener(e_listener);
        return ResponseEntity.ok().build();
    }
}
