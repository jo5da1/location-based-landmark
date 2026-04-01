import json
import os
import boto3

def handler(event, context):

    print("----------Lambda Function -----------")

    sqs = boto3.client(
        "sqs",
        endpoint_url=os.environ["SQS_ENDPOINT"]
    )

    queue_url = os.environ["QUEUE_URL"]

    print("SQS LIST:", sqs.list_queues())
    print("QUEUE URL:", queue_url)

    # Send message to SQS
    response = sqs.send_message(
        QueueUrl=queue_url,
        MessageBody=json.dumps({
            "message": "Trigger Import",
            "event": event
        })
    )
    print("MESSAGE SENT:", response)

    return {
        "statusCode": 200,
        "body": json.dumps({
            "event": event,
            "response:": response,
            "messageId": response.get("MessageId"),
            "sqs": sqs.list_queues(),
            "queue url": queue_url
        })
    }

