#!/bin/bash

echo "--------------------------------------------------------------"
echo "------------------Initializing LocalStack...            ------"
echo "--------------------------------------------------------------"

echo " "
echo "Creating Lambda..."

cd /app

bash scripts/package_lambda.sh

FUNCTION_NAME=landmark-system-test-lambda-new
ZIP_FILE=build/function.zip

aws --endpoint-url=http://localhost:4566 lambda create-function \
  --function-name $FUNCTION_NAME-function \
  --runtime python3.9 \
  --handler test_main.handler \
  --role arn:aws:iam::000000000000:role/lambda-role \
  --zip-file fileb://$ZIP_FILE

echo " "
echo "Creating API Gateway..."

API_ID=$(aws --endpoint-url=http://localhost:4566 apigateway create-rest-api \
  --name $FUNCTION_NAME"-api" \
  --query 'id' \
  --output text)

ROOT_ID=$(aws --endpoint-url=http://localhost:4566 apigateway get-resources \
  --rest-api-id $API_ID \
  --query 'items[0].id' \
  --output text)

RESOURCE_ID=$(aws --endpoint-url=http://localhost:4566 apigateway create-resource \
  --rest-api-id $API_ID \
  --parent-id $ROOT_ID \
  --path-part $FUNCTION_NAME \
  --query 'id' \
  --output text)

aws --endpoint-url=http://localhost:4566 apigateway put-method \
  --rest-api-id $API_ID \
  --resource-id $RESOURCE_ID \
  --http-method GET \
  --authorization-type "NONE"

LAMBDA_ARN=arn:aws:lambda:us-east-1:000000000000:function:$FUNCTION_NAME-function

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
echo "------------------Done Initializing LocalStack          ------"
echo "--------------------------------------------------------------"

echo " "
echo " Legacy URL:"
echo "http://localhost:4566/restapis/$API_ID/dev/_user_request_/"$FUNCTION_NAME
echo "curl http://localhost:4566/restapis/$API_ID/dev/_user_request_/"$FUNCTION_NAME

echo " "
echo " New URL:"
echo "http://localhost:4566/_aws/execute-api/$API_ID/dev/"$FUNCTION_NAME
echo "curl http://localhost:4566/_aws/execute-api/$API_ID/dev/"$FUNCTION_NAME

echo " "
echo "List Current Dir: $(pwd)"
echo "-----------------------------"
ls
echo "----********************-----"
echo " "

