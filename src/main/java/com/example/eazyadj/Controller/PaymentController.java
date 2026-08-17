package com.example.eazyadj.Controller;

import com.example.eazyadj.Dto.ApproveResponse;
import com.example.eazyadj.Dto.ReadyRequest;
import com.example.eazyadj.Dto.ReadyResponse;
import com.example.eazyadj.Service.PaymentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;


    public PaymentController(
            PaymentService paymentService
    ) {

        this.paymentService =
                paymentService;
    }


    @PostMapping("/ready")
    public ReadyResponse ready(
            @RequestBody ReadyRequest request
    ) {

        return paymentService.ready(
                request
        );
    }

    //프론트 있으면 이 코드 사용
    /*@GetMapping("/success")
    public ApproveResponse success(

            @RequestParam("pg_token")
            String pgToken,

            @RequestParam("orderId")
            String orderId,

            @RequestParam("attemptKey")
            String attemptKey

    ) {

        return paymentService.approve(
                orderId,
                pgToken,
                attemptKey
        );
    }*/
    //테스트용 코드
    @GetMapping(
            value = "/success",
            produces = MediaType.TEXT_HTML_VALUE
    )
    public ResponseEntity<?> success(

            @RequestParam("pg_token")
            String pgToken,

            @RequestParam("orderId")
            String orderId,

            @RequestParam("attemptKey")
            String attemptKey

    ) {

        try {

            ApproveResponse response =
                    paymentService.approve(
                            orderId,
                            pgToken,
                            attemptKey
                    );

            String html = """
                    <!DOCTYPE html>
                    <html lang="ko">

                    <head>
                        <meta charset="UTF-8">
                        <title>결제 완료</title>
                    </head>

                    <body style="
                        font-family: Arial, sans-serif;
                        text-align: center;
                        margin-top: 120px;
                    ">

                        <h2>결제가 완료되었습니다.</h2>

                        <p>
                            결제가 정상적으로 처리되었습니다.
                        </p>

                        <button
                            onclick="window.close()"
                            style="
                                padding: 10px 20px;
                                font-size: 16px;
                                cursor: pointer;
                            "
                        >
                            창 닫기
                        </button>

                    </body>

                    </html>
                    """;

            return ResponseEntity
                    .ok()
                    .contentType(
                            MediaType.TEXT_HTML
                    )
                    .body(html);

        } catch (ResponseStatusException e) {

            if (e.getStatusCode()
                    == HttpStatus.CONFLICT
                    &&
                    e.getReason() != null
                    &&
                    e.getReason()
                            .contains(
                                    "이미 완료된 결제"
                            )) {

                String html = """
                        <!DOCTYPE html>
                        <html lang="ko">

                        <head>
                            <meta charset="UTF-8">
                            <title>결제 완료</title>
                        </head>

                        <body style="
                            font-family: Arial, sans-serif;
                            text-align: center;
                            margin-top: 120px;
                        ">

                            <h2>
                                이미 완료된 결제입니다.
                            </h2>

                            <p>
                                해당 주문은 이미 결제가 완료되었습니다.
                            </p>

                            <button
                                onclick="window.close()"
                                style="
                                    padding: 10px 20px;
                                    font-size: 16px;
                                    cursor: pointer;
                                "
                            >
                                창 닫기
                            </button>

                        </body>

                        </html>
                        """;

                return ResponseEntity
                        .ok()
                        .contentType(
                                MediaType.TEXT_HTML
                        )
                        .body(html);
            }

            throw e;
        }
    }

    @PostMapping(
            value = "/{orderId}/cancel",
            produces = MediaType.TEXT_HTML_VALUE
    )
    public ResponseEntity<String> cancelPayment(

            @PathVariable("orderId")
            String orderId

    ) {

        paymentService.cancelPayment(
                orderId
        );


        String html = """
            <!DOCTYPE html>
            <html lang="ko">

            <head>
                <meta charset="UTF-8">
                <title>결제 취소</title>
            </head>

            <body style="
                font-family: Arial, sans-serif;
                text-align: center;
                margin-top: 120px;
            ">

                <h2>결제가 취소되었습니다.</h2>

                <p>
                    해당 결제는 더 이상 진행할 수 없습니다.
                </p>

            </body>

            </html>
            """;


        return ResponseEntity
                .ok()
                .contentType(
                        MediaType.TEXT_HTML
                )
                .body(
                        html
                );
    }

    @GetMapping("/cancel")
    public String cancel() {

        return "결제가 취소되었습니다.";
    }

    @GetMapping("/fail")
    public String fail() {

        return "결제에 실패했습니다.";
    }
}