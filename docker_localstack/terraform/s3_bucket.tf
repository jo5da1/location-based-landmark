# https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/s3_bucket

resource "aws_s3_bucket" "s3_osm_bucket" {
  bucket = "s3-osm-bucket"

  tags = {
    Name        = "S3 OSM Bucket"
    Environment = "dev"
  }
}