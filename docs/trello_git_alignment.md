# Trello - GitHub Timeline Alignment

Tai lieu nay doi chieu giua bang Trello, lich du an va lich su commit/merge tren GitHub. Muc tieu la bao dam khi dua anh chup GitHub va Trello vao bao cao, thu tu chuc nang, trang thai cong viec va moc thoi gian khong bi lech nhau.

## Ket luan doi chieu

Trello hien tai khop ve thu tu chuc nang voi GitHub: phan tich va thiet ke duoc thuc hien truoc, sau do den AI, Backend, Frontend, Testing, Deployment va bao cao cuoi. Tuy nhien, neu so voi Gantt 5 thang bat dau tu 05/01/2026 thi GitHub dang the hien mot lich su tap trung hon vao giai doan 01/04/2026 - 18/05/2026, cong them cac commit bo sung ngay 31/05/2026. Vi vay, khi bao cao nen trinh bay Trello nhu mot snapshot tien do cuoi ky, con Gantt/WBS la ke hoach tong the.

## Bang doi chieu

| Nhom cong viec | Branch/commit GitHub doi chieu | Moc thoi gian tren Git | Trang thai Trello nen de |
|---|---|---|---|
| Phan tich yeu cau, CWE, Use Case | `docs/phan-tich-yeu-cau` | 01/04/2026 - 02/04/2026 | Done |
| Thiet ke kien truc, database, UI/UX | `docs/thiet-ke-he-thong`, `docs/thiet-ke-ui-ux` | 03/04/2026 - 05/04/2026 | Done |
| AI data prep, model training, API service | `feature/ai-data-prep`, `feature/ai-model-training`, `feature/ai-api-service` | 08/04/2026 - 16/04/2026 | Done |
| Backend core, project API, WebSocket, Docker Sandbox | `feature/be-core-auth`, `feature/be-project-api`, `feature/be-websocket`, `feature/be-docker-sandbox` | 16/04/2026 - 26/04/2026 | Done |
| Frontend layout, file tree, editor, terminal, CWE panel | `feature/fe-layout-core`, `feature/fe-file-tree`, `feature/fe-editor-xterm`, `feature/fe-ai-highlight` | 26/04/2026 - 05/05/2026 | Done |
| Unit test, integration test, WebSocket stress test | `test/backend-docker`, `test/integration-ws` | 08/05/2026 - 12/05/2026 | Done hoac Doing tuy anh chup |
| Docker Compose, Nginx, deploy script | `feature/deployment` | 15/05/2026 | Doing/Done |
| Bao cao, API docs, tong ket PBL | `docs/final-report` | 18/05/2026 | To Do/Doing/Done tuy thoi diem nop |
| Minh chung milestone va scaffold demo bo sung | `milestone/*`, cac commit scaffold moi | 31/05/2026 | Done, ghi chu la bo sung minh chung |

## Diem chua khop can luu y

1. Gantt/WBS dung ke hoach 5 thang tu 05/01/2026, trong khi GitHub commit cu the tap trung tu 01/04/2026 den 18/05/2026. Cach xu ly trong bao cao: noi ro Gantt la ke hoach tong the, GitHub la bang chung commit theo giai doan trien khai va hoan thien.
2. Trello dang mo ta "giai doan cuoi" nen viec nhieu card nam o Done la hop ly. Khong nen trinh bay Trello nhu lich ngay chi tiet tu dau du an, ma nen trinh bay nhu bang quan ly tien do tai thoi diem gan nghiem thu.
3. Cac commit va branch bo sung ngay 31/05/2026 nen duoc giai thich la phan chuan hoa repository, bo sung scaffold va milestone docs de phuc vu bao cao/demo, khong phai cac task goc trong Gantt.

## De xuat khi chup bao cao

- Chup Trello o dang snapshot cuoi ky: phan lon card o Done, cac card deploy/test/report co the nam Doing hoac To Do tuy kich ban bao cao.
- Chup GitHub Network/Branches de thay thu tu docs -> AI -> backend -> frontend -> test -> deployment -> final report.
- Neu can dua ngay 15/05/2026 vao bao cao, nen gan voi `feature/deployment` vi Git co cac commit Docker Compose, Nginx va deploy script dung ngay nay.
