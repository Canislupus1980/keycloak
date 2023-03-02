![keycloak-image](https://www.google.com/imgres?imgurl=https%3A%2F%2Fcurity.io%2Fimages%2Fresources%2Ftutorials%2Fmigrations%2Ftutorials-keycloak.png&imgrefurl=https%3A%2F%2Fcurity.io%2Fresources%2Flearn%2Fmigrating-from-keycloak%2F&tbnid=VNLLKtG1wqCN7M&vet=12ahUKEwjPoqWylL79AhURxQIHHVpJA7sQMygOegUIARDXAQ..i&docid=AzYvFIIrdLfbvM&w=1202&h=514&q=keycloak&ved=2ahUKEwjPoqWylL79AhURxQIHHVpJA7sQMygOegUIARDXAQ)
# Keycloak
## Everyone yml-files uses issued certificates

![nginx-proxy](https://img.shields.io/badge/Nginx-proxy-blue)
![keycloak-16.1.1](https://img.shields.io/badge/Keycloak-16.1.1-brightgreen)
![keycloak-20.0.5](https://img.shields.io/badge/Keycloak-20.0.5-orange)
![Docker-compose](https://img.shields.io/badge/Docker-compose-lightgrey)
![Docker-compose](https://img.shields.io/badge/Digi-cert-yellowgreen)

```yml
docker network create --driver bridge --subnet=172.16.50.0/24 sso-network
```
### Instructions for creating a user "keycloak"
    
```yml    
    docker exec local_keycloak \
    /opt/jboss/keycloak/bin/add-user-keycloak.sh \
    -u admin \
    -p admin \
    && docker restart local_keycloak
```

### start script
```bash
sudo watch --interval=1 /home/cloud-user/.git/keycloak-auth-otp-sms/sms.sh
```
