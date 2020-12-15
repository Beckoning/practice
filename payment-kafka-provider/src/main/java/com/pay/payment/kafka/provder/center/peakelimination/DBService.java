package com.pay.payment.kafka.provder.center.peakelimination;

import org.springframework.stereotype.Service;

@Service
public class DBService {
    public String useDb(String select_ticket) {
        return select_ticket;
    }
}
