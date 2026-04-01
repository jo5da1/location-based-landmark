RESTAPI_ID=$(awslocal apigateway get-rest-apis | jq -r '.items[] | select(.name=="discover_nearby-gateway-rest-api") | .id')

STAGE_NAME=$(awslocal apigateway get-stages --rest-api-id $RESTAPI_ID | jq -r '.item[0].stageName')

curl -X GET http://localhost:4566/restapis/$RESTAPI_ID/$STAGE_NAME/_user_request_/discover-nearby/city/getAll \
     -H "Content-Type: application/json" \
