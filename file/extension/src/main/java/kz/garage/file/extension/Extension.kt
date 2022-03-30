package kz.garage.file.extension

enum class Extension constructor(val value: String) {
    // Image
    BMP("bmp"),

    JPG("jpg"),
    JPEG("jpeg"),

    PNG("png"),

    GIF("gif"),

    // Text
    TXT("txt"),

    // Microsoft Word
    DOC("doc"),
    DOCX("docx"),

    // Microsoft PowerPoint
    PPT("ppt"),
    PPTX("pptx"),

    // Microsoft Excel
    XLS("xls"),
    XLSX("xlsx"),

    CSV("csv"),

    PDF("pdf"),

    HTML("html"),

    MP3("mp3"),
    M4A("m4a"),
    WAV("wav"),

    THREE_GP("3gp"),
    THREE_GPP("3gpp"),

    AMR("amr"),

    AAC("aac"),

    OPUS("opus"),
    OGG("ogg"),

    FLAC("flac"),

    // Video
    MP4("mp4"),
    MOV("mov"),
    WEBM("webm"),
    MKV("mkv"),
    AVI("avi")
}