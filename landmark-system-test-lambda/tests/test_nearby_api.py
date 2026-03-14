import os
import uuid
import requests
from dotenv import load_dotenv

# Load environment variables from .env
load_dotenv()

# Get base URL from environment variable
# fallback to localhost
BASE_URL = os.getenv("LANDMARK_NEARBY_API_BASE_URL", "http://localhost:8086")

def test_print_base_url():
    """Simple test to print the base URL"""
    print("\n------ test_print_base_url ------")
    print("BASE_URL:", BASE_URL)
    assert BASE_URL is not None

def test_landmarks_nearby_endpoint_nearby():
    """Test the /api/landmark/nearby endpoint"""
    print("\n------ test_landmarks_nearby_endpoint_nearby ------")
    print("BASE_URL: " + BASE_URL)

    url = f"{BASE_URL}/api/landmark/nearby"

    payload = {
        "requestId": str(uuid.uuid4()),
        "coordinates": { "latitude": 57.7082513388622, "longitude": 11.964816090375521 },
        "radius": 1000,
        "categories": [],
        "subCategories": ["CAFE"],
        "page": 0,
        "pageSize": 10
    }
    headers = { "Content-Type": "application/json" }

    r = requests.post(url, json=payload, headers=headers)

    print(r.status_code)
    print(r.text)

    assert r.status_code == 200
    data = r.json()

    # response structure validation
    assert isinstance(data, dict)
    assert "landmarks" in data
    assert "totalCount" in data
    assert "requestId" in data

    # # landmarks should be a list
    assert isinstance(data["landmarks"], list)

    # # validate landmark structure
    if data["landmarks"]:
        landmark = data["landmarks"][0]
        assert "name" in landmark
        assert "category" in landmark
        assert "subCategory" in landmark
        assert "coordinates" in landmark

def test_landmarks_nearby_endpoint_category():
    print("\n")
    print("------test_landmarks_nearby_endpoint_category")
    print("BASE_URL: " + BASE_URL)

    url = f"{BASE_URL}/api/landmark/category"

    payload = {
        "requestId": "category-request"
    }

    headers = {
        "Content-Type": "application/json"
    }

    r = requests.get(url, json=payload, headers=headers)

    print(r.status_code)
    print(r.text)

    assert r.status_code == 200
    data = r.json()

    # response structure validation
    assert isinstance(data, dict)
    assert "categories" in data

    # # categories should be a list
    assert isinstance(data["categories"], list)

    # # validate categories structure
    if data["categories"]:
        cat = data["categories"][0]
        assert "id" in cat
        assert "category" in cat
        assert "subCategories" in cat
