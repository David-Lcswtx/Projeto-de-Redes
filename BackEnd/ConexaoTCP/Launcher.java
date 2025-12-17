import javax.swing.JOptionPane;

public class Launcher {
    public static void main(String[] args) {
        // Pergunta a porta ao usuário
        String portaStr = JOptionPane.showInputDialog("Digite a porta do servidor:");
        int porta = Integer.parseInt(portaStr);

        // Cria servidor com a porta escolhida
        TCPServidor ser = new TCPServidor(porta);

        // Inicia o servidor em uma thread separada
        Thread servidorThread = new Thread(() -> ser.iniSer());
        servidorThread.start();

        // Pergunta se deseja encerrar
        int opc = JOptionPane.showConfirmDialog(
                null,
                "Deseja parar o servidor?",
                "Encerrar Servidor",
                JOptionPane.YES_NO_OPTION
        );

        if (opc == JOptionPane.YES_OPTION) {
            ser.stopServer();
            System.exit(0);
        }
    }
}
