# Auto-discover and call API
echo "REST API : " $(awslocal apigateway get-rest-apis)

RESTAPI_ID=$(awslocal apigateway get-rest-apis | jq -r '.items[] | select(.name=="landmark_osm_import-api") | .id')
echo "REST API ID: " $RESTAPI_ID

STAGE_NAME=$(awslocal apigateway get-stages --rest-api-id $RESTAPI_ID | jq -r '.item[0].stageName')
echo "STAGE NAME : "$STAGE_NAME

RESOURCE_PATH=$(awslocal apigateway get-resources --rest-api-id $RESTAPI_ID | jq -r '.items[] | select(.path | test("/landmark-osm-import")) | .path' | sed 's|^/||')
echo "RESOURCE PATH: "$RESOURCE_PATH


echo "http://localhost:4566/restapis/$RESTAPI_ID/dev/_user_request_/$RESOURCE_PATH"

echo "http://localhost:4566/_aws/execute-api/$RESTAPI_ID/dev/$RESOURCE_PATH"
