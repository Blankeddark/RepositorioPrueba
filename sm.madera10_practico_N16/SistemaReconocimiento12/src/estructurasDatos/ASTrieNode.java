package estructurasDatos;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ASTrieNode<T> implements  Serializable
{   

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Atributo que modela el car�cter asociado al nodo
	 */
	private char caracter;

	/**
	 * Atributo que modela el elemento asociado al nodo
	 */
	private  T data;

	/**
	 * Atributo que modela el nodo hermano del nodo actual
	 */
	private ASTrieNode<T> nodoHermano;

	/**
	 * Atributo que modela el nodo hijo del nodo actual
	 */
	private ASTrieNode<T> nodoHijo;

	/**
	 * Atributo que modela si el nodo actual es una hoja
	 */
	private boolean hoja;

	/**
	 * Atributo que modela si el nodo actual es palabra
	 */
	private boolean esPalabra;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Constructor del trie. <br>
	 * <b> post: </b> Se construy� el nodo con el caracter especificado, elemento= null, izqNodo= null y hermanoDerNodo= null.
	 * @param carac Caracter que va a contener el nodo
	 */
	public ASTrieNode( char carac )
	{
		caracter = carac;
		nodoHijo = null;
		nodoHermano = null;
		data = null;
		esPalabra = false;
	}

	/**
	 * Constructor del trie. <br>
	 * <b> post: </b> Se construy� el nodo con el caracter y elemento especificados, izqNodo= null y hermanoDerNodo= null.
	 * @param carac Caracter que va a contener el nodo
	 * @param elem Elemento que contiene el nodo
	 */
	public ASTrieNode( char carac, T elem )
	{
		caracter = carac;
		nodoHermano = null;
		nodoHijo = null;
		data = elem;
		esPalabra = false;

	}



	public boolean agregar(String palabra, T info)
	{   
		//  System.out.println("En el nodo.agregar(), la palabra que llega es: " + palabra); //TODO
		boolean retornar = false;

		//Variable auxiliar que modela la longitud de la palabra a agregar
		int longitud = palabra.length();


		T datita = null;

		// Si la palabra solo tiene 1 car�cter y el elemento del hijo izquierdo es igual al elemento a insertar, entonces la palabra es repetida
		if( palabra.length( ) == 1 && nodoHijo != null && nodoHijo.darData() != null && nodoHijo.darData().equals(info) )
		{
			nodoHijo.modificarData(info);
			retornar = true;
			return retornar;
		}

		/**
		 * Caso 1.) Se usa generalmente cuando el �rbol est� vac�o o simplemente cuando se llega a un punto dentro del trie
		 * donde el nodo actual ya no posee un nodoHijo y por tanto es necesario asignarle un nuevo nodoHijo a este, ya sea con
		 * un valor nuevo o simplemente con un car�cter que formar� parte del prefijo de una nueva palabra
		 */

		if(nodoHijo == null)
		{
			ASTrieNode<T> nuevoNodo = null;

			if( longitud == 1 )
			{   
				//Caso de prueba 1.) El elemento T no exist�a entonces simplemente se asocia.
				nuevoNodo = new ASTrieNode<T>( palabra.charAt(0), info );
				nuevoNodo.modificarEsPalabra(true);
				retornar = true;
				// Se agrega un nodo como el hijo izquierdo del nodo actual
				nodoHijo = nuevoNodo;
				return retornar;
			}

			else
			{   
				// Nodo solo con el caracter
				nuevoNodo = new ASTrieNode<T>( palabra.charAt(0) );
				// Se agrega un nodo como el hijo izquierdo del nodo actual
				nodoHijo = nuevoNodo;
				nodoHijo.agregar(palabra.substring(1), info);
			}

		}

		/**
		 * Caso 2.) El nodo en el que nos encontramos actualmente posee un ca�cter que es prefijo de la palabra que entra por par�metro.
		 * 
		 */
		else if( nodoHijo != null && nodoHijo.darLetra() == palabra.charAt(0) )
		{   
			// Si la palabra que se va a agregar posee m�s de un car�cter entonces seguimos avanzando
			// Dentro de los hijos del trie hasta encontrar al hijo que finaliza la rama (es decir la palabra)
			if( palabra.length( ) != 1 )
			{   
				//Llamamos recursivamente el m�todo agregar y le pasamos como par�metro una nueva palabra con su primer
				//Cr�cter removido.
				String palabraReducida = palabra.substring(1);
				nodoHijo.agregar( palabraReducida, info );
			}

			//Cuando se haya llegado al punto de que la palabra reducida solo posee un car�cter entonces eso
			//Significa que ya hemos llegado al final de la rama y se agrega el elemento al nodo dado.

			else	
			{   
				T temp = nodoHijo.darData();

				//Se evalua si el elemento T es de tipo collection con el fin de verificar de qu� forma se va a asociar al nodo
				if(info instanceof Collection)
				{
					((Collection) info).add(temp);
					nodoHijo.modificarData(info);
				}

				else
				{
					nodoHijo.modificarData(info);
				}

				nodoHijo.modificarEsPalabra(true);
				retornar = true;
			}

		}

		/**
		 * Caso 3.) El primer car�cter de la palabra a insertar es menor que el car�cter
		 * Contenido por el nodoHijo del nodo actual.
		 */
		else if( nodoHijo != null &&  palabra.charAt(0) < nodoHijo.darLetra()  )
		{
			ASTrieNode<T> nuevoNodo = null;

			//Si la palabra simplemente tiene un car�cter entonces el nuevoNodo va a tener ese caracter
			//Asociado y la informci�n que entra por par�metro
			if( longitud == 1 )
			{
				nuevoNodo = new ASTrieNode<T>( palabra.charAt(0), info );
				nuevoNodo.modificarEsPalabra(true);
				retornar = true;
				ASTrieNode<T> temp = nodoHijo;
				nodoHijo = nuevoNodo;
				nodoHijo.nodoHermano = temp;

			}

			//En caso contrario simplemente el nuevoNodo solo tendr� el primer car�cter de la palabra como car�cter
			//asociado y se llamar� recursivamente a a agregar pas�ndole como apar�metro la palabra reducida.
			else
			{
				nuevoNodo = new ASTrieNode<T>( palabra.charAt(0) );
				String palabraReducida = palabra.substring(1);
				//Llamado recursivo
				ASTrieNode<T> temp = nodoHijo;
				nodoHijo = nuevoNodo;
				nodoHijo.nodoHermano = temp;
				nuevoNodo.agregar(palabraReducida, info);
			}

			// Modificamos el nodoHijo del nodo actual y el anterior nodoHijo lo 


		}

		/**
		 * Caso 4.) Se debe agregar el elemento sobre uno de los subarboles
		 * del nodoHermano del nodo actual.
		 */
		else if( nodoHijo != null && nodoHijo.darHermano() != null )
		{
			// Se mantiene el anterior por si se reuiere en alg�n momento recurrir al anterior hermano
			ASTrieNode<T> hermanoActual = (ASTrieNode<T>) nodoHijo.darHermano();
			ASTrieNode<T> hermanoAnterior = nodoHijo;

			// Ubicar el lugar donde deba ir el nuevo hermano
			while( hermanoActual != null && palabra.charAt(0) > hermanoActual.darLetra()  )
			{
				hermanoAnterior = hermanoActual;
				hermanoActual = (ASTrieNode<T>) hermanoActual.darHermano();
			}


			// Encontr� el nodo que deba agregar el resto de la palabra
			if( hermanoActual != null && hermanoActual.darLetra() == palabra.charAt(0) )
			{
				if( longitud == 1 )
				{   
					//Obtenemos el valor del elemento asociado con el finde aumentar en caso de que sea necesario el peso del �rbol	
					hermanoActual.modificarEsPalabra(true);
					retornar = true;

					// Se evalua si es una instancia de collection o no
					if(info instanceof Collection)
					{
						((Collection) info).add(hermanoActual.darData());
						hermanoActual.modificarData(info);
					}

					else
					{
						hermanoActual.modificarData(info);
					}

				}

				//Llamado recursivo
				else
				{   
					hermanoActual.agregar( palabra.substring( 1 ), info );
				}

			}

			else
			{
				ASTrieNode<T> nuevoNodo = null;

				if( longitud == 1 )
				{
					nuevoNodo = new ASTrieNode<T>( palabra.charAt(0), info );
					nuevoNodo.modificarEsPalabra(true);
					retornar = true;

				}

				else
				{
					nuevoNodo = new ASTrieNode<T>( palabra.charAt(0) );

					// Se debe insertar un nuevo nodo entre dos hermanos
					// Debe crear un nuevo nodo y colocarlo
					nuevoNodo.agregar( palabra.substring( 1 ), info );
				}

				// Se debe insertar un nuevo nodo entre dos hermanos
				// Debe crear un nuevo nodo y colocarlo
				hermanoAnterior.modificarNodoHermano(nuevoNodo); 
				nuevoNodo.modificarNodoHermano(hermanoActual);

			}

		}

		/**
		 * Caso 5.)  Caso donde tenemos que el nodo actual osee un hijo pero es ehijo no posee hermanos.
		 */
		else if( nodoHijo != null && nodoHijo.darHermano() == null )
		{
			ASTrieNode<T> nuevoNodo = null;

			//Se crea un nuevo hermano que contiene un elemento asociado en caso de que ya nos encontremos en el �ltimo caracter
			if( longitud == 1 )
			{
				nuevoNodo = new ASTrieNode<T>( palabra.charAt(0), info );
				//resultado = true;
				//nodoHijo.modificarNodoHermano(nuevoNodo);
				nuevoNodo.modificarEsPalabra(true);
				retornar = true;
			}

			//En caso contrario solo se crea un nuevo hermano que solo contendr� un car�cter
			else
			{
				nuevoNodo = new ASTrieNode<T>( palabra.charAt(0) );
			}

			nodoHijo.modificarNodoHermano(nuevoNodo);
			
			if(!retornar)
			{
				nodoHijo.darHermano().agregar(palabra.substring(1), info);
			}
		}

		return retornar;
	}

	public T buscar(String palabra) 
	{   
		T retorno = null;

		//Se crea un nodo que representa el nodo actual
		ASTrieNode<T> nodoActual = darHijo();

		while(nodoActual != null && palabra.length() != 0)
		{   
			//Cogemos el primer car�cter de la palabra que entra por par�metro
			char primerCaracter = palabra.charAt( 0 );

			/**
			 * Caso 1.) El nodo en el que nos encontramos actualmente es prefijo 
			 * de la palabra que entra por par�metro.
			 */
			if( nodoActual.darLetra() == primerCaracter )
			{   

				palabra = palabra.substring( 1 );

				/**
				 * Caso 1.1) El nodo en el que nos encontramos actualmente corresponde
				 * a la �ltima letra de la apalabra. Por tanto aqu� se debe agregar el elemento
				 * asociado a este nodo al iterador.
				 */
				if (palabra.length() == 0)
				{
					retorno = nodoActual.darData();
				}

				/**
				 * Caso 1.2) El nodo pertenece al prefijo de la palabra pero a�n as� no es el �ltimo car�cter de la 
				 * palabra, por tanto se prosigue a continuar la palabra, es decir, se continua el recorrido a trav�s de
				 * su hijo.
				 */
				else	
				{
					nodoActual = nodoActual.darHijo();
				}

			}

			/**
			 * Caso 2.) Si el nodo en el que nos encontramos actualmente posee un hermano
			 *  tenemos que verificar que, en el caso dado que el nodo actual no sea prefijo
			 *  de la palabra dada por par�metro, entonces su hermano lo sea.
			 */
			else if( nodoActual.darHermano() != null )
			{
				//Si el nodo actual no es prefijo entonces se pasa a verificar su hermano
				while( nodoActual != null && nodoActual.darLetra() != primerCaracter )
				{
					nodoActual = nodoActual.darHermano();
				}

			}

			/**
			 * E n cualquier otro caso se tiene que finalizar el recorrido.
			 */
			else
			{
				nodoActual = null;
			}

		}
		return retorno;
	}

	public ASIteradorSimple<String> buscarElementosPrefijos(String palabraPrefijo, ASIteradorSimple<String> iteradorBusqueda) 
	{
		//Agarro el nodo que en cuesti�n contiene el prefijo
		ASTrieNode<T> nodoPrefijo = buscarNodoPrefijo(palabraPrefijo);

		if(nodoPrefijo == null)
		{
			return iteradorBusqueda;
		}

		//Si el nodo no solo es prefijo sino que es palabra, lo agregamos.
		else if(nodoPrefijo.darData() != null)
		{
			iteradorBusqueda.add(palabraPrefijo);
		}


		//Hacemos un substring eliminando la �ltima letra de la palabra. Esto para ayudar a la recursi�n del m�todo auxiliar.
		palabraPrefijo = palabraPrefijo.substring(0, palabraPrefijo.length() - 1 );

		//M�todo auxiliar que modifica el iterador recursivamente.
		agregarPalabrasAlIteradorBuscador(iteradorBusqueda, nodoPrefijo, palabraPrefijo);
		return iteradorBusqueda;
	}


	public void agregarPalabrasAlIteradorBuscador(ASIteradorSimple<String> iteradorBusqueda, ASTrieNode<T> nodoPrefijo, String palabraPrefijo)
	{   


		//Agregamos la letra del nodo actual, esto debe ser un imperativo pa
		palabraPrefijo+= nodoPrefijo.darLetra();
		String palabraCopia = palabraPrefijo;
		ASTrieNode<T> nodoActual = null;
		ASTrieNode<T> nodoProvisional = null;

		if(nodoPrefijo.darHijo() != null)
		{
			nodoActual = nodoPrefijo.darHijo();
			palabraPrefijo+= nodoActual.darLetra();
		}


		while(nodoActual != null)
		{   

			if(nodoActual.darData() != null)
			{
				iteradorBusqueda.add(palabraPrefijo);
			}

			if(nodoActual.darHermano() != null)
			{
				nodoProvisional = nodoActual.darHermano();

				while(nodoProvisional != null)
				{
					agregarPalabrasAlIteradorBuscador(iteradorBusqueda, nodoProvisional, palabraCopia);
					nodoProvisional = nodoProvisional.darHermano();
				}

			}

			if(nodoActual.darHijo() != null)
			{
				nodoActual = nodoActual.darHijo();
				palabraPrefijo+= nodoActual.darLetra();

			}

			else
			{
				nodoActual = null;
			}
		}

	}

	public ASTrieNode<T> buscarNodoPrefijo(String palabra) 
	{   
		ASTrieNode<T> retorno = null;

		//Se crea un nodo que representa el nodo actual
		ASTrieNode<T> nodoActual = darHijo();

		while(nodoActual != null && palabra.length() != 0)
		{   
			//Cogemos el primer car�cter de la palabra que entra por par�metro
			char primerCaracter = palabra.charAt( 0 );

			/**
			 * Caso 1.) El nodo en el que nos encontramos actualmente es prefijo 
			 * de la palabra que entra por par�metro.
			 */
			if( nodoActual.darLetra() == primerCaracter )
			{   

				palabra = palabra.substring( 1 );

				/**
				 * Caso 1.1) El nodo en el que nos encontramos actualmente corresponde
				 * a la �ltima letra de la apalabra. Por tanto aqu� se debe agregar el elemento
				 * asociado a este nodo al iterador.
				 */
				if (palabra.length() == 0)
				{
					retorno = nodoActual;
				}

				/**
				 * Caso 1.2) El nodo pertenece al prefijo de la palabra pero a�n as� no es el �ltimo car�cter de la 
				 * palabra, por tanto se prosigue a continuar la palabra, es decir, se continua el recorrido a trav�s de
				 * su hijo.
				 */
				else	
				{
					nodoActual = nodoActual.darHijo();
				}

			}

			/**
			 * Caso 2.) Si el nodo en el que nos encontramos actualmente posee un hermano
			 *  tenemos que verificar que, en el caso dado que el nodo actual no sea prefijo
			 *  de la palabra dada por par�metro, entonces su hermano lo sea.
			 */
			else if( nodoActual.darHermano() != null )
			{
				//Si el nodo actual no es prefijo entonces se pasa a verificar su hermano
				while( nodoActual != null && nodoActual.darLetra() != primerCaracter )
				{
					nodoActual = nodoActual.darHermano();
				}

			}

			/**
			 * E n cualquier otro caso se tiene que finalizar el recorrido.
			 */
			else
			{
				nodoActual = null;
			}

		}

		return retorno;
	}


	public T eliminar(String palabra) 
	{   

		T eliminado = buscar(palabra);


		// Primer car�cter de la palabra
		char primerCaracter = palabra.charAt( 0 );

		if(eliminado == null)
		{
			return null;
		}

		else

		{
			/**
			 * Caso de eliminaci�n 1.) El nodo actual posee un hijo y su hijo es prefijo de la palabra que se busca eliminar.
			 */
			if( nodoHijo != null && nodoHijo.darLetra() == primerCaracter ) 
			{   

				//Si la palabra posee longitud 1 (porque no puede ser 0) entonces sabemos que es momento de eleiminar.
				if(palabra.length() == 1)
				{   

					//El nodoHijo posee elementos asociados.
					if( nodoHijo.darData() != null )
					{
						nodoHijo.modificarData(null);	
					}

				}

				//La palabra poseee m�s caracteres, por tanto es necesario avanzar en la eliminaci�n.
				else
				{   
					//Se intente eliminar recursivamente llamando al hijo del nodo actual y haci�ndolo eliminar, pas�ndole como par�metro una
					//Cadena de car�cteres con un caracter menos que la original, el caracter removido es el que ya fue evaluado.
					nodoHijo.eliminar( palabra.substring( 1 ) );

					//Si al final el nodoHijo es una hoja (fin de palabra) y no posee elementos asociados se pasa a evaluar su hermano.
					if( nodoHijo.darHijo() == null && nodoHijo.darData() == null)
					{
						nodoHijo = (ASTrieNode<T>) nodoHijo.darHermano();
						nodoHijo.eliminar(palabra.substring(1));
					}

				}

			}

			/**
			 * Caso 2.) de eliminaci�n Buscar si la palabra existe en otro nodo a la derecha y eliminarla
			 */
			else if( nodoHijo != null && nodoHijo.darLetra() < primerCaracter && nodoHijo.darHermano() != null ) 
			{
				// Buscar el nodo en el que se debe realizar la eliminaci�n
				ASTrieNode<T> hermanoIzquierdo = nodoHijo;
				ASTrieNode<T> hermano = (ASTrieNode<T>) nodoHijo.darHermano();

				//Mientras ning�n hermano no sea prefijo de la palabra que se entra entonces se sigue uscando
				while( hermano != null && hermano.darLetra() != primerCaracter )
				{
					hermanoIzquierdo = hermano;
					hermano = (ASTrieNode<T>) hermano.darHermano();
				}

				if( hermano != null && palabra.length( ) > 1 )
				{
					hermano.eliminar( palabra.substring( 1 ) );

					if( hermano.darHijo() == null && hermano.darData() == null )
					{   
						hermano = hermano.darHermano();
					}
				}


				else if( hermano != null && hermano.darData() != null  )
				{

					if( hermano.darHijo() == null )
					{   
						hermano = hermano.darHermano();

					}

					hermano.modificarData(null);
				}
			}	
		}

		return eliminado;
	}


	public ASIteradorSimple<String> eliminarElementosConPrefijo(String palabraPrefijo, ASIteradorSimple<String> iteradorEliminacion) 
	{

		ASTrieNode<T> nodoPrefijo = buscarNodoPrefijo(palabraPrefijo);

		if(nodoPrefijo == null)
		{
			return iteradorEliminacion;
		}

		//Si el nodo no solo es prefijo sino que es palabra, lo agregamos.
		else if(nodoPrefijo.darData() != null)
		{
			iteradorEliminacion.add(palabraPrefijo);
			nodoPrefijo.modificarData(null);
		}


		//Hacemos un substring eliminando la �ltima letra de la palabra. Esto para ayudar a la recursi�n del m�todo auxiliar.
		palabraPrefijo = palabraPrefijo.substring(0, palabraPrefijo.length() - 1 );

		//M�todo auxiliar que modifica el iterador recursivamente.
		agregarPalabrasAlIteradorEliminador(iteradorEliminacion, nodoPrefijo, palabraPrefijo);


		return iteradorEliminacion;
	}


	public void agregarPalabrasAlIteradorEliminador(ASIteradorSimple<String> iteradorEliminacion, ASTrieNode<T> nodoPrefijo, String palabraPrefijo)
	{   


		//Agregamos la letra del nodo actual, esto debe ser un imperativo pa
		palabraPrefijo+= nodoPrefijo.darLetra();
		String palabraCopia = palabraPrefijo;
		ASTrieNode<T> nodoActual = null;
		ASTrieNode<T> nodoProvisional = null;

		if(nodoPrefijo.darHijo() != null)
		{
			nodoActual = nodoPrefijo.darHijo();
			palabraPrefijo+= nodoActual.darLetra();
		}


		while(nodoActual != null)
		{   

			if(nodoActual.darData() != null)
			{
				iteradorEliminacion.add(palabraPrefijo);
				nodoActual.modificarData(null);
			}

			if(nodoActual.darHermano() != null)
			{
				nodoProvisional = nodoActual.darHermano();

				while(nodoProvisional != null)
				{
					agregarPalabrasAlIteradorEliminador(iteradorEliminacion, nodoProvisional, palabraCopia);
					nodoProvisional = nodoProvisional.darHermano();
				}

			}

			if(nodoActual.darHijo() != null)
			{
				nodoActual = nodoActual.darHijo();
				palabraPrefijo+= nodoActual.darLetra();
			}

			else
			{
				nodoActual = null;
			}
		}

	}

	public int darNumeroPalabras()
	{  

		int contador = 0;

		ASTrieNode<T> nodoActual = this;

		if(nodoActual.darEsPalabra() == true)
		{
			contador++;
		}

		if(nodoActual.darHermano() != null)
		{
			contador+= nodoActual.darHermano().darNumeroPalabras();
		}

		if(nodoActual.darHijo() != null)
		{
			contador+= nodoActual.darHijo().darNumeroPalabras();
		}

		return contador;
	}

	public void modificarData(T pData)
	{
		data = pData;
	}

	public void modificarLetra(char pLetra)
	{
		caracter = pLetra;
	}

	public void modificarNodoHijo(ASTrieNode<T> nuevo)
	{
		nodoHijo = nuevo;
	}

	public void modificarNodoHermano(ASTrieNode<T> nuevo)
	{
		nodoHermano = nuevo;
	}

	public T darData()
	{
		return data;
	}

	public boolean darFinPalabra() 
	{
		// TODO Auto-generated method stub
		return nodoHijo == null ? true : false;
	}

	public ASTrieNode<T> darHermano() 
	{
		// TODO Auto-generated method stub
		return nodoHermano;
	}

	public ASTrieNode<T> darHijo() 
	{
		// TODO Auto-generated method stub
		return nodoHijo;
	}

	public char darLetra() 
	{

		return caracter;
	}

	public boolean darEsPalabra()
	{
		return esPalabra;
	}

	public void modificarEsPalabra(boolean value)
	{
		esPalabra = value;
	}
}
