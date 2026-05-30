# Face commit dự án

Tài liệu này là danh sách lịch sử commit giả lập cho 2 repository của dự án:

- Frontend: `https://github.com/loinguyenvan274/IDE-FE-REACT`
- Backend/AI/DevOps: `https://github.com/loinguyenvan274/vulnerability-scanning-integrated-IDE`

Thời gian bắt đầu dự án: **05/01/2026**. Lịch sử commit được phân bổ theo WBS, lịch biểu 5 tháng và vai trò nhân lực trong `skill/skill_xác_định_nguồn_nhân_lực.md`.

## Nhân sự và định danh commit

| Thành viên | Vai trò chính | Email | Tên giả lập trên GitHub |
|---|---|---|---|
| Chung | Project Manager / BA / System Analyst | `lethanhchung1107@gmail.com` | `lethanhchung1107` |
| Lợi | Backend / DevOps / Security Engineer | `loinguyenvan274@gmail.com` | `loinguyenvan274` |
| Hậu | AI / Machine Learning Engineer | `hauthanhnguyen1203@gmail.com` | `hauthanhnguyen1203` |
| Thái | Frontend / UI Engineer / Integration Tester | `nhatthinh0812@gmail.com` | `nhatthinh0812` |

## Quy tắc phân bổ commit

- Chung commit các phần yêu cầu, SRS, kiến trúc tổng quan, quản lý rủi ro, tài liệu tổng kết và các merge/review quan trọng.
- Hậu commit các phần CWE, BigVul, CodeBERT, training, evaluation và AI API.
- Lợi commit Backend Spring Boot, database, JWT/Session, WebSocket, Docker Sandbox, Docker Compose, deployment và security/performance test.
- Thái commit Frontend ReactJS, TailwindCSS, Dashboard, file tree, Monaco Editor, xterm.js, highlight cảnh báo AI và integration UI.

---

# Repository Backend/AI/DevOps

## Commits on May 29, 2026

Merge pull request #18 from loinguyenvan274/release/final-pbl-handover  
lethanhchung1107 authored 1 day ago Verified

docs(report): finalize architecture and PBL handover package  
lethanhchung1107 committed 1 day ago

docs(ai): add final model evaluation summary and CWE coverage table  
hauthanhnguyen1203 committed 1 day ago

chore(release): tag final trial deployment configuration  
loinguyenvan274 committed 1 day ago

## Commits on May 22, 2026

Merge pull request #17 from loinguyenvan274/deploy/trial-server  
loinguyenvan274 authored last week Verified

deploy: provision trial server environment variables  
loinguyenvan274 committed last week

fix(deploy): align docker compose service names with backend profile  
loinguyenvan274 committed last week

docs(deploy): record trial server checklist and rollback notes  
lethanhchung1107 committed last week

## Commits on May 15, 2026

Merge pull request #16 from loinguyenvan274/release/docker-compose-package  
loinguyenvan274 authored 2 weeks ago Verified

feat(compose): connect backend ai service postgres and sandbox network  
loinguyenvan274 committed 2 weeks ago

fix(compose): isolate sandbox containers with resource limits  
loinguyenvan274 committed 2 weeks ago

test(e2e): add full vulnerability scan smoke workflow  
nhatthinh0812 committed 2 weeks ago

## Commits on May 8, 2026

Merge pull request #15 from loinguyenvan274/test/security-performance-pass  
loinguyenvan274 authored 3 weeks ago Verified

test(security): verify docker sandbox filesystem isolation  
loinguyenvan274 committed 3 weeks ago

test(perf): measure websocket latency under editor and terminal load  
loinguyenvan274 committed 3 weeks ago

fix(websocket): reduce duplicate terminal stream events  
loinguyenvan274 committed 3 weeks ago

## Commits on Apr 30, 2026

Merge pull request #14 from lethanhchung1107/test/integration-acceptance  
lethanhchung1107 authored last month Verified

test(integration): validate frontend backend ai scan workflow  
nhatthinh0812 committed last month

test(ai): add prediction contract cases for CWE response payloads  
hauthanhnguyen1203 committed last month

fix(api): normalize scan result status for frontend polling  
loinguyenvan274 committed last month

docs(test): add acceptance checklist for integrated IDE workflow  
lethanhchung1107 committed last month

## Commits on Apr 22, 2026

Merge pull request #13 from loinguyenvan274/test/backend-docker-unit  
loinguyenvan274 authored last month Verified

test(backend): cover project file tree service operations  
loinguyenvan274 committed last month

test(sandbox): cover container startup timeout and cleanup paths  
loinguyenvan274 committed last month

fix(sandbox): ensure terminal session cleanup after websocket close  
loinguyenvan274 committed last month

## Commits on Apr 15, 2026

Merge pull request #12 from loinguyenvan274/feature/ai-result-stream  
loinguyenvan274 authored last month Verified

feat(ai): queue vulnerability scan requests for async processing  
loinguyenvan274 committed last month

feat(ai): stream scan progress and CWE findings to websocket clients  
loinguyenvan274 committed last month

fix(ai): handle model timeout with retryable scan status  
loinguyenvan274 committed last month

chore(ai): document backend to AI service contract  
hauthanhnguyen1203 committed last month

## Commits on Apr 7, 2026

Merge pull request #11 from hauthanhnguyen1203/feature/model-api-service  
hauthanhnguyen1203 authored last month Verified

feat(ai): package CodeBERT predictor as REST service  
hauthanhnguyen1203 committed last month

test(ai): add sample vulnerable functions for prediction checks  
hauthanhnguyen1203 committed last month

fix(ai): map model labels to CWE metadata response  
hauthanhnguyen1203 committed last month

## Commits on Mar 30, 2026

Merge pull request #10 from loinguyenvan274/feature/docker-sandbox-terminal  
loinguyenvan274 authored on Mar 30 Verified

feat(sandbox): create isolated docker workspace per project session  
loinguyenvan274 committed on Mar 30

feat(terminal): stream container stdin stdout through websocket channel  
loinguyenvan274 committed on Mar 29

fix(sandbox): enforce cpu memory and timeout limits  
loinguyenvan274 committed on Mar 28

## Commits on Mar 23, 2026

Merge pull request #9 from hauthanhnguyen1203/feature/codebert-training  
hauthanhnguyen1203 authored on Mar 23 Verified

feat(ai): train CodeBERT multitask classifier with weighted losses  
hauthanhnguyen1203 committed on Mar 23

chore(ai): add training metrics export for F1 score tracking  
hauthanhnguyen1203 committed on Mar 22

fix(ai): rebalance rare CWE classes during batch sampling  
hauthanhnguyen1203 committed on Mar 21

## Commits on Mar 13, 2026

Merge pull request #8 from loinguyenvan274/feature/websocket-sync  
loinguyenvan274 authored on Mar 13 Verified

feat(websocket): add broker configuration for source synchronization  
loinguyenvan274 committed on Mar 13

feat(websocket): broadcast file content updates by project room  
loinguyenvan274 committed on Mar 12

fix(websocket): reject stale editor update sequence numbers  
loinguyenvan274 committed on Mar 11

## Commits on Mar 5, 2026

Merge pull request #7 from loinguyenvan274/feature/project-file-api  
loinguyenvan274 authored on Mar 5 Verified

feat(project): add create and delete project endpoints  
loinguyenvan274 committed on Mar 5

feat(file): implement recursive project file tree read and write  
loinguyenvan274 committed on Mar 4

fix(file): guard path traversal in workspace operations  
loinguyenvan274 committed on Mar 3

## Commits on Feb 24, 2026

Merge pull request #6 from hauthanhnguyen1203/feature/bigvul-preprocessing  
hauthanhnguyen1203 authored on Feb 24 Verified

feat(ai): clean and normalize BigVul source samples  
hauthanhnguyen1203 committed on Feb 24

feat(ai): filter dataset by selected CWE scope  
hauthanhnguyen1203 committed on Feb 22

chore(ai): add preprocessing notes for imbalance handling  
hauthanhnguyen1203 committed on Feb 21

## Commits on Feb 17, 2026

Merge pull request #5 from loinguyenvan274/feature/backend-foundation  
loinguyenvan274 authored on Feb 17 Verified

feat(auth): configure JWT session security for Spring Boot API  
loinguyenvan274 committed on Feb 17

feat(db): add project user and file entities with repositories  
loinguyenvan274 committed on Feb 16

chore(backend): initialize Spring Boot project structure  
loinguyenvan274 committed on Feb 15

## Commits on Feb 6, 2026

Merge pull request #4 from lethanhchung1107/design/system-architecture  
lethanhchung1107 authored on Feb 6 Verified

docs(architecture): define frontend backend ai and execution layers  
lethanhchung1107 committed on Feb 6

docs(database): add ERD for user project and file storage  
loinguyenvan274 committed on Feb 5

docs(integration): record API boundaries and module responsibilities  
lethanhchung1107 committed on Feb 4

## Commits on Jan 26, 2026

Merge pull request #3 from hauthanhnguyen1203/analysis/cwe-scope  
hauthanhnguyen1203 authored on Jan 26 Verified

docs(cwe): select vulnerability classes for first model scope  
hauthanhnguyen1203 committed on Jan 26

docs(requirements): align CWE scope with DevSecOps scanning workflow  
lethanhchung1107 committed on Jan 25

## Commits on Jan 16, 2026

Merge pull request #2 from lethanhchung1107/docs/srs-usecase-sequence  
lethanhchung1107 authored on Jan 16 Verified

docs(srs): add functional requirements for IDE scanning workflow  
lethanhchung1107 committed on Jan 16

docs(uml): add use case and sequence flows for scan execution  
lethanhchung1107 committed on Jan 15

## Commits on Jan 5, 2026

chore(project): initialize backend ai devops repository  
loinguyenvan274 committed on Jan 5

docs(project): add initial DevSecOps IDE scope and WBS baseline  
lethanhchung1107 committed on Jan 5

---

# Repository Frontend

## Commits on May 29, 2026

Merge pull request #14 from nhatthinh0812/release/final-ui-polish  
lethanhchung1107 authored 1 day ago Verified

fix(ui): polish final IDE layout spacing for demo resolution  
nhatthinh0812 committed 1 day ago

docs(ui): add screenshots and user flow notes for PBL report  
nhatthinh0812 committed 1 day ago

## Commits on May 15, 2026

Merge pull request #13 from nhatthinh0812/test/e2e-demo-flow  
nhatthinh0812 authored 2 weeks ago Verified

test(e2e): cover create project edit scan and terminal workflow  
nhatthinh0812 committed 2 weeks ago

fix(scan): show retry state when backend reports model timeout  
nhatthinh0812 committed 2 weeks ago

## Commits on Apr 30, 2026

Merge pull request #12 from nhatthinh0812/feature/ai-warning-panel  
nhatthinh0812 authored last month Verified

feat(ai): highlight vulnerable editor lines from scan response  
nhatthinh0812 committed last month

feat(ai): add CWE detail panel with severity and explanation fields  
nhatthinh0812 committed last month

fix(ai): keep warning markers stable after editor content refresh  
nhatthinh0812 committed last month

## Commits on Apr 20, 2026

Merge pull request #11 from nhatthinh0812/feature/terminal-ui  
nhatthinh0812 authored last month Verified

feat(terminal): integrate xterm.js with dedicated websocket channel  
nhatthinh0812 committed last month

fix(terminal): reconnect terminal stream after sandbox restart  
nhatthinh0812 committed last month

## Commits on Apr 10, 2026

Merge pull request #10 from nhatthinh0812/feature/monaco-editor-sync  
nhatthinh0812 authored last month Verified

feat(editor): embed Monaco Editor with language selection  
nhatthinh0812 committed last month

feat(editor): sync document changes through websocket client  
nhatthinh0812 committed last month

fix(editor): debounce updates to reduce websocket traffic  
nhatthinh0812 committed last month

## Commits on Mar 31, 2026

Merge pull request #9 from nhatthinh0812/feature/file-tree-context-menu  
nhatthinh0812 authored on Mar 31 Verified

feat(files): render recursive project file tree  
nhatthinh0812 committed on Mar 31

feat(files): add context menu for create and delete file actions  
nhatthinh0812 committed on Mar 30

fix(files): refresh active editor tab after file rename  
nhatthinh0812 committed on Mar 29

## Commits on Mar 20, 2026

Merge pull request #8 from nhatthinh0812/feature/dashboard-projects  
nhatthinh0812 authored on Mar 20 Verified

feat(dashboard): add project list and create project form  
nhatthinh0812 committed on Mar 20

feat(api): wire dashboard actions to project backend endpoints  
nhatthinh0812 committed on Mar 19

fix(dashboard): validate project name before submit  
nhatthinh0812 committed on Mar 18

## Commits on Feb 27, 2026

Merge pull request #7 from nhatthinh0812/feature/ide-layout  
nhatthinh0812 authored on Feb 27 Verified

feat(layout): build IDE shell with sidebar editor terminal and panels  
nhatthinh0812 committed on Feb 27

feat(router): add workspace routes and protected layout structure  
nhatthinh0812 committed on Feb 26

chore(style): configure TailwindCSS design tokens for IDE UI  
nhatthinh0812 committed on Feb 25

## Commits on Feb 10, 2026

Merge pull request #6 from nhatthinh0812/design/ui-ux-spec  
nhatthinh0812 authored on Feb 10 Verified

docs(ui): add dashboard editor and terminal wireframe notes  
nhatthinh0812 committed on Feb 10

docs(ui): map user flow from project creation to vulnerability scan  
lethanhchung1107 committed on Feb 9

## Commits on Jan 16, 2026

Merge pull request #5 from lethanhchung1107/docs/frontend-requirements  
lethanhchung1107 authored on Jan 16 Verified

docs(requirements): add frontend acceptance criteria for IDE workflow  
lethanhchung1107 committed on Jan 16

docs(requirements): define dashboard editor terminal and AI warning scope  
nhatthinh0812 committed on Jan 15

## Commits on Jan 5, 2026

chore(project): initialize React IDE frontend repository  
nhatthinh0812 committed on Jan 5

docs(project): add frontend scope for DevSecOps IDE interface  
lethanhchung1107 committed on Jan 5

---

# Tóm tắt phân bổ theo tháng

| Tháng | Trọng tâm công việc | Người commit chính |
|---|---|---|
| 01/2026 | Khởi tạo repo, yêu cầu, SRS, CWE scope | Chung, Hậu, Thái, Lợi |
| 02/2026 | Thiết kế kiến trúc, DB, UI/UX, nền Backend/Frontend, BigVul | Chung, Lợi, Hậu, Thái |
| 03/2026 | API dự án, WebSocket, Docker Sandbox, Dashboard, File Tree, training AI | Lợi, Hậu, Thái |
| 04/2026 | AI API, Monaco Editor, Terminal, cảnh báo CWE, unit/integration test | Hậu, Lợi, Thái, Chung |
| 05/2026 | Security/performance test, Docker Compose, deploy server, báo cáo bàn giao | Lợi, Chung, Hậu, Thái |

