# Sơ đồ Gantt dự án

Quy ước:
* Đơn vị thời gian trong bảng: ngày làm việc.
* Start/Finish dưới đây dùng lịch bắt đầu sớm từ kết quả AON/CPM.
* Công việc có dấu `*` là công việc thuộc đường găng.

## Bảng Gantt theo ngày làm việc

| Ký hiệu | Công việc | Start | Finish | Duration | Công việc trước | Đường găng |
|---|---|---:|---:|---:|---|---|
| A | Thu thập yêu cầu hệ thống từ bài toán DevSecOps | 0 | 4 | 4 | - | * |
| B | Xác định danh mục CWE cần phát hiện | 4 | 7 | 3 | A | * |
| C | Lập tài liệu SRS, Use Case, Sequence | 7 | 12 | 5 | A, B | * |
| D | Thiết kế kiến trúc tổng quan | 12 | 17 | 5 | C | * |
| E | Thiết kế database ERD và cấu trúc lưu trữ | 17 | 21 | 4 | D | * |
| F | Thiết kế UI/UX cho Editor, Terminal, Dashboard | 17 | 21 | 4 | D |  |
| G | Tiền xử lý dữ liệu BigVul | 7 | 15 | 8 | B |  |
| H | Cấu hình CodeBERT và đầu ra đa nhiệm | 15 | 24 | 9 | G |  |
| I | Huấn luyện mô hình AI | 24 | 37 | 13 | H |  |
| J | Đánh giá F1-score và đóng gói AI API | 37 | 43 | 6 | I |  |
| K | Khởi tạo Backend, DB, Entity, JWT/Session | 21 | 27 | 6 | E | * |
| L | Xây dựng API quản lý dự án và cây thư mục | 27 | 35 | 8 | K | * |
| M | Message Broker và WebSocket đồng bộ mã nguồn | 35 | 43 | 8 | L |  |
| N | Docker Client, Sandbox và Terminal I/O | 27 | 36 | 9 | K |  |
| O | Hàng đợi gọi AI Server và stream kết quả | 43 | 49 | 6 | J, M |  |
| P | Khởi tạo Frontend, TailwindCSS, Router, layout IDE | 21 | 26 | 5 | F |  |
| Q | Dashboard quản lý dự án và form tạo mới | 35 | 40 | 5 | P, L | * |
| R | Cây thư mục đệ quy và Context Menu | 40 | 46 | 6 | Q, L | * |
| S | Monaco Editor, auto-complete, đồng bộ WebSocket | 46 | 54 | 8 | R, M | * |
| T | xterm.js và WebSocket riêng cho Terminal | 36 | 42 | 6 | N, P |  |
| U | Cảnh báo AI, highlight lỗi, panel CWE | 54 | 61 | 7 | O, S | * |
| V | Unit Test Backend và Docker Service | 36 | 41 | 5 | L, N |  |
| W | Integration Test Frontend -> AI Server | 61 | 68 | 7 | U, V | * |
| X | Security & Performance Test WebSocket và Docker Sandbox | 68 | 74 | 6 | W | * |
| Y | Kết nối module và đóng gói Docker Compose | 74 | 80 | 6 | X | * |
| Z | Triển khai hệ thống lên server chạy thử | 80 | 85 | 5 | Y | * |
| AA | Hoàn thiện tài liệu kiến trúc và báo cáo PBL | 85 | 93 | 8 | W, Z | * |

## Mermaid Gantt

Ngày bắt đầu minh họa: 2026-01-05. Thời lượng vẫn giữ theo số ngày làm việc đã ước lượng trong lịch.

```mermaid
gantt
    title Gantt dự án DevSecOps
    dateFormat  YYYY-MM-DD
    axisFormat  %d/%m

    section Phân tích và thiết kế
    A - Thu thập yêu cầu              :crit, a, 2026-01-05, 4d
    B - Xác định CWE                  :crit, b, after a, 3d
    C - SRS, Use Case, Sequence       :crit, c, after b, 5d
    D - Thiết kế kiến trúc            :crit, d, after c, 5d
    E - Thiết kế database             :crit, e, after d, 4d
    F - Thiết kế UI/UX                :f, after d, 4d

    section AI
    G - Tiền xử lý BigVul             :g, after b, 8d
    H - Cấu hình CodeBERT             :h, after g, 9d
    I - Huấn luyện mô hình            :i, after h, 13d
    J - Đánh giá và đóng gói AI API   :j, after i, 6d

    section Backend / DevOps
    K - Khởi tạo Backend              :crit, k, after e, 6d
    L - API dự án và cây thư mục      :crit, l, after k, 8d
    M - Broker/WebSocket              :m, after l, 8d
    N - Docker Sandbox                :n, after k, 9d
    O - Queue AI và stream kết quả    :o, after j m, 6d

    section Frontend
    P - Khởi tạo Frontend             :p, after f, 5d
    Q - Dashboard                     :crit, q, after p l, 5d
    R - Cây thư mục UI                :crit, r, after q l, 6d
    S - Monaco/WebSocket              :crit, s, after r m, 8d
    T - xterm.js Terminal             :t, after n p, 6d
    U - Cảnh báo AI/CWE               :crit, u, after o s, 7d

    section Testing và triển khai
    V - Unit Test                     :v, after l n, 5d
    W - Integration Test              :crit, w, after u v, 7d
    X - Security/Performance Test     :crit, x, after w, 6d
    Y - Docker Compose                :crit, y, after x, 6d
    Z - Triển khai server             :crit, z, after y, 5d
    AA - Tài liệu và báo cáo PBL      :crit, aa, after w z, 8d
```

## Đường găng trên Gantt

```text
A -> B -> C -> D -> E -> K -> L -> Q -> R -> S -> U -> W -> X -> Y -> Z -> AA
```

Tổng thời gian dự án theo đường găng: **93 ngày làm việc**.
