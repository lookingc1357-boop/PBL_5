import React from "react";

export default function ContextMenu({ x = 0, y = 0, onCreate, onDelete }) {
  return (
    <div style={{ position: "fixed", left: x, top: y, background: "#1b222c", border: "1px solid #2a303a", padding: 8 }}>
      <button onClick={onCreate}>Create file</button>
      <button onClick={onDelete}>Delete file</button>
    </div>
  );
}
