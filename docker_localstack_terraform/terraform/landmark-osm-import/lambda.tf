# https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/lambda_function

# Lambda function
resource "aws_lambda_function" "landmark_osm_import" {
  function_name = "landmark_osm_import_trigger"
  handler       = "lambda_handler.handler"
  runtime       = "python3.11"
  filename      = ".././target/landmark_osm_import/function.zip"
  role          = aws_iam_role.landmark_osm_import.arn
  environment {
    variables = {
      ENVIRONMENT = "dev"
      LOG_LEVEL   = "info"
      QUEUE_URL   = aws_sqs_queue.osm_import_queue.url
      SQS_ENDPOINT = aws_sqs_queue.osm_import_queue.url
    }
  }
  tags = {
    Environment = "dev"
    Application = "Landmark OSM Import Trigger"
  }
}

resource "aws_lambda_permission" "landmark_osm_import" {
  statement_id  = "AllowAPIGatewayInvoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.landmark_osm_import.function_name
  principal     = "apigateway.amazonaws.com"
}
