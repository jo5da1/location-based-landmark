# https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/api_gateway_rest_api

# -----------------------------
# REST API
# -----------------------------
resource "aws_api_gateway_rest_api" "discover_nearby" {
  name = "discover_nearby-gateway-rest-api"
}

# -----------------------------
# Root Resource: /discover-nearby
# -----------------------------
resource "aws_api_gateway_resource" "discover_nearby" {
  parent_id   = aws_api_gateway_rest_api.discover_nearby.root_resource_id
  path_part   = "discover-nearby"
  rest_api_id = aws_api_gateway_rest_api.discover_nearby.id
}
# Now doing
resource "aws_api_gateway_method" "discover_nearby_get" {
  authorization = "NONE"
  http_method   = "GET"
  resource_id   = aws_api_gateway_resource.discover_nearby.id
  rest_api_id   = aws_api_gateway_rest_api.discover_nearby.id
}

resource "aws_api_gateway_integration" "discover_nearby_get" {
  http_method             = aws_api_gateway_method.discover_nearby_get.http_method
  resource_id             = aws_api_gateway_resource.discover_nearby.id
  rest_api_id             = aws_api_gateway_rest_api.discover_nearby.id
  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = aws_lambda_function.discover_nearby.invoke_arn
}

# -----------------------------
# Nested Resource: /discover-nearby/city
# -----------------------------
resource "aws_api_gateway_resource" "discover_nearby_city" {
  parent_id   = aws_api_gateway_resource.discover_nearby.id
  path_part   = "city"
  rest_api_id = aws_api_gateway_rest_api.discover_nearby.id
}

# -----------------------------
# POST /discover-nearby/city/save
# -----------------------------
resource "aws_api_gateway_resource" "city_save" {
  parent_id   = aws_api_gateway_resource.discover_nearby_city.id
  path_part   = "save"
  rest_api_id = aws_api_gateway_rest_api.discover_nearby.id
}

resource "aws_api_gateway_method" "city_save_post" {
  authorization = "NONE"
  http_method   = "POST"
  resource_id   = aws_api_gateway_resource.city_save.id
  rest_api_id   = aws_api_gateway_rest_api.discover_nearby.id
}

resource "aws_api_gateway_integration" "city_save_post" {
  http_method             = aws_api_gateway_method.city_save_post.http_method
  resource_id             = aws_api_gateway_resource.city_save.id
  rest_api_id             = aws_api_gateway_rest_api.discover_nearby.id
  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = aws_lambda_function.discover_nearby.invoke_arn
}

# -----------------------------
# GET /discover-nearby/city/getAll
# -----------------------------
resource "aws_api_gateway_resource" "city_get_all" {
  parent_id   = aws_api_gateway_resource.discover_nearby_city.id
  path_part   = "getAll"
  rest_api_id = aws_api_gateway_rest_api.discover_nearby.id
}

resource "aws_api_gateway_method" "city_get_all" {
  authorization = "NONE"
  http_method   = "GET"
  resource_id   = aws_api_gateway_resource.city_get_all.id
  rest_api_id   = aws_api_gateway_rest_api.discover_nearby.id
}

resource "aws_api_gateway_integration" "city_get_all" {
  http_method             = aws_api_gateway_method.city_get_all.http_method
  resource_id             = aws_api_gateway_resource.city_get_all.id
  rest_api_id             = aws_api_gateway_rest_api.discover_nearby.id
  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = aws_lambda_function.discover_nearby.invoke_arn
}

# -----------------------------
# GET /discover-nearby/city/get/{city}
# -----------------------------
resource "aws_api_gateway_resource" "city_get" {
  parent_id   = aws_api_gateway_resource.discover_nearby_city.id
  path_part   = "get"
  rest_api_id = aws_api_gateway_rest_api.discover_nearby.id
}

resource "aws_api_gateway_resource" "city_get_by_name" {
  parent_id   = aws_api_gateway_resource.city_get.id
  path_part   = "{city}"
  rest_api_id = aws_api_gateway_rest_api.discover_nearby.id
}

resource "aws_api_gateway_method" "city_get_by_name" {
  authorization = "NONE"
  http_method   = "GET"
  resource_id   = aws_api_gateway_resource.city_get_by_name.id
  rest_api_id   = aws_api_gateway_rest_api.discover_nearby.id
}

resource "aws_api_gateway_integration" "city_get_by_name" {
  http_method             = aws_api_gateway_method.city_get_by_name.http_method
  resource_id             = aws_api_gateway_resource.city_get_by_name.id
  rest_api_id             = aws_api_gateway_rest_api.discover_nearby.id
  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = aws_lambda_function.discover_nearby.invoke_arn
}

# -----------------------------
# Deployment & Stage
# -----------------------------
resource "aws_api_gateway_deployment" "discover_nearby" {
  rest_api_id = aws_api_gateway_rest_api.discover_nearby.id

  depends_on = [
    aws_api_gateway_integration.discover_nearby_get,
    aws_api_gateway_integration.city_save_post,
    aws_api_gateway_integration.city_get_all,
    aws_api_gateway_integration.city_get_by_name
  ]
}

resource "aws_api_gateway_stage" "discover_nearby" {
  deployment_id = aws_api_gateway_deployment.discover_nearby.id
  rest_api_id   = aws_api_gateway_rest_api.discover_nearby.id
  stage_name    = "dev"
}