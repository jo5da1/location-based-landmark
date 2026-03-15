#### Project Structure
```
landmark-system-test-localstack-lambda/
 ├── docker/
 │    └── init.sh
 ├── lambda/
 │    └── handler.py
 ├── tests/ 
 │    ├── .env
 │    ├── __init__.py
 │    ├── requirements.txt
 │    ├── test_main.py
 │    └── test_nearby_api.py
 ├── docker-compose.yml
 └── ReadMe.md 
```

## To run locally
```
chmod +x init.sh
```
```
docker compose up
```
Look for Legacy URL or  New URL in docker log
```
curl http://localhost:4566/restapis/xf5kyrlxiq/dev/_user_request_/landmark-system-test-lambda

curl http://localhost:4566/_aws/execute-api/xf5kyrlxiq/dev/landmark-system-test-lambda
```
