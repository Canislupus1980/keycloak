# beCloud  SMS Sender Provider

**verify in Quarkus 21.0.1**

```sh
cp target/providers/keycloak-phone-provider.jar ${KEYCLOAK_HOME}/providers/
cp target/providers/keycloak-phone-provider.resources.jar ${KEYCLOAK_HOME}/providers/
cp target/providers/keycloak-sms-provider-becloud.jar ${KEYCLOAK_HOME}/providers/


${KEYCLOAK_HOME}/bin/kc.sh build

${KEYCLOAK_HOME}/bin/kc.sh start  --spi-phone-message-service-default-service=becloud \
  --spi-message-sender-service-becloud-secret=${secret} \
  --spi-message-sender-service-becloud-app=${AppId} \
  --spi-message-sender-service-becloud-opt-template=${templateId} 
```
```
templateId is: [$realm-]<[$type | $kind]>-<template>

type: 
    VERIFY("verification"),
    AUTH("authentication"),
    OTP("OTP"),
    RESET("reset credential"),
    REGISTRATION("registration");
```
