## AcceleoStandalone-CLI

This is a small CLI .jar that allows you to compile and run .mtl acceleo files.
All the paths are relative to the .jar location.
Required libs inside lib.7z, the .jar can be found inside "out/artifacts/".

constraints (avoided by editing costants inside src/core/Generate.java):
* the main Acceleo template module **must** be called "generateElement"
* the .mtl file **must** be placed in "mtl/" folder
* the .mtl file **must** be called "generate.mtl"

Usage: java -jar AcceleoStandalone-CLI.jar [options]
	
	Options:
	--ecore, -e [required]
	  	relative path to .ecore metamodel
	--entrypoint, -ep [required]
	  	metamodel entry point EClass name
	--outname, -on
	  	generated artifact file name
	  	Default: WebDsl.xtext
	--outtargetdir, -o
	  	generated artifact relative path
	 	Default: gen/

