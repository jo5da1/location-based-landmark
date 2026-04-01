#!/bin/bash
set -e

echo "Starting OSM worker..."

echo "-------------: "
echo "SQS_ENDPOINT : " $SQS_ENDPOINT
echo "QUEUE_URL    : " $QUEUE_URL
echo "S3_ENDPOINT  : " $S3_ENDPOINT
echo "S3_BUCKET    : " $S3_BUCKET
echo "OSM_FILE     : " $OSM_FILE
echo "-------------: "

while true; do

  echo "Polling SQS..."

  MSG=$(aws --endpoint-url="$SQS_ENDPOINT" \
    sqs receive-message \
    --queue-url "$QUEUE_URL" \
    --max-number-of-messages 1 \
    --wait-time-seconds 10)

  if [ -n "$MSG" ]; then
    echo " "
    echo "MSG : " $MSG
  fi

  BODY=$(echo "$MSG" | jq -r '.Messages[0].Body')

  if [ -z "$BODY" ]; then
    echo "No messages"
    sleep 2
    continue
  fi

  echo "BODY : " $BODY

  RECEIPT=$(echo "$MSG" | jq -r '.Messages[0].ReceiptHandle')
  echo "RECEIPT : " $RECEIPT

  if ! aws --endpoint-url=$S3_ENDPOINT s3 ls s3://$S3_BUCKET/$OSM_FILE ; then
    echo "No $OSM_FILE found in $S3_BUCKET"
    echo " "
    aws --endpoint-url=$SQS_ENDPOINT \
        sqs delete-message \
        --queue-url $QUEUE_URL \
        --receipt-handle $RECEIPT
    continue
  fi

  # Run your existing import logic
  /usr/local/bin/osm_import_s3.sh

  # Delete message
  aws --endpoint-url=$SQS_ENDPOINT \
    sqs delete-message \
    --queue-url $QUEUE_URL \
    --receipt-handle $RECEIPT

done

