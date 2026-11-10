package exporter;

import model.ResumeData;
import java.io.File;
import java.io.IOException;

public interface ResumeExporter {
    void export(ResumeData resume, File destinationFile) throws IOException;
    String getFormatName();
    String getFileExtension();
}
