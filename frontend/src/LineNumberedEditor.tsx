import { useRef, useCallback, useMemo } from 'react';
import Prism from 'prismjs';
import 'prismjs/components/prism-json'; // Load the JSON language definition
import 'prismjs/themes/prism.css'; // Import a theme (you can pick others like prism-tomorrow.css)

interface LineNumberedEditorProps {
  value: string;
  onChange?: (value: string) => void;
  readOnly?: boolean;
  placeholder?: string;
}

// Shared text styles to ensure the textarea and the highlight layer align perfectly
const sharedTextStyles: React.CSSProperties = {
  margin: 0,
  padding: '16px', // Increased slightly for better breathing room
  border: '0',
  boxSizing: 'border-box',
  fontFamily: 'Consolas, Monaco, "Andale Mono", "Ubuntu Mono", monospace', // Hardcode the font stack!
  fontSize: '14px',
  lineHeight: '1.5',
  letterSpacing: 'normal',
  wordSpacing: 'normal',
  wordBreak: 'normal',
  wordWrap: 'normal',
  whiteSpace: 'pre',
  tabSize: 2,
};

export default function LineNumberedEditor({
  value,
  onChange,
  readOnly = false,
  placeholder,
}: LineNumberedEditorProps) {
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const gutterRef = useRef<HTMLDivElement>(null);
  const highlightRef = useRef<HTMLPreElement>(null);

  const lineCount = value ? value.split('\n').length : 1;

  const handleScroll = useCallback(() => {
    if (textareaRef.current) {
      const { scrollTop, scrollLeft } = textareaRef.current;
      if (gutterRef.current) gutterRef.current.scrollTop = scrollTop;
      if (highlightRef.current) {
        highlightRef.current.scrollTop = scrollTop;
        highlightRef.current.scrollLeft = scrollLeft;
      }
    }
  }, []);

  const highlightedCode = useMemo(() => {
    const safeValue = value || ''; 
    return Prism.highlight(safeValue, Prism.languages.json, 'json');
  }, [value]);

  return (
    <div style={{
      display: 'flex',
      border: '1px solid var(--border)',
      borderRadius: '6px',
      overflow: 'hidden',
      height: '500px',
      background: 'var(--bg)',
    }}>
      {/* Line number gutter */}
      <div
        ref={gutterRef}
        style={{
          padding: '16px 0', // Must match the top/bottom padding of sharedTextStyles
          background: 'var(--code-bg)',
          color: 'var(--text)',
          textAlign: 'right',
          userSelect: 'none',
          overflow: 'hidden',
          minWidth: '48px',
          borderRight: '1px solid var(--border)',
          fontFamily: sharedTextStyles.fontFamily, // Keep fonts identical
          fontSize: sharedTextStyles.fontSize,
          lineHeight: sharedTextStyles.lineHeight,
        }}
      >
        {Array.from({ length: lineCount }, (_, i) => (
          <div key={i} style={{ padding: '0 10px' }}>
            {i + 1}
          </div>
        ))}
      </div>

      {/* Editor Content Area */}
      <div style={{ flex: 1, position: 'relative', overflow: 'hidden' }}>
        
        {/* Highlight Layer (Behind) */}
        <pre
          ref={highlightRef}
          aria-hidden="true"
          // 2. ADD CLASSNAME: Prism themes target class="language-*"
          className="language-json" 
          style={{
            ...sharedTextStyles,
            position: 'absolute',
            top: 0,
            left: 0,
            width: '100%',
            height: '100%',
            overflow: 'hidden', 
            color: 'var(--text-h)',
            pointerEvents: 'none', 
            background: 'transparent', // Override Prism's default background
            textShadow: 'none',        // Override Prism's text-shadow
          }}
          dangerouslySetInnerHTML={{ __html: highlightedCode || (placeholder && !value ? placeholder : '') }}
        />

        {/* Textarea Input Layer (In Front) */}
        <textarea
          ref={textareaRef}
          value={value}
          onChange={(e) => onChange?.(e.target.value)}
          onScroll={handleScroll}
          readOnly={readOnly}
          spellCheck={false}
          style={{
            ...sharedTextStyles,
            position: 'absolute',
            top: 0,
            left: 0,
            width: '100%',
            height: '100%',
            resize: 'none',
            background: 'transparent', 
            color: 'transparent',      
            caretColor: '#fff', // Or 'var(--text-h)' - explicitly set caret color
            overflow: 'auto',
          }}
        />
      </div>
    </div>
  );
}