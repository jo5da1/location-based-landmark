#!/bin/bash

##
aws --endpoint-url=http://localhost:4566 dynamodb create-table \
--table-name users \
--attribute-definitions AttributeName=id,AttributeType=S \
--key-schema AttributeName=id,KeyType=HASH \
--billing-mode PAY_PER_REQUEST

##
#aws --endpoint-url=http://localhost:4566 lambda create-function \
#  --function-name discover-nearby-function \
#  --runtime java21 \
#  --handler com.joda.discover.nearby.lambda.StreamLambdaHandler \
#  --role arn:aws:iam::000000000000:role/lambda-role \
#  --zip-file fileb://target/function.zip

##
#aws --endpoint-url=http://localhost:4566 apigateway create-rest-api \
#  --name "discover-nearby-api"