import React, { useState, useEffect } from "react";
import { fetchLandmarkOptions } from '../api/landmarkApi';
import { LandmarkOption } from '../types/landmarkTypes';

interface OptionsPanelProps {
  options: string[];
  setOptions: React.Dispatch<React.SetStateAction<string[]>>;
}

const OptionsPage: React.FC<OptionsPanelProps> = ({ options, setOptions }) => {
  const [availableOptions, setAvailableOptions] = useState<LandmarkOption[]>([]);
  const [selectedOptions, setSelectedOptions] = useState<number[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const loadOptions = async () => {
    setLoading(true);
    setError(null);
    try {
      const data: LandmarkOption[] = await fetchLandmarkOptions();
      setAvailableOptions(data);
     } catch (error) {
      console.error("Failed to load options", error);
      setError("Failed to load options");
    } finally {
      setLoading(false);
    }
  };

  const toggleOption = (id: number) => {
    console.log("Do nothing!!")
    setSelectedOptions(prev =>
      prev.includes(id) ? prev.filter(o => o !== id) : [...prev, id]
    );
  };

  // Sync selected option names to parent
  useEffect(() => {
    const selectedNames = availableOptions
      .filter(opt => selectedOptions.includes(opt.id))
      .map(opt => opt.name);
    setOptions(selectedNames);
  }, [selectedOptions, availableOptions, setOptions]);

  const loaded = availableOptions.length > 0;

  return (
    <div style={{ padding: "20px" }}>
      <h2>Landmarks</h2>
      <button onClick={loadOptions} disabled={loading}>
        {loading ? "Loading..." : "Load"}
      </button>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {loaded && (
        <div style={{ marginTop: "20px" }}>
          {availableOptions.map(opt => (
            <label key={opt.id} style={{ display: "block", marginBottom: "8px" }}>
              <input
                type="checkbox"
                checked={selectedOptions.includes(opt.id)}
                onChange={() => toggleOption(opt.id)}
                disabled={loading}
              />
              {opt.name}
            </label>
          ))}
        </div>
      )}
    </div>
  );
};

export default OptionsPage;