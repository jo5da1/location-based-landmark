#!/bin/bash

echo "Running tests locally..."

export $(grep -v '^#' config/.env | xargs)

# echo "----pip list  -before"
# python -m pip list

echo "pip install requirements in progress..(silently..)"
pip install -q --upgrade pip
pip install -q -r requirements/test.txt

# echo "----pip list  -after "
# python -m pip list

pytest tests/test_nearby_api.py -v