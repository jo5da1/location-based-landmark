from test_nearby_api import (
    test_print_base_url,
    test_landmarks_nearby_endpoint_category,
    test_landmarks_nearby_endpoint_nearby
)

def handler(event, context):
    print("Running Landmark System Tests")

    test_print_base_url()
    test_landmarks_nearby_endpoint_category()
    test_landmarks_nearby_endpoint_nearby()

    return {
        "statusCode": 200,
        "body": "Tests executed"
    }

def main():
    print("=== Landmark System Test ===")
    test_print_base_url()
    test_landmarks_nearby_endpoint_category()
    test_landmarks_nearby_endpoint_nearby()

# Run program
if __name__ == "__main__":
    main()