# keycloak
proxy-nginx keycloak docker-compose DigiCert

####Instructions for creating a user "keycloak"#####
    
    
    docker exec local_keycloak \
    /opt/jboss/keycloak/bin/add-user-keycloak.sh \
    -u admin \
    -p admin \
    && docker restart local_keycloak


####start scripts#####
sudo watch --interval=1 /home/cloud-user/.git/keycloak-auth-otp-sms/sms.sh
