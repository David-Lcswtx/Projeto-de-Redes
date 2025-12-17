import javax.swing.JOptionPane;

public class Launcher {
    public static void main(String[] args) {
        TCPServidor ser = new TCPServidor();

        Thread servidorThread = new Thread(() -> ser.iniSer());
        servidorThread.start();

        int opc = JOptionPane.showConfirmDialog(null,
                "Deseja parar o servidor?",
                "Encerrar Servidor",
                JOptionPane.YES_NO_OPTION);

        if (opc == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
