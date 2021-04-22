package com.markdownsite.web.installer;

import com.markdownsite.core.interfaces.SourceConfigInitializer;
import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.exceptions.PropertyValidationException;
import com.markdownsite.integration.exceptions.SourceNotFoundException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.models.SourceProviderConfigProperty;
import com.markdownsite.integration.providers.MarkdownSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@inheritDoc}
 * The type File based installer.
 * This installer is based on the file based properties.
 * The properties are read from the application.properties
 */
@Component
@ConfigurationProperties
public class FileBasedInstaller implements Installer{

    private SourceConfigInitializer sourceConfigInitializer;
    private MarkdownSourceProvider markdownSourceProvider;

    @Value("${source.identifier}")
    private String sourceIdentifier;

    @Autowired
    public FileBasedInstaller(@Qualifier("PropertyFileSourceConfigInitializer") SourceConfigInitializer sourceConfigInitializer,
                              MarkdownSourceProvider markdownSourceProvider) {
        this.sourceConfigInitializer = sourceConfigInitializer;
        this.markdownSourceProvider = markdownSourceProvider;
    }

    @Override
    public void install(InstallationMode installationMode) throws InstallerException {
        if (!InstallationMode.NEW.equals(installationMode))
            throw new InstallerException(InstallerErrorCode.INSTALLER_MODE_NOT_SUPPORTED);
        // Installation steps.
        // 1. Get all the configured source.
        // 2. Set the config of the source.
        // 3. Initialize the source.
        try {
            MarkdownSource source = markdownSourceProvider.getSource(sourceIdentifier);
            List<SourceProviderConfigProperty> sourceConfig = source.getSourceConfig();
            List<SourceProviderConfigProperty> updatedProperties = sourceConfigInitializer.initProperties(sourceConfig);
            source.updateSourceConfig(updatedProperties);
            source.initializeSource();
        } catch (SourceNotFoundException e) {
            e.printStackTrace();
        } catch (PropertyValidationException e) {
            e.printStackTrace();
        } catch (AbstractException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean supports(InstallationConfig installationConfig) {
        return InstallationConfig.FILE.equals(installationConfig);
    }
}
