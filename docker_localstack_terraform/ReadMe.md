# LocalStack
http://localhost:4566/_localstack/health

# Some DynamoDb Commands

```
awslocal dynamodb list-tables 
awslocal dynamodb scan --table-name discover_city
awslocal dynamodb describe-table --table-name discover_city
```

```
aws --endpoint-url=http://localhost:4566 dynamodb list-tables
aws --endpoint-url=http://localhost:4566 dynamodb scan --table-name discover_city
aws --endpoint-url=http://localhost:4566 dynamodb describe-table --table-name discover_city
```

````
cd target
zip function.zip discover-nearby-0.0.1-SNAPSHOT.jar
````


---

# Some Lambda Commands
```
awslocal lambda list-functions
awslocal apigateway get-rest-apis
awslocal apigateway get-stages --rest-api-id <id>
awslocal apigateway get-resources --rest-api-id <id>
awslocal apigateway get-deployments --rest-api-id <id>
awslocal apigateway get-deployment --rest-api-id <id> --deployment-id <id>
```

# To see logs
````
awslocal logs tail /aws/lambda/discover_nearby_function --follow                             

````

# Endpoints
```
curl -X GET http://localhost:4566/restapis/0rxfydhp1c/dev/_user_request_/discover-nearby/

curl -X GET http://localhost:4566/restapis/0rxfydhp1c/dev/_user_request_/discover-nearby/city/getAll

curl -X POST http://localhost:4566/restapis/0rxfydhp1c/dev/_user_request_/discover-nearby/city/save \
     -H "Content-Type: application/json" \
     -d "Paris" 

curl -X GET http://localhost:4566/restapis/0rxfydhp1c/dev/_user_request_/discover-nearby/city/get/Paris \
     -H "Content-Type: application/json" 
```

# URL 
```
API Gateway routing is strict:
{base}/{stage}/_user_request_/{resource-path}

http://localhost:4566
  /restapis/crwkrvar7v
  /dev
  /_user_request_
  /discover_nearby
```
```
http://localhost:4566/restapis/<restapi_id>/<stage_name>/_user_request_/<resource_path>
```


# S3
````
awslocal s3 ls
awslocal s3 mb s3://my-local-bucket
awslocal s3 cp hello.txt s3://my-local-bucket/
awslocal s3 ls s3://my-local-bucket
````
```
awslocal s3 ls s3://s3-osm-bucket

awslocal s3 cp map.osm s3://s3-osm-bucket/
awslocal s3 cp postgres/data/release/map.osm s3://s3-osm-bucket/
awslocal s3 cp _postgres/data/release/map.osm s3://s3-osm-bucket/

```

# SQS
```
awslocal sqs list-queues
```

````
awslocal sqs send-message \
  --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/osm-import-queue \
  --message-body '{"bucket":"s3-osm-bucket","key":"map.osm"}'
````

#  
````
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name osm-import-queue
awslocal sqs create-queue --queue-name osm-import-queue
````

