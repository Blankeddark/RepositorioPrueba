package diccionario.mundo;

import java.io.Serializable;

public class PalabraPractico implements Comparable<PalabraPractico>, Serializable {
	
	//------------------------------------------------------------------------------------------------------------
	//Constantes
	//------------------------------------------------------------------------------------------------------------

	private static final String PALABRA_NO_V�LIDA = "La palabra a agregar no es v�lida.";
	private static final String DEFINICI�N_NO_V�LIDA = "La definici�n para la palabra a agregar no es v�lida.";
	
	//------------------------------------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------------------------------------

	/**
	 * La palabra a guardar. palabra != "", ni puede ser un n�mero.
	 */
	private String palabra;
	
	/**
	 * La definici�n de la palabra guardada. definici�n no puede ser un n�mero, pero s�
	 * puede ser ""
	 */
	private String definici�n;
	
	/**
	 * Tipo de la palabra. Tipo = adj., adb., sust., verb.
	 */
	private String tipo;
	
	//TODO declaro la frecuencia
	private int frecuencia;
	
	
	//------------------------------------------------------------------------------------------------------------
	//Constructores
	//------------------------------------------------------------------------------------------------------------

	public PalabraPractico(String palabra, String definici�n, String tipo) throws Exception
	{
		//Verifico que la palabra sea v�lida
		
		if (palabra.equals(""))
			throw new Exception (PALABRA_NO_V�LIDA + " Palabra: " + palabra);
		
		try
		{
			Double.parseDouble(palabra);
			
		}
		catch (Exception e)
		{
			palabra.trim();
			this.palabra = palabra;
		}
		
		if (this.palabra.equals(""))
			throw new Exception(PALABRA_NO_V�LIDA + "\nPalabra: " + palabra);
		
		//Verifico que la definici�n sea v�lida
		boolean defNoV�lida = false;
		try
		{
			Double.parseDouble(definici�n);
			defNoV�lida = true;
		}
		catch (Exception e)
		{
			this.definici�n = definici�n;
		}

		if (defNoV�lida)
			throw new Exception(DEFINICI�N_NO_V�LIDA + "\nPalabra: " + palabra + "\nDefinici�n: " + definici�n);
		
		this.tipo = tipo;
		
		frecuencia = 1;
	}
	
	public PalabraPractico(String palabra, String definici�n) throws Exception
	{
		//Verifico que la palabra sea v�lida
		frecuencia = 1;
		if (palabra.equals(""))
			throw new Exception (PALABRA_NO_V�LIDA + " Palabra: " + palabra);
		
		try
		{
			Double.parseDouble(palabra);
			
		}
		catch (Exception e)
		{
			palabra.trim();
			this.palabra = palabra;
		}
		
		if (this.palabra.equals(""))
			throw new Exception(PALABRA_NO_V�LIDA + "\nPalabra: " + palabra);
		
		//Verifico que la definici�n sea v�lida
		boolean defNoV�lida = false;
		try
		{
			Double.parseDouble(definici�n);
			defNoV�lida = true;
		}
		catch (Exception e)
		{
			definici�n.trim();
			this.definici�n = definici�n;
		}

		if (defNoV�lida)
			throw new Exception(DEFINICI�N_NO_V�LIDA + "\nPalabra: " + palabra + "\nDefinici�n: " + definici�n);
		
		tipo = "";
	}
	
	public PalabraPractico(String palabra)
	{
		//Verifico que la palabra sea v�lida
		
//		if (palabra.equals(""))
//			throw new Exception (PALABRA_NO_V�LIDA + " Palabra: " + palabra);
//		
//		try
//		{
//			Double.parseDouble(palabra);
//			
//		}
//		catch (Exception e)
//		{
//			palabra.trim();
//			this.palabra = palabra;
//		}
		
		this.palabra = palabra;
		definici�n = "";
		tipo = "";
		
	}
	
	public PalabraPractico(Palabra p)
	{
		this.palabra = p.darPalabra();
		this.frecuencia = p.darFrecuencia();
		this.definici�n = p.darDefinici�n();
		this.tipo = p.darTipo();
	}
	
	//------------------------------------------------------------------------------------------------------------
	//M�todos
	//------------------------------------------------------------------------------------------------------------
	
	public int compareTo(PalabraPractico otraPalabra)
	{
		return otraPalabra.frecuencia - frecuencia;
	}

	/**
	 * @return la definici�n
	 */
	public String darDefinici�n() {
		return definici�n;
	}

	/**
	 * @param definici�n la definici�n to set
	 */
	public void setDefinici�n(String definici�n) {
		this.definici�n = definici�n;
	}

	/**
	 * @return la tipo
	 */
	public String darTipo() {
		return tipo;
	}

	/**
	 * @param tipo la tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return la palabra
	 */
	public String darPalabra() {
		return palabra;
	}
	
	public void setPalabra(String palabra)
	{
		this.palabra = palabra;
	}
	
	public void aumentarFrecuencia()
	{
		frecuencia++;
	}
	
	public int darFrecuencia()
	{
		return frecuencia;
	}

}
