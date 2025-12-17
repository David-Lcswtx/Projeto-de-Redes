import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServidor {

    int por = 5001;
    List<ClienteHandler> clientes = Collections.synchronizedList(new ArrayList<>());

    public void iniSer() {
        try (ServerSocket ser = new ServerSocket(por)) {
            while (true) {
                Socket cli = ser.accept();
                ClienteHandler ch = new ClienteHandler(cli);
                clientes.add(ch);
                ch.start();
            }
        } catch (Exception e) {
            System.err.println("Erro servidor: " + e.getMessage());
        }
    }

    private class ClienteHandler extends Thread {
        Socket cli;
        BufferedReader ent;
        PrintWriter sai;

        ClienteHandler(Socket cli) {
            this.cli = cli;
            try {
                ent = new BufferedReader(new InputStreamReader(cli.getInputStream()));
                sai = new PrintWriter(cli.getOutputStream(), true);
            } catch (IOException e) {
                System.err.println("Erro handler: " + e.getMessage());
            }
        }

        public void run() {
            try {
                String msg;
                while ((msg = ent.readLine()) != null) {
                    for (ClienteHandler c : clientes) {
                        c.sai.println(msg);
                    }
                }
                clientes.remove(this);
                ent.close();
                sai.close();
                cli.close();
            } catch (IOException e) {
                System.err.println("Erro thread: " + e.getMessage());
            }
        }
    }
}
