package spoon;

import org.apache.commons.io.FileUtils;
import spoon.compiler.Environment;
import spoon.compiler.SpoonCompiler;
import spoon.processing.ProcessingManager;
import spoon.processing.Processor;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.FactoryImpl;
import spoon.reflect.visitor.FragmentDrivenJavaPrettyPrinter;
import spoon.support.DefaultCoreFactory;
import spoon.support.QueueProcessingManager;
import spoon.support.StandardEnvironment;
import spoon.support.compiler.jdt.JDTBasedSpoonCompiler;


import java.io.File;
import java.io.IOException;


public class Instru {
    protected String outputDirectory;
    protected String projectDirectory;
    protected String ressourceDirectory = "src/main/resources";
    private String testDirectory = "src/test/java";
    Processor processor;

    public Instru(Processor processor){
    	this.projectDirectory = testDirectory;
    	this.outputDirectory = ressourceDirectory;
    	this.processor = processor;
    }

    public Instru(String projectDirectory, String outputDirectory, Processor processor) {
        this.projectDirectory = projectDirectory;
        this.outputDirectory = outputDirectory;
        this.processor = processor;
    }

    public void instru() throws IOException {
        String src = projectDirectory + System.getProperty("file.separator");
        String out = outputDirectory + System.getProperty("file.separator") +testDirectory;

        //initialize spoon
        Factory factory = initSpoon(src);

        //apply the processor
        applyProcessor(factory, processor);

        //write the intrumentalize java code into the output directory
        Environment env = factory.getEnvironment();
        env.useSourceCodeFragments(true);
        applyProcessor(factory, new SimpleJavaOutputProcessor(new File(out), new FragmentDrivenJavaPrettyPrinter(env)));
    }


    //initialize spoon and create the ast
    protected Factory initSpoon(String srcDirectory) {
        StandardEnvironment env = new StandardEnvironment();
        env.setVerbose(false);
        env.setDebug(false);

        DefaultCoreFactory f = new DefaultCoreFactory();
        Factory factory = new FactoryImpl(f, env);
        SpoonCompiler c = new JDTBasedSpoonCompiler(factory);
        for (String dir : srcDirectory.split(System.getProperty("path.separator")))
            try {
                c.addInputSource(new File(dir));
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
            c.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }

    protected void applyProcessor(Factory factory, Processor processor) {
        ProcessingManager pm = new QueueProcessingManager(factory);
        pm.addProcessor(processor);
        pm.process();
    }
}
