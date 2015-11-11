package spoon;

import java.io.IOException;

import spoon.processor.CountAssertProcessor;

public class Main {

	public static void main(String[] args) throws IOException{
		Instru instru = new Instru(new CountAssertProcessor());

        //instrumentalize the java code of output directory with LogProcessor
        instru.instru();
	}
}
