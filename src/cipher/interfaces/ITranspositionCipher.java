/**
 * @ ITranspositionCipher.java 
 */
package cipher.interfaces;

/**
 * <pre>
 * cipher.interfaces
 * ITranspositionCipher.java 
 * </pre>
 *
 * @brief	: 
 * @author	: 문재웅(mjw8585@gmail.com)
 * @Date	: 2016. 10. 12.
 */
public interface ITranspositionCipher {
	
	//public void initialize(int key);
	public String decryptMessage(int key, String message);
	public String encryptMessage(int key, String message);
}
