package net.spacive.xlsx.content;

// TODO this might be overkill since polymorphism is not actually used
// simply, registration would take as parameter a source file and ContentType
public interface IContentFile extends ISourceFile {
    ContentType getContentType();
}
