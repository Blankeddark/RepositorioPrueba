package diccionario.mundo;

import java.io.BufferedReader;

import estructurasDatos.ASAVLTree;
import estructurasDatos.ASList;

public interface IAdministradorANS {

	//------------------------------------------------------------------------------------------------------------
	//M�todos
	//------------------------------------------------------------------------------------------------------------

//	/**
//	 * A�ade un nuevo diccionario con el nombre ingresado por par�metro.
//	 * @param nombre nombre del nuevo diccionario
//	 * @throws Exception si hay un problema con la informaci�n.
//	 * @return true si el diccionario pudo ser agregado, false de lo contario.
//	 */
//	public boolean a�adirDiccionario(String nombre) throws Exception;

	/**
	 * @return la cantidad de diccionarios guardados actualmente.
	 */
	public int darCantidadDiccionarios();

	/**
	 * Cambia el diccionario seleccionado.
	 * @param n nuevo diccionario seleccionadio.
	 */
	public void cambiarDiccionarioActual(Diccionario n);

	/**
	 * Cambia el documento actual por el pasado por par�metro.
	 * @param d nuevo documento actual
	 */
	public void cambiarDocumentoActual(Documento d);

	/**
	 * A�ade un nuevo documento y cambia el documento actual a este.
	 * @param nombre nombre del nuevo documento. Si no se especifica uno, 
	 * 			es decir, se pasa "" como par�metro, se le asignar� uno
	 * 			autom�ticamente.
	 * @param texto texto del nuevo documento.
	 * @throws Exception si el documento a agregar ya existe o no tiene un texto v�lido
	 * 					para su reconocimiento.
	 */
	public void a�adirDocumento(String nombre, String texto) throws Exception;

	/**
	 * @return total de palabras guardadas en todos los diccionarios.
	 */
	public int darTotalPalabras();

	/**
	 * Reconoce el texto en el documento actual con la informaci�n en el diccionario actual.
	 * @return el texto reconocido del documento actual.
	 * @throws Exception si hay un problema reconociendo el texto, o no hay un documento o un
	 * 				diccionario actual seleccionado.
	 */
	public String reconocerTexto() throws Exception;

	/**
	 * Se a�ade el documento con la URL ingresada por par�metro. De ah� se obtiene su nombre
	 * y su texto.
	 * @param URL en el PC local del documento a agregar.
	 * @param br BufferedReader hacia el archivo.
	 * @throws Exception si hay un problema encontrando al documento o agreg�ndolo.
	 */
	public boolean a�adirDocumentoConURL(String URL, BufferedReader br) throws Exception;
	
	/**
	 * Se busca un documento seg�n lo ingresado por par�metro.
	 * @param tipoB�squeda Si ser� con un conjunto de palabras o prefijos. 
	 * 			tipoB�squeda.equals(AdministradorANS.B�SQUEDA_PALABRA) ||
	 * 			tipoB�squeda.equals(AdministradorANS.B�SQUEDA_PREFIJO)
	 * @param palabrasOPrefijos El conjunto de palabras o prefijos con los que se realizar� la b�squeda.
	 * @param tipoCriterio tipo del criterio de b�squeda, es decir, si se requiere que todas las palabras/prefijos
	 * 				est�n en el documento al menos una vez o s�lo una de ellas.
	 * 
	 * 				tipoCriterio.equals(CRITERIO_CON_TODAS_PALABRAS) || 
	 * 				tipoCriterio.equals(CRITERIO_CON_UNA_DE_LAS_PALABRAS)
	 * 
	 * @return el conjunto de documentos que cumplen con los criterios. Si no hay, retorna un conjunto vac�o.
	 * @throws Exception si hay un problema con el tipoB�squeda, el tipoCriterio, o el conjunto de palabras/prefijos est� vac�o.
	 */
	public ASAVLTree<Documento> buscarDocumentos(String tipoB�squeda, ASList<String> palabrasOPrefijos, String tipoCriterio) throws Exception;

}
