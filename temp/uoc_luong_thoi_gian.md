
| Ký hiệu | Mã WBS | Công việc | Cách ước lượng phù hợp | MO (ngày) | ML (ngày) | MP (ngày) | EST (ngày) | Dự phòng rủi ro áp dụng |
|---|---|---|---|---:|---:|---:|---:|---|
| A | 1.1 | Thu thập yêu cầu hệ thống từ bài toán DevSecOps | PERT do yêu cầu có thể thay đổi | 3 | 4 | 7 | 4 | Có, rủi ro thay đổi yêu cầu |
| B | 1.2 | Xác định danh mục các loại lỗ hổng CWE cần phát hiện theo tiêu chuẩn | PERT do phụ thuộc chuẩn CWE và phạm vi AI | 2 | 3 | 6 | 3 | Có, rủi ro thay đổi chuẩn CWE |
| C | 1.3 | Lập tài liệu đặc tả yêu cầu SRS và biểu đồ Use Case, Sequence | PERT kết hợp rà soát tài liệu | 3 | 5 | 8 | 5 | Có, rủi ro sai yêu cầu |
| D | 2.1 | Thiết kế kiến trúc tổng quan Frontend, Backend, AI Layer, Execution Layer | PERT do cần thống nhất nhiều module | 4 | 5 | 8 | 5 | Có, rủi ro tích hợp |
| E | 2.2 | Thiết kế cơ sở dữ liệu ERD và cấu trúc lưu trữ cho Project, User, File | Điểm chức năng cho dữ liệu và API | 3 | 4 | 7 | 4 | Có, rủi ro thay đổi cấu trúc dữ liệu |
| F | 2.3 | Thiết kế giao diện UI/UX cho Editor, Terminal và Dashboard | Điểm chức năng UI | 3 | 4 | 6 | 4 | Có, rủi ro hiểu sai luồng giao diện |
| G | 3.1 | Thu thập, làm sạch, tiền xử lý BigVul và lọc theo ngưỡng | PERT do dữ liệu mất cân bằng | 5 | 7 | 12 | 8 | Có, rủi ro chất lượng dữ liệu AI |
| H | 3.2 | Cấu hình CodeBERT và thiết kế kiến trúc đầu ra đa nhiệm | PERT cho công việc AI rủi ro cao | 6 | 8 | 14 | 9 | Có, rủi ro mô hình sai/chậm |
| I | 3.3 | Huấn luyện mô hình với Inverse Frequency Weighting và Uncertainty Weighting | PERT cho huấn luyện AI | 8 | 12 | 20 | 13 | Có, rủi ro hiệu năng và dữ liệu |
| J | 3.4 | Đánh giá F1-score, kiểm thử dự đoán và đóng gói mô hình thành API Service | PERT kết hợp GEF vì phụ thuộc tích hợp | 4 | 5 | 10 | 6 | Có, rủi ro AI timeout/API |
| K | 4.1.1 | Khởi tạo Backend, cấu hình DB, Entity/Repository và JWT/Session | Điểm chức năng Backend | 4 | 6 | 9 | 6 | Có, rủi ro cấu hình bảo mật |
| L | 4.1.2 | Xây dựng API quản lý dự án và logic đọc/ghi cây thư mục | Điểm chức năng Backend | 6 | 8 | 12 | 8 | Có, rủi ro sai cấu trúc file |
| M | 4.1.3 | Cấu hình Message Broker và WebSocket đồng bộ mã nguồn | PERT do đồng bộ thời gian thực rủi ro | 5 | 7 | 12 | 8 | Có, rủi ro mất đồng bộ WebSocket |
| N | 4.1.4 | Tích hợp Docker Client, khởi tạo Sandbox độc lập, xử lý I/O Terminal | PERT do rủi ro bảo mật và tài nguyên | 6 | 8 | 14 | 9 | Có, rủi ro Sandbox sập/cạn tài nguyên |
| O | 4.1.5 | Thiết kế hàng đợi gọi API sang AI Server và stream kết quả về Frontend | PERT do tích hợp AI/Backend/Frontend | 4 | 6 | 10 | 6 | Có, rủi ro AI timeout và tích hợp |
| P | 4.2.1 | Khởi tạo Frontend, cấu hình TailwindCSS, Router và layout IDE | Điểm chức năng Frontend | 3 | 5 | 7 | 5 | Không đáng kể |
| Q | 4.2.2 | Xây dựng Dashboard quản lý dự án và form tạo mới | Điểm chức năng Frontend | 4 | 5 | 8 | 5 | Có, rủi ro thay đổi yêu cầu UI |
| R | 4.2.3 | Render cây thư mục đệ quy và Context Menu tạo/xóa tệp tin | Điểm chức năng UI có logic tương tác | 4 | 5 | 9 | 6 | Có, rủi ro sai đồng bộ file |
| S | 4.2.4 | Nhúng Monaco Editor, auto-complete và đồng bộ nội dung qua WebSocket | PERT do đồng bộ thời gian thực rủi ro | 5 | 7 | 12 | 8 | Có, rủi ro mất đồng bộ WebSocket |
| T | 4.2.5 | Tích hợp xterm.js và kênh WebSocket riêng cho Terminal | PERT do phụ thuộc Backend Terminal I/O | 4 | 6 | 10 | 6 | Có, rủi ro Sandbox/Terminal |
| U | 4.2.6 | Xử lý cảnh báo AI, highlight dòng lỗi và panel chi tiết CWE | PERT do phụ thuộc kết quả AI | 5 | 6 | 10 | 7 | Có, rủi ro sai định dạng JSON highlight |
| V | 5.1 | Unit Test cho Backend và Docker Service | GEF do phụ thuộc chất lượng code | 3 | 5 | 8 | 5 | Có, rủi ro bảo mật Docker |
| W | 5.2 | Integration Test luồng Frontend -> AI Server và quét lỗ hổng | PERT do tích hợp nhiều module | 5 | 6 | 12 | 7 | Có, rủi ro xung đột tích hợp |
| X | 5.3 | Security & Performance Test WebSocket và Docker Sandbox | PERT do rủi ro bảo mật/hiệu năng cao | 4 | 6 | 10 | 6 | Có, rủi ro Sandbox và WebSocket |
| Y | 6.1 | Kết nối hoàn chỉnh các module và đóng gói Docker Compose | PERT do tích hợp cuối | 4 | 5 | 9 | 6 | Có, rủi ro xung đột tích hợp |
| Z | 6.2 | Triển khai hệ thống lên server chạy thử nghiệm | PERT do phụ thuộc môi trường server | 3 | 4 | 8 | 5 | Có, rủi ro môi trường triển khai |
| AA | 6.3 | Hoàn thiện tài liệu kiến trúc và báo cáo tổng kết PBL | PERT do phụ thuộc kết quả toàn dự án | 5 | 8 | 12 | 8 | Có, rủi ro thay đổi yêu cầu và nhân sự |
