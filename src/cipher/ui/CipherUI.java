/**
 * @ CipherUI.java 
 */
package cipher.ui;

import java.awt.Event;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import cipher.domain.CipherType;
import cipher.factory.JythonObjectFactory;
import cipher.factory.JythonObjectFactory2;
import cipher.interfaces.ICaesarCipher;
import cipher.interfaces.ICaesarHacker;
import cipher.interfaces.ITranspositionCipher;
import parameterHack.domain.DbmsType;
import parameterHack.domain.FuzzerType;

/**
 * <pre>
 * parameterHack.ui
 * CipherUI.java 
 * </pre>
 *
 * @brief	: 
 * @author	: 문재웅(mjw8585@gmail.com)
 * @Date	: 2016. 10. 11.
 */
public class CipherUI extends JPanel{

	
	JComboBox cipherTypeBox;
    Vector<String> cipherTypes;
    JTextArea input1;
    JTextArea progress;
    JTextArea output;
    JLabel progressLabel;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JTextField keyField;
    Vector dbmses;
    JComboBox dbmsBox;
    JButton triggerBtn1;
    JButton triggerBtn2;
    JButton triggerBtn3;
    JMenuBar menuBar;
    JMenu menu1;
    JMenuItem menuItem1;
    JMenuItem menuItem2;
    JMenuItem menuItem3;
    JLabel refLabel1;
    JythonObjectFactory factory = JythonObjectFactory.getInstance(); 
    
    /** 
     *  생성자 
     */ 
    public CipherUI() {
    	
    	/************************************************************************/
    	/************************  컴포넌트 세팅  *****************************/
    	
    	// 메뉴바
    	menuBar = new JMenuBar();
    	menu1 = new JMenu("메뉴1");
    	menuItem1 = new JMenuItem("암호화");
    	menuItem2 = new JMenuItem("복호화");
    	menuItem3 = new JMenuItem("해킹");
    	
    	menuBar.add(menu1);
    	menu1.add(menuItem1);
    	menu1.add(menuItem2);
    	menu1.add(menuItem3);
    	menuItem1.setAccelerator(KeyStroke.getKeyStroke('F', Event.CTRL_MASK));
    	menuItem1.addActionListener(new CipherActionHandler("encrypt"));
    	menuItem2.setAccelerator(KeyStroke.getKeyStroke('B', Event.CTRL_MASK));
    	menuItem2.addActionListener(new CipherActionHandler("decrypt"));
    	menuItem3.setAccelerator(KeyStroke.getKeyStroke('H', Event.CTRL_MASK));
    	menuItem3.addActionListener(new HackingActionHandler());
    	
    	// 암호 타입 콤보 박스
    	cipherTypes = new Vector<String>();
    	cipherTypes.add("--SELECT--");
    	for(CipherType cipher : CipherType.values()){
    		cipherTypes.add(cipher.toString());
    	}
    	cipherTypeBox = new JComboBox<>(cipherTypes);
    	cipherTypeBox.addActionListener(new CipherTypeSelectHandler());
    	
    	
    	// 파라메터 입력 텍스트 Input
    	input1 = new JTextArea(5, 10);
    	input1.setLineWrap(true);
    	JScrollPane inputPane1 = new JScrollPane(input1);
    	
    	// 진행상황 출력
    	progress = new JTextArea(5,10);
    	progress.setLineWrap(true);
    	JScrollPane progressPane = new JScrollPane(progress);
    	
    	// 파라메터 출력 텍스트 Output
    	output = new JTextArea(5, 10);
    	output.setLineWrap(true);
    	JScrollPane outputPane = new JScrollPane(output);
    	
    	progressLabel = new JLabel("진행 상황");
    	label1 = new JLabel("입력 문자열");
    	label2 = new JLabel("암호 타입 선택 (Cipher Type Select)");
    	label3 = new JLabel("출력 값 (Output) : 자동으로 클립보드에 복사됩니다.");
    	label4 = new JLabel("키 값(Key Value)");
    	refLabel1 = new JLabel("컴포넌트간 이동은 Ctrl + TAB 으로 가능합니다.");
    	// 암호화 키를 입력하는 필드
    	keyField = new JTextField();
    	
    	/*
    	dbmses = new Vector<String>();
    	dbmses.add("--DBMS SELECT--");
    	for(DbmsType dbmsType : DbmsType.values()){
    		dbmses.add(dbmsType);
    	}
    	dbmsBox = new JComboBox<>(dbmses);
    	*/
    	
    	// 버튼
    	triggerBtn1 = new JButton("암호화 (Ctrl + F)");
    	triggerBtn2 = new JButton("복호화 (Ctrl + B)");
    	triggerBtn3 = new JButton("해킹 (Ctrl + H)");
    	triggerBtn1.addActionListener(new CipherActionHandler("encrypt"));
    	triggerBtn2.addActionListener(new CipherActionHandler("decrypt"));
    	triggerBtn3.addActionListener(new HackingActionHandler());
    	
    	/************************************************************************/
    	/**********************  컨테이너 view 세팅  **************************/
    	// 레이아웃 세팅
    	super.setLayout(null); 
    	
    	// 컴포넌트 추가 
    	add(menuBar);
    	add(label1);
    	add(cipherTypeBox);
    	//add(dbmsBox);
    	add(label2);
    	add(inputPane1);
    	add(label3);
    	add(progressLabel);
    	add(progressPane);
    	add(outputPane);
    	add(label4);
    	add(keyField);
    	add(triggerBtn1);
    	add(triggerBtn2);
    	add(triggerBtn3);
    	add(refLabel1);
    	
    	// 컴포넌트 위치 조정
    	//menuBar.setBounds(0,0,400,20); 
    	
    	label1.setBounds( 30, 30, 300, 20);
    	inputPane1.setBounds( 30, 60, 500, 100);
    	
    	label2.setBounds( 550, 30, 300, 20);
    	cipherTypeBox.setBounds(550, 60, 150, 20);
    	//dbmsBox.setBounds(720, 60, 150, 20);
    	//dbmsBox.setVisible(false);
    	
    	progressLabel.setBounds(30, 200, 300, 20);
    	progressPane.setBounds(30, 230, 500, 150 );
    	
    	label3.setBounds( 30, 400, 400, 20);
    	outputPane.setBounds( 30, 430, 500, 100);
    	
    	label4.setBounds(550, 100, 300, 20);
    	label4.setVisible(false);
    	keyField.setBounds(550, 120, 250, 20);
    	keyField.setVisible(false);
    	refLabel1.setBounds(550, 190, 300, 20);
    	triggerBtn1.setBounds(550, 220, 130, 40);
    	triggerBtn2.setBounds(700, 220, 130, 40);
    	triggerBtn3.setBounds(550, 280, 130, 40);
    	
    	// 크기 지정
    	setSize(950, 700);
    	setVisible(true);
	    	
	}
	    
    /**
     * <pre>
     * parameterHack.ui
     * CipherUI.java 
     * </pre>
     *
     * @brief	: 암호화 / 복호화 로직을 수행   
     * @author	: 문재웅(mjw8585@gmail.com)
     * @Date	: 2016. 10. 12.
     */
    class CipherActionHandler implements ActionListener{
    	
    	private String mode = "decrypt";
    	
    	
		public CipherActionHandler(String mode) {
			this.mode = mode;
		}

		// 선택한 암호 타입과 mode에 따라 액션을 실행.
    	@Override
    	public void actionPerformed(ActionEvent e) {

    		// STEP 1. 필수 값 체크 
    		String message = input1.getText();
    		if(message == null || message.equals("")){
    			JOptionPane.showMessageDialog(null, "입력 문자열을 입력해 주십시오.");
    			return ;
    		}
    		
    		String keyValue = keyField.getText();
    		if(keyValue == null || keyValue.equals("")){
    			JOptionPane.showMessageDialog(null, "키 값을 입력해 주십시오.");
    			return ;
    		}
    		// STEP 2. 암/복호화 실행. 
    		String result = "";
    		
    		System.out.println("현재 모드 : " + mode);

    		// 시저 암호
    		if (cipherTypeBox.getSelectedItem() == CipherType.CAESAR.toString()){
    			//progress.setText("시저 암호를 실시합니다...");
    			int key = 0;
    			try{
    				key = Integer.parseInt(keyValue);
    			}catch (NumberFormatException ex){
    				//ex.printStackTrace();
    				JOptionPane.showMessageDialog(null, "시저 암호의 키 값은 숫자만 가능합니다.");
    				return ;
    			}
    			//factory = new JythonObjectFactory2(ICaesarCipher.class, "CaesarCipher", "CaesarCipher");
    			//ICaesarCipher cipher = (ICaesarCipher)factory.createObject();
    			ICaesarCipher cipher = (ICaesarCipher)factory.createObject(
    					 ICaesarCipher.class, "CaesarCipher");
    			cipher.initialize(key);
    			
    			//progress.setText(progress.getText() + "\r\n처리가 완료되었습니다."); 
    			result = cipher.process(mode, message);
    		}
    		// 전치 암호 
    		else if (cipherTypeBox.getSelectedItem() == CipherType.TRANSPOSITON.toString()){
    			int key = 0;
    			try{
    				key = Integer.parseInt(keyValue);
    			}catch (NumberFormatException ex){
    				JOptionPane.showMessageDialog(null, "전치 암호의 키 값은 숫자만 가능합니다.");
    				return ;
    			}
    			ITranspositionCipher cipher = (ITranspositionCipher)factory.createObject(
    					 ITranspositionCipher.class, "TranspositionCipher");
    			//cipher.initialize(key);
    			if(mode.equals("encrypt")){
    				result = cipher.encryptMessage(key, message);
    				System.out.println("암호화 : " + result);
    			}else if (mode.equals("decrypt")){
    				result = cipher.decryptMessage(key, message);
    				System.out.println("복호화 : " + result);
    			}
    		}
    		
    		output.setText(result);
    		//클립보드에 복사 
    		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    		if(result != null)
    		{
    		     StringSelection contents = new StringSelection(result);
    		     clipboard.setContents(contents, null);
    		}
    	}
    }
    
    /**
     * <pre>
     * cipher.ui
     * CipherUI.java 
     * </pre>
     *
     * @brief	: 암호화 방식을 선택했을 때 실행되는 핸들러 
     * @author	: 문재웅(mjw8585@gmail.com)
     * @Date	: 2016. 10. 12.
     */
    class CipherTypeSelectHandler implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		
    		// 선택한 암호 타입에 따라 보조 입력 필드를 세팅  
    		if (cipherTypeBox.getSelectedItem() == CipherType.CAESAR.toString() ||
    				cipherTypeBox.getSelectedItem() == CipherType.TRANSPOSITON.toString()){
    			keyField.setVisible(true);
    			label4.setVisible(true);
    		}else{
    			keyField.setVisible(false);
    			label4.setVisible(false);
    		}
    	}
    }
    
    /**
     * <pre>
     * cipher.ui
     * CipherUI.java 
     * </pre>
     *
     * @brief	: 해킹 작업을 실시하는 핸들러
     * @author	: 문재웅(mjw8585@gmail.com)
     * @Date	: 2016. 10. 12.
     */
    public class HackingActionHandler implements ActionListener{
    	
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		// STEP 1. 값 체크 
    		String message = input1.getText();
    		if(message == null || message.equals("")){
    			JOptionPane.showMessageDialog(null, "입력 문자열을 입력해 주십시오.");
    			return ;
    		}
    		
    		if(cipherTypeBox.getSelectedIndex() == 0){
    			JOptionPane.showMessageDialog(null, "암호 타입을 선택해 주십시오.");
    			// 향후 : 암호 타입을 선택하지 않으면 가능한 모든 암호 타입으로 시도합니다. 
    			// 계속하시겠습니까?
    			return ;
    		}
    		
    		if (cipherTypeBox.getSelectedItem() == CipherType.CAESAR.toString()){
    			// 시저 해커 
    			ICaesarHacker hacker= (ICaesarHacker)factory.createObject(
    					ICaesarHacker.class, "CaesarHacker");
    			
    			hacker.setUI(this);
    			
    			progress.setText("");
    			String result = hacker.hacking(message);
    		}
    	}
    	
    	public void printResult(String text){
    		System.out.println(text);
    		progress.setText(progress.getText() + "\r\n" + text);
    	}
    }
	   
}
