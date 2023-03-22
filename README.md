![keycloak-image](https://repository-images.githubusercontent.com/11125589/bd31cf00-70f4-11e9-9fb2-4f241568e586)
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

### Running the sms-gate script
```sh
sudo watch --interval=1 /home/cloud-user/.git/keycloak-auth-otp-sms/sms.sh
```
Refrence & Links
https://github.com/cooperlyt/keycloak-phone-provider
