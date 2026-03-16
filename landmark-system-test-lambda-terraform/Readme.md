```
landmark-system-test-lambda-terraform/
├── config/
│   └── .env
├── docker/
│   └── init-localstack.sh
├── requirements/
│   ├── lambda.txt
│   └── test.txt
├── scripts/
│   ├── package_lambda.sh
│   └── run_local_tests.sh
├── terraform/
│   ├── apigateway.tf
│   ├── iam.tf
│   ├── lambda.tf
│   ├── main.tf
│   ├── outputs.tf
│   ├── variables.tf
│   └── provider.tf
├── tests/
│   ├── __init__.py
│   ├── test_main.py
│   └── test_nearby_api.py
├── gitignore
├── docker-compose.yml
└── README.md
```
```
package_lambda.sh
      ↓
init-localstack.sh
      ↓
aws lambda create-function
aws apigateway create-rest-api
aws apigateway create-resource
aws apigateway put-method
aws apigateway put-integration
aws apigateway create-deployment
```
```
| Component                 | Terraform Resource            |
| ------------------------- | ----------------------------- |
| Lambda function           | `aws_lambda_function`         |
| API Gateway REST API      | `aws_api_gateway_rest_api`    |
| API Gateway resource path | `aws_api_gateway_resource`    |
| HTTP Method               | `aws_api_gateway_method`      |
| Lambda Integration        | `aws_api_gateway_integration` |
| API Deployment            | `aws_api_gateway_deployment`  |
| Stage                     | `aws_api_gateway_stage`       |
| IAM role                  | `aws_iam_role`                |
| Lambda permission         | `aws_lambda_permission`       |
```

### Run Locally (1)

---
1. Creating Virtual Env
```
python -m venv venv --prompt="lndmrk_sys_test-lmd-tr"
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
python -m venv venv --prompt="lndmrk_sys_test-lmd-tr"
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
