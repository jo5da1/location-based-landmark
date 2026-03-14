### Run Locally

---
1. Creating Virtual Env
```
python -m venv venv --prompt="lndmrk_sys_test"
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
6. Run the app
```
python -m pytest tests/test_nearby_api.py -v
pytest tests/test_nearby_api.py -v

python -m pytest tests/test_nearby_api.py -s
pytest tests/test_nearby_api.py -s

python -m pytest tests/test_nearby_api.py
pytest tests/test_nearby_api.py

which python
which pytest

python -m src.main 
```
7. Freeze Requirements
```
pip freeze > requirements_running.txt
```

### Run in Docker

---

1. build docker image
```
sh docker-build.sh
```
or
```
docker build -t landmark-system-test .
```
2. run in docker
```
docker compose up
```