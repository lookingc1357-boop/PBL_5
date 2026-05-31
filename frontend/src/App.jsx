import React, { useState } from "react";
import { createRoot } from "react-dom/client";
import { Play, ShieldCheck } from "lucide-react";
import Dashboard from "./Dashboard.jsx";
import Editor from "./components/Editor.jsx";
import FileTree from "./components/FileTree.jsx";
import Terminal from "./components/Terminal.jsx";
import CWEPanel from "./components/CWEPanel.jsx";
import "./styles.css";

const sampleCode = `#include <string.h>

int login(char *input) {
  char buffer[16];
  strcpy(buffer, input);
  return 0;
}`;

function App() {
  const [code, setCode] = useState(sampleCode);
  const [findings, setFindings] = useState([]);

  const runScan = async () => {
    const response = await fetch("http://localhost:8000/scan", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        project_id: "pbl-devsecops",
        path: "src/auth.c",
        language: "c",
        code,
      }),
    }).catch(() => null);

    if (!response) {
      setFindings([{ cwe: "CWE-120", severity: "HIGH", line: 5, message: "Demo finding from offline UI mode." }]);
      return;
    }
    const data = await response.json();
    setFindings(data.findings || []);
  };

  return (
    <div className="app-shell">
      <header className="topbar">
        <strong>QLDA DevSecOps IDE</strong>
        <button onClick={runScan}><ShieldCheck size={16} /> Scan CWE</button>
      </header>
      <aside className="sidebar">
        <Dashboard />
        <FileTree />
      </aside>
      <main className="editor">
        <Editor code={code} onChange={setCode} findings={findings} />
      </main>
      <aside className="panel">
        <CWEPanel findings={findings} />
      </aside>
      <Terminal actionIcon={<Play size={14} />} />
    </div>
  );
}

createRoot(document.getElementById("root")).render(<App />);
