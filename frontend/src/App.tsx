import { useState } from 'react';
import './App.css';
import LineNumberedEditor from './LineNumberedEditor';

function App() {
  const [inputJson, setInputJson] = useState('');
  const [outputJson, setOutputJson] = useState('');

  const handleMinify = async () => {
    try {
      const response = await fetch('/api/json/minify', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // Keep this consistent with what Spring Boot expects
        },
        body: inputJson, // Your controller uses @RequestBody String jsonInput
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.text();
      setOutputJson(data);
    } catch (error) {
      console.error('Error minifying JSON:', error);
      setOutputJson('Error processing JSON');
    }
  };

   const handleBeautify = async () => {
    try {
      const response = await fetch('/api/json/beautify', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: inputJson,
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.text();
      setOutputJson(data);
    } catch (error) {
      console.error('Error beautifying JSON:', error);
      setOutputJson('Error processing JSON');
    }
  };
   const handleCompare = async () => {
    pass;
  };

  return (
    <div style={{ padding: '20px', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      <h1>JSON Tools</h1>

      <div style={{ display: 'flex', gap: '20px', width: '100%', justifyContent: 'center', alignItems: 'flex-start' }}>
        <div style={{ flex: 1, minWidth: 0 }}>
          <LineNumberedEditor
            value={inputJson}
            onChange={setInputJson}
            placeholder="Paste your JSON here..."
          />
        </div>

        <div style={{ display: 'flex', flexDirection: 'column', gap: '10px', paddingTop: '10px' }}>
          <button onClick={handleMinify}>Minify</button>
          <button onClick={handleBeautify}>Beautify</button>
          <button onClick={handleCompare}>Compare</button>
        </div>

        <div style={{ flex: 1, minWidth: 0 }}>
          <LineNumberedEditor
            value={outputJson}
            onChange={setOutputJson}
            placeholder="Result will appear here..."
          />
        </div>
      </div>
    </div>
  );
}

export default App;