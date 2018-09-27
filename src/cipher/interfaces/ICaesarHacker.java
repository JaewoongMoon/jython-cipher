/**
 * @ ICaesarHacker.java 
 */
package cipher.interfaces;

import cipher.ui.CipherUI;

/**
 * <pre>
 * cipher.interfaces
 * ICaesarHacker.java 
 * </pre>
 *
 * @brief	: 
 * @author	: 문재웅(mjw8585@gmail.com)
 * @Date	: 2016. 10. 12.
 */
public interface ICaesarHacker {

	public void setUI(CipherUI.HackingActionHandler ui);
	
	public String hacking(String message);
}
