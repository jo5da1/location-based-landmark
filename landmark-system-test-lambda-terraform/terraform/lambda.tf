resource "aws_lambda_function" "landmark_system_test_lambda_terraform" {

  function_name = "landmark-system-test-lambda-terraform-function"
  handler       = "test_main.handler"
  runtime       = "python3.9"

  filename      = "../build/function.zip"

  role = aws_iam_role.lambda_role.arn
}