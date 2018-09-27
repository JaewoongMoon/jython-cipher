/**
 * @ JythonObjectFactory.java 
 */
package cipher.factory;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 * <pre>
 * factory
 * JythonObjectFactory.java 
 * Object factory implementation that is defined 
 * in generic fashion. 
 * </pre>
 *
 * @brief	: 
 * @author	: 문재웅(mjw8585@gmail.com)
 * @Date	: 2016. 10. 11.
 */
public class JythonObjectFactory {

	private static JythonObjectFactory instance = null;
	private static PyObject pyObject = null;
	
	protected JythonObjectFactory(){
		
	}
	
	/**
	 * @Method 	: getInstance
	 * @brief	: Create a singleton object. Only allow one instance to be created
	 * @author	: 문재웅(mjw8585@gmail.com)
	 * @Date	: 2016. 10. 11.
	 * @return
	 */
	public static JythonObjectFactory getInstance(){
		if(instance == null){
			instance = new JythonObjectFactory();
		}
		
		// set python class path
		/*
		try{
			URL resource = JythonObjectFactory.class.getResource("/cipher/py");
			//URL resource = Thread.currentThread().getContextClassLoader().getResource("../py"); //doesn't work-
			// 실행환경에서는 resource 가 null 이 되어버림. 
			URI uri = resource.toURI();
			Path path = Paths.get(uri);
			String pythonPath = path.toString(); 
			Properties props = new Properties();
			props.setProperty("python.path", pythonPath);  
			PythonInterpreter.initialize(System.getProperties(), props, new String[] {""});
		}catch(URISyntaxException ex){
			Logger.getLogger(JythonObjectFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
		*/
		return instance;
	}
	
	public static Object createObject(Object interfaceType, String moduleName){
		Object javaInt = null;
		// Create a Pythoninterpreter object and import our Jython module
		// to obtain a reference. 
		
		PythonInterpreter interpeter = new PythonInterpreter();
		interpeter.exec("from " + moduleName + " import " + moduleName);
		
		pyObject = interpeter.get(moduleName);
		
		try{
			// Create a new object reference of the Jython module and store 
			// into PyObject.
			PyObject newObj = pyObject.__call__();
			// Call __tojava__ method on the new object along with the interface
			// name to create the java bytecode
			javaInt = newObj.__tojava__(Class.forName(interfaceType.toString().substring(
					interfaceType.toString().indexOf(" ") + 1,
					interfaceType.toString().length())));
		} catch(ClassNotFoundException ex){
			Logger.getLogger(JythonObjectFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
		return javaInt;
	}
}
