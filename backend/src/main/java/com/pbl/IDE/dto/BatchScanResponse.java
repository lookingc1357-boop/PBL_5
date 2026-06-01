package com.pbl.IDE.dto;
import java.util.List;

/** Wrapper trả về cho Scenario 1 (batch) */
public record BatchScanResponse(
        String status,                    // "COMPLETED" | "ERROR"
        List<FunctionScanResult> results,
        String filePath,                    // đường dẫn file (để FE map lại)
        String error
) {
        public String getStatus() {
                return status;
        }
}

