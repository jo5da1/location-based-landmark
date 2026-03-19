#!/bin/bash

echo "--------------------------------------------------------------"
echo "------ Initializing LocalStack with Terraform           ------"
echo "--------------------------------------------------------------"

cd /app

bash scripts/localstack-resources.sh

cd terraform

terraform init
terraform apply -auto-approve