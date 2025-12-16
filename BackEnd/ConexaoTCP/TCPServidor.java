import java.net.*;
import java.io.*;

public class TCPServidor {

    int por = 5001;
    Thread thr;
    Socket cli;
    BufferedReader ent;
    PrintWriter sai;
    ServerSocket ser;
    volatile boolean rodando = true;

    public void iniSer() {
        try {
            ser = new ServerSocket(por);

            while (rodando) {
                cli = ser.accept();

                ent = new BufferedReader(new InputStreamReader(cli.getInputStream()));
                sai = new PrintWriter(cli.getOutputStream(), true);

                iniThr();

                while (cli.isConnected() && rodando) {
                    Thread.sleep(100);
                }

                fecCon();
            }

        } catch (IOException e) {
            if (rodando) {
                System.err.println("Erro servidor: " + e.getMessage());
            }
        } catch (InterruptedException e) {
            System.err.println("Servidor interrompido");
        }
    }

    private void iniThr() {
        thr = new Thread(() -> {
            try {
                String msg;
                while ((msg = recMsg()) != null && rodando) {
                    System.out.println("Recebido: " + msg);
                }
            } catch (IOException e) {
                if (rodando) System.err.println("Erro thread: " + e.getMessage());
            }
        });
        thr.start();
    }

    public void envMsg(String txt) {
        if (sai != null) sai.println(txt);
    }

    public String recMsg() throws IOException {
        return ent.readLine();
    }

    public void fecCon() throws IOException {
        if (ent != null) ent.close();
        if (sai != null) sai.close();
        if (cli != null) cli.close();
    }

    public void stop() throws IOException {
        rodando = false;
        if (ser != null) ser.close();
    }
}
