# Milestone 02 - Core Development

Planned schedule window: project month 2.

## Work packages

| Code | Scope | Planned duration | Owner |
|---|---|---:|---|
| H | Configure CodeBERT and multitask output structure | 9 days | Hau |
| I | Train model with inverse frequency and uncertainty weighting | 13 days | Hau |
| L | Build project management API and file tree read/write logic | 8 days | Loi |
| M | Configure broker and WebSocket source synchronization | 8 days | Loi, Thai |
| N | Integrate Docker sandbox and terminal I/O | 9 days | Loi, Thai |
| Q | Build dashboard and create-project form | 5 days | Thai, Loi |
| R | Render recursive file tree and context menu actions | 6 days | Thai, Loi |

## Deliverables

- AI training pipeline skeleton and weighted class strategy.
- Backend project/file APIs with WebSocket synchronization flow.
- Docker sandbox and terminal execution policy.
- Dashboard and file tree workflow connected to backend contracts.

## Integration notes

- Backend and frontend agree on project id, file path and delta update payload fields.
- AI training output must preserve CWE labels required by the UI warning panel.
- Sandbox limits are introduced before terminal UI is treated as complete.
