from fastapi import FastAPI
from pydantic import BaseModel

from filter import normalize_code
from model import MockCweClassifier

app = FastAPI(title="QLDA AI Vulnerability Service")
classifier = MockCweClassifier()


class ScanRequest(BaseModel):
    project_id: str
    path: str
    language: str = "c"
    code: str


@app.get("/health")
def health():
    return {"status": "ok", "service": "ai"}


@app.post("/scan")
def scan(request: ScanRequest):
    normalized = normalize_code(request.code)
    findings = classifier.predict(normalized)
    return {
        "projectId": request.project_id,
        "path": request.path,
        "language": request.language,
        "status": "done",
        "findings": findings,
    }
