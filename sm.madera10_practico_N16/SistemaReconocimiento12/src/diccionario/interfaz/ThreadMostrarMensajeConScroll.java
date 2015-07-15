
package diccionario.interfaz;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ThreadMostrarMensajeConScroll extends Thread {
	
	String mensaje;
	String título;
	
	public ThreadMostrarMensajeConScroll(String mensaje, String título)
	{
		this.mensaje = mensaje;
		this.título = título;
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
		                              título,
		                              JOptionPane.WARNING_MESSAGE);
	}

}
