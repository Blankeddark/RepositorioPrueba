package diccionario.mundo;

import java.io.Serializable;

public class PalabraPractico implements Comparable<PalabraPractico>, Serializable {
	
	//------------------------------------------------------------------------------------------------------------
	//Constantes
	//------------------------------------------------------------------------------------------------------------

	private static final String PALABRA_NO_VÁLIDA = "La palabra a agregar no es válida.";
	private static final String DEFINICIÓN_NO_VÁLIDA = "La definición para la palabra a agregar no es válida.";
	
	//------------------------------------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------------------------------------

	/**
	 * La palabra a guardar. palabra != "", ni puede ser un número.
	 */
	private String palabra;
	
	/**
	 * La definición de la palabra guardada. definición no puede ser un número, pero sí
	 * puede ser ""
	 */
	private String definición;
	
	/**
	 * Tipo de la palabra. Tipo = adj., adb., sust., verb.
	 */
	private String tipo;
	
	//TODO declaro la frecuencia
	private int frecuencia;
	
	
	//------------------------------------------------------------------------------------------------------------
	//Constructores
	//------------------------------------------------------------------------------------------------------------

	public PalabraPractico(String palabra, String definición, String tipo) throws Exception
	{
		//Verifico que la palabra sea válida
		
		if (palabra.equals(""))
			throw new Exception (PALABRA_NO_VÁLIDA + " Palabra: " + palabra);
		
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
			throw new Exception(PALABRA_NO_VÁLIDA + "\nPalabra: " + palabra);
		
		//Verifico que la definición sea válida
		boolean defNoVálida = false;
		try
		{
			Double.parseDouble(definición);
			defNoVálida = true;
		}
		catch (Exception e)
		{
			this.definición = definición;
		}

		if (defNoVálida)
			throw new Exception(DEFINICIÓN_NO_VÁLIDA + "\nPalabra: " + palabra + "\nDefinición: " + definición);
		
		this.tipo = tipo;
		
		frecuencia = 1;
	}
	
	public PalabraPractico(String palabra, String definición) throws Exception
	{
		//Verifico que la palabra sea válida
		frecuencia = 1;
		if (palabra.equals(""))
			throw new Exception (PALABRA_NO_VÁLIDA + " Palabra: " + palabra);
		
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
			throw new Exception(PALABRA_NO_VÁLIDA + "\nPalabra: " + palabra);
		
		//Verifico que la definición sea válida
		boolean defNoVálida = false;
		try
		{
			Double.parseDouble(definición);
			defNoVálida = true;
		}
		catch (Exception e)
		{
			definición.trim();
			this.definición = definición;
		}

		if (defNoVálida)
			throw new Exception(DEFINICIÓN_NO_VÁLIDA + "\nPalabra: " + palabra + "\nDefinición: " + definición);
		
		tipo = "";
	}
	
	public PalabraPractico(String palabra)
	{
		//Verifico que la palabra sea válida
		
//		if (palabra.equals(""))
//			throw new Exception (PALABRA_NO_VÁLIDA + " Palabra: " + palabra);
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
		definición = "";
		tipo = "";
		
	}
	
	public PalabraPractico(Palabra p)
	{
		this.palabra = p.darPalabra();
		this.frecuencia = p.darFrecuencia();
		this.definición = p.darDefinición();
		this.tipo = p.darTipo();
	}
	
	//------------------------------------------------------------------------------------------------------------
	//Métodos
	//------------------------------------------------------------------------------------------------------------
	
	public int compareTo(PalabraPractico otraPalabra)
	{
		return otraPalabra.frecuencia - frecuencia;
	}

	/**
	 * @return la definición
	 */
	public String darDefinición() {
		return definición;
	}

	/**
	 * @param definición la definición to set
	 */
	public void setDefinición(String definición) {
		this.definición = definición;
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
