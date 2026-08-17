package com.example.eazyadj.Service;

import com.example.eazyadj.Dto.*;
import com.example.eazyadj.Entity.Money;
import com.example.eazyadj.Entity.Payment;
import com.example.eazyadj.Entity.PaymentAttempt;
import com.example.eazyadj.Repository.PaymentAttemptRepository;
import com.example.eazyadj.Repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentAttemptRepository
            paymentAttemptRepository;

    private final RestClient restClient;


    @Value("${payment.kakao.secret-key}")
    private String secretKey;


    @Value("${payment.kakao.cid}")
    private String cid;


    public PaymentService(
            PaymentRepository paymentRepository,
            PaymentAttemptRepository paymentAttemptRepository
    ) {

        this.paymentRepository =
                paymentRepository;

        this.paymentAttemptRepository =
                paymentAttemptRepository;

        this.restClient =
                RestClient.builder()
                        .baseUrl(
                                "https://open-api.kakaopay.com"
                        )
                        .build();
    }

    public ReadyResponse ready(
            ReadyRequest request
    ) {

        String orderId =
                request.getPartnerOrderId();

        Payment payment =
                paymentRepository
                        .findByPartnerOrderId(
                                orderId
                        )
                        .orElse(null);

        if (payment != null) {

            if ("APPROVED".equals(
                    payment.getStatus()
            )) {

                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "이미 완료된 결제입니다. orderId="
                                + orderId
                );
            }

            if ("APPROVING".equals(
                    payment.getStatus()
            )) {

                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "현재 결제 승인 처리 중입니다."
                );
            }

            if ("APPROVE_UNKNOWN".equals(
                    payment.getStatus()
            )) {

                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "이전 결제 승인 결과 확인이 필요합니다."
                );
            }
        }

        if (payment == null) {

            Payment newPayment =
                    new Payment();

            newPayment.setPartnerOrderId(
                    orderId
            );

            newPayment.setPartnerUserId(
                    request.getPartnerUserId()
            );

            newPayment.setDriverId(
                    request.getDriverId()
            );

            newPayment.setIdempotencyKey(
                    UUID.randomUUID()
                            .toString()
            );

            newPayment.setStatus(
                    "CREATED"
            );

            try {

                paymentRepository
                        .saveAndFlush(
                                newPayment
                        );

                payment =
                        newPayment;

            } catch (
                    DataIntegrityViolationException e
            ) {

                payment =
                        paymentRepository
                                .findByPartnerOrderId(
                                        orderId
                                )
                                .orElseThrow(
                                        () ->
                                                new ResponseStatusException(
                                                        HttpStatus.CONFLICT,
                                                        "동일 주문 생성 충돌이 발생했습니다."
                                                )
                                );

                if ("APPROVED".equals(
                        payment.getStatus()
                )) {

                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "이미 완료된 결제입니다."
                    );
                }
            }
        }

        PaymentAttempt attempt =
                new PaymentAttempt();


        String attemptKey =
                UUID.randomUUID()
                        .toString();

        attempt.setPayment(
                payment
        );

        attempt.setAttemptKey(
                attemptKey
        );

        attempt.setStatus(
                "READY_REQUESTED"
        );

        attempt.setCreatedAt(
                LocalDateTime.now()
        );

        paymentAttemptRepository
                .saveAndFlush(
                        attempt
                );

        String approvalUrl =
                request.getApprovalUrl();

        String separator =
                approvalUrl.contains("?")
                        ? "&"
                        : "?";

        request.setApprovalUrl(
                approvalUrl
                        + separator
                        + "attemptKey="
                        + attemptKey
        );

        request.setCid(
                cid
        );

        try {

            ReadyResponse response =
                    restClient
                            .post()
                            .uri(
                                    "/online/v1/payment/ready"
                            )
                            .header(
                                    "Authorization",
                                    "SECRET_KEY "
                                            + secretKey
                            )
                            .contentType(
                                    MediaType.APPLICATION_JSON
                            )
                            .body(
                                    request
                            )
                            .retrieve()
                            .body(
                                    ReadyResponse.class
                            );

            if (response == null) {

                attempt.setStatus(
                        "READY_FAILED"
                );

                paymentAttemptRepository
                        .save(attempt);

                throw new RuntimeException(
                        "결제 준비 응답이 없습니다."
                );
            }

            attempt.setTid(
                    response.getTid()
            );

            attempt.setStatus(
                    "READY"
            );

            paymentAttemptRepository
                    .save(
                            attempt
                    );

            payment.setStatus(
                    "READY"
            );

            paymentRepository.save(
                    payment
            );

            return response;

        } catch (
                ResponseStatusException e
        ) {

            throw e;

        } catch (Exception e) {

            attempt.setStatus(
                    "READY_FAILED"
            );

            paymentAttemptRepository
                    .save(
                            attempt
                    );

            throw e;
        }
    }

    public ApproveResponse approve(

            String orderId,
            String pgToken,
            String attemptKey

    ) {

        PaymentAttempt attempt =
                paymentAttemptRepository
                        .findByAttemptKey(
                                attemptKey
                        )
                        .orElseThrow(
                                () ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "결제 시도 정보를 찾을 수 없습니다."
                                        )
                        );

        Payment payment =
                attempt.getPayment();

        if (!orderId.equals(
                payment.getPartnerOrderId()
        )) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "주문 정보가 일치하지 않습니다."
            );
        }

        if ("APPROVED".equals(
                payment.getStatus()
        )) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "이미 완료된 결제입니다. orderId="
                            + orderId
            );
        }

        if (!"READY".equals(
                attempt.getStatus()
        )) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "사용할 수 없는 결제창입니다. status="
                            + attempt.getStatus()
            );
        }

        int updated =
                paymentRepository
                        .changeReadyToApproving(
                                payment.getPaymentId()
                        );

        if (updated == 0) {

            Payment latestPayment =
                    paymentRepository
                            .findById(
                                    payment.getPaymentId()
                            )
                            .orElseThrow();

            if ("APPROVED".equals(
                    latestPayment.getStatus()
            )) {

                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "이미 완료된 결제입니다."
                );
            }

            if ("APPROVING".equals(
                    latestPayment.getStatus()
            )) {

                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "다른 결제 요청을 승인 처리 중입니다."
                );
            }

            if ("APPROVE_UNKNOWN".equals(
                    latestPayment.getStatus()
            )) {

                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "이전 승인 결과 확인이 필요합니다."
                );
            }

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "현재 승인할 수 없는 결제입니다. status="
                            + latestPayment.getStatus()
            );
        }

        attempt.setStatus(
                "APPROVING"
        );

        paymentAttemptRepository
                .save(
                        attempt
                );

        ApproveRequest request =
                new ApproveRequest();

        request.setCid(
                cid
        );

        request.setTid(
                attempt.getTid()
        );

        request.setPartnerOrderId(
                payment.getPartnerOrderId()
        );

        request.setPartnerUserId(
                payment.getPartnerUserId()
        );

        request.setPgToken(
                pgToken
        );

        ApproveResponse response;

        try {

            response =
                    restClient
                            .post()
                            .uri(
                                    "/online/v1/payment/approve"
                            )
                            .header(
                                    "Authorization",
                                    "SECRET_KEY "
                                            + secretKey
                            )
                            .contentType(
                                    MediaType.APPLICATION_JSON
                            )
                            .body(
                                    request
                            )
                            .retrieve()
                            .body(
                                    ApproveResponse.class
                            );

        } catch (
                HttpClientErrorException e
        ) {

            attempt.setStatus(
                    "APPROVE_FAILED"
            );

            paymentAttemptRepository
                    .save(
                            attempt
                    );

            paymentRepository.updateStatus(
                    payment.getPaymentId(),
                    "READY"
            );

            throw e;

        } catch (
                HttpServerErrorException
                | ResourceAccessException e
        ) {

            attempt.setStatus(
                    "APPROVE_UNKNOWN"
            );

            paymentAttemptRepository
                    .save(
                            attempt
                    );

            paymentRepository.updateStatus(
                    payment.getPaymentId(),
                    "APPROVE_UNKNOWN"
            );

            throw e;
        }

        if (response == null) {

            attempt.setStatus(
                    "APPROVE_UNKNOWN"
            );

            paymentAttemptRepository
                    .save(
                            attempt
                    );

            paymentRepository.updateStatus(
                    payment.getPaymentId(),
                    "APPROVE_UNKNOWN"
            );

            throw new RuntimeException(
                    "결제 승인 응답이 없습니다."
            );
        }

        saveApprovedPayment(
                payment,
                attempt,
                response
        );


        return response;
    }

    private void saveApprovedPayment(

            Payment payment,
            PaymentAttempt attempt,
            ApproveResponse response

    ) {

        payment.setPaymentMethodType(
                response.getPaymentMethodType()
        );

        payment.setApprovedAt(
                response.getApprovedAt()
        );

        payment.setStatus(
                "APPROVED"
        );

        attempt.setStatus(
                "APPROVED"
        );

        if (response.getAmount() != null) {

            Money money =
                    new Money();

            if (response
                    .getAmount()
                    .getTotal() != null) {

                money.setAmount(
                        BigDecimal.valueOf(
                                response
                                        .getAmount()
                                        .getTotal()
                        )
                );
            }

            if (response
                    .getAmount()
                    .getTaxFree() != null) {

                money.setTaxFree(
                        BigDecimal.valueOf(
                                response
                                        .getAmount()
                                        .getTaxFree()
                        )
                );
            }

            if (response
                    .getAmount()
                    .getVat() != null) {

                money.setVat(
                        BigDecimal.valueOf(
                                response
                                        .getAmount()
                                        .getVat()
                        )
                );
            }
            money.setApprovedAt(
                    response.getApprovedAt()
            );


            money.setDriverId(
                    payment.getDriverId()
            );

            payment.setMoney(
                    money
            );
        }

        paymentAttemptRepository.save(
                attempt
        );

        paymentRepository.save(
                payment
        );
    }
}