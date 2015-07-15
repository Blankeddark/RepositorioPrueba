package diccionario.mundo;

import java.io.Serializable;

import estructurasDatos.ASAVLTree;

public class Carpeta implements Comparable<Carpeta>, Serializable {

	private ASAVLTree<Documento> documentos;
	private String nombre;
	
	public Carpeta(String nombre)
	{
		this.nombre = nombre;
		documentos = new ASAVLTree<Documento>();
	}
	
	public int compareTo(Carpeta otraCarpeta)
	{
		return nombre.compareTo(otraCarpeta.nombre);
	}

	/**
	 * @return the documentos
	 */
	public ASAVLTree<Documento> darDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos the documentos to set
	 */
	public void setDocumentos(ASAVLTree<Documento> documentos) {
		this.documentos = documentos;
	}

	/**
	 * @return the nombre
	 */
	public String darNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean añadirDocumento(Documento doc)
	{
		try 
		{
			return documentos.add(doc);
		} 
		
		catch (Exception e) 
		{
			return false;
		}
	}
	
}
