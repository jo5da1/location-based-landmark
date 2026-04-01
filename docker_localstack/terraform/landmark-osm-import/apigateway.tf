resource "aws_api_gateway_rest_api" "landmark_osm_import" {
  name = "landmark_osm_import-api"
}

resource "aws_api_gateway_resource" "landmark_osm_import" {
  rest_api_id = aws_api_gateway_rest_api.landmark_osm_import.id
  parent_id   = aws_api_gateway_rest_api.landmark_osm_import.root_resource_id
  path_part   = "landmark-osm-import"
}

resource "aws_api_gateway_method" "landmark_osm_import" {
  rest_api_id   = aws_api_gateway_rest_api.landmark_osm_import.id
  resource_id   = aws_api_gateway_resource.landmark_osm_import.id
  http_method   = "GET"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "landmark_osm_import" {
  rest_api_id = aws_api_gateway_rest_api.landmark_osm_import.id
  resource_id = aws_api_gateway_resource.landmark_osm_import.id
  http_method = aws_api_gateway_method.landmark_osm_import.http_method
  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri = aws_lambda_function.landmark_osm_import.invoke_arn
}

resource "aws_api_gateway_deployment" "landmark_osm_import" {
  rest_api_id = aws_api_gateway_rest_api.landmark_osm_import.id
  depends_on = [
    aws_api_gateway_integration.landmark_osm_import
  ]
}

resource "aws_api_gateway_stage" "landmark_osm_import" {
  deployment_id = aws_api_gateway_deployment.landmark_osm_import.id
  rest_api_id   = aws_api_gateway_rest_api.landmark_osm_import.id
  stage_name    = "dev"
}
