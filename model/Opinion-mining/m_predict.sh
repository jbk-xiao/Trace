#!/bin/bash

echo 'start Predict...'

cd /data/st01/Opinion-mining/Polarity/
sudo python3 predictCategory.py

cd /data/st01/Opinion-mining/Category/
sudo python3 predictScore.py
