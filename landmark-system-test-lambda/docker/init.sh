#!/bin/bash

echo "--------------------------------------------------------------"
echo "------------------landmark-system-test-lambda------"
echo "--------------------------------------------------------------"

echo " "
echo "Creating Lambda..."

echo " "
echo "List Current Dir: $(pwd)"
echo "-----------------------------"
ls
echo "----********************-----"
echo " "

cd /tests

echo " "
echo "List Current Dir: $(pwd)"
echo "-----------------------------"
ls
echo "----********************-----"
echo " "


echo "Create DIR"
mkdir -p tests_run

cp .env /tests/tests_run/
cp __init__.py /tests/tests_run/
cp requirements.txt /tests/tests_run/
cp test_main.py /tests/tests_run/
cp test_nearby_api.py /tests/tests_run/

cd /tests/tests_run
echo "List Current Dir: $(pwd)"
echo "-----------------------------"
ls
echo "----********************-----"
echo " "


echo " "
echo "Installing dependencies..."
pip install -r requirements.txt -t .

echo " "
echo "Creating Lambda zip..."
zip -r function.zip . -x "__pycache__/*"

aws --endpoint-url=http://localhost:4566 lambda create-function \
  --function-name landmark-system-test-lambda-function \
  --runtime python3.9 \
  --handler test_main.handler \
  --role arn:aws:iam::000000000000:role/lambda-role \
  --zip-file fileb://function.zip

echo " "
echo "Creating API Gateway..."

API_ID=$(aws --endpoint-url=http://localhost:4566 apigateway create-rest-api \
  --name "landmark-system-test-lambda-api" \
  --query 'id' \
  --output text)

ROOT_ID=$(aws --endpoint-url=http://localhost:4566 apigateway get-resources \
  --rest-api-id $API_ID \
  --query 'items[0].id' \
  --output text)

RESOURCE_ID=$(aws --endpoint-url=http://localhost:4566 apigateway create-resource \
  --rest-api-id $API_ID \
  --parent-id $ROOT_ID \
  --path-part landmark-system-test-lambda \
  --query 'id' \
  --output text)

aws --endpoint-url=http://localhost:4566 apigateway put-method \
  --rest-api-id $API_ID \
  --resource-id $RESOURCE_ID \
  --http-method GET \
  --authorization-type "NONE"

LAMBDA_ARN=arn:aws:lambda:us-east-1:000000000000:function:landmark-system-test-lambda-function

aws --endpoint-url=http://localhost:4566 apigateway put-integration \
  --rest-api-id $API_ID \
  --resource-id $RESOURCE_ID \
  --http-method GET \
  --type AWS_PROXY \
  --integration-http-method POST \
  --uri arn:aws:apigateway:us-east-1:lambda:path/2015-03-31/functions/$LAMBDA_ARN/invocations

aws --endpoint-url=http://localhost:4566 apigateway create-deployment \
  --rest-api-id $API_ID \
  --stage-name dev

echo "--------------------------------------------------------------"
echo "------------------landmark-system-test-lambda------"
echo "--------------------------------------------------------------"

echo " "
echo " Legacy URL:"
echo "http://localhost:4566/restapis/$API_ID/dev/_user_request_/landmark-system-test-lambda"
echo "curl http://localhost:4566/restapis/$API_ID/dev/_user_request_/landmark-system-test-lambda"

echo " "
echo " New URL:"
echo "http://localhost:4566/_aws/execute-api/$API_ID/dev/landmark-system-test-lambda"
echo "curl http://localhost:4566/_aws/execute-api/$API_ID/dev/landmark-system-test-lambda"

echo " "
echo "List Current Dir: $(pwd)"
echo "-----------------------------"
ls
echo "----********************-----"
echo " "

