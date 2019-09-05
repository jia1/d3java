package d3java;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStreamReader;

public class D3JavaChart {
	
	private static D3JavaChart instance = new D3JavaChart();
	private ScriptEngine engine;

	private D3JavaChart() {
		ScriptEngineManager factory = new ScriptEngineManager();
	    engine = factory.getEngineByName("JavaScript");
		try {
			engine.eval(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("d3java/js/require.js")));
			engine.eval(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("d3java/js/d3.min.js")));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	public static D3JavaChart getInstance() {
		return instance;
	}

	public String getChart(String chartFile) {
		try {
			return engine.eval(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(chartFile))).toString();
		} catch (ScriptException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getChart(String chartFile, String generatorFunctionName, String dataset) {
		try {
			engine.eval(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(chartFile)));
			return ((Invocable) engine).invokeFunction(generatorFunctionName, dataset).toString();
		} catch (ScriptException | NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}
	}
}
