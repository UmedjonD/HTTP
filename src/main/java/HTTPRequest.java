import com.sun.xml.internal.messaging.saaj.packaging.mime.Header;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPRequest {
    static String query = "http://www.yandex.ru/maps/";
    static String query2 = "https://yandex.ru/maps/44/izhevsk/?ll=53.211463%2C56.852775&z=13";
    static StringBuilder sb = new StringBuilder();

    public  void  getRequest() {

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("GET");
           // connection.setUseCaches(false); //не кэшируется
            connection.setConnectTimeout(300); //время подключения
            connection.setReadTimeout(300); // время читения от сервера

            connection.connect(); // подкючения

            /*
             *получаем ответ от сервера
             */
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    //sb.append("\n");
                }
                System.out.println(sb.toString());
            } else {
                System.out.println("fail: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }
    /*
    * получаем токен
    * */
    public  void getToken(){
        String token = null;

        Pattern pattern = Pattern.compile("csrfToken\\\":\\\"[^\\\\W]+:[\\\\d]+");
        Matcher matcher = pattern.matcher(sb);

        while (matcher.find()) token = matcher.group().replaceAll("csrfToken","");

        System.out.println(token);
    }
    /*
    *из хэдов получаем значение yandexuid
    * */
    public  void getYandexId(){
        //String yandexuid2 = "8237912921544609681";
        String yandexuid = null;

        Pattern pattern = Pattern.compile("yandexuid=[\\\\d]+");
        Matcher matcher = pattern.matcher(sb);

        while (matcher.find()) yandexuid = matcher.group();

        System.out.println(yandexuid);
    }

//    public void getRequestTwo(String token, String yandexuid) {
//        String string = null;
//        HttpURLConnection connection = null;
//        try {
//            connection = (HttpURLConnection) new URL(query2.concat(URLEncoder.encode("Ижевск, Майская, 23",
//                    "UTF-8").concat(" " + token))).openConnection();
//            connection.setRequestProperty(" ",yandexuid);
//            connection.connect();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
