import React, { useState, useEffect } from "react";
import { fetchLandmarkOptions } from '../api/landmarkApi';
import { LandmarkCategory } from '../types/landmarkTypes';

interface OptionsPanelProps {
  options: string[];
  setOptions: React.Dispatch<React.SetStateAction<string[]>>;
}

const OptionsPage: React.FC<OptionsPanelProps> = ({ options, setOptions }) => {

  const [availableOptions, setAvailableOptions] = useState<LandmarkCategory[]>([]); 
  const [selectedCategories, setSelectedCategories] = useState<number[]>([]);
  const [selectedSubcategories, setSelectedSubcategories] = useState<string[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const loadOptions = async () => {
    
    setLoading(true);
    setError(null);

    try {
      const data: LandmarkCategory[] = await fetchLandmarkOptions();
      setAvailableOptions(data);
     } catch (error) {
      console.error("Failed to load options", error);
      setError("Failed to load options");
    } finally {
      setLoading(false);
    }
  };

  const toggleCategory = (id: number) => {
    console.log("toggleCategory: Do nothing!!")
    setSelectedCategories(prev =>
      prev.includes(id) 
      ? prev.filter(o => o !== id) 
      : [...prev, id]
    );
  };

  const toggleSubcategory = (subcategory: string) => {
    console.log("toggleSubcategory: Do nothing!!")
    setSelectedSubcategories(prev =>
      prev.includes(subcategory)
        ? prev.filter(s => s !== subcategory)
        : [...prev, subcategory]
    );
  };

  // Sync selected option names to parent
  useEffect(() => {
    const categoryNames = availableOptions
      .filter(cat => selectedCategories.includes(cat.id))
      .map(cat => cat.category);
    setOptions([...categoryNames, ...selectedSubcategories]);
  }, [
    selectedCategories, 
    selectedSubcategories, 
    availableOptions, 
    setOptions
  ]);

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
          {
            availableOptions.map(cat => (
              <div key={cat.id} style={{ marginBottom: "16px" }}>
                {/* Category Checkbox */}
                <label key={cat.id} style={{ display: "block", marginBottom: "8px" }}>
                  <input
                    type="checkbox"
                    checked={selectedCategories.includes(cat.id)}
                    onChange={() => toggleCategory(cat.id)}
                    disabled={loading}
                  />
                  {cat.category}
                </label>

                {/* Subcategory Checkboxes */}
                <div style={{ paddingLeft: "20px", marginTop: "8px" }}>
                  {cat.subCategories.map(sub => (
                    <label key={sub} style={{ display: "block" }}>
                      <input
                        type="checkbox"
                        checked={selectedSubcategories.includes(sub)}
                        onChange={() => toggleSubcategory(sub)}
                        disabled={loading}
                      />
                      {sub}
                    </label>
                  ))}
                </div>
              </div>
            ))
          }
        </div>
      )}
    </div>
  );
};

export default OptionsPage;