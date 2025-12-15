import java.net.*;
import java.io.*;

public class TCPServidor {

    int por = 5001;
    Thread thr;
    Socket cli;
    BufferedReader ent;
    PrintWriter sai;

    public void iniSer() {
        try (ServerSocket ser = new ServerSocket(por)) {

            cli = ser.accept();

            ent = new BufferedReader(
                new InputStreamReader(cli.getInputStream())
            );

            sai = new PrintWriter(
                cli.getOutputStream(), true
            );

            iniThr();

            while (cli.isConnected()) {
                Thread.sleep(100);
            }

            fecCon();

        } catch (Exception e) {
            System.err.println("Erro servidor: " + e.getMessage());
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
        cli.close();
    }
}
