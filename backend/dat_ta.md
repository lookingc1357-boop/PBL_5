# Mô tả API Frontend cho IDE

## Phần 1: File Explorer API (STOMP WebSocket)

### Tổng quan

Backend `IDE` hiện hỗ trợ thao tác dự án qua STOMP WebSocket với các chức năng:

- Tạo file/folder
- Xóa file/folder
- Copy file/folder
- Move file/folder
- Cập nhật nội dung file
- Lấy cây thư mục (project tree)
- Đọc nội dung file
- Lắng nghe thay đổi file/folder realtime

Thư mục gốc lưu file của dự án trong user là:

- `D:/docker_store/{username}/{project_name}`

### WebSocket cấu hình

- Endpoint: `/ws`
- Application prefix: `/app`
- User destination prefix: `/user/queue`

Frontend nên kết nối STOMP tới backend và subscribe 2 channel:

- `/user/queue/explorer-response`
- `/user/queue/file-events`

Sau khi kết nối thành công, frontend nên gửi message tới:

- Dest: `/app/explorer/subscribe`
- Payload: `{}`

### Danh sách endpoint STOMP

- `/app/explorer/create`
- `/app/explorer/delete`
- `/app/explorer/copy`
- `/app/explorer/move`
- `/app/explorer/update`
- `/app/explorer/list`
- `/app/explorer/read`
- `/app/explorer/subscribe`

## 1. Subscribe watcher

- Dest: `/app/explorer/subscribe`
- Payload: `{}`

Mục đích:

- Backend gọi `IOService.ensureWatcher(username)`
- Bắt đầu theo dõi thư mục `D:/docker_store/{username}/{project_name}`
- Phát event khi có tạo/xóa/sửa đổi

## 2. List project tree

### Request

- Dest: `/app/explorer/list`
- Payload:

```json
{ "path": "" }
```

hoặc

```json
{ "path": "src/main/java/com/pbl/IDE" }
```

### Response

```json
{
  "status": "ok",
  "action": "list",
  "tree": {
    "name": "IDE",
    "path": "",
    "directory": true,
    "children": [ ... ]
  }
}
```

### Ghi chú

- `path` là đường dẫn relative với thư mục user
- `path` rỗng hoặc `"/"` hoặc `"."` sẽ trả toàn bộ root user
- `tree.children` có thể chứa thư mục và file

## 3. Read file content

### Request

- Dest: `/app/explorer/read`
- Payload:

```json
{ "path": "src/main/java/com/pbl/IDE/SomeFile.java" }
```

### Response

```json
{
  "status": "ok",
  "action": "read",
  "path": "src/main/java/com/pbl/IDE/SomeFile.java",
  "content": "..."
}
```

### Ghi chú

- Chỉ đọc được file, không đọc folder
- Nội dung trả về dạng UTF-8

## 4. Tạo file/folder

### Request

- Dest: `/app/explorer/create`

File:

```json
{
  "path": "src/main/java/com/pbl/IDE/NewFile.java",
  "directory": false
}
```

Folder:

```json
{
  "path": "src/main/resources/templates",
  "directory": true
}
```

## 5. Xóa file/folder

### Request

- Dest: `/app/explorer/delete`
- Payload:

```json
{ "path": "src/main/java/com/pbl/IDE/NewFile.java" }
```

## 6. Copy file/folder

### Request

- Dest: `/app/explorer/copy`
- Payload:

```json
{
  "source": "src/main/java/com/pbl/IDE/OldFile.java",
  "target": "src/main/java/com/pbl/IDE/CopyFile.java"
}
```

## 7. Move file/folder

### Request

- Dest: `/app/explorer/move`
- Payload:

```json
{
  "source": "src/main/java/com/pbl/IDE/CopyFile.java",
  "target": "src/main/java/com/pbl/IDE/MoveFile.java"
}
```

## 8. Update file

### Request

- Dest: `/app/explorer/update`
- Payload:

```json
{
  "path": "src/main/java/com/pbl/IDE/SomeFile.java",
  "operations": [
    { "index": 2, "type": "removed", "value": "" },
    { "index": 2, "type": "added", "value": "System.out.println(\"Hello\");" }
  ]
}
```

### Ghi chú

- `type` có thể là `removed` hoặc `added`
- `index` là chỉ số dòng trong file
- Cơ chế cập nhật: xóa các dòng `removed` trước, sau đó thêm các dòng `added`
- File được ghi ra file tạm `.temp` rồi rename sang file gốc để tránh trạng thái nửa chừng

## 9. Response chung của backend

Các response đều trả về định dạng chung:

- `status`: `ok`
- `action`: `created`/`deleted`/`copied`/`moved`/`updated`/`list`/`read`
- `path`, `source`, `target`, `tree`, `content` tùy action

Example:

```json
{
  "status": "ok",
  "action": "created",
  "path": "src/main/java/com/pbl/IDE/NewFile.java"
}
```

## 10. Event realtime khi thay đổi file/folder

Frontend nhận event trên channel:

- `/user/queue/file-events`

Ví dụ payload:

```json
{
  "action": "modified",
  "path": "src/main/java/com/pbl/IDE/SomeFile.java",
  "isDirectory": false,
  "timestamp": 1710000000000
}
```

### Các action có thể có

- `created`
- `deleted`
- `modified`

## 11. Lưu ý frontend

- Không dùng đường dẫn tuyệt đối, chỉ dùng path relative với thư mục user
- `path` có thể rỗng hoặc `"/"`/`"."` để lấy root
- Tất cả giao tiếp hiện tại là STOMP, không có REST API cho explorer
- Nếu cần hiển thị cây thư mục, gọi `/app/explorer/list` rồi dựng UI dựa trên `tree.children`

## 12. Gợi ý UI

1. Kết nối STOMP.
2. Subscribe `/user/queue/explorer-response` và `/user/queue/file-events`.
3. Gửi `/app/explorer/subscribe`.
4. Khi cần hiển thị file tree, gọi `/app/explorer/list`.
5. Khi cần mở file, gọi `/app/explorer/read`.
6. Khi thao tác file/folder, gọi endpoint tương ứng.
7. Khi nhận event `file-events`, cập nhật lại cây UI nếu cần.

---

# Phần 2: Project Management API (REST)

## Tổng quan Project Service

Backend cung cấp API để quản lý các dự án của user qua REST endpoints.

Cấu trúc thư mục:

- `D:/docker_store/{username}` - không gian làm việc của user
- `D:/docker_store/{username}/{project_name}` - thư mục dự án

## Danh sách endpoint REST

- `POST /api/projects/create` - Tạo dự án mới
- `POST /api/projects/delete` - Xóa dự án
- `GET /api/projects/list` - Lấy danh sách toàn bộ dự án

---

## 1. Tạo dự án mới

### Request

- Method: `POST`
- URL: `/api/projects/create`
- Header: `Content-Type: application/json`
- Payload:

```json
{
  "projectName": "my-awesome-project"
}
```

### Response

```json
{
  "status": "ok",
  "action": "created",
  "projectName": "my-awesome-project"
}
```

---

## 2. Xóa dự án

### Request

- Method: `POST`
- URL: `/api/projects/delete`
- Header: `Content-Type: application/json`
- Payload:

```json
{
  "projectName": "my-awesome-project"
}
```

### Response

```json
{
  "status": "ok",
  "action": "deleted",
  "projectName": "my-awesome-project"
}
```

---

## 3. Lấy danh sách dự án

### Request

- Method: `GET`
- URL: `/api/projects/list`

### Response

```json
{
  "status": "ok",
  "action": "list",
  "projects": [
    {
      "name": "my-awesome-project",
      "createdAt": "2026-04-22T10:30:45",
      "lastModified": "2026-04-22T15:45:30",
      "description": "This is my awesome project. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua..."
    },
    {
      "name": "another-project",
      "createdAt": "2026-04-20T08:15:20",
      "lastModified": "2026-04-21T14:20:10",
      "description": ""
    }
  ]
}
```

### Ghi chú

- `name`: tên folder dự án
- `createdAt`: ngày tạo dự án (format ISO 8601: `yyyy-MM-ddTHH:mm:ss`)
- `lastModified`: lần chỉnh sửa gần nhất (format ISO 8601)
- `description`: 200 kí tự đầu tiên từ file `README.md` của dự án
  - Nếu file `README.md` không tồn tại, trả về string rỗng
- Danh sách được sắp xếp theo `lastModified` giảm dần (gần nhất trước)

---

## 4. Luồng sử dụng đề xuất

1. **Lấy danh sách dự án**: Gọi `GET /api/projects/list` để hiển thị tất cả dự án của user
2. **Tạo dự án mới**: Gọi `POST /api/projects/create` khi user tạo project mới
3. **Xóa dự án**: Gọi `POST /api/projects/delete` khi user muốn xóa project
4. **Chọn dự án**: Sau khi user chọn một dự án, chuyển sang File Explorer API
   - Sử dụng STOMP để kết nối và lấy cây file của dự án đó
   - Path trong File Explorer là: `D:/docker_store/{username}/{project_name}`

---

## 5. Lưu ý

- Tất cả project API yêu cầu xác thực (user phải login)
- `projectName` không được chứa ký tự đặc biệt nguy hiểm
- Khi xóa dự án, tất cả file/folder bên trong sẽ bị xóa
- `description` giới hạn 200 kí tự từ README.md (nếu file quá lớn)

7. Khi nhận event `file-events`, cập nhật lại cây UI nếu cần.
