import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.ContentHandler;
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
            connection.setConnectTimeout(300); //время подключения
            connection.setReadTimeout(300); // время читения от сервера
            connection.getRequestMethod();
            connection.setRequestProperty("Cookie", "src\\main\\java\\header.txt");
            //connection.setRequestProperty("Cookie", "maps_los=1; _ym_wasSynced=%7B%22time%22%3A1544611767553%2C%22params%22%3A%7B%22eu%22%3A0%7D%2C%22bkParams%22%3A%7B%7D%7D; _ym_uid=1544611768534771151; _ym_d=1544611768; mda=0; yandexuid=4943878111544611767; i=1+OjfGQKgMk3/lixyokDcuq774J/5uRP4ts/3igr8zcSr/h+Xwa1nH9KkkTIMJfNuV9tqhcPbsVhHn5M4gnRTjpcAPM=; my=YwA=; zm=m-white_404.webp.css%3Awww_oQ9UbzpJ2l8mzLtS-dvp2hjgWvg%3Al; spravka=dD0xNTQ0NjExODc5O2k9NzkuMTMzLjc1LjU4O3U9MTU0NDYxMTg3OTQ3MTI1NjY4MTtoPWI1NTRjZDUyYzY2MjYyYzEwZWQwODVhODNhYWQzMjJm; _ym_isad=1; yp=1859971767.yrts.1544611767#1859971767.yrtsi.1544611767#1560454702.szm.1:1920x1080:356x799");

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

        Pattern pattern = Pattern.compile("coordinates=[\\d]+");
        Matcher matcher = pattern.matcher(sb);
        while (matcher.find()) yandexuid = matcher.group();
        System.out.println(coordinates);

    }
}
