
def handler(event, context):
    print("=== Landmark System Test ===")
    return {
        "statusCode": 200,
        "body": "Hello Landmark System Test Lambda LocalStack!"
    }