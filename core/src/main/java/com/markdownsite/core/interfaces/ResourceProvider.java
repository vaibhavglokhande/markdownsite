package com.markdownsite.core.interfaces;

import java.util.Set;

/**
 * The interface Resource provider.
 * This interface provides the static resources.
 */
public interface ResourceProvider {
    /**
     * Provides the list of links to js resources
     *
     * <br>
     * <p><b>Examples:</b></p>
     * <p>scripts/jquery.min.js</p>
     * <p>https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js</p>
     *
     * @return the js resources
     */
    Set<String> getJsResources();

    /**
     * Provides the links to the files of css resources.
     *
     * <br>
     * * <p><b>Examples:</b></p>
     * * <p>css/custom.css</p>
     * * <p>https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css</p>
     *
     * @return the css resources
     */
    Set<String> getCssResources();

    /**
     * Provides the inline scripts. The inline scripts are loaded in script tag
     *
     * <br>
     *
     * <p><b>Example:</b></p>
     * <code>
     * document.getElementById("id");
     * </code>
     *
     * @return the inline js resources
     */
    Set<String> getInlineJsResources();

    /**
     * Provides the inline styles. The inline css resources are loaded in style tag
     *
     * <br>
     * <p><b>Example:</b></p>
     * <code>
     * .container {
     * margin: 10px;
     * }
     * </code>
     *
     * @return the inline css resources
     */
    Set<String> getInlineCssResources();
}
