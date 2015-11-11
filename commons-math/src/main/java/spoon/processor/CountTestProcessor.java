package spoon.processor;

import org.junit.Test;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtElement;

public class CountTestProcessor extends AbstractProcessor<CtAnnotation<Test>> {

	public int nb=0;
	
	@Override
	public void process(CtAnnotation<Test> element) {
		nb++;
	}
	
	@Override
	public void processingDone(){
		System.out.println("nombre de méthode de test défini : "+ nb);	
	}

}
