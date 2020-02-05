#!/bin/bash

base="http://localhost:8080/books/"
content_type="Content-Type: application/x-www-form-urlencoded"

endpoint="${base}new"
echo "Testing POST on ${endpoint}"
echo "-----------------------------------------------------------------------------------"
curl --request POST --header "$content_type" --data-urlencode "title=XXX&authors=Y,Z&date=2019/05/05&domain=IT&published=Interplanetary Press" "${endpoint}"
echo "-----------------------------------------------------------------------------------"
sleep 3s

endpoint="${base}"
echo "Testing GET on ${endpoint}"
echo "-----------------------------------------------------------------------------------"
curl --request GET --header "$content_type" "${endpoint}"
echo "-----------------------------------------------------------------------------------"
sleep 3s

endpoint="${base}author/"
echo "Testing GET on ${endpoint}"
echo "-----------------------------------------------------------------------------------"
curl --request GET --header "$content_type" "${endpoint}Longus"
echo "-----------------------------------------------------------------------------------"
sleep 3s

endpoint="${base}authorCount/"
echo "Testing GET on ${endpoint}"
echo "-----------------------------------------------------------------------------------"
curl --request GET --header "$content_type" "${endpoint}1"
echo "-----------------------------------------------------------------------------------"
sleep 3s

endpoint="${base}title/"
echo "Testing GET on ${endpoint}"
echo "-----------------------------------------------------------------------------------"
curl --request GET --header "$content_type" "${endpoint}Loop me to the end of Love"
echo "-----------------------------------------------------------------------------------"
sleep 3s

endpoint="${base}titleRegex/"
echo "Testing GET on ${endpoint}"
echo "-----------------------------------------------------------------------------------"
curl --request GET --header "$content_type" "${endpoint}Loop me to the end of Love"
echo "-----------------------------------------------------------------------------------"
sleep 3s

endpoint="${base}domain/"
echo "Testing GET on ${endpoint}"
echo "-----------------------------------------------------------------------------------"
curl --request GET --header "$content_type" "${endpoint}Science - Theory of Scientific Writing"
echo "-----------------------------------------------------------------------------------"
sleep 3s

endpoint="${base}year/"
echo "Testing GET on ${endpoint}"
echo "-----------------------------------------------------------------------------------"
curl --request GET --header "$content_type" "${endpoint}2019"
echo "-----------------------------------------------------------------------------------"
sleep 3s

endpoint="${base}update"
echo "Testing GET on ${endpoint}"
echo "-----------------------------------------------------------------------------------"
curl --request PUT --header "$content_type" --data-urlencode "title=Loop me to the end of Love&field=year&value=2020"  "${endpoint}"
echo "-----------------------------------------------------------------------------------"
sleep 3s
