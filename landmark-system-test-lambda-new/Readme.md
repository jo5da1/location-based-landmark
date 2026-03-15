```
landmark-system-test-lambda-new/
├── docker/
│   ├── Dockerfile
│   └── init-localstack.sh
├── lambda/
│   └── handler.py
├── tests/
│   ├── __init__.py
│   ├── test_main.py
│   └── test_nearby_api.py
├── config/
│   └── .env
├── scripts/
│   ├── package_lambda.sh
│   └── run_local_tests.sh
├── requirements/
│   ├── lambda.txt
│   └── test.txt
├── docker-compose.yml
├── README.md
└── .gitignore
```

### Run Locally (1)

---
1. Creating Virtual Env
```
python -m venv venv --prompt="lndmrk_sys_test-lmd-new"
```

2. Activating Virtual Env
```
source venv/bin/activate
```
3. Install Requirements
```
pip install -r requirements/test.txt
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

```

8. Freeze Requirements
```
pip freeze > requirements_running.txt
```


### Run Locally (2)

---
1. Creating Virtual Env
```
python -m venv venv --prompt="lndmrk_sys_test-lmd-new"
```

2. Activating Virtual Env
```
source venv/bin/activate
```
3. Run script
```
sh scripts/run_local_tests.sh
```

### Run as docker compose

---

```
docker-compose up
```

Look for Legacy URL or New URL in docker log
```
Legacy URL:
curl http://localhost:4566/restapis/bifuydsmyx/dev/_user_request_/landmark-system-test-lambda-new

New URL:
curl http://localhost:4566/_aws/execute-api/bifuydsmyx/dev/landmark-system-test-lambda-new
```
