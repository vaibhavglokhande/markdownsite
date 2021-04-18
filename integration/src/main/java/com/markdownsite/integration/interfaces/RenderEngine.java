package com.markdownsite.integration.interfaces;

import com.markdownsite.integration.models.RenderEngineConfigProperty;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * The interface Render engine.
 */
public interface RenderEngine {

    /**
     * Render string.
     *
     * @param markdownContent the markdown content
     * @return the string
     */
    String render(String markdownContent);

    /**
     * Render string.
     *
     * @param markdownFile the markdown file
     * @return the string
     * @throws IOException the io exception
     */
    String render(File markdownFile) throws IOException;

    /**
     * Engine name string.
     *
     * @return the string
     */
    String engineName();

    /**
     * Engine description string.
     *
     * @return the string
     */
    String engineDescription();

    /**
     * Gets config.
     *
     * @return the config
     */
    Map<String, RenderEngineConfigProperty> getConfig();

    /**
     * Update render engine config.
     *
     * @param renderEngineConfig the render engine config
     */
    void updateRenderEngineConfig(Map<String, RenderEngineConfigProperty> renderEngineConfig);

    /**
     * Supports boolean.
     *
     * @param uuid the uuid
     * @return the boolean
     */
    boolean supports(String uuid);

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    UUID getUUID();
}
