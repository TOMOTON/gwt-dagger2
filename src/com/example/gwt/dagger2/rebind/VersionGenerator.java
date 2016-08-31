/**
 * Licensed to TOMOTON GmbH under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TOMOTON GmbH licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.gwt.dagger2.rebind;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

import com.example.gwt.dagger2.client.Version;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
 
public class VersionGenerator extends Generator { 
    
    @Override public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {	
		String result = null;
		try {
			String version = findVersion(logger, context);
			JClassType classType = context.getTypeOracle().getType(typeName);
			String packageName = packageNameFrom(classType);
			String simpleName = simpleNameFrom(classType);
			result = packageName + '.' + simpleName;
			SourceWriter source = getSourceWriter(logger, context, classType); 
			if(source != null) { //? Otherwise, work needs to be done.
			    source.println();
			    source.println("private String value;");
			    source.println();
			    source.println("public " + simpleName + "() {");
			    populateInstanceFactory(logger, context, typeName, source, version);
			    source.println("}");
			    source.println();
			    source.println("@Override");
			    source.println("public String getValue() {");
			    source.println(" return value;");
			    source.println("}");
			    source.println(); source.commit(logger);
			    //emitVersionArtifact(logger, context, version);
		    }
		} catch (NotFoundException nfe) {
		    logger.log(Type.ERROR, "Could not find extension point type '" + typeName + "'!", nfe);
		    throw new UnableToCompleteException();
		} 
		return result;
    }
    
    @SuppressWarnings("resource")
	private String findVersion(TreeLogger logger, GeneratorContext context) throws UnableToCompleteException {
    	String result = "0.0.0-DEV";
    	Scanner scanner = null;
    	try {
			URL location = Version.class.getClassLoader().getResource("VERSION");
			scanner = new Scanner(location.openStream(), "UTF-8").useDelimiter("\\A");
			result = scanner.next();
		} catch (IOException ioe) {
		    logger.log(Type.ERROR, "Could not determine build version!", ioe);
		    throw new UnableToCompleteException();
		} finally {
			try { scanner.close(); } catch (Exception ignore) {}
		}
    	return result;
    }
    
    private void populateInstanceFactory(TreeLogger logger, GeneratorContext context, String typeName, SourceWriter source, String version) throws UnableToCompleteException {
    	source.println(" this.value =\""+ version +"\";");
    }
    
    private SourceWriter getSourceWriter(TreeLogger logger, GeneratorContext context, JClassType classType) {
    	String packageName = packageNameFrom(classType);
    	String simpleName = simpleNameFrom(classType);
    	ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);
    	composer.addImplementedInterface(classType.getName());
    	composer.addImport(classType.getQualifiedSourceName());
    	PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
    	if (printWriter == null) {
    		return null; 
    	} else {
    		return composer.createSourceWriter(context, printWriter);
    	} 
    }
    
    private String packageNameFrom(JClassType classType) { 
    	return classType.getPackage().getName();
    } 
    
    private String simpleNameFrom(JClassType classType) {
    	return classType.getSimpleSourceName() + "_default_VersionGenerator"; 
    }
    
}
