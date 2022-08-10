package kz.garage.multimedia.store.ext

import androidx.annotation.StringRes
import kz.garage.file.extension.Extension
import kz.garage.multimedia.store.model.Content
import kz.garage.multimedia.store.model.Document

inline val Content.PublicFile.extension: Extension?
    get() = Extension::class.java.enumConstants?.find {
        it.value == getFile()?.extension
    }

inline val Content.RemoteFile.extension: Extension?
    get() = Extension::class.java.enumConstants?.find {
        it.value == url.split("/").last().split(".").last()
    }


inline val Content.representation: Int
    @StringRes
    get() = if (this is Document) representation else -1

inline val Document.representation: Int
    @StringRes
    get() = when (publicFile?.extension ?: remoteFile?.extension) {
        Extension.TXT -> R.string.multimedia_store_ext_text_file
        Extension.DOC, Extension.DOCX -> R.string.multimedia_store_ext_microsoft_word_document
        Extension.XLS, Extension.XLSX -> R.string.multimedia_store_ext_microsoft_excel_document
        Extension.PPT, Extension.PPTX -> R.string.multimedia_store_ext_microsoft_power_point_document
        Extension.PDF -> R.string.multimedia_store_ext_pdf_file
        Extension.HTML -> R.string.multimedia_store_ext_html_text
        else -> R.string.multimedia_store_ext_file
    }
