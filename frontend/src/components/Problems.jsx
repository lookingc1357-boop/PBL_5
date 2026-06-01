import React from 'react';
import { AlertCircle, XCircle, Info, MoreHorizontal, X } from 'lucide-react';

const Problems = ({ onClose, lineVuls = {}, onProblemClick }) => {
  // Convert vulnerabilities map to a flat list
  const problemsRes = Object.entries(lineVuls).flatMap(([path, vuls]) => {
    if (!Array.isArray(vuls)) return [];
    return vuls.map((vul, idx) => ({
      id: `${path}-${idx}`,
      type: 'error', // or infer from v.cweId if available
      file: path.split('/').pop(),
      filePath: path,
      line: vul.line,
      col: 1, // backend might not provide column
      message: `${vul.cweId || 'Vulnerability'}: ${vul.description || 'Potential security issue'}`,
      raw: vul
    }));
  });

  return (
    <div className="flex flex-col h-full w-full bg-[var(--panel-bg)] text-[var(--text-secondary)] font-sans overflow-hidden">
      {/* Header */}
      <div className="flex items-center justify-between px-4 h-10 bg-[var(--panel-bg)] border-b border-[var(--border-strong)] select-none">
        <div className="flex items-center space-x-4 h-full">
          <button className="flex items-center h-full text-[11px] font-bold tracking-tight text-[var(--text-primary)] relative">
            <span>PROBLEMS</span>
            <span className="ml-2 flex items-center justify-center w-4 h-4 bg-[var(--hover-bg)] rounded text-[9px]">{problemsRes.length}</span>
            <div className="absolute bottom-0 left-0 right-0 h-[1px] bg-blue-500" />
          </button>
        </div>

        <div className="flex items-center space-x-1">
          <button 
            onClick={onClose}
            className="p-1 hover:bg-[var(--hover-bg)] rounded text-[var(--text-muted)] hover:text-[var(--text-primary)] transition-colors"
          >
            <X size={16} />
          </button>
        </div>
      </div>

      {/* Problems List */}
      <div className="flex-1 overflow-y-auto overflow-x-hidden no-scrollbar">
        {problemsRes.length > 0 ? (
          <div className="flex flex-col">
            {problemsRes.map((p) => (
              <div 
                key={p.id} 
                onClick={() => onProblemClick?.(p.filePath, p.line)}
                className="flex items-start px-4 py-2 hover:bg-[var(--hover-soft)] cursor-pointer group border-b border-[var(--border-subtle)]"
              >
                <div className="mr-3 mt-0.5">
                  {p.type === 'error' && <XCircle size={14} className="text-red-500" />}
                  {p.type === 'warning' && <AlertCircle size={14} className="text-yellow-500" />}
                  {p.type === 'info' && <Info size={14} className="text-blue-400" />}
                </div>
                <div className="flex-1 min-w-0">
                  <div className="flex items-center justify-between space-x-4">
                    <p className="text-xs text-[var(--text-secondary)] truncate">{p.message}</p>
                    <span className="text-[10px] text-[var(--text-muted)] whitespace-nowrap">{p.file} [{p.line}, {p.col}]</span>
                  </div>
                </div>
              </div>
            ))}
          </div>
        ) : (
          <div className="h-full flex flex-col items-center justify-center text-[var(--text-muted)] space-y-2 opacity-60">
            <AlertCircle size={32} />
            <p className="text-xs">No problems have been detected in the workspace.</p>
          </div>
        )}
      </div>

      {/* Footer */}
      <div className="flex items-center justify-between px-3 h-6 bg-[var(--surface-raised)] border-t border-[var(--border-strong)] text-[10px] text-[var(--text-muted)] select-none">
        <div className="flex items-center space-x-3">
          <span>{problemsRes.filter(p => p.type === 'error').length} Vulnerabilities</span>
        </div>
      </div>
    </div>
  );
};

export default Problems;
