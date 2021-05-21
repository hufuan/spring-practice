package com.javatechie.ps.api.controller;

import com.javatechie.ps.api.entity.Payment;
import com.javatechie.ps.api.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    public PaymentService service;

    @PostMapping("/doPayment")
    public Payment doPayment(@RequestBody Payment payment) {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        Payment ret = null;
        try {
            ret = service.doPayment(payment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    @GetMapping("/{orderId}")
    public Payment findPaymentHistoryByOrderId(@PathVariable int orderId) {
        return service.findPaymentHistoryByOrderId(orderId);
    }
    public String paymentProcessing(){
        //api should be 3rd party payment gateway(paypal, paytm...)
       return new Random().nextBoolean() ? "sucess" :"failure";
    }
}
