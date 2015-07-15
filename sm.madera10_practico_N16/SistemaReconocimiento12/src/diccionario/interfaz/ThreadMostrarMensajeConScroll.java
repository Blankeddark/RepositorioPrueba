
package diccionario.interfaz;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ThreadMostrarMensajeConScroll extends Thread {
	
	String mensaje;
	String t�tulo;
	
	public ThreadMostrarMensajeConScroll(String mensaje, String t�tulo)
	{
		this.mensaje = mensaje;
		this.t�tulo = t�tulo;
	}
	
	public void run()
	{
		JFrame frmOpt = new JFrame();
		frmOpt.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmOpt.setLocation(dim.width/2-frmOpt.getSize().width/2, dim.height/2-frmOpt.getSize().height/2);
		frmOpt.setAlwaysOnTop(true);
		JTextArea textArea = new JTextArea (mensaje);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JOptionPane.showMessageDialog(frmOpt,
		                              scrollPane,
		                              t�tulo,
		                              JOptionPane.WARNING_MESSAGE);
	}

}
