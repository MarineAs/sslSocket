import com.bumptech.glide.Glide
import java.io.*
import java.util.*
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory


class ClientSocket(private val host: String?, private val resourceLoc: String) {
    lateinit var factory: SSLSocketFactory
    lateinit var clientSocket: SSLSocket
    lateinit var imageByteArray: ByteArray


    fun makeRequest() {
        factory = SSLSocketFactory.getDefault() as SSLSocketFactory
        clientSocket = factory.createSocket(host, 443) as SSLSocket
        val writer = PrintWriter(clientSocket.getOutputStream())

        val protocol = "GET /$resourceLoc HTTP/1.1"
        val connection = "Connection: close"
        val headerEnd = ""
        val HostHeader = "Host: www.allelectronics.am"


        writer.println(protocol)
        writer.println(HostHeader)
        writer.println(connection)
        writer.println(headerEnd)
        writer.flush()
    }

    fun getData(): String {
        var input = DataInputStream(BufferedInputStream(clientSocket.inputStream))
        var br = BufferedReader(InputStreamReader(clientSocket.inputStream))
        var body: String
        lateinit var header: String
        var headerBuilder = StringBuilder()
        while (br.readLine() != null) {
            header = br.readLine()
            headerBuilder.append(header).append("\r\n")

        }

        return headerBuilder.toString()

    }

    fun getHeaders(): String {


        val reader = BufferedInputStream(clientSocket.inputStream)

        var byteCode = 0
        val builder = StringBuilder()

        while (reader.read().also { byteCode = it } != -1) {
            builder.append(byteCode.toChar())
        }

        val text = builder.toString()
        // sub[0] is supposed to contain the header and sub[1] should contain the bytes of the           image
        val sub = text.split("\r\n\r\n".toRegex()).toTypedArray()

        imageByteArray = sub[1].toByteArray()

        return sub[0]
    }

//    fun getImageByteArray(): ByteArray {
//
//return android.util.Base64.decode(imageByteArray,0)
//
//
//    }
}


