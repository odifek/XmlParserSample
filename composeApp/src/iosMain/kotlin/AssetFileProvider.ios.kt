import okio.FileSystem
import okio.Path.Companion.toPath
import platform.Foundation.NSBundle

// bundled resources are kept in compose-resources path in iOS framework.
// See org.jetbrains.compose.resources.Resource.ios.kt
// This is only possible when we add compose resources dependency. Otherwise, I have not found an easy way to do it.
actual val defaultAssetFileProvider: AssetFileProvider = AssetFileProvider { path ->
    val assetFile = NSBundle.mainBundle.resourcePath + "/compose-resources/" + path
    FileSystem.SYSTEM.source(assetFile.toPath())
}