package estructurasDatos;
import java.io.Serializable;

/**
 * Clase principal que representa un �rbol AVL no recursivo
 */
public class ASAVLTree < T extends Comparable<T> > implements  IASTree<T>, Iterable<T>, Serializable
{

	/**
	 * Atributo que modela la raiz del �rbol
	 */
	private ASTreeNode<T> raiz = null;

	/**
	 * Atributo que modela el peso del �rbol
	 */
	private int peso = 0;

	private int contadorPadreX = 0;

	private int maximosPadresVisitar = 0;

	/**
	 * Clase enumerada que define el tipo del nodo a trav�s de la palabra reservada enum que 
	 * nos permite conocer un conjunto finito de elementos. En este caso la izquierda o la 
	 * derecha que son los tipos que puede ser el nodo
	 */
	private enum tipoNodo
	{
		IZQUIERDA, DERECHA
	}
	
	public boolean add(T data) throws Exception
	{   

		//Caso de excepci�n, el nodo a agregar tiene un valor nulo.
		if (data == null) 
		{
			throw new Exception ("No se pueden agregar objetos al �rbol cuyo valor sea null");
		}

		//Caso 1.) El �rbol est� vac�o, en este caso la raiz ser� el nodo a agregar.
		else if ( isEmpty() ) 
		{
			//La raiz del �rbol ahora ser� el nodo que quer�amos agregar
			raiz = new ASTreeNode<T>(data);

			//Se actualiza el peso agreg�ndole una 
			peso++;
			System.out.println(peso);

			//Se rebalancea el nuevo �rbol y se actualizan sus datos
			actualizarFactorBalanceoYAltura(raiz);
			rebalancear(raiz, null, null);

			return true;
		} 

		//Caso 2.) El �rbol no est� vac�o
		else 
		{  
			//Se calculan la mayor cantidad de padres que es posible visitar
			maximosPadresVisitar = darMaximo();

			/**
			 * Creamos arreglos que nos permitir�n monitorear la direcci�n y los nodos
			 * que hemos recorrido para encontrar la posici�n a agregar
			 */
			@SuppressWarnings("unchecked")
			ASTreeNode<T>[] padresVisitar = new ASTreeNode[maximosPadresVisitar];
			tipoNodo[] tiposHijo = new tipoNodo[maximosPadresVisitar];

			int contadorPadre = 0;
			ASTreeNode<T> nodoActual = raiz;

			// Busca la posici�n para a�adirla al arreglo
			while (nodoActual != null) 
			{   

				int comp = data.compareTo(nodoActual.darData());

				/**
				 * Se compara la informaci�n del nodo a agregar con la del actual
				 * En caso de que sean iguales se retorna false pues no se aceptan
				 * valores repetidos dentro del �rbol, en caso contrario se avanza
				 * a la siguiente etapa.
				 */

				if (comp == 0) 
				{
					return false;
				} 


				else 
				{
					padresVisitar[contadorPadre] = nodoActual;

					//El hijo es menor que su padre
					if (comp < 0)
					{
						nodoActual = nodoActual.darNodoIzquierdo();
						tiposHijo[contadorPadre] = tipoNodo.IZQUIERDA;

					} 

					//El hijo es mayor que su padre
					else if (comp > 0)
					{
						nodoActual = nodoActual.darNodoDerecho();
						tiposHijo[contadorPadre] = tipoNodo.DERECHA;

					}

					contadorPadre++;
				}


			}


			// Se agrega el nodo a la posici�n encontrada

			nodoActual = padresVisitar[contadorPadre - 1];

			/**
			 * Si el nodo a agregar se catalog� como menor que su padre en la posici�n encontrada
			 * Entonces el nodo izquierdo de la posici�n encontrada pasa a ser el nodo a agregar
			 */
			if (tiposHijo[contadorPadre - 1] == tipoNodo.IZQUIERDA)
			{
				nodoActual.modificarNodoIzquierdo(new ASTreeNode<T>(data));
				peso++;
			} 

			/**
			 * En caso de que el nodo se catalogara como mayor al de la posici�n 
			 * encontrada entonces el nodo derecho a esta posici�n ser� el elemento a agregar.
			 */
			else if (tiposHijo[contadorPadre - 1] == tipoNodo.DERECHA)
			{
				nodoActual.modificarNodoDerecho(new ASTreeNode<T>(data));
				peso++;
			}

			/**
			 *  Se recorren nuevamente el arreglo de padres modificando la informaci�n del
			 *  �rbol con el fin de modificar los nodos por los nuevos nodos correctos
			 *  y rebalancear el �rbol en caso de ser necesario.
			 */
			for (int i = contadorPadre - 1; i > 0; i--) 
			{
				nodoActual = padresVisitar[i];
				actualizarFactorBalanceoYAltura(nodoActual);
				rebalancear(nodoActual, padresVisitar[i - 1], tiposHijo[i - 1]);
			}

			//Caso en el que el �rbol estaba vac�o
			actualizarFactorBalanceoYAltura(raiz);
			rebalancear(raiz, null, null);

			modificarContadorX(contadorPadre);
		}


		return true;

	}


	/**
	 * M�todo que se encarga de actualizar el factor de balanceo general del �rbol AVL
	 * con el fin de saber qu� rotaci�n realizar y cu�ndo realizarla
	 * @param nodoActualizar El nodo al cual se le va a hayar su factor de balanceo y su altura
	 */
	private void actualizarFactorBalanceoYAltura(ASTreeNode<T> nodoActualizar) 
	{
		// Obtenemos el hijo
		ASTreeNode<T> hijoMenor = nodoActualizar.darNodoIzquierdo();
		ASTreeNode<T> hijoMayor = nodoActualizar.darNodoDerecho();

		// Calculamos el factor de balanceo
		int alturaIzquierda = (hijoMenor == null ? -1 : hijoMenor.darAltura());
		int alturaDerecha = (hijoMayor == null ? -1 : hijoMayor.darAltura());
		nodoActualizar.modificarFactorBalanceo(alturaIzquierda - alturaDerecha);

		// Calculamos la altura
		alturaIzquierda = (hijoMenor == null ? -1 : hijoMenor.darAltura());
		alturaDerecha = (hijoMayor == null ? -1 : hijoMayor.darAltura()); //TODO
		nodoActualizar.modificarAltura(Math.max(alturaIzquierda, alturaDerecha) + 1);
	}


	/**
	 * M�todo que se encarga de rebalancear el �rbol AVL con el fin de que tanto el lado derecho
	 * como el lado izquierdo posean una cantidad similar de elementos
	 * @param nodoBalancear El nodo al cual se le va a aplicar el rebalanceo
	 * @param nodoPadre El nodo que es el padre del nodo a rotar.
	 * @param tipo El tipo del nodo a balancear conr especto a su padre.
	 */
	private void rebalancear(ASTreeNode<T> nodoBalancear, ASTreeNode<T> nodoPadre, tipoNodo tipo) 
	{
		int factorBalanceo = nodoBalancear.darFactorBalanceo();

		//Condicional que evalua las rotaciones derechas
		if (factorBalanceo > 1) 
		{
			// El lado izquiero del �rbol es m�s pesado

			if (nodoBalancear.darNodoIzquierdo().darFactorBalanceo() < 0) 
			{
				// Realizamos una rotaci�n izquierda antes de realizar una derecha.
				//Lo anterior solo si se cumple la confici�n para hacer una rotaci�n derecha doble.

				rotacionIzquierda(nodoBalancear.darNodoIzquierdo(), nodoBalancear, tipoNodo.IZQUIERDA);
			}

			// Se realiza una rotaci�n derecha simple
			rotacionDerecha(nodoBalancear, nodoPadre, tipo);
		} 

		//Condicional que evalua las rotaciones izquierdas
		else if (factorBalanceo < -1) 
		{
			// El lado derecho del �rbol es el m�s pesado

			if (nodoBalancear.darNodoDerecho().darFactorBalanceo() > 0) 
			{
				// Se realiza una rotaci�n derecha simple antes de realizar la rotaci�n izquierda.
				// Lo anterior solo si se quiere hacer rotaci�n izquierda doble
				rotacionDerecha(nodoBalancear.darNodoDerecho(), nodoBalancear, tipoNodo.DERECHA);
			}

			//Rotaci�n izquierda simple
			rotacionIzquierda(nodoBalancear, nodoPadre, tipo);
		}
	}

	/**
	 * M�todo que se encarga de manipular las rotaciones hacia la derecha del �rbol AVL
	 * cuando estas sean necesitadas
	 * @param nodoBalancear El nodo que vamos a rotar.
	 * @param nodoPadre El nodo que es el padre de nodoBalancear.
	 * @param tipo El tipo de hijo que es el nodo a balancear con respecto al nodoPadre
	 */
	private void rotacionDerecha(ASTreeNode<T> nodoBalancear, ASTreeNode<T> nodoPadre, tipoNodo tipo) 
	{

		ASTreeNode<T> temp = nodoBalancear.darNodoIzquierdo();

		if (temp == null)
		{
			return;
		}

		// Se realiza una rotaci�n hacia la derecha
		nodoBalancear.modificarNodoIzquierdo(temp.darNodoDerecho());
		temp.modificarNodoDerecho(nodoBalancear);

		// Colocamos el nuevo padre
		if (nodoPadre == null) 
		{
			raiz = temp;
		} 

		else if (tipo == tipoNodo.IZQUIERDA) 
		{
			nodoPadre.modificarNodoIzquierdo(temp);
		} 

		else
		{
			nodoPadre.modificarNodoDerecho(temp);
		}

		// Reorganizamos el nodo temporal y el nodo a rotar
		actualizarFactorBalanceoYAltura(nodoBalancear);
		actualizarFactorBalanceoYAltura(temp);
	}


	/**
	 * M�todo que se encarga de manipular las rotaciones hacia la izquierda del �rbol AVL
	 * cuando estas sean necesitadas
	 * @param nodoBalancear El nodo que vamos a rotar.
	 * @param nodoPadre El nodo que es el padre de nodoBalancear.
	 * @param tipo El tipo de hijo que es el nodo a balancear con respecto al nodoPadre
	 */
	private void rotacionIzquierda(ASTreeNode<T> nodoBalancear, ASTreeNode<T> nodoPadre, tipoNodo tipo) 
	{
		ASTreeNode<T> temp = nodoBalancear.darNodoDerecho();

		if (temp == null) 
		{
			return;
		}

		// Realizamos rotaci�n izquierda
		nodoBalancear.modificarNodoDerecho(temp.darNodoIzquierdo());
		temp.modificarNodoIzquierdo(nodoBalancear);;

		// Actualiza la conexi�n con el padre
		if (nodoPadre == null) 
		{
			raiz = temp;
		} 

		else if (tipo == tipoNodo.IZQUIERDA)
		{
			nodoPadre.modificarNodoIzquierdo(temp);
		} 

		else 
		{
			nodoPadre.modificarNodoDerecho(temp);
		}

		// Reorganizamos los nodos, primero el de rotar y luego el temporal.
		actualizarFactorBalanceoYAltura(nodoBalancear);
		actualizarFactorBalanceoYAltura(temp);
	}


	
	public T remove(T data) throws Exception
	{
		T info = null;

		if (data == null) 
		{
			throw new Exception("El elemento a eliminar no puede ser null");
		} 

		else if (isEmpty() == true) 
		{
			return null;
		}

		// Calcula el n�mero m�ximo de padres a visistar. Teniendo en cuenta el peso, la raiz y los 2 hijos.
		int maximoPadresVisitar = darMaximo();

		// Se crean arreglos con el fin de monitorear los nodos por los que avanzamos.

		ASTreeNode<T>[] padres = new ASTreeNode[maximoPadresVisitar];
		tipoNodo[] tiposHijos = new tipoNodo[maximoPadresVisitar];

		int contadorPadres = 0;
		ASTreeNode<T> nodoActual = raiz;
		int comp = 0;
		comp = data.compareTo(nodoActual.darData());

		while (nodoActual != null && comp != 0)
		{
			padres[contadorPadres] = nodoActual;
			if (comp < 0)
			{
				nodoActual = nodoActual.darNodoIzquierdo();
				tiposHijos[contadorPadres] = tipoNodo.IZQUIERDA;
			} 

			else 
			{
				nodoActual = nodoActual.darNodoDerecho();
				tiposHijos[contadorPadres] = tipoNodo.DERECHA;
			}

			comp = data.compareTo(nodoActual.darData());
			contadorPadres++;
		} 

		if (nodoActual == null) 
		{
			return null;
		}

		else
		{
			// Obtenemos la informaci�n del nodo
			info = nodoActual.darData();

			// Recuperamos los hijos del nodo a eliminar
			ASTreeNode<T> hijoMenor = nodoActual.darNodoIzquierdo();
			ASTreeNode<T> hijoMayor = nodoActual.darNodoDerecho();

			// Dentro de estos condicionales manejamos los 3 casos de eliminaci�n

			// Caso 1.) El nodo a eliminar no tiene hijos
			if (hijoMenor == null && hijoMayor == null) 
			{

				if (contadorPadres > 0)
				{
					// El nodo a eliminar es una hoja

					ASTreeNode<T> parent = padres[contadorPadres - 1];
					if (tiposHijos[contadorPadres - 1] == tipoNodo.IZQUIERDA) 
					{
						parent.modificarNodoIzquierdo(null);
					} 

					else
					{
						parent.modificarNodoDerecho(null);

					}


				} 

				else 
				{
					// El nodo a eliminar es la raiz del �rbol
					raiz = null;

				}


			} 

			// Caso 2.) El nodo a eliminar tiene un hijo
			else if (hijoMenor == null && hijoMayor != null || hijoMenor != null && hijoMayor == null) 
			{


				// Capturamos el hijo del nodo a eliminar
				ASTreeNode<T> hijoUnico = (hijoMenor == null ? hijoMayor : hijoMenor);

				if (contadorPadres > 1) 
				{
					ASTreeNode<T> parent = padres[contadorPadres - 1];

					if (tiposHijos[contadorPadres - 2] == tipoNodo.IZQUIERDA)
					{
						parent.modificarNodoIzquierdo(hijoUnico);

					}

					else 
					{
						parent.modificarNodoDerecho(hijoUnico);

					}
				} 

				else 
				{
					raiz = hijoUnico;

				}



			} 

			// Caso 3.) El nodo a eliminar tiene 2 hijoss
			else 
			{


				// Encontramos un sucesor del nodo
				ASTreeNode<T> sucesor = hijoMayor;
				ASTreeNode<T> sucesorPadre = nodoActual;
				tipoNodo tipo = tipoNodo.DERECHA;

				while (sucesor.darNodoIzquierdo() != null) 
				{
					sucesorPadre = sucesor;
					padres[contadorPadres++] = sucesorPadre;
					sucesor = sucesor.darNodoIzquierdo();
					tipo = tipoNodo.IZQUIERDA;
				}

				// Reemplazamos el nodo que vamos a eliminar con su sucesor
				nodoActual.modificarData(sucesor.darData());


				// Trim off successor
				if (tipo == tipoNodo.DERECHA) 
				{
					sucesorPadre.modificarNodoDerecho(null);

				} 

				else 
				{
					sucesorPadre.modificarNodoIzquierdo(null);

				}


			}

			peso = peso - 1;
		}



		// Una vez finalizado la eliminaci�n actualizamos el �rbol y rebalanceamos si lo necesitamos
		for (int i = contadorPadres - 1; i > 0; i--) 
		{
			nodoActual = padres[i];
			actualizarFactorBalanceoYAltura(nodoActual);
			rebalancear(nodoActual, padres[i - 1], tiposHijos[i - 1]);
		}


		if (contadorPadres > 0) 
		{
			actualizarFactorBalanceoYAltura(this.raiz);
			rebalancear(this.raiz, null, null);
		}	


		return info;
	}

	public T get(T data) throws Exception
	{
		if (data == null) 
		{
			throw new Exception ("No se pueden buscar objetos nulos");
		}

		ASTreeNode<T> nodoActual = raiz;
		while (nodoActual != null) 
		{
			int comp = data.compareTo(nodoActual.darData());
			if (comp == 0) 
			{
				return nodoActual.darData();
			} 

			else if (comp < 0) 
			{
				nodoActual = nodoActual.darNodoIzquierdo();
			} 

			else 
			{
				nodoActual = nodoActual.darNodoDerecho();
			}
		}

		return null;
	}


	/**
	 * M�todo que se encarga de determinar si un nodo en espec�fico pertenece al �rbol
	 * @return True si el nodo est� dentro del �rbol, false en caso contrario.
	 */
	public boolean contains(T data) 
	{
		try 
		{
			return get(data) != null ? true : false;
		} 

		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			return false;
		}

	}


	/**
	 * M�todo que se encarga de decir si el �rbol est� vac�o o no.
	 * @return True si el �rbol est� vac�o, false en caso contrario.
	 */
	public boolean isEmpty()
	{   
		boolean rta = false;

		if(peso == 0 && raiz == null)
		{
			rta = true;
		}

		return rta;
	}

	public int darPeso() 
	{
		return peso;
	}


	/**
	 * M�todo que retorna un iterador con los elementos del �rbol AVL contenidor
	 * en pre-orden.
	 * @return el itrador en preorden del �rbol.
	 */
	public ASLagannIterator<T> preOrden() throws Exception 
	{
		ASLagannIterator<T> resultados = new ASLagannIterator<T>(peso);

		if (raiz != null) 
		{
			preOrdenRecursivo(resultados, raiz);
		}

		return resultados;
	}

	/**
	 * M�todo que construye recursivamente una contenedora en preOrden a partir
	 * del nodo actual.
	 * @param iteradorPrevio iterador al cual se van a agregar los elementos.
	 * @param actual representa el nodo actual.
	 * @throws Exception 
	 */
	private void preOrdenRecursivo(ASLagannIterator<T> iteradorPrevio, ASTreeNode<T> actual) throws Exception 
	{
		iteradorPrevio.add(actual.darData());

		if (actual.darNodoIzquierdo() != null) 
		{
			preOrdenRecursivo(iteradorPrevio, actual.darNodoIzquierdo());
		}


		if (actual.darNodoDerecho() != null) 
		{
			preOrdenRecursivo(iteradorPrevio, actual.darNodoDerecho());
		}

	}

	/**
	 * M�todo que retorna un iterador que contiene los elementos del �rbol AVL
	 * en post-orden.
	 * @return un iterador con los elementos en post-orden.
	 */
	public ASLagannIterator<T> postOrden() throws Exception 
	{
		ASLagannIterator<T> resultados = new ASLagannIterator<T>(peso);

		if (raiz != null) 
		{
			postOrdenRecursivo(resultados, raiz);
		}

		return resultados;
	}

	/**
	 * Construye recursivamente una contenedora en postOrden a partir del nodo actual.
	 * @param iteradorPrevio el iterador al cual se van a agregar los elementos.
	 * @param nodoActual el nodo a partir del cual se comienza a construir el iterador.
	 * @throws Exception 
	 */
	private void postOrdenRecursivo(ASLagannIterator<T> iteradorPrevio, ASTreeNode<T> nodoActual) throws Exception 
	{   

		if (nodoActual.darNodoIzquierdo() != null) 
		{

			postOrdenRecursivo(iteradorPrevio, nodoActual.darNodoIzquierdo());

		}

		if (nodoActual.darNodoDerecho() != null)
		{

			postOrdenRecursivo(iteradorPrevio, nodoActual.darNodoDerecho());

		}

		iteradorPrevio.add(nodoActual.darData());
	}


	/**
	 * M�todo que retorna un iterador con todos los elementos del �rbol organizados
	 * inOrden.
	 * @return retorna un iterador en orden del �rbol.
	 * @throws Exception 
	 */
	public ASLagannIterator<T> enOrden() throws Exception 
	{
		ASLagannIterator<T> resultados = new ASLagannIterator<T>(peso);

		if (raiz != null)
		{
			enOrdenRecursivo(resultados, raiz);
		}

		return resultados;
	}

	/**
	 * Construye recursivamente una contenedora en orden a partir de un nodo actual.
	 * @param iteradorPrevio iterador al cual se van a a�adir los elementos.
	 * @param nodoActual el nodo a partir el cual se va a crear el iterador.
	 * @throws Exception 
	 */
	private void enOrdenRecursivo(ASLagannIterator<T> iteradorPrevio, ASTreeNode<T> nodoActual) throws Exception
	{
		if (nodoActual.darNodoIzquierdo() != null) 
		{
			enOrdenRecursivo(iteradorPrevio, nodoActual.darNodoIzquierdo());
		}

		iteradorPrevio.add(nodoActual.darData());

		if (nodoActual.darNodoDerecho() != null) 
		{
			enOrdenRecursivo(iteradorPrevio, nodoActual.darNodoDerecho());
		}

	}


	/**
	 * Devuelve los elementos del �rbol utilizando un recorrido por niveles. <br>
	 * <b>post: </b> Se retorn� el iterador para recorrer los elementos del �rbol por niveles.
	 * @return Iterador con los elementos del �rbol en un recorrido por niveles
	 * @throws Exception 
	 */
	public ASLagannIterator<T> levelOrden( ) throws Exception
	{
		ASLagannIterator<T> resultado = new ASLagannIterator<T>( peso);

		if( raiz != null )
		{   

			darRecorridoNiveles( resultado, raiz);
		}

		return resultado;

	}


	private void darRecorridoNiveles(ASLagannIterator<T> iteratorPrevio, ASTreeNode<T> nodoActual) throws Exception
	{  
		ALinkedQueue<ASTreeNode<T>> cola = new ALinkedQueue<ASTreeNode<T>>( );
		cola.add( nodoActual );

		while( cola.size( ) != 0 )
		{
			ASTreeNode<T> nodo = null;
			try
			{
				// Toma el primer �rbol de la cola
				nodo = cola.atender( );
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
			try
			{
				iteratorPrevio.add( nodo.darData() );
			}

			catch( Exception e )
			{
				e.printStackTrace();
			}

			// Agrega los dos sub�rboles (si no son vac�os) a la cola
			if( nodo.darNodoIzquierdo() != null )
			{
				cola.add( nodo.darNodoIzquierdo());
			}

			if( nodo.darNodoDerecho() != null )
			{
				cola.add( nodo.darNodoDerecho() );
			}
		}
	}




	/**
	 * M�todo que se encarga de eliminar todos los nodos del �rbol y dejarlo vac�o
	 * nuevamente.
	 */
	public void clear() 
	{
		raiz = null;
		peso = 0;
	}

	
	public int darAltura() 
	{
		return ( isEmpty() ? -1 : raiz.darAltura() );
	}

	/**
	 * M�todo que retorna el �ndice actual del arreglo de padres por visitar.
	 * @return Retorna el indice actual del arreglo de apdres por visitar.
	 */
	public int darContador()
	{
		return contadorPadreX;
	}

	/**
	 * M�todo quie se encarga de definir la cantidad m�xima de nodos que vamos a visitar
	 * para realizar operaciones tales como inserci�n o eliminaci�n. Dadas las
	 * caracter�sticas de un �rbol AVL se tiene en cuenta que la cantidad maxima
	 * posible de nodos a recorrer siempre ser� la mitad del peso del �rbol 
	 * (dado que se espera que haya aproximadamente la misma cantidad de elementos
	 * tanto del lado izquierdo como del derecho) + 1 elemento extra posible que 
	 * equivale a la ra�z del �rbol.	 
	 * @return se retorna un int que equivale a la cantidad de nodos m�xima que se 
	 * van a recorrer para realizar una operaci�n.
	 */
	private int darMaximo()
	{
		return (int) ( (peso/2) + 2 );
	}

	/**
	 * M�todo privado que se encarga de modificar internamente el contador
	 * del padre que estamos visitando.
	 * @param x Nuevdo valor del contador.
	 */
	private void modificarContadorX(int x)
	{
		contadorPadreX = x;
	}

	/**
	 * M�todo que retorna recursivamente el elemento con el valor m�s grande
	 * dentro del �rbol.
	 * @return Retorna el nodo que posee el valor m�s grande dentro del �rbol.
	 */
	public T darNodoMayor()
	{
		return (raiz != null) ? raiz.mayorElemento().darData() : null;
	}

	/**
	 * M�todo que retorna recursivamente el elemento con el valor m�s peque�o
	 * dentro del �rbol.
	 * @return Retorna el nodo que posee el valor m�s peque�o dentro del �rbol.
	 */
	public T darNodoMenor()
	{
		return (raiz != null) ? raiz.menorElemento().darData() : null;
	}
	
	public ASTreeNode darRa�z()
	{
		return raiz;
	}

	public ASLagannIterator<T> iterator() 
	{
		try {
			return enOrden();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
