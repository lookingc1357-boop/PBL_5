package com.pbl.IDE.dto;

/** Wrapper trả về cho Scenario 2 (single) */
public record SingleScanResponse(
        String status,                    // "COMPLETED" | "ERROR"
        FunctionScanResult result,
        String error
) {}
 