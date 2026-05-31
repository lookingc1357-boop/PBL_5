import React from "react";

const projects = [
  { name: "DevSecOps IDE", status: "Active" },
  { name: "CWE Scanner Demo", status: "Review" },
];

export default function Dashboard() {
  return (
    <section>
      <h3>Projects</h3>
      {projects.map((project) => (
        <div className="project-card" key={project.name}>
          <strong>{project.name}</strong>
          <div>{project.status}</div>
        </div>
      ))}
    </section>
  );
}
