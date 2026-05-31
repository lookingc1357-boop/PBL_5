import React from "react";

export default function CWEPanel({ findings }) {
  return (
    <section>
      <h3>CWE Findings</h3>
      {findings.length === 0 && <p>No findings yet.</p>}
      {findings.map((finding, index) => (
        <article className="finding" key={`${finding.cwe}-${index}`}>
          <strong>{finding.cwe}</strong>
          <div>{finding.severity} - line {finding.line}</div>
          <p>{finding.message}</p>
        </article>
      ))}
    </section>
  );
}
