package com.markdownsite.integration.models;

import lombok.Data;

import java.util.List;

@Data
public class SourceProviderConfig {
    private List<SourceProviderConfigProperty<?>> providerConfigProperties;

    public SourceProviderConfig(List<SourceProviderConfigProperty<?>> providerConfigProperties) {
        this.providerConfigProperties = providerConfigProperties;
    }
}
