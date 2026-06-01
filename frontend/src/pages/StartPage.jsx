import React, { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'motion/react';
import { useNavigate } from 'react-router-dom';
import UserMenu from '../components/UserMenu';
import UserAvatar from '../components/UserAvatar';
import SettingsModal from '../components/SettingsModal';
import { projectService } from '../services/projectService';
import { 
  Shield, 
  Plus, 
  ChevronRight,
  ChevronDown,
  Search,
  Monitor,
  MoreVertical,
  Trash2,
  Edit2,
  Calendar,
  Clock
} from 'lucide-react';

const StartPage = () => {
  const navigate = useNavigate();
  const [activeMenu, setActiveMenu] = useState(null);
  const [showUserMenu, setShowUserMenu] = useState(false);
  const [showSettings, setShowSettings] = useState(false);
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isCreating, setIsCreating] = useState(false);
  const [newProjectName, setNewProjectName] = useState('');

  useEffect(() => {
    fetchProjects();
  }, []);

  const fetchProjects = async () => {
    try {
      setLoading(true);
      const response = await projectService.listProjects();
      
      if (response.status === 'ok' && Array.isArray(response.projects)) {
        const mappedProjects = response.projects.map(project => ({
          id: project.name,
          title: project.name,
          description: project.description || 'No description provided.',
          author: 'By User',
          created: project.createdAt ? new Date(project.createdAt).toLocaleDateString() : 'N/A',
          lastEdited: project.lastModified ? new Date(project.lastModified).toLocaleDateString() : 'Recently'
        }));
        setProjects(mappedProjects);
      }
    } catch (error) {
      console.error('Failed to fetch projects:', error);
    } finally {
      setLoading(false);
    }
  };

  const toggleMenu = (e, idx) => {
    e.stopPropagation();
    setActiveMenu(activeMenu === idx ? null : idx);
  };

  const handleDelete = async (projectName) => {
    try {
      await projectService.deleteProject(projectName);
      setProjects(prev => prev.filter(p => p.id !== projectName));
      setActiveMenu(null);
    } catch (error) {
      alert('Delete failed: ' + error.message);
    }
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    if (!newProjectName.trim()) return;

    try {
      await projectService.createProject(newProjectName);
      setNewProjectName('');
      setIsCreating(false);
      fetchProjects();
    } catch (error) {
      alert('Create failed: ' + error.message);
    }
  };

  return (
    <div className="min-h-screen w-full bg-[var(--app-bg)] text-[var(--text-secondary)] font-sans selection:bg-blue-500/30" onClick={() => setActiveMenu(null)}>
      <SettingsModal
        isOpen={showSettings}
        onClose={() => setShowSettings(false)}
      />
      {/* Top Navbar */}
      <nav className="flex items-center justify-between px-6 py-4 bg-[var(--panel-bg)] border-b border-[var(--border-subtle)]">
        <div className="flex items-center space-x-3">
          <div className="flex items-center justify-center w-8 h-8 rounded-lg bg-[var(--text-primary)]">
            <Shield size={18} className="text-[var(--app-bg)]" />
          </div>
          <h1 className="text-lg font-bold text-[var(--text-primary)] tracking-tight">VulnSight IDE</h1>
        </div>

        <div className="flex items-center space-x-4">
          {/* Search Button */}
          <button className="p-2 hover:bg-[var(--hover-bg)] rounded-full text-[var(--text-muted)] hover:text-[var(--text-primary)] transition-colors border border-[var(--border-subtle)]">
            <Search size={18} />
          </button>

          {/* Sort Dropdown */}
          <div className="flex items-center px-4 py-2 bg-[var(--surface-bg)] border border-[var(--border-subtle)] rounded-full cursor-pointer hover:bg-[var(--hover-bg)] transition-colors group">
            <span className="text-sm font-medium mr-2">Gần đây nhất</span>
            <ChevronDown size={14} className="text-[var(--text-muted)] group-hover:text-[var(--text-primary)] transition-colors" />
          </div>

          {/* Create New Button */}
          <button 
            onClick={() => setIsCreating(true)}
            className="px-6 py-2 bg-blue-500 text-white text-sm font-bold rounded-full hover:bg-blue-600 transition-all shadow-sm flex items-center"
          >
            <Plus size={18} className="mr-2" />
            Tạo mới
          </button>

          {/* Avatar Area */}
          <div className="relative">
            <UserAvatar 
              onClick={() => setShowUserMenu(!showUserMenu)}
              size="lg"
            />

            <UserMenu
              isOpen={showUserMenu}
              onClose={() => setShowUserMenu(false)}
              onSettingsClick={() => setShowSettings(true)}
              className="top-full mt-2"
            />
          </div>

          {showUserMenu && (
            <div 
              className="fixed inset-0 z-[99]" 
              onClick={() => setShowUserMenu(false)}
            />
          )}
        </div>
      </nav>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-6 py-10">
        <section className="mb-12">
          <div className="flex items-center justify-between mb-8">
            <div className="flex items-center space-x-2">
              <span className="w-1.5 h-6 bg-blue-500 rounded-full" />
              <h2 className="text-xl font-bold text-[var(--text-primary)]">Your Projects</h2>
            </div>
          </div>

          {isCreating && (
            <div className="mb-8 p-6 bg-[var(--panel-bg)] border border-[var(--border-subtle)] rounded-xl animate-in fade-in slide-in-from-top-4 duration-300 shadow-[var(--shadow-panel)]">
              <h3 className="text-lg font-bold text-[var(--text-primary)] mb-4">Create New Project</h3>
              <form onSubmit={handleCreate} className="flex gap-4">
                <input
                  autoFocus
                  type="text"
                  value={newProjectName}
                  onChange={(e) => setNewProjectName(e.target.value)}
                  placeholder="Enter project name..."
                  className="flex-1 bg-[var(--surface-bg)] border border-[var(--border-subtle)] rounded-md px-4 py-2 text-[var(--text-primary)] outline-none focus:border-blue-400 transition-colors"
                />
                <button 
                  type="submit"
                  disabled={!newProjectName.trim()}
                  className="px-6 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  Create
                </button>
                <button 
                  type="button"
                  onClick={() => setIsCreating(false)}
                  className="px-6 py-2 bg-[var(--surface-bg)] text-[var(--text-primary)] rounded-md hover:bg-[var(--hover-bg)] transition-colors"
                >
                  Cancel
                </button>
              </form>
            </div>
          )}

          {loading ? (
            <div className="flex justify-center items-center h-64">
              <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-[var(--text-primary)]"></div>
            </div>
          ) : projects.length === 0 ? (
            <div className="text-center py-20 bg-[var(--panel-bg)] border border-dashed border-[var(--border-subtle)] rounded-xl">
              <p className="text-[var(--text-muted)]">No projects found. Create one to get started!</p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              {projects.map((project, idx) => (
                <motion.div
                  key={project.id}
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ delay: idx * 0.1 }}
                  className="group p-5 bg-[var(--panel-bg)] border border-[var(--border-subtle)] rounded-xl hover:border-blue-400/60 transition-all duration-300 relative flex flex-col justify-between shadow-[var(--shadow-panel)]"
                >
                  <div>
                    <div className="flex justify-between items-start mb-2">
                      <div className="space-y-1">
                        <h3 className="text-base font-bold text-[var(--text-primary)] group-hover:text-blue-500 transition-colors">
                          {project.title}
                        </h3>
                        <div className="flex items-center space-x-1.5">
                          <span className="text-xs text-[var(--text-muted)]">{project.author}</span>
                          <div className="w-1 h-1 rounded-full bg-[var(--border-subtle)]" />
                          <Monitor size={12} className="text-[var(--text-muted)]" />
                        </div>
                      </div>
                      
                      {/* Three Dots Menu */}
                      <div className="relative">
                        <button 
                          onClick={(e) => toggleMenu(e, idx)}
                          className="p-1 hover:bg-[var(--hover-bg)] rounded-md text-[var(--text-muted)] hover:text-[var(--text-primary)] transition-colors"
                        >
                          <MoreVertical size={16} />
                        </button>
                        
                        <AnimatePresence>
                          {activeMenu === idx && (
                            <motion.div
                              initial={{ opacity: 0, scale: 0.95, y: -10 }}
                              animate={{ opacity: 1, scale: 1, y: 0 }}
                              exit={{ opacity: 0, scale: 0.95, y: -10 }}
                              className="absolute right-0 top-8 w-40 bg-[var(--surface-raised)] border border-[var(--border-subtle)] rounded-md shadow-xl z-50 py-1"
                            >
                              <button className="w-full px-4 py-2 text-left text-xs hover:bg-[var(--hover-bg)] flex items-center space-x-2">
                                <Edit2 size={12} />
                                <span>Rename</span>
                              </button>
                              <button 
                                onClick={() => handleDelete(project.id)}
                                className="w-full px-4 py-2 text-left text-xs hover:bg-[var(--hover-bg)] text-red-400 flex items-center space-x-2"
                              >
                                <Trash2 size={12} />
                                <span>Delete project</span>
                              </button>
                            </motion.div>
                          )}
                        </AnimatePresence>
                      </div>
                    </div>

                    {/* Date Information */}
                    <div className="flex flex-col space-y-1 mb-4 text-[10px] text-[var(--text-muted)]">
                      <div className="flex items-center space-x-1.5">
                        <Calendar size={10} />
                        <span>Started: {project.created}</span>
                      </div>
                      <div className="flex items-center space-x-1.5">
                        <Clock size={10} />
                        <span>Last edited: {project.lastEdited}</span>
                      </div>
                    </div>

                    <p className="text-xs text-[var(--text-muted)] leading-relaxed mb-6 line-clamp-2">
                      {project.description}
                    </p>
                  </div>

                  <div className="flex">
                    <button 
                      onClick={() => navigate(`/workspaces/${project.id}`)}
                      className="px-4 py-1.5 bg-blue-500 hover:bg-blue-600 text-white text-xs font-semibold rounded-md transition-all flex items-center group/btn shadow-lg shadow-blue-500/10"
                    >
                      Start
                      <ChevronRight size={14} className="ml-1 group-hover/btn:translate-x-0.5 transition-transform" />
                    </button>
                  </div>
                </motion.div>
              ))}
            </div>
          )}
        </section>
      </main>
    </div>
  );
};

export default StartPage;
