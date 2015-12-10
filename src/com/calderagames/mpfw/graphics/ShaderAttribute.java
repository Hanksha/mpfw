package com.calderagames.mpfw.graphics;

/**
 * Holds the attributes and uniform's name of a {@link ShaderProgram}.
 */
public class ShaderAttribute {

	/**Name of the position attribute in the vertex shader*/
	public String POSITION_ATTR;
	/**Name of the texture coordinates attribute in the vertex shader*/
	public String TEXCOORD_ATTR;
	/**Name of the color attribute in the vertex shader*/
	public String COLOR_ATTR;
	/**Name of the projection matrix in the vertex shader*/
	public String PROJECTION_ATTR;
	/**Name of the model view matrix in the vertex shader*/
	public String MODELVIEW_ATTR;
	/**Name of the texture diffuse in the fragment shader*/
	public String TEXDIFFUSE_ATTR;
	
	/**Constructs a {@link ShaderProgram} attribute with default attributes.</br>
	 * Default attributes:
	 * <ul>
	 * <li>POSITION_ATTR = "in_position"</br></li>
	 * <li>TEXCOORD_ATTR = "in_texCoord"</br></li>
	 * <li>COLOR_ATTR = "in_color"</li>
	 * <li>PROJECTION_ATTR = "projection"</li>
	 * <li>MODELVIEW_ATTR = "modelView"</li>
	 * <li>TEXDIFFUSE_ATTR = "u_texDiffuse"</li>
	 * </ul>
	 */
	public ShaderAttribute() {
		POSITION_ATTR = "in_position";
		TEXCOORD_ATTR = "in_texCoord";
		COLOR_ATTR = "in_color";
		PROJECTION_ATTR = "projection";
		MODELVIEW_ATTR = "modelView";
		TEXDIFFUSE_ATTR = "u_texDiffuse";
	}
}
