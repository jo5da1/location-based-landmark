#!/bin/bash

rm -rf build
mkdir build
mkdir build/config

echo "pip install requirements in progress..(silently..)"
pip install -qqq -r requirements/test.txt -t build

cp tests/*.py build/
cp config/.env build/config/.env

cd build

zip -r function.zip . -x "__pycache__/*"
