# https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/sqs_queue
resource "aws_sqs_queue" "osm_import_queue" {
  name = "osm-import-queue"
}
