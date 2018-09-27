/**
 * @ CipherType.java 
 */
package cipher.domain;

/**
 * <pre>
 * cipher.domain
 * CipherType.java 
 * </pre>
 *
 * @brief	: 
 * @author	: 문재웅(mjw8585@gmail.com)
 * @Date	: 2016. 10. 12.
 */
public enum CipherType {

	CAESAR(1), //시저
	TRANSPOSITON(2),
	SUBSTITUTION(3),
	VIGENERE(4)
	;
	// 기타 추후 DES, AES, RSA 등 추가 
	
	private final int value;
	
	CipherType(int value){
		this.value = value;
	}
	
	public int intValue(){
		return value;
	}
	
	public static CipherType valueOf(int value){
		switch(value){
		case 1: return CAESAR;
		case 2: return TRANSPOSITON;
		case 3: return SUBSTITUTION;
		case 4: return VIGENERE;
		default : throw new AssertionError("Unknown value : " +value);
		}
	}
}
