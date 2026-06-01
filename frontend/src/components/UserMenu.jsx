import React from 'react';
import { User, Settings, LogOut } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';

import UserAvatar from './UserAvatar';

const UserMenu = ({ isOpen, onClose, onSettingsClick, className = "" }) => {
  const navigate = useNavigate();

  if (!isOpen) return null;

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
    onClose();
  };

  const handleSettingsClick = () => {
    onSettingsClick?.();
    onClose();
  };

  return (
    <div className={`absolute right-0 w-72 bg-[var(--surface-raised)] border border-[var(--border-subtle)] rounded-2xl shadow-2xl z-[1000] overflow-hidden font-sans ${className}`}>
      <div className="p-4">
        {/* User Info Header */}
        <div className="flex items-center space-x-3 mb-4 cursor-default">
          <UserAvatar size="lg" />
          <div className="flex-1 min-w-0">
            <div className="text-sm font-semibold text-[var(--text-primary)] truncate leading-tight">loinguyenf</div>
            <div className="text-[11px] text-[var(--text-muted)] mt-0.5">Free Plan</div>
          </div>
        </div>

        <div className="h-px bg-[var(--border-subtle)] my-3" />

        {/* Menu Items */}
        <div className="space-y-1">
          <button className="w-full flex items-center justify-between px-3 py-2.5 rounded-xl hover:bg-[var(--hover-bg)] text-[var(--text-secondary)] hover:text-[var(--text-primary)] transition-all group">
            <div className="flex items-center space-x-3">
              <div className="w-8 h-8 rounded-lg bg-[var(--surface-bg)] flex items-center justify-center group-hover:bg-[var(--hover-soft)] transition-colors">
                <User size={18} strokeWidth={1.5} />
              </div>
              <span className="text-sm font-medium">Profile</span>
            </div>
          </button>

          <button
            onClick={handleSettingsClick}
            className="w-full flex items-center justify-between px-3 py-2.5 rounded-xl hover:bg-[var(--hover-bg)] text-[var(--text-secondary)] hover:text-[var(--text-primary)] transition-all group"
          >
            <div className="flex items-center space-x-3">
              <div className="w-8 h-8 rounded-lg bg-[var(--surface-bg)] flex items-center justify-center group-hover:bg-[var(--hover-soft)] transition-colors">
                <Settings size={18} strokeWidth={1.5} />
              </div>
              <span className="text-sm font-medium">Settings</span>
            </div>
          </button>
        </div>
      </div>

      {/* Logout Action Bar */}
      <div className="p-2 bg-[var(--surface-bg)] mt-2">
        <button 
          onClick={handleLogout}
          className="w-full flex items-center space-x-3 px-4 py-3 rounded-xl hover:bg-red-500/10 text-[var(--text-muted)] hover:text-red-400 transition-all group"
        >
          <LogOut size={18} strokeWidth={1.5} />
          <span className="text-sm font-semibold">Log out</span>
        </button>
      </div>
    </div>
  );
};

export default UserMenu;
