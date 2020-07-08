package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Payment;
import com.bookworm.demo.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.EJB;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @EJB
    private PaymentRepository paymentRepository;

    @Override
    @Transactional
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    @Override
    @Transactional
    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public Payment getPayment(int id) throws ResourceNotFoundException {
        return paymentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id));
    }

    @Override
    @Transactional
    public void deletePayment(int id) {
        paymentRepository.deleteById(id);
    }
}
