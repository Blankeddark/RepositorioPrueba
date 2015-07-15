package diccionario.interfaz;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ThreadMostrarMensaje extends Thread {
	
	String mensaje;
	
	public ThreadMostrarMensaje(String mensaje)
	{
		this.mensaje = mensaje;
	}
	
	public void run()
	{
		JFrame frmOpt = new JFrame();
		frmOpt.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmOpt.setLocation(dim.width/2-frmOpt.getSize().width/2, dim.height/2-frmOpt.getSize().height/2);
		frmOpt.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(frmOpt, mensaje);
		frmOpt.setVisible(false);
	}

}
