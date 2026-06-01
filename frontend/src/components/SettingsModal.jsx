import React from "react";
import { AnimatePresence, motion } from "motion/react";
import SettingsPanel from "./SettingsPanel";

const SettingsModal = ({ isOpen, onClose }) => (
  <AnimatePresence>
    {isOpen && (
      <div className="fixed inset-0 z-[1200] flex items-center justify-center p-4">
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          className="absolute inset-0 bg-black/60 backdrop-blur-sm"
          onClick={onClose}
        />
        <motion.div
          initial={{ opacity: 0, scale: 0.96, y: 12 }}
          animate={{ opacity: 1, scale: 1, y: 0 }}
          exit={{ opacity: 0, scale: 0.96, y: 12 }}
          className="relative h-[420px] w-full max-w-2xl overflow-hidden rounded-xl border border-[var(--border-subtle)] bg-[var(--panel-bg)] shadow-2xl"
          onClick={(event) => event.stopPropagation()}
        >
          <SettingsPanel onClose={onClose} />
        </motion.div>
      </div>
    )}
  </AnimatePresence>
);

export default SettingsModal;
