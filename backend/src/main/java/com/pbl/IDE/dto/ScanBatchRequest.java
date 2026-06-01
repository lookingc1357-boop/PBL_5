package com.pbl.IDE.dto;

import java.util.List;

// ─── Inbound DTOs ────────────────────────────────────────────────────────────

/** Scenario 1: FE gửi toàn bộ danh sách hàm trong file */
public record ScanBatchRequest(List<FunctionItem> functions, String filePath) {
    public record FunctionItem(String signature, String code) {}
}
