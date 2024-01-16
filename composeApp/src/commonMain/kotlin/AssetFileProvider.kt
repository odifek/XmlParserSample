import okio.Source

expect val defaultAssetFileProvider: AssetFileProvider
/**
 * Exposes a single method that returns [okio.Source] with which we can read/stream the asset file
 * The path is the assets/filepath of the file stored in resources/assets
 */
fun interface AssetFileProvider {
    fun get(path: String): Source
}
