import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServidor {
    private int porta;
    private ServerSocket servidor;
    private List<ClienteHandler> clientes = Collections.synchronizedList(new ArrayList<>());

    public TCPServidor(int porta) {
        this.porta = porta;
    }

    public void iniSer() {
        try {
            servidor = new ServerSocket(porta);
            while (!servidor.isClosed()) {
                Socket cli = servidor.accept();
                ClienteHandler ch = new ClienteHandler(cli);
                clientes.add(ch);
                ch.start();
            }
        } catch (Exception e) {
            System.err.println("Erro servidor: " + e.getMessage());
        }
    }

    public void stopServer() {
        try {
            if (servidor != null && !servidor.isClosed()) {
                servidor.close();
            }
            synchronized (clientes) {
                for (ClienteHandler c : clientes) {
                    c.close();
                }
                clientes.clear();
            }
        } catch (IOException e) {
            System.err.println("Erro ao encerrar servidor: " + e.getMessage());
        }
    }

    private class ClienteHandler extends Thread {
        private Socket cli;
        private BufferedReader ent;
        private PrintWriter sai;

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
                    synchronized (clientes) {
                        for (ClienteHandler c : clientes) {
                            if (c != this) {
                                c.sai.println(msg);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro thread: " + e.getMessage());
            } finally {
                clientes.remove(this);
                close();
            }
        }

        public void close() {
            try {
                if (ent != null) ent.close();
                if (sai != null) sai.close();
                if (cli != null && !cli.isClosed()) cli.close();
            } catch (IOException e) {}
        }
    }
}
