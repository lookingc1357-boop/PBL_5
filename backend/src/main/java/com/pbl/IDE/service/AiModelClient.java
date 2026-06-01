package com.pbl.IDE.service;

import java.util.List;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pbl.IDE.dto.FunctionScanResult;
import com.pbl.IDE.dto.ScanBatchRequest.FunctionItem;




import lombok.extern.slf4j.Slf4j;

/**
 * Giao tiếp với AI Model (CodeBERT / GraphCodeBERT engine).
 * Cấu hình URL qua application.properties: ai.model.base-url
 */
@Slf4j
@Service
public class AiModelClient {

    public List<FunctionScanResult> analyze(List<FunctionItem> functions) {
        log.info("[AI] [MOCK MODE] analyze {} functions", functions.size());

        if (functions == null || functions.isEmpty()) {
            return List.of();
        }

        // ==================== ĐOẠN CODE MOCK DATA TẠM THỜI ====================
        List<FunctionScanResult> mockResults = new ArrayList<>();

        for (int i = 0; i < functions.size(); i++) {
            FunctionItem func = functions.get(i);
            String signature = func.signature() != null ? func.signature() : "void dummyFunction" + i + "()";

            // Giả lập: Cứ hàm số chẵn thì bị lỗi bảo mật (Vulnerable), hàm số lẻ thì sạch (Clean)
            if (i % 2 == 0) {
                List<FunctionScanResult.VulDetail> mockVuls = List.of(
                        new FunctionScanResult.VulDetail(
                                "CWE-89",
                                "Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection') trong hàm " + signature,
                                3
                        ),
                        new FunctionScanResult.VulDetail(
                                "CWE-79",
                                "Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')",
                                5
                        )
                );
                mockResults.add(new FunctionScanResult(signature, true, mockVuls));
            } else {
                // Hàm sạch, không có lỗ hổng
                mockResults.add(new FunctionScanResult(signature, false, new ArrayList<>()));
            }
        }

        return mockResults;
        // =====================================================================

        /* COMMENT LẠI CODE GỌI AI THẬT ĐỂ DÙNG SAU

private final WebClient webClient;



public AiModelClient(@Value("${ai.model.base-url:http://localhost:8000}") String baseUrl) {

this.webClient = WebClient.builder()

.baseUrl(baseUrl)

// .defaultHeader("ngrok-skip-browser-warning", "true") // Bỏ qua trang cảnh báo

// .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

.build();

}



public List<FunctionScanResult> analyze(List<FunctionItem> functions) {

log.info("[AI] analyze {} functions", functions.size());



record BatchRequest(List<FunctionItem> functions) {

}

record AiResult(String signature, boolean isVul, List<VulDetail> vulnerabilities) {

record VulDetail(String cweId, String description, int line) {

}

}



List<AiResult> aiResults = webClient.post()

.uri("/analyze")

.bodyValue(new BatchRequest(functions))

.retrieve()

.bodyToFlux(AiResult.class)

.collectList()

.block();



if (aiResults == null)

return List.of();



return aiResults.stream()

.map((r) -> {



List<FunctionScanResult.VulDetail> vulDetails = new ArrayList<>();

if (r.vulnerabilities != null) {

vulDetails = r.vulnerabilities.stream()

.map(d -> new FunctionScanResult.VulDetail(d.cweId, d.description, d.line))

.toList();

}

return new FunctionScanResult(r.signature, r.isVul, vulDetails);

})

.toList();

}
        */
    }
}