package com.markdownsite.integration.interfaces;

import java.util.List;

/**
 * The interface Resource provider.
 * This interface provides the methods to configure the JS and CSS resources that
 * can be loaded at runtime.
 */
public interface ResourceConfig {

    /**
     * Provide the links to the files of js resources. This can be link to static js or cdn.
     *
     * <br>
     * <p><b>Examples:</b></p>
     * <p>scripts/jquery.min.js</p>
     * <p>https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js</p>
     *
     * @return the js resources
     */
    List<String> getJsResources();

    /**
     * Provide the links to the files of css resources. This can be link to static css or cdn.
     *
     * <br>
     *      * <p><b>Examples:</b></p>
     *      * <p>css/custom.css</p>
     *      * <p>https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css</p>
     * @return the css resources
     */
    List<String> getCssResources();

    /**
     * Provide the inline scripts. The inline scripts are loaded in script tag
     *
     * <br>
     *
     * <p><b>Example:</b></p>
     * <code>
     *     document.getElementById("id");
     * </code>
     *
     * @return the inline js resources
     */
    List<String> getInlineJsResources();

    /**
     *Provide the inline styles. The inline css resources are loaded in style tag
     *
     * <br>
     * <p><b>Example:</b></p>
     * <code>
     *     .container {
     *         margin: 10px;
     *     }
     * </code>
     *
     * @return the inline css resources
     */
    List<String> getInlineCssResources();
}
