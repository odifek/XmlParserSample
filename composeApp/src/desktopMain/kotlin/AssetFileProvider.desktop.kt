import okio.FileSystem
import okio.Path.Companion.toPath

// Okio provides a dedicated RESOURCES filesystem for accessing resources bundled in the jar file.
actual val defaultAssetFileProvider: AssetFileProvider = AssetFileProvider { path ->
    FileSystem.RESOURCES.source(path.toPath())
}