# Auto-discover and call API
echo "REST API : " $(awslocal apigateway get-rest-apis)

RESTAPI_ID=$(awslocal apigateway get-rest-apis | jq -r '.items[] | select(.name=="discover_nearby-gateway-rest-api") | .id')
echo "REST API ID: " $RESTAPI_ID

STAGE_NAME=$(awslocal apigateway get-stages --rest-api-id $RESTAPI_ID | jq -r '.item[0].stageName')
echo "STAGE NAME : "$STAGE_NAME

RESOURCE_PATH=$(awslocal apigateway get-resources --rest-api-id $RESTAPI_ID | jq -r '.items[] | select(.path | test("/discover-nearby")) | .path' | sed 's|^/||')
echo "RESOURCE PATH: "$RESOURCE_PATH

