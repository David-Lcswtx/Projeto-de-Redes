import java.net.*;
import java.io.*;
import cripto.Criptografia;

public class TCPCliente {
    Socket soc;
    PrintWriter sai;
    BufferedReader ent;

    public void conIP(String ip, int por) {
        try {
            soc = new Socket(ip, por);
            sai = new PrintWriter(soc.getOutputStream(), true);
            ent = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        } catch (IOException e) {
            System.err.println("Erro cliente: " + e.getMessage());
        }
    }

    public void envMsg(String txt) {
        if (sai != null) {
            int i = txt.indexOf(":");
            if (i != -1) {
                String nome = txt.substring(0, i + 1);
                String msg = txt.substring(i + 1).trim();
                String cifrada = Criptografia.criptografar(msg);
                sai.println(nome + " " + cifrada);
            } else {
                sai.println(Criptografia.criptografar(txt));
            }
        }
    }

    public String recMsg() throws IOException {
        return ent.readLine();
    }

    public void fecCon() throws IOException {
        ent.close();
        sai.close();
        soc.close();
    }
}
