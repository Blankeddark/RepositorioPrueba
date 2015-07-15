package diccionario.mundo;

import java.io.BufferedReader;

import estructurasDatos.ASAVLTree;
import estructurasDatos.ASList;

public interface IAdministradorANS {

	//------------------------------------------------------------------------------------------------------------
	//Métodos
	//------------------------------------------------------------------------------------------------------------

//	/**
//	 * Añade un nuevo diccionario con el nombre ingresado por parámetro.
//	 * @param nombre nombre del nuevo diccionario
//	 * @throws Exception si hay un problema con la información.
//	 * @return true si el diccionario pudo ser agregado, false de lo contario.
//	 */
//	public boolean añadirDiccionario(String nombre) throws Exception;

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
	 * Cambia el documento actual por el pasado por parámetro.
	 * @param d nuevo documento actual
	 */
	public void cambiarDocumentoActual(Documento d);

	/**
	 * Añade un nuevo documento y cambia el documento actual a este.
	 * @param nombre nombre del nuevo documento. Si no se especifica uno, 
	 * 			es decir, se pasa "" como parámetro, se le asignará uno
	 * 			automáticamente.
	 * @param texto texto del nuevo documento.
	 * @throws Exception si el documento a agregar ya existe o no tiene un texto válido
	 * 					para su reconocimiento.
	 */
	public void añadirDocumento(String nombre, String texto) throws Exception;

	/**
	 * @return total de palabras guardadas en todos los diccionarios.
	 */
	public int darTotalPalabras();

	/**
	 * Reconoce el texto en el documento actual con la información en el diccionario actual.
	 * @return el texto reconocido del documento actual.
	 * @throws Exception si hay un problema reconociendo el texto, o no hay un documento o un
	 * 				diccionario actual seleccionado.
	 */
	public String reconocerTexto() throws Exception;

	/**
	 * Se añade el documento con la URL ingresada por parámetro. De ahí se obtiene su nombre
	 * y su texto.
	 * @param URL en el PC local del documento a agregar.
	 * @param br BufferedReader hacia el archivo.
	 * @throws Exception si hay un problema encontrando al documento o agregándolo.
	 */
	public boolean añadirDocumentoConURL(String URL, BufferedReader br) throws Exception;
	
	/**
	 * Se busca un documento según lo ingresado por parámetro.
	 * @param tipoBúsqueda Si será con un conjunto de palabras o prefijos. 
	 * 			tipoBúsqueda.equals(AdministradorANS.BÚSQUEDA_PALABRA) ||
	 * 			tipoBúsqueda.equals(AdministradorANS.BÚSQUEDA_PREFIJO)
	 * @param palabrasOPrefijos El conjunto de palabras o prefijos con los que se realizará la búsqueda.
	 * @param tipoCriterio tipo del criterio de búsqueda, es decir, si se requiere que todas las palabras/prefijos
	 * 				estén en el documento al menos una vez o sólo una de ellas.
	 * 
	 * 				tipoCriterio.equals(CRITERIO_CON_TODAS_PALABRAS) || 
	 * 				tipoCriterio.equals(CRITERIO_CON_UNA_DE_LAS_PALABRAS)
	 * 
	 * @return el conjunto de documentos que cumplen con los criterios. Si no hay, retorna un conjunto vacío.
	 * @throws Exception si hay un problema con el tipoBúsqueda, el tipoCriterio, o el conjunto de palabras/prefijos está vacío.
	 */
	public ASAVLTree<Documento> buscarDocumentos(String tipoBúsqueda, ASList<String> palabrasOPrefijos, String tipoCriterio) throws Exception;

}
