package kz.garage.multimedia.store.projection

import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns

sealed class Projection private constructor() {

    abstract fun get(): List<String>

    object Image : Projection() {
        override fun get(): List<String> {
            val projection = mutableListOf(
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DISPLAY_NAME,
                MediaStore.Images.ImageColumns.TITLE,
                MediaStore.Images.ImageColumns.DESCRIPTION,
                MediaStore.Images.ImageColumns.SIZE,
                MediaStore.Images.ImageColumns.DATE_ADDED,
                MediaStore.Images.ImageColumns.DATE_MODIFIED,
                MediaStore.Images.ImageColumns.MIME_TYPE,
                MediaStore.Images.ImageColumns.WIDTH,
                MediaStore.Images.ImageColumns.HEIGHT
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                projection.add(MediaStore.Images.ImageColumns.BUCKET_ID)
                projection.add(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)
                projection.add(MediaStore.Images.ImageColumns.DATE_TAKEN)
                projection.add(MediaStore.Images.ImageColumns.VOLUME_NAME)
            }

            return projection
        }
    }

    object Video : Projection() {
        override fun get(): List<String> {
            val projection = mutableListOf(
                MediaStore.Video.VideoColumns._ID,
                MediaStore.Video.VideoColumns.TITLE,
                MediaStore.Video.VideoColumns.DISPLAY_NAME,
                MediaStore.Video.VideoColumns.DESCRIPTION,
                MediaStore.Video.VideoColumns.DATE_ADDED,
                MediaStore.Video.VideoColumns.DATE_MODIFIED,
                MediaStore.Video.VideoColumns.MIME_TYPE,
                MediaStore.Video.VideoColumns.SIZE,
                MediaStore.Video.VideoColumns.WIDTH,
                MediaStore.Video.VideoColumns.HEIGHT
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                projection.add(MediaStore.Video.VideoColumns.BUCKET_ID)
                projection.add(MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME)
                projection.add(MediaStore.Video.VideoColumns.DATE_TAKEN)
                projection.add(MediaStore.Video.VideoColumns.DURATION)
                projection.add(MediaStore.Video.VideoColumns.VOLUME_NAME)
            }

            return projection
        }
    }

    object Audio : Projection() {
        override fun get(): List<String> {
            val projection = mutableListOf(
                MediaStore.Audio.AudioColumns._ID,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DISPLAY_NAME,
                MediaStore.Audio.AudioColumns.DATE_ADDED,
                MediaStore.Audio.AudioColumns.DATE_MODIFIED,
                MediaStore.Audio.AudioColumns.MIME_TYPE,
                MediaStore.Audio.AudioColumns.SIZE,
                MediaStore.Audio.AudioColumns.ALBUM_ID,
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                projection.add(MediaStore.Audio.AudioColumns.BUCKET_ID)
                projection.add(MediaStore.Audio.AudioColumns.BUCKET_DISPLAY_NAME)
                projection.add(MediaStore.Audio.AudioColumns.DATE_TAKEN)
                projection.add(MediaStore.Audio.AudioColumns.DURATION)
                projection.add(MediaStore.Audio.AudioColumns.VOLUME_NAME)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                projection.add(MediaStore.Audio.AudioColumns.ALBUM)
                projection.add(MediaStore.Audio.AudioColumns.ALBUM_ARTIST)
            }

            return projection
        }
    }

    object File : Projection() {
        override fun get(): List<String> {
            val projection = mutableListOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.PARENT,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.WIDTH,
                MediaStore.Files.FileColumns.HEIGHT
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                projection.add(MediaStore.Files.FileColumns.BUCKET_ID)
                projection.add(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)
                projection.add(MediaStore.Files.FileColumns.DATE_TAKEN)
                projection.add(MediaStore.Files.FileColumns.DURATION)
                projection.add(MediaStore.Files.FileColumns.VOLUME_NAME)
            }

            return projection
        }
    }

    object Openable : Projection() {
        override fun get(): List<String> {
            return listOf(
                OpenableColumns.DISPLAY_NAME,
                OpenableColumns.SIZE
            )
        }
    }
}