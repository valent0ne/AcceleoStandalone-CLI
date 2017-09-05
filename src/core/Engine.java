package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Engine {

    //logger
    final private static Logger LOGGER = LoggerFactory.getLogger(Engine.class);

    /**
     * environment cleaning
     * @param path path to target
     */
    static void clean(String path){

        try {
            File file = new File(path);
            if(file.delete()) {
                LOGGER.info("{} deleted", path);
            }else {
                LOGGER.warn("can't delete {}, proceeding anyway...", path);
            }
        }catch(Exception e) {
            LOGGER.warn("clean {}, proceeding anyway...",e.getMessage());
        }
    }

    /**
     * .mtl compilation
     */
    static void mtlCompiler (String source, String dest){


        LOGGER.info("compiling .mtl...");

        try{
            AcceleoCompiler acceleoCompiler = new AcceleoCompiler();
            acceleoCompiler.setSourceFolder(source);
            acceleoCompiler.setOutputFolder(dest);
            acceleoCompiler.doCompile(new BasicMonitor());
        }catch (Exception e){
            LOGGER.error(".mtl compiler {}",e.getMessage());
            System.exit(1);
        }



        LOGGER.info(".emtl compiled to {}", dest);
    }

    /**
     * .ecore load and registration
     * @param source path to .ecore metamodel
     */
    static URI ecoreLoader (String source){
        LOGGER.info("loading {}", source);

        try{
            // Create a resource set.
            ResourceSet resourceSet = new ResourceSetImpl();

            // Register the default resource factory (needed for standalone)
            resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( "ecore", new EcoreResourceFactoryImpl());

            // Register the package (needed for standalone)
            EcorePackage ecorePackage = EcorePackage.eINSTANCE;

            // Get the URI of the model file.
            URI fileURI = URI.createFileURI(new File(source).getAbsolutePath());

            // Demand load the resource for this file.
            Resource resource = resourceSet.getResource(fileURI, true);

            LOGGER.info("loaded {}", source);

            return fileURI;

        }catch (Exception e){
            LOGGER.error(".ecore loader {}", e.getMessage());
            System.exit(1);
        }

        return null;

    }

    /**
     * acceleo generation
     */
    static void runAcceleo(String entryPoint, String fileName, String dest, URI fileURI) {

        try{
            List<String> acceleoArgs = new ArrayList<>();
            acceleoArgs.add(entryPoint);
            acceleoArgs.add(fileName);

            File targetFolder = new File(dest);

            Generate generator = new Generate(fileURI, targetFolder, acceleoArgs);
            generator.doGenerate(new BasicMonitor());
        }catch (Throwable e){
            LOGGER.error("acceleo generation: {}",e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }



        LOGGER.info("generation completed to {}", dest);
    }

    /**
     * copyies generated .xtext file in dest folder
     * @param source source file
     * @param dest dest file
     * @param xtext  .xtext file name
     */
    static void xtextDispatcher(String source, String dest, String xtext){

        LOGGER.info("copying {} to {}", xtext, dest);

        File file = new File(source);
        try{
            Files.copy(file.toPath(), new File(dest).toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            LOGGER.error("Files.copy: {}", e.getMessage());
            System.exit(1);
        }

        LOGGER.info("{} copied", xtext);

    }

    /**
     * return the last segment after "/" in a string
     */
    static String getLastSegment(String s){
        try{
            String[] aux = s.split("/");
            String res =  aux[aux.length-1];
            return res;
        }catch (Exception e){
            return "";
        }

    }

    static void separator(){
        System.out.println(" ");
    }





}
