package usecase


import AssetFileProvider
import defaultAssetFileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import model.OpenLyricsSong
import nl.adaptivity.xmlutil.QName
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.core.XmlVersion
import nl.adaptivity.xmlutil.serialization.XML
import okio.buffer
import okio.use

class ReadOpenLyricsUseCase(
    private val assetFileProvider: AssetFileProvider = defaultAssetFileProvider,
    private val xml: XML = defaultXml,
) {
    suspend operator fun invoke(lyricsAsset: String) = withContext(Dispatchers.IO) {
        val lyricsXmlContent = assetFileProvider.get(lyricsAsset).use { fileSource ->
            fileSource.buffer().use { bufferedSource ->
                bufferedSource.readUtf8()
            }
        }
        try {
            val openLyricsSong = xml.decodeFromString(
                deserializer = OpenLyricsSong.serializer(),
                string = lyricsXmlContent
                    .replace("<br/>", "\n"),
                rootName = QName("http://openlyrics.info/namespace/2009/song", "song"),
            )
            Result.success(openLyricsSong)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}

val defaultXml: XML by lazy {
    XML {
        xmlVersion = XmlVersion.XML10
        xmlDeclMode = XmlDeclMode.Auto
        indentString = "  "
        repairNamespaces = true
    }
}