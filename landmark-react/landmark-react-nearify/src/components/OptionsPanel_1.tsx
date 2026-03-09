import React from 'react';

type Props = {
  options: string[];
  setOptions: (opts: string[]) => void;
};

const AVAILABLE_OPTIONS = ['Cafe', 'Restaurant', 'Park'];

const OptionsPanel_1: React.FC<Props> = ({ options, setOptions }) => {
  const toggleOption = (opt: string) => {
    if (options.includes(opt)) {
      setOptions(options.filter(o => o !== opt));
    } else {
      setOptions([...options, opt]);
    }
  };

  return (
    <div style={{ padding: '1rem', width: '200px', borderRight: '1px solid gray' }}>
    <h3>Select Landmarks</h3>
      {AVAILABLE_OPTIONS.map(opt => (
        <label key={opt} style={{ display: 'block', marginBottom: '0.5rem' }}>
          <input
            type="checkbox"
            checked={options.includes(opt)}
            onChange={() => toggleOption(opt)}
          />
          {opt}
        </label>
      ))}
    </div>
  );
};

export default OptionsPanel_1;
