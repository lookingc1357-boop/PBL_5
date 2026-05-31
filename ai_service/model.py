class MockCweClassifier:
    def predict(self, code: str):
        findings = []
        lowered = code.lower()
        if "strcpy" in lowered or "gets(" in lowered:
            findings.append(
                {
                    "cwe": "CWE-120",
                    "severity": "HIGH",
                    "line": self._line_of(code, ["strcpy", "gets("]),
                    "message": "Possible classic buffer overflow pattern.",
                    "confidence": 0.91,
                }
            )
        if "select * from" in lowered and "+" in lowered:
            findings.append(
                {
                    "cwe": "CWE-89",
                    "severity": "CRITICAL",
                    "line": self._line_of(code, ["select * from"]),
                    "message": "Possible SQL injection string concatenation.",
                    "confidence": 0.88,
                }
            )
        if "system(" in lowered:
            findings.append(
                {
                    "cwe": "CWE-78",
                    "severity": "HIGH",
                    "line": self._line_of(code, ["system("]),
                    "message": "OS command execution should be validated and sandboxed.",
                    "confidence": 0.84,
                }
            )
        return findings

    def _line_of(self, code: str, needles):
        for index, line in enumerate(code.splitlines(), start=1):
            if any(needle in line.lower() for needle in needles):
                return index
        return 1
