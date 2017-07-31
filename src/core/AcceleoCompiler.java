
package core;

import org.eclipse.acceleo.parser.compiler.AbstractAcceleoCompiler;
import org.eclipse.emf.common.util.Monitor;

/**
 * The Acceleo Stand Alone compiler.
 * 
 */
public class AcceleoCompiler extends AbstractAcceleoCompiler {
    
    /**
     * The entry point of the compilation.
     * 
     * @param args
     *             The arguments used in the compilation: the source folder,
     *             the output folder, a boolean indicating if we should use binary resource
     *             serialization and finally the dependencies of the project.
     
    public static void core(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Missing parameters"); //$NON-NLS-1$
        }
        AcceleoCompiler acceleoCompiler = new AcceleoCompiler();
        acceleoCompiler.setSourceFolder(args[0]);
        acceleoCompiler.setOutputFolder(args[1]);
        acceleoCompiler.setBinaryResource(Boolean.valueOf(args[2]).booleanValue());
        if (args.length == 4 && args[3] != null && !"".equals(args[3])) { //$NON-NLS-1$
            acceleoCompiler.setDependencies(args[3]);
        }
        acceleoCompiler.doCompile(new BasicMonitor());
    }
    */
    /**
     * Launches the compilation of the mtl files in the generator.
     * 
     */
    @Override
    public void doCompile(Monitor monitor) {
        super.doCompile(monitor);
    }
    
    /**
     * Registers the packages of the metamodels used in the generator.
     * 
     */
    @Override
    protected void registerPackages() {
        super.registerPackages();
        /*
         * If you want to add the other packages used by your generator, for example UML:
         * org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
         **/
    }

    /**
     * Registers the resource factories.
     * 
     */
    @Override
    protected void registerResourceFactories() {
        super.registerResourceFactories();
        /*
         * If you want to add other resource factories, for example if your metamodel uses a specific serialization and it is not contained in a ".ecore" file:
         * org.eclipse.emf.ecore.resource.Resource.Factory.Registry.getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
         **/
    }
}