
import com.techbeloved.sample.xmlparser.AndroidInjector
import okio.source

// For android, we have to access it the usual way assets files are accessed.
// Okio provides a simple extension to convert an InputStream to a Source.
actual val defaultAssetFileProvider: AssetFileProvider = AssetFileProvider { path ->
    AndroidInjector.application.assets.open(
        path.removePrefix("assets/")
    ).source()
}