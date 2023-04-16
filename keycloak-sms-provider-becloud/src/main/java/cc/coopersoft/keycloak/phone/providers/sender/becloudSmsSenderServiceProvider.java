package cc.coopersoft.keycloak.phone.providers.sender;

import cc.coopersoft.keycloak.phone.providers.constants.TokenCodeType;
import cc.coopersoft.keycloak.phone.providers.exception.MessageSendException;
import cc.coopersoft.keycloak.phone.providers.spi.MessageSenderService;
import cc.coopersoft.common.OptionalUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.keycloak.Config;
import org.keycloak.models.RealmModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class becloudSmsSenderServiceProvider implements MessageSenderService {

  private final Config.Scope config;
  private final RealmModel realm;

  
  private static final String
          SERVER_URL="https://smsgate.domen/send_sms_message_with_token/";


  public becloudSmsSenderServiceProvider(Config.Scope config, RealmModel realm) {
    this.config = config;
    this.realm = realm;
  }

  @Override
  public void sendSmsMessage(TokenCodeType type, String phoneNumber, String code, int expires, String kind) throws MessageSendException {

    HttpPost httpPost = new HttpPost(SERVER_URL);
    String curTime = String.valueOf((new Date()).getTime() / 1000L);
    
    String checkSum = CheckSumBuilder.getCheckSum(config.get("secret"), code, curTime);

    
    List<NameValuePair> nvps = new ArrayList<>();
    
    String kindName = OptionalUtils.ofBlank(kind).orElse(type.name().toLowerCase());
    String templateId = Optional.ofNullable(config.get(realm.getName().toLowerCase() + "-" + kindName + "-template"))
        .orElse(config.get(kindName + "-template"));
    nvps.add(new BasicNameValuePair("phone_numbers", phoneNumber));
    nvps.add(new BasicNameValuePair("content", code));
    nvps.add(new BasicNameValuePair("client_name", phoneNumber));
    nvps.add(new BasicNameValuePair("contact_person", phoneNumber));


    // execute request
    try {
      /*
       * 1. Print the execution result, the print result will generally be 200, 315, 403, 404, 413, 414, 500
       * 2. If there is a problem with the specific code, please refer to the Code status table on the official website
       */
      httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
      HttpResponse response = HttpClientBuilder.create().setDefaultHeaders(List.of(
              new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
              new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded"),
              new BasicHeader(HttpHeaders.AUTHORIZATION, "key")))
              .build().execute(httpPost);
      System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void close() {

  }
}
