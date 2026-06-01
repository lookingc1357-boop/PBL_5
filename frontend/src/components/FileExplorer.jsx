import React, { useState } from "react";
import { ChevronRight, ChevronDown, Upload, X } from "lucide-react";
import {
  VscFile,
  VscFolder,
  VscFolderOpened,
  VscNewFile,
  VscNewFolder,
  VscRefresh,
} from "react-icons/vsc";
import TabIcon from "./TabIcon";
import { explorerService } from "../services/explorerService";

const FileExplorer = ({
  tree,
  onFileClick,
  onClose,
  onCreateFile,
  onCreateFolder,
  onDelete,
  onRename,
}) => {
  const [expanded, setExpanded] = useState({ "/": true });
  const [creating, setCreating] = useState(null); // { type: 'file' | 'folder', parentPath: string }
  const [renaming, setRenaming] = useState(null); // item object
  const [tempName, setTempName] = useState("");

  const toggleExpand = (path) => {
    setExpanded((prev) => ({ ...prev, [path]: !prev[path] }));
  };

  const startCreating = (parentPath, type) => {
    setCreating({ type, parentPath });
    setTempName("");
    if (parentPath && !expanded[parentPath]) {
      setExpanded((prev) => ({ ...prev, [parentPath]: true }));
    }
  };

  const startRenaming = (item) => {
    setRenaming(item);
    setTempName(item.name);
  };

  const handleInputSubmit = () => {
    if (!tempName.trim()) {
      setCreating(null);
      setRenaming(null);
      return;
    }

    if (creating) {
      if (creating.type === "folder") {
        onCreateFolder(creating.parentPath, tempName.trim());
      } else {
        onCreateFile(creating.parentPath, tempName.trim());
      }
    } else if (renaming) {
      onRename(renaming, tempName.trim());
    }

    setCreating(null);
    setRenaming(null);
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      handleInputSubmit();
    } else if (e.key === "Escape") {
      setCreating(null);
      setRenaming(null);
    }
  };

  const renderInput = (type, isRename = false) => (
    <div
      className={`flex items-center px-4 py-1 bg-[var(--hover-soft)] ${isRename ? "" : "animate-in fade-in slide-in-from-left-1 duration-200"}`}
    >
      <div className="w-5 shrink-0" />
      <div className="shrink-0 mr-2">
        {type === "folder" ? (
          <VscFolder
            size={16}
            className="text-blue-400/80"
            style={{ width: "16px", height: "16px" }}
          />
        ) : (
          <TabIcon fileName={tempName} size={14} />
        )}
      </div>
      <input
        autoFocus
        className="bg-[var(--input-bg)] text-[var(--text-primary)] outline-none border border-blue-500 px-1 w-full text-xs h-5 shadow-inner"
        value={tempName}
        onChange={(e) => setTempName(e.target.value)}
        onKeyDown={handleKeyDown}
        onBlur={handleInputSubmit}
      />
    </div>
  );

  const renderItem = (item) => {
    if (!item) return null;

    if (renaming && renaming.path === item.path) {
      return (
        <div key={`rename-${item.path}`}>
          {renderInput(item.directory ? "folder" : "file", true)}
        </div>
      );
    }

    const isExpanded = expanded[item.path];
    const itemPath = item.path;

    if (item.directory) {
      return (
        <div
          key={itemPath}
          className="animate-in fade-in slide-in-from-left-1 duration-200"
        >
          <div
            className="flex items-center px-4 py-1 hover:bg-[var(--hover-soft)] cursor-pointer group text-sm text-[var(--text-secondary)]"
            onClick={() => toggleExpand(itemPath)}
          >
            <div className="mr-1 shrink-0">
              {isExpanded ? (
                <ChevronDown size={14} className="text-[var(--text-muted)]" />
              ) : (
                <ChevronRight size={14} className="text-[var(--text-muted)]" />
              )}
            </div>
            {isExpanded ? (
              <VscFolderOpened
                size={18}
                className="mr-2 text-blue-400/80 shrink-0"
              />
            ) : (
              <VscFolder size={18} className="mr-2 text-blue-400/80 shrink-0" />
            )}
            <span className="truncate flex-1">{item.name}</span>

            <div className="flex items-center space-x-1 opacity-0 group-hover:opacity-100 transition-opacity ml-2">
              <button
                onClick={(e) => {
                  e.stopPropagation();
                  startCreating(item.path, "file");
                }}
                className="p-1 hover:bg-[var(--hover-bg)] rounded text-[var(--text-muted)] hover:text-[var(--text-primary)]"
                title="New File"
              >
                <VscNewFile size={14} />
              </button>
              <button
                onClick={(e) => {
                  e.stopPropagation();
                  startCreating(item.path, "folder");
                }}
                className="p-1 hover:bg-[var(--hover-bg)] rounded text-[var(--text-muted)] hover:text-[var(--text-primary)]"
                title="New Folder"
              >
                <VscNewFolder size={14} />
              </button>
              <button
                onClick={(e) => {
                  e.stopPropagation();
                  startRenaming(item);
                }}
                className="p-1 hover:bg-[var(--hover-bg)] rounded text-[var(--text-muted)] hover:text-[var(--text-primary)]"
                title="Rename"
              >
                <VscRefresh size={12} className="rotate-90" />
              </button>
              <button
                onClick={(e) => {
                  e.stopPropagation();
                  onDelete?.(item);
                }}
                className="p-1 hover:bg-[var(--hover-bg)] rounded text-[var(--text-muted)] hover:text-red-400"
                title="Delete"
              >
                <X size={14} />
              </button>
            </div>
          </div>
          {isExpanded && (
            <div className="ml-4 border-l border-[var(--border-subtle)] pl-2">
              {creating &&
                creating.parentPath === itemPath &&
                renderInput(creating.type)}
              {item.children && item.children.map((child) => renderItem(child))}
            </div>
          )}
        </div>
      );
    }

    return (
      <div
        key={itemPath}
        className="flex items-center px-4 py-1 hover:bg-[var(--hover-soft)] cursor-pointer text-sm text-[var(--text-secondary)] group"
        onClick={() => onFileClick?.(item)}
      >
        <div className="w-5 shrink-0" />
        <div className="shrink-0">
          <TabIcon fileName={item.name} size={14} />
        </div>
        <span className="truncate ml-2 flex-1">{item.name}</span>

        <div className="flex items-center space-x-1 opacity-0 group-hover:opacity-100 transition-opacity ml-2">
          <button
            onClick={(e) => {
              e.stopPropagation();
              startRenaming(item);
            }}
            className="p-1 hover:bg-[var(--hover-bg)] rounded text-[var(--text-muted)] hover:text-[var(--text-primary)]"
            title="Rename"
          >
            <VscRefresh size={12} className="rotate-90" />
          </button>
          <button
            onClick={(e) => {
              e.stopPropagation();
              onDelete?.(item);
            }}
            className="p-1 hover:bg-[var(--hover-bg)] rounded text-[var(--text-muted)] hover:text-red-400"
            title="Delete"
          >
            <X size={14} />
          </button>
        </div>
      </div>
    );
  };

  const handleRefresh = () => {
    explorerService.listProjectTree();
  };

  const handleRootCreateFile = () => startCreating(tree?.path || "", "file");
  const handleRootCreateFolder = () =>
    startCreating(tree?.path || "", "folder");

  return (
    <div className="h-full bg-[var(--panel-bg)] flex flex-col overflow-hidden select-none">
      <div className="flex items-center justify-between px-4 h-10 border-b border-[var(--border-strong)]">
        <span className="text-[11px] font-bold text-[var(--text-secondary)] tracking-wider uppercase">
          Explorer
        </span>
        <div className="flex items-center space-x-1">
          <button
            onClick={onClose}
            className="p-1 hover:bg-[var(--hover-bg)] rounded text-[var(--text-muted)] hover:text-[var(--text-primary)] transition-colors"
          >
            <X size={16} />
          </button>
        </div>
      </div>

      <div className="flex items-center px-4 py-1.5 border-b border-[var(--border-strong)] space-x-2 overflow-x-auto no-scrollbar">
        <button
          className="p-1.5 hover:bg-[var(--hover-bg)] rounded-md text-[var(--text-muted)] hover:text-[var(--text-primary)] shrink-0 transition-colors"
          title="Upload"
        >
          <Upload size={18} className="shrink-0" />
        </button>
        <button
          onClick={handleRefresh}
          className="p-1.5 hover:bg-[var(--hover-bg)] rounded-md text-[var(--text-muted)] hover:text-[var(--text-primary)] shrink-0 transition-colors"
          title="Refresh"
        >
          <VscRefresh size={18} className="shrink-0" />
        </button>
        <button
          onClick={handleRootCreateFile}
          className="p-1.5 hover:bg-[var(--hover-bg)] rounded-md text-[var(--text-muted)] hover:text-[var(--text-primary)] shrink-0 transition-colors"
          title="New File"
        >
          <VscNewFile size={18} className="shrink-0" />
        </button>
        <button
          onClick={handleRootCreateFolder}
          className="p-1.5 hover:bg-[var(--hover-bg)] rounded-md text-[var(--text-muted)] hover:text-[var(--text-primary)] shrink-0 transition-colors"
          title="New Folder"
        >
          <VscNewFolder size={18} className="shrink-0" />
        </button>
      </div>

      <div className="flex-1 overflow-y-auto overflow-x-hidden no-scrollbar pt-2">
        {creating &&
          creating.parentPath === (tree?.path || "") &&
          renderInput(creating.type)}
        {tree ? (
          tree.children ? (
            tree.children.map((child) => renderItem(child))
          ) : (
            renderItem(tree)
          )
        ) : (
          <div className="px-4 py-2 text-xs text-[var(--text-muted)]">
            Loading workspace...
          </div>
        )}
      </div>

      <div className="px-4 py-3 bg-[var(--panel-bg)] border-t border-[var(--border-subtle)] text-[10px] text-[var(--text-muted)]">
        <div className="flex justify-between mb-1.5">
          <span className="flex items-center">
            <div className="w-1.5 h-1.5 rounded-full bg-blue-500 mr-1.5" />
            Workspace
          </span>
          <span className="text-[var(--text-secondary)]">Connected to backend</span>
        </div>
      </div>
    </div>
  );
};

export default FileExplorer;
