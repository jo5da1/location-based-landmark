output "api_url" {

  value = "http://localhost:4566/restapis/${aws_api_gateway_rest_api.api.id}/dev/_user_request_/landmark-system-test-lambda-terraform"
}