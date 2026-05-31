import React, { useState } from "react";

export default function Terminal({ actionIcon }) {
  const [lines, setLines] = useState(["$ sandbox ready"]);

  return (
    <section className="terminal">
      <button onClick={() => setLines((current) => [...current, "$ run src/auth.c", "Program executed in isolated sandbox"])}>
        {actionIcon} Run
      </button>
      <pre>{lines.join("\n")}</pre>
    </section>
  );
}
