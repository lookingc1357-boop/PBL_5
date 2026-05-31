import React from "react";

export default function Editor({ code, onChange, findings }) {
  const warningLines = new Set(findings.map((finding) => finding.line));
  const annotated = code
    .split("\n")
    .map((line, index) => `${String(index + 1).padStart(2, " ")} ${warningLines.has(index + 1) ? "!" : " "} ${line}`)
    .join("\n");

  return (
    <textarea
      aria-label="Source editor"
      value={annotated}
      onChange={(event) => {
        const cleaned = event.target.value
          .split("\n")
          .map((line) => line.replace(/^\s*\d+\s[! ]\s/, ""))
          .join("\n");
        onChange(cleaned);
      }}
    />
  );
}
