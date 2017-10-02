package com.orange.moos.catalog.listener.rest;

import com.orange.moos.catalog.domain.DeliverOrders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.REST;

@RestController
@RequestMapping("/orders")
@Profile(REST)
public class OrdersApi {
    private final Logger log = LoggerFactory.getLogger(OrdersApi.class);

    /**
     * POST  /debit-credits : Create a new debitCredit.
     *
     * @param deliverOrders the deliverOrders
     * @return the ResponseEntity with status 201 (Created),
     * or with status 400 (Bad Request) if the validation failed
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/")
    public ResponseEntity<Void> submitOrders(@RequestBody DeliverOrders deliverOrders) throws URISyntaxException {
        log.info("REST DeliverOrders request : {}", deliverOrders);
//        if (debitCredit.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new debitCredit cannot already have an ID")).body(null);
//        }
//        DebitCredit result = debitCreditRepository.save(debitCredit);
//        return ResponseEntity.created(new URI("/api/debit-credits/" + result.getId()))
//                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//                .body(result);
        final ResponseEntity<Void> build = ResponseEntity.accepted().build();
        return build;
    }
}
