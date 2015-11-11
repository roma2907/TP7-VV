package spoon.processor;

import org.eclipse.jdt.internal.compiler.ast.AssertStatement;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtInvocation;

public class CountAssertProcessor extends AbstractProcessor<CtInvocation<AssertStatement>> {

	private int nb;

	@Override
	public void processingDone() {
		System.out.println("nombre de assert défini : " + nb);
	}

	@Override
	public void process(CtInvocation<AssertStatement> element) {

		System.out.println(element.getSignature());
		if(element.getSignature().contains("Assert.")) {
			nb++;
		}
	}
}
