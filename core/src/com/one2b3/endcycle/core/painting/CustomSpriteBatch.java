package com.one2b3.endcycle.core.painting;

import static com.badlogic.gdx.math.MathUtils.round;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.assets.Assets;
import com.one2b3.endcycle.engine.objects.forms.BatchRectangle;
import com.one2b3.endcycle.engine.shaders.CustomShaderProgram;
import com.one2b3.endcycle.engine.shaders.ShaderManager;
import com.one2b3.endcycle.engine.shaders.ShaderType;
import com.one2b3.endcycle.screens.menus.Colors;

public class CustomSpriteBatch extends SpriteBatch {

	static final String PATH_VERT = "shaders/default.vert", PATH_FRAG = "shaders/default.frag";
	static final Matrix4 VIEW = new Matrix4();
	static final Matrix4 TRANSFORM = new Matrix4();
	static final Affine2 AFFINE = new Affine2();

	final Matrix4 baseTransform = new Matrix4();
	final Array<Matrix4> transforms;

	final Stack<FrameBuffer> frameBuffers;
	final Stack<ShaderProgram> shaders;
	final Array<Rectangle> masks;
	final BatchRectangle batchRectangle;

	int mask = -1;
	boolean addTint;

	public CustomSpriteBatch() {
		this(new CustomShaderProgram(Assets.findHandle(PATH_VERT), Assets.findHandle(PATH_FRAG)));
	}

	public CustomSpriteBatch(ShaderProgram defaultShader) {
		super(2048, defaultShader);
		addTint = true;
		if (!getShader().isCompiled()) {
			Gdx.app.error("Shader", getShader().getLog());
		}
		frameBuffers = new Stack<>();
		shaders = new Stack<>();
		masks = new Array<>();
		for (int i = 0; i < 10; i++) {
			masks.add(new Rectangle());
		}
		transforms = new Array<>();
		batchRectangle = new BatchRectangle(Cardinal.getWidth(), Cardinal.getHeight());

		updateShaderParams();
	}

	public void startShader(ShaderProgram shader) {
		shaders.add(getShader());
		if (shader != getShader()) {
			setShader(shader);
		}
	}

	public void stopShader() {
		if (shaders.size() > 0) {
			ShaderProgram last = shaders.pop();
			if (last != getShader()) {
				setShader(last);
			}
		}
	}

	public void setShaderType(ShaderType shader) {
		setShader(shader == null ? null : shader.program);
	}

	@Override
	public void setShader(ShaderProgram shader) {
		super.setShader(shader);
		updateShaderParams();
	}

	private void updateShaderParams() {
		ShaderProgram shader = getShader();
		boolean addTint = shader instanceof CustomShaderProgram;
		if (addTint != this.addTint) {
			this.addTint = addTint;
			if (addTint) {
				setColor(Colors.temp(getColor()).mul(0.5F));
			} else {
				setColor(Colors.temp(getColor()).mul(2.0F));
			}
		}
	}

	@Override
	public void setColor(Color tint) {
		if (tint == null) {
			tint = Color.WHITE;
		}
		if (addTint) {
			super.setColor(tint.r * 0.5F, tint.g * 0.5F, tint.b * 0.5F, tint.a * 0.5F);
		} else {
			super.setColor(tint);
		}
	}

	@Override
	public void setPackedColor(float color) {
		if (addTint) {
			super.setPackedColor(color * 0.5F);
		} else {
			super.setPackedColor(color);
		}
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		if (addTint) {
			super.setColor(r * 0.5F, g * 0.5F, b * 0.5F, a * 0.5F);
		} else {
			super.setColor(r, g, b, a);
		}
	}

	public void drawScreenTint(float a) {
		drawScreenTint(0.0F, 0.0F, 0.0F, a);
	}

	public void drawScreenTint(Color c) {
		if (c != null) {
			drawScreenTint(c.r, c.g, c.b, c.a);
		}
	}

	public void drawScreenTint(float r, float g, float b, float a) {
		batchRectangle.setTint(r, g, b, a);
		batchRectangle.width = 2 / getProjectionMatrix().getScaleX();
		batchRectangle.height = 2 / getProjectionMatrix().getScaleY();
		Matrix4 transform = super.getTransformMatrix();
		float scaleX = transform.getScaleX();
		float scaleY = transform.getScaleY();
		batchRectangle.draw(this, -transform.val[Matrix4.M03] / scaleX, -transform.val[Matrix4.M13] / scaleY, 1.0F / scaleX, 1.0F / scaleY);
	}

	public void drawRectangle(float x, float y, float w, float h, Color c) {
		if (c != null) {
			drawRectangle(x, y, w, h, c.r, c.g, c.b, c.a);
		}
	}

	public void drawRectangle(float x, float y, float w, float h, float r, float g, float b, float a) {
		if (w < 0) {
			x += w;
			w *= -1;
		}
		if (h < 0) {
			y += h;
			h *= -1;
		}
		batchRectangle.width = w;
		batchRectangle.height = h;
		batchRectangle.setTint(r, g, b, a);
		batchRectangle.draw(this, x, y);
	}

	public void draw(FrameBuffer frameBuffer, float x, float y) {
		draw(frameBuffer, x, y, frameBuffer.getColorBufferTexture().getWidth(), frameBuffer.getColorBufferTexture().getHeight());
	}

	public void draw(FrameBuffer frameBuffer, float x, float y, float width, float height) {
		draw(frameBuffer.getColorBufferTexture(), x, y + height, width, -height);
	}

	public BatchRectangle getBatchRectangle() {
		return batchRectangle;
	}

	public void startMasking(float x, float y, float width, float height) {
		startMasking(x, y, width, height, false);
	}

	public void startMasking(float x, float y, float width, float height, boolean relative) {
		flush();
		if (width < 0) {
			x += width;
			width *= -1;
		}
		if (height < 0) {
			y += height;
			height *= -1;
		}
		float xOffset = super.getTransformMatrix().val[Matrix4.M03];
		float yOffset = super.getTransformMatrix().val[Matrix4.M13];
		float scaleX = getScaleX();
		float scaleY = getScaleY();

		int xs = round(x * scaleX + xOffset), ys = round(y * scaleY + yOffset);
		int w = round(width * scaleX), h = round(height * scaleY);
		Rectangle current = ScissorStack.peekScissors();
		if (current != null || (relative && mask >= 0)) {
			if (current == null) {
				current = getMask();
			}
			if (!Float.isNaN(current.x)) {
				w = Math.min(xs + w, (int) (current.x + current.width));
				h = Math.min(ys + h, (int) (current.y + current.height));
				xs = Math.max(xs, (int) current.x);
				ys = Math.max(ys, (int) current.y);
				w = Math.max(0, w - xs);
				h = Math.max(0, h - ys);
			}
		}
		Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
		Gdx.gl.glScissor(xs, ys, w, h);
		mask++;
		while (mask >= masks.size) {
			masks.add(new Rectangle());
		}
		masks.get(mask).set(xs, ys, w, h);
	}

	public Rectangle getMask() {
		return masks.get(mask);
	}

	public void resetBlendFunction() {
		setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE);
	}

	public void setBlendFunction(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
		flush();
		setBlendFunction(-1, -1);
		Gdx.gl.glBlendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
	}

	public void stopMasking() {
		mask--;
		Rectangle rect = (mask > -1 ? masks.get(mask) : null);
		flush();
		if (rect != null && !Float.isNaN(rect.x)) {
			Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
			Gdx.gl.glScissor((int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height);
		} else {
			Rectangle scissor = ScissorStack.peekScissors();
			if (mask > -1 || scissor == null) {
				Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
			} else {
				HdpiUtils.glScissor((int) scissor.x, (int) scissor.y, (int) scissor.width, (int) scissor.height);
			}
		}
	}

	public void bind(FrameBuffer buffer) {
		flush();
		Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
		masks.get(++mask).x = Float.NaN;

		if (!frameBuffers.isEmpty()) {
			frameBuffers.peek().end();
		}
		frameBuffers.push(buffer);

		buffer.begin();
		updateView(buffer);
	}

	public void clear() {
		Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public void clearTransparent() {
		Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public FrameBuffer unbind() {
		flush();
		FrameBuffer oldBuffer = frameBuffers.pop();
		oldBuffer.end();

		if (frameBuffers.isEmpty()) {
			updateView(null);
		} else {
			FrameBuffer buffer = frameBuffers.peek();
			buffer.begin();
			updateView(buffer);
		}
		stopMasking();
		return oldBuffer;
	}

	public void setScale(float scaleX, float scaleY) {
		TRANSFORM.set(AFFINE.setToScaling(scaleX, scaleY));
		setTransformMatrix(TRANSFORM);
	}

	public void setScaleTranslation(float x, float y, float scaleX, float scaleY) {
		TRANSFORM.set(AFFINE.setToScaling(scaleX, scaleY).translate(x, y));
		setTransformMatrix(TRANSFORM);
	}

	public final float getTranslationX() {
		return super.getTransformMatrix().val[Matrix4.M03] / super.getTransformMatrix().getScaleX();
	}

	public final float getTranslationY() {
		return super.getTransformMatrix().val[Matrix4.M13] / super.getTransformMatrix().getScaleY();
	}

	public final float getScaleX() {
		return super.getTransformMatrix().getScaleX();
	}

	public final float getScaleY() {
		return super.getTransformMatrix().getScaleY();
	}

	public void updateView(FrameBuffer buffer) {
		if (buffer == null) {
			VIEW.setToOrtho2D(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		} else {
			VIEW.setToOrtho2D(0, 0, buffer.getWidth(), buffer.getHeight());
		}
		setProjectionMatrix(VIEW);
	}

	public void reset() {
		if (isDrawing()) {
			end();
		}
		resetBlendFunction();
		if (mask > -1) {
			mask = -1;
			Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
		}
		if (frameBuffers.size() > 0) {
			frameBuffers.clear();
			FrameBuffer.unbind();
			updateView(null);
		}
	}

	@Override
	public void setTransformMatrix(Matrix4 transform) {
		baseTransform.set(transform);
		recalculateTransformMatrix();
	}

	@Override
	public Matrix4 getTransformMatrix() {
		return baseTransform;
	}

	public void addTransformMatrix(Matrix4 matrix) {
		transforms.add(matrix);
		recalculateTransformMatrix();
	}

	public void removeTransformMatrix(Matrix4 matrix) {
		if (transforms.removeValue(matrix, true)) {
			recalculateTransformMatrix();
		}
	}

	public void recalculateTransformMatrix() {
		Matrix4 transform = TRANSFORM.set(baseTransform);
		for (int i = 0; i < transforms.size; i++) {
			Matrix4 matrix = transforms.get(i);
			transform.scale(matrix.getScaleX(), matrix.getScaleY(), matrix.getScaleZ());
			transform.translate(matrix.val[Matrix4.M03] / matrix.getScaleX(), matrix.val[Matrix4.M13] / matrix.getScaleY(),
					matrix.val[Matrix4.M23] / matrix.getScaleZ());
		}
		super.setTransformMatrix(transform);
	}

	public void startOverrideShader() {
		if (ShaderManager.defaultOverride != null) {
			startShader(ShaderManager.defaultOverride);
		}
	}

	public void stopOverrideShader() {
		if (ShaderManager.defaultOverride != null) {
			stopShader();
		}
	}

}
