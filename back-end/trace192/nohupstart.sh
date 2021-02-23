#!/bin/bash
tm=$(date +%F-%H%M%S)
nohup mvn spring-boot:run > trace"${tm}".out 2>&1 &