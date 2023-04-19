![keycloak-image](https://repository-images.githubusercontent.com/11125589/bd31cf00-70f4-11e9-9fb2-4f241568e586)
# Keycloak
## Everyone yml-files uses issued certificates

![nginx-proxy](https://img.shields.io/badge/Nginx-proxy-blue)
![keycloak-16.1.1](https://img.shields.io/badge/Keycloak-16.1.1-brightgreen)
![keycloak-21.0.2](https://img.shields.io/badge/Keycloak-21.0.2-orange)
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
## To install the SMS Authenticator one has to:

Build and package the project:

```bash
mvn -e clean install
```
## Add the jar to the Keycloak server:

```bash
docker cp target/providers/keycloak-phone-provider.jar keycloak:/opt/bitnami/keycloak/providers/
docker cp target/providers/keycloak-phone-provider.resources.jar keycloak:/opt/bitnami/keycloak/providers/
docker cp target/providers/keycloak-sms-provider-becloud.jar keycloak:/opt/bitnami/keycloak/providers/
docker-compose -f ./docker-compose.yml restart
```

### Log in via SMS

![image](https://user-images.githubusercontent.com/86954730/232290670-5d9e47f6-f272-4bae-8432-b9c40e108218.png)

![image](https://user-images.githubusercontent.com/86954730/232290780-c92106ab-25fc-4531-9d41-6cf0457ce853.png)

![image](https://user-images.githubusercontent.com/86954730/232997608-6493c2b9-99b2-4b9f-accb-6fc605cf7ca0.png)

![image](https://user-images.githubusercontent.com/86954730/232291327-24332cc1-81f4-4719-ad8c-c0e30390c279.png)

![image](https://user-images.githubusercontent.com/86954730/232290877-13880487-c955-4b5c-bc60-f3e2ba7a394f.png)

![image](https://user-images.githubusercontent.com/86954730/232290972-ed5450a9-7591-44f7-893b-826f5a68c47d.png)

![image](https://user-images.githubusercontent.com/86954730/232291005-36f88479-c946-4e38-a5d4-43f2198c4882.png)

### Running the sms-gate script
```sh
sudo watch --interval=1 /home/cloud-user/.git/keycloak-auth-otp-sms/sms.sh
```
Refrence & Links
https://github.com/cooperlyt/keycloak-phone-provider
