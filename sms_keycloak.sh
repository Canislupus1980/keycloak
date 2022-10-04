#!/bin/bash

# https://github.com/altanovela/keycloak-auth-otp-sms.git

file="/home/cloud-user/.git/keycloak/docker-compose.yml"

sudo docker-compose -f $file logs | tail -1 > sms.log
cat sms.log | col -bp > sms.log.bp
#cat -v sms.log.bp

a=$(grep "Send OTP Code" sms.log.bp | awk '{ print $11 }')
b=$(grep "Send OTP Code" sms.log.bp | awk '{ print $15 }')
c=$(grep "Send OTP Code" sms.log.bp | awk '{ print $3 }' | sed -r 's/,.+//' | cut -c 6-)
cc=$(echo $c | awk -F : '{ a=$1*60+$2 ; if (NF<3) print a ; else print a*60+$3 }')
d=$(date +"%T" | awk -F : '{ a=$1*60+$2 ; if (NF<3) print a ; else print a*60+$3 }')

if (( $d - $cc < 3 )); then
    echo "Time matched. SMS send."
    curl -X POST --location "https://smsgate.exemple.com/send_sms_message_with_token/" -H "Content-Type: application/json" -H "Authorization: key -H "Content-Type: application/x-www-form-urlencoded" -d "phone_numbers=$b&content=$a&client_name=$b&contact_person=$b"
    sudo docker-compose -f $file logs >> log.log
else
    echo "Time did not match."
fi

truncate -s 0 /var/lib/docker/containers/*/*-json.log
