package cc.coopersoft.keycloak.phone.providers.sender;

import cc.coopersoft.keycloak.phone.providers.constants.TokenCodeType;
import cc.coopersoft.keycloak.phone.providers.exception.MessageSendException;
import cc.coopersoft.keycloak.phone.providers.spi.MessageSenderService;
import cc.coopersoft.common.OptionalStringUtils;
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

  //The request path URL for sending the verification code
  private static final String
          SERVER_URL="https://smsgate.becloud.by/send_sms_message_with_token/";


//  Verification code length, ranging from 4 to 10, the default is 4
//  private static final String CODELEN="6";

  public becloudSmsSenderServiceProvider(Config.Scope config, RealmModel realm) {
    this.config = config;
    this.realm = realm;
  }

  @Override
  public void sendSmsMessage(TokenCodeType type, String phoneNumber, String code, int expires, String kind) throws MessageSendException {

    HttpPost httpPost = new HttpPost(SERVER_URL);
    String curTime = String.valueOf((new Date()).getTime() / 1000L);
    /*
     * Refer to the java code for calculating CheckSum. 
     * In the parameter list of the above document, 
     * there is an example of the calculation document of CheckSum
     */
    String checkSum = CheckSumBuilder.getCheckSum(config.get("secret"), code, curTime);

    // Set the parameters of the request, the requestBody parameter
    List<NameValuePair> nvps = new ArrayList<>();
    /*
     * 1. If it is a template SMS, please note that the parameter mobile has s. 
     * For detailed parameter configuration, please refer to "Send Template SMS Document"
     * 2. The parameter format is the format of jsonArray, such as "['+375298888888','+375446666666']"
     * 3. Params is based on how many parameters there are in your template, 
     * and the parameters in it are also in jsonArray format
     */
    String kindName = OptionalStringUtils.ofBlank(kind).orElse(type.name().toLowerCase());
    String templateId = Optional.ofNullable(config.get(realm.getName().toLowerCase() + "-" + kindName + "-template"))
        .orElse(config.get(kindName + "-template"));
    nvps.add(new BasicNameValuePair("phone_numbers", phoneNumber));
    nvps.add(new BasicNameValuePair("content",code));
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
              new BasicHeader(HttpHeaders.AUTHORIZATION, "key ")))
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
