import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        HTTPRequest httpRequest = new HTTPRequest();

        httpRequest.getRequest();
        httpRequest.getToken();
        httpRequest.getYandexId();

    }

}
