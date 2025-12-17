import java.net.*;
import java.io.*;

public class TCPPeer {
    private int porta;
    private ServerSocket server;
    private Socket peer;
    private BufferedReader ent;
    private PrintWriter sai;

    public TCPPeer(int porta) {
        this.porta = porta;
    }

    public void iniciarListener() {
        new Thread(() -> {
            try {
                server = new ServerSocket(porta);
                peer = server.accept();
                ent = new BufferedReader(new InputStreamReader(peer.getInputStream()));
                sai = new PrintWriter(peer.getOutputStream(), true);
            } catch (IOException e) {
                System.err.println("Erro listener: " + e.getMessage());
            }
        }).start();
    }

    public void conectar(String ip, int porta) {
        try {
            if (peer == null || peer.isClosed()) {
                peer = new Socket(ip, porta);
                ent = new BufferedReader(new InputStreamReader(peer.getInputStream()));
                sai = new PrintWriter(peer.getOutputStream(), true);
            }
        } catch (IOException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
        }
    }

    public String receber() throws IOException {
        if (ent != null) {
            return ent.readLine();
        }
        return null;
    }

    public void enviar(String msg) {
        if (sai != null) {
            sai.println(msg);
        }
    }

    public void fechar() {
        try {
            if (ent != null) ent.close();
            if (sai != null) sai.close();
            if (peer != null && !peer.isClosed()) peer.close();
            if (server != null && !server.isClosed()) server.close();
        } catch (IOException e) {}
    }
}
