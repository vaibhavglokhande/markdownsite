package com.markdownsite.core.services;

import com.markdownsite.core.interfaces.SourceService;
import com.markdownsite.integration.exceptions.SourceException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.models.SourceInfo;
import com.markdownsite.integration.providers.MarkdownSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SourceServiceImpl implements SourceService {

    private MarkdownSourceProvider sourceProvider;

    @Autowired
    public SourceServiceImpl(MarkdownSourceProvider sourceProvider) {
        this.sourceProvider = sourceProvider;
    }

    @Override
    public List<SourceInfo> getAllSourcesInfo() throws SourceException {
        Map<String, MarkdownSource> allSources = sourceProvider.getAllSources();
        List<SourceInfo> sourceInfoList = new ArrayList<>();
        allSources.forEach((key, markdownSource) -> {
            SourceInfo sourceInfo = new SourceInfo();
            sourceInfo.setSourceId(key);
            sourceInfo.setSourceName(markdownSource.sourceName());
            sourceInfo.setSourceDescription(markdownSource.sourceDescription());
            sourceInfoList.add(sourceInfo);
        });
        return sourceInfoList;
    }

    @Override
    public SourceInfo getSourceInfo(String sourceId) throws SourceException {
        MarkdownSource source = sourceProvider.getSource(sourceId);
        SourceInfo sourceInfo = new SourceInfo();
        sourceInfo.setSourceId(sourceId);
        sourceInfo.setSourceName(source.sourceName());
        sourceInfo.setSourceDescription(source.sourceDescription());
        return sourceInfo;
    }
}
