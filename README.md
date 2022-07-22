# keycloak
proxy-nginx keycloak docker-compose

####Instructions for creating a user "keycloak"#####
    docker exec local_keycloak \
    /opt/jboss/keycloak/bin/add-user-keycloak.sh \
    -u admin \
    -p admin \
&& docker restart local_keycloak
