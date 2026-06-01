package com.pbl.IDE.dto;

/** Scenario 2: FE gửi một hàm đang được chỉnh sửa */
public record ScanSingleRequest(
        String signature,   // tên / signature của hàm
        String code,        // source code của hàm
        Position position   // vị trí con trỏ trong editor
) {
    public record Position(int line, int column) {}
}