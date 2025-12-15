import java.net.*;
import java.io.*;

public class TCPCliente {

    String hos = "localhost";
    int por = 5001;
    Socket soc;
    PrintWriter sai;
    BufferedReader ent;
    Thread thr;

    public void conIP(String ip, int por) {
        try {
            soc = new Socket(ip, por);

            sai = new PrintWriter(
                soc.getOutputStream(), true
            );

            ent = new BufferedReader(
                new InputStreamReader(soc.getInputStream())
            );

            iniThr();

        } catch (IOException e) {
            System.err.println("Erro cliente: " + e.getMessage());
        }
    }

    public void envMsg(String txt) {
        sai.println(txt);
    }

    public String recMsg() throws IOException {
        return ent.readLine();
    }

    private void iniThr() {
        thr = new Thread(() -> {
            try {
                String msg;
                while ((msg = recMsg()) != null) {
                    System.out.println("Recebido: " + msg);
                }
            } catch (IOException e) {
                System.err.println("Erro thread: " + e.getMessage());
            }
        });
        thr.start();
    }

    public void fecCon() throws IOException {
        ent.close();
        sai.close();
        soc.close();
    }
}
