import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPRequest {
    static String query = "https://yandex.ru/maps";
    static String query2 = "https://yandex.ru/maps/44/izhevsk/?ll=53.219942%2C56.869135&mode=whatshere&whatshere%5Bpoint%5D=53.220318%2C56.870090&whatshere%5Bzoom%5D=17&z=17";
    static StringBuilder sb = new StringBuilder();
    static StringBuilder sb2 = new StringBuilder();
    static String yandexuid = null;
    static String token = null;
    static String coordinates = null;


    public  void  getRequest() {

        HttpURLConnection connection = null;


        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("GET");
           // connection.setUseCaches(false); //не кэшируется
            connection.setConnectTimeout(200); //время подключения
            connection.setReadTimeout(200); // время читения от сервера
            connection.getRequestMethod();
            connection.setRequestProperty("Cookie", "src\\main\\java\\header.txt");

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
    public  String getToken(){

        Pattern pattern = Pattern.compile("csrfToken\\\":\\\"[^\\\"]++");
        Matcher matcher = pattern.matcher(sb);
        while (matcher.find()) token = matcher.group();
        return  token;
    }
    /*
    *из хэдов получаем значение yandexuid
    * */
    public  String getYandexId(){

        Pattern pattern = Pattern.compile("yandexuid=[\\d]+");
        Matcher matcher = pattern.matcher(sb);
        while (matcher.find()) yandexuid = matcher.group();
        return yandexuid;
    }

    public void getRequestTwo(String token, String yandexuid) {
        String string = null;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(query2).openConnection();
            connection.setRequestProperty("Cookie", token);
            connection.setRequestProperty("Cookie",yandexuid);
            connection.setRequestProperty("Cookie", "src\\main\\java\\header2.txt");
            connection.connect();


            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    sb2.append(line);
                }
                System.out.println(sb2.toString());
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
    * доделать
    * */
    public void gerCoordinates(){

        Pattern pattern = Pattern.compile("coordinates\\\":.*?(?=,\\\")");
        Matcher matcher = pattern.matcher(sb2);
        while (matcher.find()) coordinates = matcher.group();
        System.out.println(coordinates);

    }
}
