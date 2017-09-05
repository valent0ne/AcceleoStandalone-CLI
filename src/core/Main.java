package core;


import java.io.File;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static core.Engine.*;

public class Main {

	//logger
	final private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

	//line separator
	final private static String LINE_SEPARATOR = System.lineSeparator();

	//file path separator
    final private static String PATH_SEPARATOR = File.separator;


	//CLI params declaration

	//path to .ecore metamodel
	@Parameter(names = {"--ecore", "-e"}, required = true, description = "relative path to .ecore metamodel")
	private static String pathToEcore;

	private static String mtlTargetDirectory = "mtl"+PATH_SEPARATOR;

	// .emtl file name
    private static String emtlName = "generate.emtl";

	//metamodel entry point
	@Parameter(names = {"--entrypoint", "-ep"}, required = true, description = "metamodel entry point EClass name")
	private static String mmodelEntryPoint;

	//generated .xtext path
	@Parameter(names = {"--outtargetdir", "-o"}, description = "generated artifact relative path")
	private static String outTargetDirectory = "gen"+PATH_SEPARATOR;

	//generated xtext file name
	@Parameter(names = {"--outname", "-on"}, description = "generated artifact file name")
	private static String outName = "WebDsl.xtext";



	private static String absPath = "./";


	public static void main(String ... args) {

		Main main = new Main();
		JCommander jc = JCommander.newBuilder()
				.addObject(main)
				.build();

		jc.setProgramName("java -jar AcceleoStandalone-CLI.jar");

		try{
			jc.parse(args);

		}catch (Exception e){

			jc.usage();
			System.exit(1);
		}


		if(pathToEcore.length()<6 || !(pathToEcore.substring(pathToEcore.length()-6).equals(".ecore"))){
			pathToEcore = pathToEcore.concat(".ecore");
		}

        separator();
		LOGGER.info("[DATA RECAP]{}", LINE_SEPARATOR);

		LOGGER.info("files to clean:{}" +
                "     {}{}" +
                "     {}",
                LINE_SEPARATOR, absPath+outTargetDirectory+outName, LINE_SEPARATOR, absPath+mtlTargetDirectory+emtlName);
		LOGGER.info(".mtl compilation:{}" +
				"     source = {}{}" +
				"     destination = {}",
                LINE_SEPARATOR, absPath+"mtl"+PATH_SEPARATOR, LINE_SEPARATOR, absPath+mtlTargetDirectory);
		LOGGER.info("acceleo generation:{}" +
				"     .ecore file to process = {}{}" +
				"     .xtext artifact destination directory = {}{}" +
				"     metamodel entry point = {}",
                LINE_SEPARATOR, absPath+pathToEcore, LINE_SEPARATOR,  absPath+outTargetDirectory, LINE_SEPARATOR, mmodelEntryPoint);


		main.run();

	}


	private void run(){

	    separator();
		LOGGER.info("[STARTING PROCESS]{}", LINE_SEPARATOR);
		try{

			clean(absPath+outTargetDirectory+outName);
			clean(absPath+mtlTargetDirectory+emtlName);
			mtlCompiler(absPath+"mtl"+PATH_SEPARATOR, absPath+mtlTargetDirectory);
			runAcceleo(mmodelEntryPoint, outName, absPath+outTargetDirectory, ecoreLoader(absPath+pathToEcore));

		}catch(Exception e){
			LOGGER.error(e.getMessage());
            separator();
			LOGGER.error("[PROCESS ABORTED]{}", LINE_SEPARATOR);
			System.exit(1);
		}
        separator();
		LOGGER.info("[PROCESS COMPLETED]{}", LINE_SEPARATOR);
	}
}
