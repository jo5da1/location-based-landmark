import express from 'express';
const app = express();

app.get('/api/options', (req, res) => {
  const mockOptions = [
    { id: 1, name: 'Museum' },
    { id: 2, name: 'Park' },
    { id: 3, name: 'Restaurant' },
    { id: 4, name: 'Historic Site' },
    { id: 5, name: 'Monument' },
  ];
  res.json(mockOptions);
});

app.listen(3000, () => console.log('Server running on port 3000'));