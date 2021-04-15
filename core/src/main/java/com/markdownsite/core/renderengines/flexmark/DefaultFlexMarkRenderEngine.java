package com.markdownsite.core.renderengines.flexmark;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component("FlexMarkRenderEngine")
public class DefaultFlexMarkRenderEngine extends AbstractFlexMarkRenderEngine {

    private UUID uuid = UUID.nameUUIDFromBytes(this.getClass().getName().getBytes(StandardCharsets.UTF_8));

    @Override
    public boolean supports(String uuid) {
        return this.uuid.toString().equals(uuid);
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }
}
