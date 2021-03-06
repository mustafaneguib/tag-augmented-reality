/**
The MIT License (MIT)

Copyright (c) 2014 MN Tech Solutions
Copyright (c) 2014 tagAugmentedReality
Copyright (c) 2014 Mustafa Neguib

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/



package com.tagaugmentedreality.openglobjectloader;

import static android.opengl.GLES10.GL_REPLACE;
import static android.opengl.GLES10.GL_TEXTURE0;
import static android.opengl.GLES10.GL_TEXTURE_ENV;
import static android.opengl.GLES10.GL_TEXTURE_ENV_MODE;
import static android.opengl.GLES10.glActiveTexture;
import static android.opengl.GLES10.glTexEnvf;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

/**
 * This class is an object representation of 
 * a model containing the vertex information,
 * texture coordinates, the vertex indices
 * and drawing functionality, which is called 
 * by the renderer.
 * 
 * @originalAuthor Savas Ziplies (nea/INsanityDesign)
 * @improvedAndModified  Mustafa Neguib (www.mntechsolutions.net)
 */
public class TagOpenGLModel extends OpenGLModel{

	/**
	 * The Model constructor.
	 * 
	 * Initiate the buffers.
	 */
	public TagOpenGLModel() {


	}


	public TagOpenGLModel(float [] vertexBuffer, float [] textureBuffer, short [] facesBuffer, int textureImageResourceId) {
		super(vertexBuffer, textureBuffer, facesBuffer, textureImageResourceId);

	}


	/**
	 * Load the textures
	 * 
	 * @param gl - The GL Context
	 * @param context - The Activity context
	 */
	public void loadGLTexture(GL10 gl, Context context,String text) {


		try
		{
			//Generate One texture pointer...
			gl.glGenTextures(1, textureId, 0);
			//...and bind it to our array
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId[0]);

			//Create Nearest Filtered Texture
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

			//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

			glActiveTexture(GL_TEXTURE0);

			glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE,GL_REPLACE);

			//Get the texture from the Android resource directory
			InputStream is = context.getResources().openRawResource(textureImageResourceId);
			Bitmap bitmap = null;
			try {
				//BitmapFactory is an Android graphics utility for images
				bitmap = BitmapFactory.decodeStream(is);
				//gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE,GL10.GL_DECAL);
				//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
				textureBitmap=bitmap;

			} finally {
				//Always clear and close
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					e.printStackTrace();

				}
			}



		}//end try
		catch(Exception e)
		{

			e.printStackTrace();

		}//end catch


	}

}
