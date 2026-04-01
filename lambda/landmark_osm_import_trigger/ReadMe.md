### Build Zip

---
1. Creating Virtual Env
```
python -m venv venv --prompt="lndmrk_osm_import_trigger"
```

2. Activating Virtual Env
```
source venv/bin/activate
```
3. Install Requirements 
```
pip install -r requirements.txt
```
4. Check Installed Requirements
```
python -m pip list
```
5. Check the Version of pip and Virtual env activated
```
pip -V
```
6. Build Zip
```
which python
zip -r function.zip . -x "__pycache__/*"
```
7. Freeze Requirements
```
pip freeze > requirements_running.txt
```
