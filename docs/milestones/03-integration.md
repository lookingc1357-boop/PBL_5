# Milestone 03 - Integration

Planned schedule window: project month 3.

## Work packages

| Code | Scope | Planned duration | Owner |
|---|---|---:|---|
| J | Evaluate F1-score and package AI model as API service | 6 days | Hau, Loi |
| O | Queue AI requests and stream scan results to frontend | 6 days | Loi, Hau, Thai |
| S | Embed editor, autocomplete and WebSocket synchronization | 8 days | Thai, Loi |
| T | Integrate xterm.js with dedicated terminal WebSocket | 6 days | Thai, Loi |
| U | Render AI warnings, line highlight and CWE detail panel | 7 days | Thai, Hau, Loi |
| V | Unit test backend and Docker service logic | 5 days | Loi |

## Deliverables

- AI scan API contract returning project id, file path, status and CWE findings.
- Backend stream model for long-running scans.
- Editor and terminal flows connected through WebSocket channels.
- CWE panel and line highlight behavior aligned with AI response payload.
- Unit test coverage for sandbox resource policy.

## Acceptance focus

- Scan workflow must remain responsive when AI work is slow.
- Editor and terminal sockets are separated to reduce noisy cross-channel events.
- CWE line numbers are stable enough for report screenshots and demo review.
