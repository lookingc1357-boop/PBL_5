# Lập lịch biểu dự án

Giả định lập lịch: tổng thời gian thực hiện là 5 tháng, tương đương khoảng 20 tuần làm việc. Lịch dùng quan hệ phụ thuộc FS là chính, cho phép chạy song song Backend, Frontend và AI sau khi hoàn tất phân tích - thiết kế. Các thời lượng dưới đây lấy từ bảng `bang_uoc_luong_thoi_gian_cong_viec.md`.

| STT | Ký hiệu | Công việc | Thời gian ước lượng | Công việc trước | Số lượng nhân lực | Tên nhân lực | Mô tả |
|---:|---|---|---:|---|---:|---|---|
| 1 | A | Thu thập yêu cầu hệ thống từ bài toán DevSecOps | 4 ngày | - | 4 | Chung, Hậu, Lợi, Thái | Chung điều phối lấy yêu cầu từ các mảng AI, Backend, Frontend; có tính đến rủi ro thay đổi yêu cầu. |
| 2 | B | Xác định danh mục các loại lỗ hổng CWE cần phát hiện | 3 ngày | A | 2 | Hậu, Chung | Hậu chọn phạm vi CWE; Chung chốt phạm vi yêu cầu để giảm rủi ro đổi chuẩn CWE. |
| 3 | C | Lập tài liệu SRS, Use Case, Sequence | 5 ngày | A, B | 1 | Chung | Hoàn thiện đặc tả làm cơ sở thiết kế và nghiệm thu. |
| 4 | D | Thiết kế kiến trúc tổng quan Frontend, Backend, AI Layer, Execution Layer | 5 ngày | C | 3 | Chung , Lợi, Thái | Chốt kiến trúc module và luồng tích hợp để giảm rủi ro xung đột Frontend/Backend/AI. |
| 5 | E | Thiết kế database ERD và cấu trúc lưu trữ Project, User, File | 4 ngày | D | 2 | Lợi, Chung | Lợi thiết kế dữ liệu Backend; Chung rà soát theo yêu cầu nghiệp vụ. |
| 6 | F | Thiết kế UI/UX cho Editor, Terminal và Dashboard | 4 ngày | D | 1 | Thái | Thái thiết kế giao diện IDE; 
| 7 | G | Thu thập, làm sạch, tiền xử lý BigVul và lọc theo ngưỡng | 8 ngày | B | 1 | Hậu | Chuẩn hóa dữ liệu AI, xử lý mất cân bằng dữ liệu theo rủi ro mô hình. |
| 8 | H | Cấu hình CodeBERT và thiết kế kiến trúc đầu ra đa nhiệm | 9 ngày | G | 1 | Hậu | Xây dựng nền mô hình AI cho phân loại lỗ hổng CWE. |
| 9 | I | Huấn luyện mô hình với Inverse Frequency Weighting và Uncertainty Weighting | 13 ngày | H | 1 | Hậu | Huấn luyện và điều chỉnh trọng số để giảm rủi ro chất lượng AI thấp. |
| 10 | J | Đánh giá F1-score, kiểm thử dự đoán và đóng gói mô hình thành API Service | 6 ngày | I | 1 | Hậu, Lợi | Hậu đánh giá model; |
| 11 | K | Khởi tạo Backend, cấu hình DB, Entity/Repository và JWT/Session | 6 ngày | E | 1 | Lợi | Tạo nền Backend Spring Boot, bảo mật phiên và kết nối DB. |
| 12 | L | Xây dựng API quản lý dự án và logic đọc/ghi cây thư mục | 8 ngày | K | 1 | Lợi | Xây dựng API lõi cho Project/File, phục vụ Dashboard và cây thư mục. |
| 13 | M | Cấu hình Message Broker và WebSocket đồng bộ mã nguồn | 8 ngày | L | 1 | Lợi, Thái | Lợi xử lý WebSocket Backend & Thái phối hợp định dạng dữ liệu đồng bộ Editor. |
| 14 | N | Tích hợp Docker Client, khởi tạo Sandbox độc lập, xử lý I/O Terminal | 9 ngày | K | 1 | Lợi | Lợi xử lý Docker Sandbox và giới hạn tài nguyên & Thái phối hợp Terminal UI. |
| 15 | O | Thiết kế hàng đợi gọi API sang AI Server và stream kết quả về Frontend | 6 ngày | J, M | 2 | Lợi, Hậu | Kết nối Backend với AI Service, stream kết quả để tránh timeout. |
| 16 | P | Khởi tạo Frontend, TailwindCSS, Router và layout IDE | 5 ngày | F | 1 | Thái | Tạo khung Frontend và layout chuẩn IDE. |
| 17 | Q | Xây dựng Dashboard quản lý dự án và form tạo mới | 5 ngày | P, L | 2 | Thái, Lợi | Thái xây UI Dashboard; Lợi hỗ trợ API quản lý dự án. |
| 18 | R | Render cây thư mục đệ quy và Context Menu tạo/xóa tệp tin | 6 ngày | Q, L | 2 | Thái, Lợi | Đồng bộ thao tác file giữa Frontend và Backend. |
| 19 | S | Nhúng Monaco Editor, auto-complete và đồng bộ nội dung qua WebSocket | 8 ngày | R, M | 2 | Thái, Lợi | Tích hợp Editor, delta update và cơ chế phục hồi khi mất kết nối. |
| 20 | T | Tích hợp xterm.js và kênh WebSocket riêng cho Terminal | 6 ngày | N, P | 2 | Thái, Lợi | Kết nối Terminal UI với Docker Sandbox, có xét rủi ro tài nguyên. |
| 21 | U | Xử lý cảnh báo AI, highlight dòng lỗi và panel chi tiết CWE | 7 ngày | O, S | 3 | Thái, Hậu, Lợi | Hiển thị kết quả AI trên Editor, thống nhất JSON highlight để tránh lỗi tích hợp. |
| 22 | V | Unit Test cho Backend và Docker Service | 5 ngày | L, N | 1 | Lợi | Test logic Backend, Docker Service và các ràng buộc bảo mật. |
| 23 | W | Integration Test luồng Frontend -> AI Server và quét lỗ hổng | 7 ngày | U, V | 4 | Chung, Hậu, Lợi, Thái | Kiểm thử xuyên suốt Frontend, Backend, AI; dùng PR/review để giảm xung đột tích hợp. |
| 24 | X | Security & Performance Test WebSocket và Docker Sandbox | 6 ngày | W | 2 | Lợi, Thái | Đo độ trễ WebSocket, kiểm tra giới hạn CPU/RAM và cách ly Sandbox. |
| 25 | Y | Kết nối hoàn chỉnh các module và đóng gói Docker Compose | 6 ngày | X | 3 | Lợi, Chung, Thái | Tích hợp cuối và đóng gói chạy thử bằng Docker Compose. |
| 26 | Z | Triển khai hệ thống lên server chạy thử nghiệm | 5 ngày | Y | 2 | Lợi, Chung | Triển khai server, kiểm tra cấu hình môi trường và dữ liệu chạy thử. |
| 27 | AA | Hoàn thiện tài liệu kiến trúc và báo cáo tổng kết PBL | 8 ngày | W, Z | 2 | Chung, Hậu | Chung hoàn thiện tài liệu kiến trúc/báo cáo; Hậu bổ sung phần AI và kết quả đánh giá. |

## Phân bổ theo mốc 5 tháng

| Mốc thời gian | Công việc chính | Nhân lực trọng tâm | Ghi chú rủi ro |
|---|---|---|---|
| Tháng 1 | A, B, C, D, E, F, G, K, P | Chung, Hậu, Lợi, Thái | Chốt yêu cầu và phạm vi CWE sớm để tránh phát sinh 48 giờ rủi ro thay đổi yêu cầu. |
| Tháng 2 | H, I, L, M, N, Q, R | Hậu, Lợi, Thái | Tách luồng AI, Backend, Frontend chạy song song; kiểm soát rủi ro Sandbox và WebSocket từ sớm. |
| Tháng 3 | J, O, S, T, U, V | Hậu, Lợi, Thái | Tập trung tích hợp AI Service, Editor, Terminal và WebSocket. |
| Tháng 4 | W, X, Y | Chung, Hậu, Lợi, Thái | Kiểm thử tích hợp, bảo mật và hiệu năng; dành buffer cho xung đột Frontend/Backend/AI. |
| Tháng 5 | Z, AA, buffer rủi ro và hoàn thiện nghiệm thu | Chung, Lợi, Hậu, Thái | Dự phòng rủi ro nhân sự 24 giờ và các lỗi nghiệm thu cuối. |

## Ghi chú phụ thuộc

| Chuỗi phụ thuộc chính | Ý nghĩa |
|---|---|
| A -> B -> G -> H -> I -> J -> O -> U -> W | Đường phụ thuộc AI đến tích hợp cảnh báo lỗ hổng. |
| A -> C -> D -> E -> K -> L -> M -> O -> U -> W | Đường phụ thuộc Backend/API/WebSocket đến tích hợp hệ thống. |
| A -> C -> D -> F -> P -> Q -> R -> S -> U -> W | Đường phụ thuộc Frontend/Editor đến tích hợp cảnh báo AI. |
| K -> N -> T -> X | Đường phụ thuộc Docker Sandbox, Terminal và kiểm thử bảo mật. |

## Kiểm soát rủi ro trong lịch

| Rủi ro | Người chịu trách nhiệm chính trong lịch | Cách xử lý trong lịch biểu |
|---|---|---|
| Thay đổi yêu cầu hoặc chuẩn CWE | Chung, Hậu | Chốt A, B, C trong tháng 1; giữ buffer tháng 5. |
| Sandbox sập hoặc cạn tài nguyên | Lợi | Đưa kiểm soát tài nguyên vào N và kiểm thử lại ở X. |
| Mô hình AI chậm hoặc sai | Hậu | Ước lượng dài hơn cho G, H, I, J và dùng O để chạy nền/stream kết quả. |
| Mất đồng bộ WebSocket | Lợi, Thái | Kiểm soát ở M, S và kiểm thử tích hợp W. |
| Xung đột tích hợp Frontend, Backend, AI | Chung, Thái | Chốt kiến trúc D, API contract ở O và test W. |
| Rủi ro nhân sự, ước lượng sai | Toàn bộ nhóm | Duy trì họp tuần, pair-programming khi trễ và giữ buffer tháng 5. |
