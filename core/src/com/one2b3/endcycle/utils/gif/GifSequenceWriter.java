package com.one2b3.endcycle.utils.gif;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

import com.one2b3.endcycle.engine.proguard.KeepClass;

/**
 * @author Elliot Kroo (elliot[at]kroo[dot]net)
 */
@KeepClass
public class GifSequenceWriter {
	protected final ImageWriter gifWriter;
	protected final ImageWriteParam imageWriteParam;
	protected final IIOMetadata imageMetaData;
	protected IIOImage image;

	public GifSequenceWriter(ImageOutputStream outputStream, BufferedImage image, int timeBetweenFramesMS)
			throws IIOException, IOException {
		// my method to create a writer
		gifWriter = getWriter();
		imageWriteParam = gifWriter.getDefaultWriteParam();

		ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(image.getType());

		imageMetaData = gifWriter.getDefaultImageMetadata(imageTypeSpecifier, imageWriteParam);

		String metaFormatName = imageMetaData.getNativeMetadataFormatName();

		IIOMetadataNode root = (IIOMetadataNode) imageMetaData.getAsTree(metaFormatName);

		IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");

		graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
		graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
		graphicsControlExtensionNode.setAttribute("transparentColorFlag", "TRUE");
		graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString((int) Math.ceil(timeBetweenFramesMS * 0.1F)));
		graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

		IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
		commentsNode.setAttribute("CommentExtension", "EndCycle VS GIF");

		IIOMetadataNode appEntensionsNode = getNode(root, "ApplicationExtensions");

		IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");

		child.setAttribute("applicationID", "NETSCAPE");
		child.setAttribute("authenticationCode", "2.0");

		child.setUserObject(new byte[] { 0x1, (byte) (0), (byte) ((0) & 0xFF) });
		appEntensionsNode.appendChild(child);

		imageMetaData.setFromTree(metaFormatName, root);

		gifWriter.setOutput(outputStream);
		gifWriter.prepareWriteSequence(imageMetaData);

		this.image = new IIOImage(image, null, imageMetaData);
	}

	public void write(BufferedImage image) throws IOException {
		this.image.setRenderedImage(image);
		gifWriter.writeToSequence(this.image, null);
	}

	public void close() throws IOException {
		gifWriter.endWriteSequence();
	}

	private static ImageWriter getWriter() throws IIOException {
		Iterator<ImageWriter> iter = ImageIO.getImageWritersBySuffix("gif");
		if (!iter.hasNext()) {
			throw new IIOException("No GIF Image Writers Exist");
		} else {
			return iter.next();
		}
	}

	private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
		int nNodes = rootNode.getLength();
		for (int i = 0; i < nNodes; i++) {
			if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName) == 0) {
				return ((IIOMetadataNode) rootNode.item(i));
			}
		}
		IIOMetadataNode node = new IIOMetadataNode(nodeName);
		rootNode.appendChild(node);
		return (node);
	}
}
