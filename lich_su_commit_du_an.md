# Lịch sử commit và quá trình phát triển dự án

Tài liệu này diễn giải lịch sử commit của repository theo quá trình hình thành bộ tài liệu quản lý dự án DevSecOps. Nội dung được tổng hợp từ Git log và các tài liệu hiện có trong repository như `WBS.md`, `bang_uoc_luong_thoi_gian_cong_viec.md`, `lap_lich_bieu_du_an.md`, `so_do_aon_du_an.md`, `so_do_gantt_du_an.md`, `thông_tin_rui_ro_dự_án.md` và các file trong thư mục `skill/`.

## Tổng quan lịch sử Git

| Commit | Ngày | Thông điệp | Vai trò trong quá trình phát triển |
|---|---|---|---|
| `bcc43d6` | 2026-05-29 | `add` | Khởi tạo bộ tài liệu nền tảng cho dự án: WBS, ước lượng thời gian, kỹ năng lập kế hoạch, rủi ro, AON và Gantt. |
| `c6c81bc` | 2026-05-30 | `up` | Cập nhật tài liệu lịch biểu, bổ sung file Excel/hình ảnh đầu ra và bản nháp ước lượng trong thư mục `temp/`. |

Repository hiện có 2 commit chính, phản ánh 2 giai đoạn phát triển tài liệu: giai đoạn xây dựng khung quản lý dự án và giai đoạn hoàn thiện đầu ra minh họa/lịch biểu.

## Commit `bcc43d6` - Khởi tạo bộ tài liệu dự án

**Ngày commit:** 2026-05-29  
**Thông điệp commit:** `add`  
**Quy mô thay đổi:** 10 file mới, 566 dòng được thêm.

### Các file được thêm

| File | Nội dung chính |
|---|---|
| `WBS.md` | Phân rã công việc dự án theo 6 nhóm: phân tích yêu cầu, thiết kế hệ thống, phát triển AI, phát triển phần mềm, kiểm thử, triển khai và bàn giao. |
| `bang_uoc_luong_thoi_gian_cong_viec.md` | Ước lượng thời gian từng công việc bằng các phương pháp như PERT, điểm chức năng và GEF, có xét dự phòng rủi ro. |
| `lap_lich_bieu_du_an.md` | Lập lịch biểu 5 tháng, phân bổ công việc theo mốc tháng, nguồn lực và phụ thuộc. |
| `so_do_aon_du_an.md` | Xây dựng sơ đồ AON, tính ES, EF, LS, LF, Slack và xác định đường găng. |
| `so_do_gantt_du_an.md` | Biểu diễn lịch dự án theo bảng Gantt và Mermaid Gantt. |
| `thông_tin_rui_ro_dự_án.md` | Liệt kê các rủi ro chính: thay đổi yêu cầu/CWE, Sandbox, AI, WebSocket, tích hợp và nhân sự. |
| `skill/skill_ước_lượng_thời_gian.md` | Mô tả cách ước lượng thời gian công việc. |
| `skill/skill_xác_định_phụ_thuộc_Công_việc.md` | Mô tả cách xác định quan hệ phụ thuộc giữa các công việc. |
| `skill/skill_xác_định_nguồn_nhân_lực.md` | Mô tả cách gán công việc cho 4 thành viên: Chung, Hậu, Lợi, Thái. |
| `.vscode/.checkmarxIgnored` | File cấu hình liên quan môi trường phát triển. |

### Ý nghĩa phát triển

Commit này đặt nền móng cho toàn bộ kế hoạch dự án. Từ `WBS.md`, dự án được chia thành các hạng mục rõ ràng:

| Nhóm công việc | Nội dung |
|---|---|
| 1.0 Phân tích yêu cầu | Thu thập yêu cầu DevSecOps, xác định CWE, lập SRS và biểu đồ. |
| 2.0 Thiết kế hệ thống | Thiết kế kiến trúc Frontend, Backend, AI Layer, Execution Layer, database và UI/UX. |
| 3.0 Phát triển AI | Xử lý BigVul, cấu hình CodeBERT, huấn luyện, đánh giá F1-score và đóng gói AI API. |
| 4.0 Phát triển phần mềm | Xây dựng Backend Spring Boot, Frontend ReactJS, WebSocket, Docker Sandbox, Editor, Terminal và Dashboard. |
| 5.0 Kiểm thử | Unit Test, Integration Test, Security & Performance Test. |
| 6.0 Triển khai và bàn giao | Docker Compose, triển khai server thử nghiệm và hoàn thiện tài liệu PBL. |

Sau khi có WBS, commit này tiếp tục bổ sung lớp quản lý tiến độ. File `bang_uoc_luong_thoi_gian_cong_viec.md` biến các hạng mục WBS thành các công việc có mã A đến AA, mỗi công việc có thời lượng ước lượng, phương pháp ước lượng và dự phòng rủi ro. Đây là cơ sở để lập lịch 5 tháng trong `lap_lich_bieu_du_an.md`.

Về mặt phụ thuộc công việc, `so_do_aon_du_an.md` xác định đường găng:

```text
A -> B -> C -> D -> E -> K -> L -> Q -> R -> S -> U -> W -> X -> Y -> Z -> AA
```

Tổng thời gian đường găng là 93 ngày làm việc, tương đương khoảng 18.6 tuần, phù hợp với giả định dự án kéo dài 5 tháng. Đây là điểm quan trọng vì nó chứng minh kế hoạch khả thi nếu các nhóm AI, Backend và Frontend được triển khai song song sau giai đoạn phân tích - thiết kế.

### Vai trò nhân lực trong commit này

Commit đầu tiên cũng chuẩn hóa cách phân công trách nhiệm:

| Thành viên | Vai trò chính | Phạm vi phụ trách |
|---|---|---|
| Chung | Project Manager / Business Analyst / System Analyst | Yêu cầu, SRS, kiến trúc tổng quan, quản lý phạm vi, tài liệu tổng kết. |
| Hậu | AI / Machine Learning Engineer | CWE, BigVul, CodeBERT, huấn luyện, đánh giá và đóng gói AI Service. |
| Lợi | Backend / DevOps / Security Engineer | Spring Boot, database, JWT/Session, WebSocket, Docker Sandbox, Docker Compose, deployment. |
| Thái | Frontend / UI Engineer / Integration Tester | ReactJS, TailwindCSS, Dashboard, Monaco Editor, xterm.js, highlight cảnh báo AI và kiểm thử tích hợp. |

Như vậy, commit `bcc43d6` không chỉ thêm tài liệu rời rạc mà hình thành đầy đủ khung quản lý dự án: phạm vi công việc, thời gian, phụ thuộc, rủi ro, nhân lực và lịch biểu.

## Commit `c6c81bc` - Cập nhật lịch biểu và bổ sung đầu ra minh họa

**Ngày commit:** 2026-05-30  
**Thông điệp commit:** `up`  
**Quy mô thay đổi:** 5 file thay đổi, gồm 3 file mới dạng binary, 1 file Markdown mới trong `temp/` và chỉnh sửa `so_do_gantt_du_an.md`.

### Các thay đổi chính

| File | Trạng thái | Nội dung/ý nghĩa |
|---|---|---|
| `so_do_gantt_du_an.md` | Cập nhật | Chỉnh sửa nhỏ trong tài liệu Gantt để đồng bộ biểu diễn tiến độ. |
| `outputs/project_schedule.xlsx` | Thêm mới | File Excel đầu ra cho lịch dự án. |
| `temp/Book1.xlsx` | Thêm mới | File Excel tạm/phụ trợ trong quá trình dựng lịch hoặc biểu đồ. |
| `temp/Picture3.png` | Thêm mới | Hình ảnh minh họa được tạo ra trong quá trình xử lý lịch biểu/biểu đồ. |
| `temp/uoc_luong_thoi_gian.md` | Thêm mới | Bản nháp hoặc tài liệu phụ về ước lượng thời gian. |

### Ý nghĩa phát triển

Commit này thể hiện giai đoạn hoàn thiện đầu ra sau khi khung quản lý dự án đã được thiết lập ở commit đầu. Thay vì chỉ có tài liệu Markdown, dự án bắt đầu có thêm sản phẩm phụ trợ ở dạng Excel và hình ảnh để phục vụ báo cáo, trình bày hoặc kiểm tra lịch biểu.

Việc bổ sung `outputs/project_schedule.xlsx` cho thấy lịch dự án đã được chuyển từ mô tả Markdown sang dạng bảng tính có thể sử dụng để theo dõi, chỉnh sửa hoặc nộp kèm báo cáo. File `temp/Book1.xlsx` và `temp/Picture3.png` nhiều khả năng là dữ liệu trung gian trong quá trình tạo bảng lịch hoặc xuất hình minh họa. File `temp/uoc_luong_thoi_gian.md` đóng vai trò bản nháp/ghi chú bổ sung cho phần ước lượng.

Về nội dung quản lý dự án, commit này củng cố phần Gantt, tức phần trực quan hóa tiến độ theo thời gian. Điều này liên kết trực tiếp với các tài liệu:

| Tài liệu nền | Cách commit `c6c81bc` kế thừa |
|---|---|
| `bang_uoc_luong_thoi_gian_cong_viec.md` | Dùng thời lượng EST của từng công việc làm dữ liệu đầu vào. |
| `so_do_aon_du_an.md` | Dùng Start/Finish và đường găng từ AON/CPM. |
| `lap_lich_bieu_du_an.md` | Đồng bộ kế hoạch 5 tháng và phân bổ theo giai đoạn. |
| `so_do_gantt_du_an.md` | Cập nhật biểu đồ Gantt và lịch trình trực quan. |

## Diễn tiến phát triển dự án theo lịch sử commit

| Giai đoạn | Commit liên quan | Mô tả |
|---|---|---|
| 1. Xác định phạm vi | `bcc43d6` | Tạo WBS, xác định dự án DevSecOps gồm AI, Backend, Frontend, Docker Sandbox, WebSocket và quy trình kiểm thử/triển khai. |
| 2. Ước lượng công việc | `bcc43d6` | Chuyển WBS thành các công việc A-AA, ước lượng thời gian bằng PERT/điểm chức năng/GEF và đưa rủi ro vào kế hoạch. |
| 3. Xác định phụ thuộc | `bcc43d6` | Lập AON, tính đường găng, xác định các chuỗi phụ thuộc quan trọng giữa AI, Backend, Frontend và Testing. |
| 4. Lập lịch 5 tháng | `bcc43d6` | Tạo lịch biểu theo tháng, cho phép các nhóm AI, Backend và Frontend chạy song song sau phân tích - thiết kế. |
| 5. Trực quan hóa tiến độ | `bcc43d6`, `c6c81bc` | Tạo và cập nhật Gantt, bổ sung Excel/hình ảnh để hỗ trợ trình bày tiến độ. |
| 6. Chuẩn bị đầu ra báo cáo | `c6c81bc` | Bổ sung file đầu ra trong `outputs/` và file tạm trong `temp/`, phục vụ việc hoàn thiện tài liệu/báo cáo. |

## Nhận xét về lịch sử commit

Lịch sử commit hiện tại ngắn nhưng thể hiện rõ hướng phát triển tài liệu:

1. Commit `bcc43d6` là commit nền tảng, tạo gần như toàn bộ hệ thống tài liệu quản lý dự án.
2. Commit `c6c81bc` là commit hoàn thiện, bổ sung đầu ra trực quan và cập nhật phần Gantt.
3. Tên commit còn khá ngắn (`add`, `up`), chưa mô tả rõ nội dung thay đổi. Với các lần phát triển tiếp theo, nên đặt thông điệp commit cụ thể hơn, ví dụ:

```text
docs: add WBS and project estimation documents
docs: update Gantt schedule and export project timeline
docs: add risk management and AON critical path analysis
```

## Kết luận

Trong suốt quá trình phát triển hiện có, repository được xây dựng theo hướng từ phân tích phạm vi đến lập kế hoạch chi tiết:

```text
WBS -> Ước lượng thời gian -> Phân bổ nhân lực -> Phụ thuộc công việc -> AON/CPM -> Gantt -> File đầu ra báo cáo
```

Hai commit hiện tại cho thấy dự án đã hoàn thành phần nền tảng tài liệu quản lý tiến độ. Bộ tài liệu đủ để mô tả phạm vi, nguồn lực, rủi ro, phụ thuộc, đường găng và lịch triển khai 5 tháng cho hệ thống DevSecOps.
