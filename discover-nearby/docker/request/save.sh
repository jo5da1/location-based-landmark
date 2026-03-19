# Prompt the user
read -p "Enter city name: " city

# Display what the user entered
echo "You entered: $city"

RESTAPI_ID=$(awslocal apigateway get-rest-apis | jq -r '.items[] | select(.name=="discover_nearby-gateway-rest-api") | .id')
echo "REST API ID: " $RESTAPI_ID

STAGE_NAME=$(awslocal apigateway get-stages --rest-api-id $RESTAPI_ID | jq -r '.item[0].stageName')
echo "STAGE NAME : "$STAGE_NAME

CITY=$city

curl -X POST http://localhost:4566/restapis/$RESTAPI_ID/$STAGE_NAME/_user_request_/discover-nearby/city/save \
     -H "Content-Type: application/json" \
     -d $CITY
