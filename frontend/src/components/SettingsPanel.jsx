import React from "react";
import { MonitorCog, Moon, Sun, X } from "lucide-react";
import { useTheme } from "../context/ThemeContext";

const SettingsPanel = ({ onClose }) => {
  const { theme, setTheme, isDark } = useTheme();

  return (
    <div className="flex flex-col h-full w-full bg-[var(--panel-bg)] text-[var(--text-primary)] font-sans overflow-hidden select-none">
      <div className="flex items-center justify-between px-5 h-12 border-b border-[var(--border-strong)]">
        <span className="text-sm font-semibold text-[var(--text-primary)]">
          Settings
        </span>
        <button
          onClick={onClose}
          className="p-1 hover:bg-[var(--hover-bg)] rounded text-[var(--text-muted)] hover:text-[var(--text-primary)] transition-colors"
          title="Close Settings"
        >
          <X size={16} />
        </button>
      </div>

      <div className="flex min-h-0 flex-1">
        <div className="w-44 shrink-0 border-r border-[var(--border-subtle)] bg-[var(--surface-bg)] p-3">
          <button className="flex w-full items-center gap-2 rounded-md bg-[var(--hover-bg)] px-3 py-2 text-left text-sm font-medium text-[var(--text-primary)]">
            <MonitorCog size={16} />
            Appearance
          </button>
        </div>

        <div className="min-w-0 flex-1 overflow-y-auto p-5">
          <div className="mb-4">
            <div className="text-sm font-semibold text-[var(--text-primary)]">
              Appearance
            </div>
            <div className="mt-1 text-xs text-[var(--text-muted)]">
              Customize how the IDE looks.
            </div>
          </div>

          <div className="flex items-center justify-between gap-3 rounded-md border border-[var(--border-subtle)] bg-[var(--surface-bg)] px-3 py-3">
            <div className="min-w-0">
              <div className="text-sm text-[var(--text-primary)]">Theme</div>
              <div className="text-xs text-[var(--text-muted)]">
                {isDark ? "Dark mode" : "Light mode"}
              </div>
            </div>

            <div className="flex rounded-md border border-[var(--border-subtle)] overflow-hidden shrink-0">
              <button
                onClick={() => setTheme("light")}
                className={`flex items-center justify-center w-10 h-8 transition-colors ${
                  theme === "light"
                    ? "bg-blue-500 text-white"
                    : "bg-transparent text-[var(--text-muted)] hover:bg-[var(--hover-bg)] hover:text-[var(--text-primary)]"
                }`}
                title="Light mode"
              >
                <Sun size={16} />
              </button>
              <button
                onClick={() => setTheme("dark")}
                className={`flex items-center justify-center w-10 h-8 transition-colors ${
                  theme === "dark"
                    ? "bg-blue-500 text-white"
                    : "bg-transparent text-[var(--text-muted)] hover:bg-[var(--hover-bg)] hover:text-[var(--text-primary)]"
                }`}
                title="Dark mode"
              >
                <Moon size={16} />
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SettingsPanel;
