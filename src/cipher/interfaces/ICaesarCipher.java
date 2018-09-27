/**
 * @ ICaesarCipher.java 
 */
package cipher.interfaces;

/**
 * <pre>
 * interfaces
 * ICaesarCipher.java 
 * </pre>
 *
 * @brief	: 
 * @author	: 문재웅(mjw8585@gmail.com)
 * @Date	: 2016. 10. 11.
 */
public interface ICaesarCipher {

	public void initialize(int key);
	public String process(String mode, String message);
	
}
