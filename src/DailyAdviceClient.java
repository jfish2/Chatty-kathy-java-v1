import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DailyAdviceClient {

    public void go() {
        try {
            Socket socket = new Socket("127.0.0.1", 4242);
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);

            String advice = reader.readLine();
            System.out.println("Today you should: " + advice);

            reader.close();
        }catch(IOException io) {
            io.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DailyAdviceClient dac = new DailyAdviceClient();
        dac.go();
    }
}
