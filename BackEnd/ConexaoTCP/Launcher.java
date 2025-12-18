import javax.swing.JOptionPane;

public class Launcher {
    public static void main(String[] args) {
        
        String portaStr = JOptionPane.showInputDialog("Digite a porta do servidor:");
        int porta = Integer.parseInt(portaStr);

        TCPServidor ser = new TCPServidor(porta);

        Thread servidorThread = new Thread(() -> ser.iniSer());
        servidorThread.start();

        int opc = JOptionPane.showConfirmDialog(null,
                "Deseja parar o servidor?",
                "Encerrar Servidor",
                JOptionPane.YES_NO_OPTION);

        if (opc == JOptionPane.YES_OPTION) {
            ser.stopServer();
            System.exit(0);
        }
    }
}
