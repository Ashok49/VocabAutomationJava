package com.ashokvocab.vocab_automation.service.impl;

import com.ashokvocab.vocab_automation.service.TwilioService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import com.twilio.type.Twiml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioServiceImpl implements TwilioService {

    private final Logger logger = LoggerFactory.getLogger(TwilioServiceImpl.class);

    private final String fromNumber;
    private final String toNumber;

    public TwilioServiceImpl(
            @Value("${twilio.account-sid}") String accountSid,
            @Value("${twilio.auth-token}") String authToken,
            @Value("${twilio.from-number}") String fromNumber,
            @Value("${twilio.to-number}") String toNumber) {

        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void makeCall(String audioUrl) {
        try {
            Call call = Call.creator(
                    new PhoneNumber(toNumber),
                    new PhoneNumber(fromNumber),
                    new Twiml(String.format("<Response><Play>%s</Play></Response>", audioUrl))
            ).create();

            logger.info("📞 Call initiated: {}", call.getSid());
        } catch (Exception ex) {
            logger.error("❌ Twilio call failed.", ex);
        }
    }
}