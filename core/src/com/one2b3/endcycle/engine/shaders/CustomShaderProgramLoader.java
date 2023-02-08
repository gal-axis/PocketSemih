package com.one2b3.endcycle.engine.shaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.engine.shaders.CustomShaderProgramLoader.CustomShaderProgramParameter;

public class CustomShaderProgramLoader extends AsynchronousAssetLoader<CustomShaderProgram, CustomShaderProgramParameter> {

	static final String VERTEX_EXTENSION = ".vert";
	static final String FRAGMENT_EXTENSION = ".frag";

	public CustomShaderProgramLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, CustomShaderProgramParameter parameter) {
		return null;
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, CustomShaderProgramParameter parameter) {
	}

	@Override
	public CustomShaderProgram loadSync(AssetManager manager, String fileName, FileHandle file, CustomShaderProgramParameter parameter) {
		String vertFileName = null, fragFileName = null;
		if (parameter != null) {
			vertFileName = parameter.vertexFile;
			fragFileName = parameter.fragmentFile;
		}
		if (vertFileName == null) {
			if (file.extension().isEmpty()) {
				vertFileName = fileName + VERTEX_EXTENSION;
			} else if (fileName.endsWith(FRAGMENT_EXTENSION)) {
				vertFileName = fileName.substring(0, fileName.length() - FRAGMENT_EXTENSION.length()) + VERTEX_EXTENSION;
			}
		}
		if (fragFileName == null) {
			if (file.extension().isEmpty()) {
				fragFileName = fileName + FRAGMENT_EXTENSION;
			} else if (fileName.endsWith(VERTEX_EXTENSION)) {
				fragFileName = fileName.substring(0, fileName.length() - VERTEX_EXTENSION.length()) + FRAGMENT_EXTENSION;
			}
		}
		FileHandle vertexFile = vertFileName == null ? file : resolve(vertFileName);
		FileHandle fragmentFile = fragFileName == null ? file : resolve(fragFileName);
		String vertexCode = vertexFile.readString();
		String fragmentCode = vertexFile.equals(fragmentFile) ? vertexCode : fragmentFile.readString();
		if (parameter != null) {
			if (parameter.prependVertexCode != null) {
				vertexCode = parameter.prependVertexCode + vertexCode;
			}
			if (parameter.prependFragmentCode != null) {
				fragmentCode = parameter.prependFragmentCode + fragmentCode;
			}
		}

		CustomShaderProgram shaderProgram = new CustomShaderProgram(vertexCode, fragmentCode);
		if ((parameter == null || parameter.logOnCompileFailure) && !shaderProgram.isCompiled()) {
			manager.getLogger().error("ShaderProgram " + fileName + " failed to compile:\n" + shaderProgram.getLog());
		}
		return shaderProgram;
	}

	static public class CustomShaderProgramParameter extends AssetLoaderParameters<CustomShaderProgram> {
		public String vertexFile;
		public String fragmentFile;
		public boolean logOnCompileFailure = true;
		public String prependVertexCode;
		public String prependFragmentCode;

		public CustomShaderProgramParameter() {
		}

		public CustomShaderProgramParameter(String vertexFile, String fragmentFile) {
			this.vertexFile = vertexFile;
			this.fragmentFile = fragmentFile;
		}
	}

}
