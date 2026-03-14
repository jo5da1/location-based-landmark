from tests.test_nearby_api import test_print, test_landmarks_nearby_endpoint_category, test_landmarks_nearby_endpoint_nearby

def main():
    print("=== Landmark System Test ===")
    test_print();
    test_landmarks_nearby_endpoint_category();
    test_landmarks_nearby_endpoint_nearby();

# Run program
if __name__ == "__main__":
    main()