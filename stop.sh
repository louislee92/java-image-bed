#!/bin/bash

ps aux | grep "java-image-bed-1.0-SNAPSHOT.jar" |grep -v grep| cut -c 9-15 | xargs kill -9

sleep 1

ps aux | grep "java-image-bed-1.0-SNAPSHOT.jar"
