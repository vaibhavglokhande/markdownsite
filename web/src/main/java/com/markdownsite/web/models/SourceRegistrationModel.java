package com.markdownsite.web.models;

import com.markdownsite.integration.models.SourceInfo;
import com.markdownsite.integration.models.SourceProviderConfigProperty;
import lombok.Data;

import java.util.List;

@Data
public class SourceRegistrationModel {
    private SourceInfo sourceInfo;
    private List<SourceProviderConfigProperty> configProperties;
}
