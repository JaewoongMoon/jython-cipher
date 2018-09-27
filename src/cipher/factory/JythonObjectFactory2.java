/**
 * @ JythonObjectFactory2.java 
 */
package cipher.factory;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

/**
 * <pre>
 * factory
 * JythonObjectFactory2.java
 * Jython Object Factory using PySystemState 
 * </pre>
 *
 * @brief	: 
 * @author	: 문재웅(mjw8585@gmail.com)
 * @Date	: 2016. 10. 11.
 */
public class JythonObjectFactory2 {

	private final Class interfaceType;
	private final PyObject klass;
	
	// Constructor obtains a reference to the importer, module, and the class name
	/*
	public JythonObjectFactory2(PySystemState state, Class interfaceType, 
			String moduleName, String className){
		this.interfaceType = interfaceType;
		PyObject importer = state.getBuiltins().__getitem__(Py.newString("__import__"));
		PyObject module = importer.__call__(Py.newString(moduleName));
		klass = module.__getattr__(className);
		System.err.println("module=" + module + ",class=" + klass);
	}*/
	
	// This constructor passes through to the other constructor
	public JythonObjectFactory2(Class interfaceType, String moduleName, String className){
		// set python class path
		try{
			//URL resource = this.getClass().getResource("../py");
			//URL resource = this.getClass().getResource("/cipher/py"); //양쪽 다 로컬 환경에서는 동작하지만,
			//URL resource = Thread.currentThread().getContextClassLoader().getResource("/cipher/py");
			// 실행 파일로 만들어서 실행하면 동작하지 않음. 어쩌지? 
			//String pythonPath = Paths.get(resource.toURI()).toString(); 
			//String pythonPath = System.getProperty("user.dir") + "/cipher/py";
			String pythonPath = this.getClass().getResource("../py").getPath() ;
			System.out.println(pythonPath);
			Properties props = new Properties();
			props.setProperty("python.path", pythonPath);  
			PySystemState.initialize(System.getProperties(), props);
		}catch(Exception ex){
			Logger.getLogger(JythonObjectFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		//this(new PySystemState(), interfaceType, moduleName, className);
		PySystemState state = new PySystemState();
		this.interfaceType = interfaceType;
		PyObject importer = state.getBuiltins().__getitem__(Py.newString("__import__"));
		PyObject module = importer.__call__(Py.newString(moduleName));
		klass = module.__getattr__(className);
		System.err.println("module=" + module + ",class=" + klass);
	}
	
	// All of the following methods return
	// a coerced Jython object based upon the pieces of information
	// that were passed into the factory. The differences 
	// between them are the number of arguments.
	public Object createObject(){
		return klass.__call__().__tojava__(interfaceType);
	}
	
	public Object createObject(Object arg1){
		return klass.__call__(Py.java2py(arg1)).__tojava__(interfaceType);
	}
	
	public Object createObject(Object arg1, Object arg2){
		return klass.__call__(Py.java2py(arg1), Py.java2py(arg2)).__tojava__(interfaceType);
	}
	
	public Object createObject(Object arg1, Object arg2, Object arg3){
		return klass.__call__(Py.java2py(arg1), Py.java2py(arg2)
				, Py.java2py(arg3)).__tojava__(interfaceType);
	}
	
	public Object createObject(Object args[], String keywords[]){
		PyObject convertedArgs[] = new PyObject[args.length];
		for(int i=0; i < args.length; i++){
			convertedArgs[i] = Py.java2py(args[i]);
		}
		return klass.__call__(convertedArgs, keywords).__tojava__(interfaceType);
	}
	
	public Object createObject(Object... args){
		return createObject(args, Py.NoKeywords);
	}
	
}
