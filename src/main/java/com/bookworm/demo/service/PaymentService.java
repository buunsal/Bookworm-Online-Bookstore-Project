package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {

    List<Payment> getPayments();

    void savePayment(Payment payment);

    Payment getPayment(int id) throws ResourceNotFoundException;

    void deletePayment(int id) throws ResourceNotFoundException;

}
