package kz.garage.file.extension

enum class Extensions constructor(val values: Set<Extension>) {
    IMAGE(setOf(Extension.JPG, Extension.JPEG, Extension.PNG)),

    AUDIO(setOf(Extension.MP3, Extension.WAV, Extension.OPUS, Extension.OGG)),

    VIDEO(setOf(Extension.MP4, Extension.MOV, Extension.WEBM, Extension.MKV, Extension.AVI)),

    DOCUMENT(
        setOf(
            Extension.TXT,
            Extension.HTML,
            Extension.DOC,
            Extension.DOCX,
            Extension.XLS,
            Extension.XLSX,
            Extension.PDF
        )
    )
}