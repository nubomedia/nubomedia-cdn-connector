package de.fhg.fokus.nubomedia.cdn;

/**
 * Exception class for cdn connector related errors
 * 
 * @author Alice Cheambe (alice.cheambe@fokus.fraunhofer.de)
 *
 */
public class CdnException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CdnException(String message)
	{
		super(message);
	}
	
	public CdnException(Throwable t)
	{
		super(t);
	}

	public CdnException(String message, Throwable t)
	{
		super(message, t);
	}
}