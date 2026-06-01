package com.pbl.IDE.dto;
import java.util.List;

import javax.sound.sampled.Line;
// ─── Outbound DTOs ───────────────────────────────────────────────────────────

/** Kết quả phân tích một hàm */
public record FunctionScanResult(
        String signature,       // key để FE map lại
        boolean isVul,
        List<VulDetail> vulnerabilities 
) {
        public record VulDetail(String cweId, String description,Integer line) {}
}
