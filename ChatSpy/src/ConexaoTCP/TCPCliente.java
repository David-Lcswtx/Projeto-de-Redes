import java.net.*;
import java.io.*;
import cripto.Criptografia;

public class TCPCliente {
    Socket soc;
    PrintWriter sai;
    BufferedReader ent;
    Thread thr;

    public void conIP(String ip, int por) {
        try {
            soc = new Socket(ip, por);
            sai = new PrintWriter(soc.getOutputStream(), true);
            ent = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            iniThr();
        } catch (IOException e) {
            System.err.println("Erro cliente: " + e.getMessage());
        }
    }

    public void envMsg(String txt) {
        if (sai != null) {
            String criptografado = Criptografia.criptografar(txt);
            sai.println(criptografado);
        } else {
            System.err.println("Cliente não conectado.");
        }
    }

    public String recMsg() throws IOException {
        String recebido = ent.readLine();
        return recebido != null ? Criptografia.descriptografar(recebido) : null;
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
