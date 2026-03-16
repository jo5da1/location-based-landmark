resource "aws_lambda_permission" "apigw" {

  statement_id  = "AllowExecutionFromAPIGateway"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.landmark_system_test_lambda_terraform.function_name
  principal     = "apigateway.amazonaws.com"
}