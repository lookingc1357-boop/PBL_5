import React from "react";
import { FileCode, Folder } from "lucide-react";

const nodes = [
  { type: "folder", name: "src" },
  { type: "file", name: "src/auth.c" },
  { type: "file", name: "src/main.c" },
  { type: "file", name: "README.md" },
];

export default function FileTree() {
  return (
    <section>
      <h3>Files</h3>
      {nodes.map((node) => (
        <div className="tree-node" key={node.name}>
          {node.type === "folder" ? <Folder size={16} /> : <FileCode size={16} />}
          <span>{node.name}</span>
        </div>
      ))}
    </section>
  );
}
