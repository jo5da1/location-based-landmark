# https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/lambda_function

# Lambda function
resource "aws_lambda_function" "discover_nearby" {

  function_name = "discover_nearby_function"

  filename      = ".././target/discover-nearby/discover-nearby-0.0.1-SNAPSHOT.jar"
  //filename      = data.archive_file.discover_nearby.output_path
  handler       = "com.joda.discover.nearby.lambda.StreamLambdaHandler"
  code_sha256   = data.archive_file.discover_nearby.output_base64sha256

  role          = aws_iam_role.discover_nearby.arn

  runtime = "java21"

  environment {
    variables = {
      ENVIRONMENT = "dev"
      LOG_LEVEL   = "info"
      DYNAMODB_ENDPOINT = var.dynamodb_endpoint
    }
  }

  tags = {
    Environment = "dev"
    Application = "Discover Nearby"
  }
}



# Package the Lambda function code
data "archive_file" "discover_nearby" {
  type        = "zip"
  source_file = ".././target/discover-nearby/discover-nearby-0.0.1-SNAPSHOT.jar"
  output_path = ".././target/discover-nearby/function.zip"

#  source_file = "${path.module}/target/discover-nearby-0.0.1-SNAPSHOT.jar"
#  output_path = "${path.module}/target/function.zip"
}

resource "aws_iam_role" "discover_nearby" {
  name               = "lambda_execution_role"
  assume_role_policy = data.aws_iam_policy_document.discover_nearby.json
}

# IAM role for Lambda execution
data "aws_iam_policy_document" "discover_nearby" {
  statement {
    effect = "Allow"

    principals {
      type        = "Service"
      identifiers = ["lambda.amazonaws.com"]
    }

    actions = ["sts:AssumeRole"]
  }
}


resource "aws_lambda_permission" "apigw" {
  statement_id  = "AllowAPIGatewayInvoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.discover_nearby.function_name
  principal     = "apigateway.amazonaws.com"
}