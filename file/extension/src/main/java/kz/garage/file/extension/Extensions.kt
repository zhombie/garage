package kz.garage.file.extension

enum class Extensions constructor(val values: Set<Extension>) {
    IMAGE(
        setOf(
            Extension.BMP,
            Extension.JPG,
            Extension.JPEG,
            Extension.PNG,
            Extension.RAW,
            Extension.GIF,
            Extension.WEBP
        )
    ),

    AUDIO(
        setOf(
            Extension.MP3,
            Extension.WAV,
            Extension.M4A,
            Extension.OPUS,
            Extension.OGG,
            Extension.FLAC
        )
    ),

    VIDEO(
        setOf(
            Extension.MP4,
            Extension.MOV,
            Extension.WEBM,
            Extension.MKV,
            Extension.THREE_GP,
            Extension.THREE_GPP,
            Extension.AVI
        )
    ),

    DOCUMENT(
        setOf(
            Extension.TXT,
            Extension.EPUB,
            Extension.PAGES,
            Extension.HTM,
            Extension.HTML,
            Extension.DOC,
            Extension.DOCX,
            Extension.PPT,
            Extension.PPTX,
            Extension.CSV,
            Extension.XLS,
            Extension.XLSX,
            Extension.PDF
        )
    ),

    ARCHIVE(
        setOf(
            Extension.SEVEN_ZIP,
            Extension.RAR,
            Extension.ZIP,
            Extension.ZIPX
        )
    ),
}