package stormTP.stream

import java.io.{IOException, Serializable}
import java.net.{DatagramPacket, DatagramSocket, InetAddress}

import org.slf4j.LoggerFactory

class StreamEmiter(private val port: Int, private val ip: String) extends Serializable {
  private val logger = LoggerFactory.getLogger(getClass)

  def send(row: String): Unit = {
    val udpSocket = new DatagramSocket()
    try {
      val mcIPAdress = InetAddress.getByName(ip)
      val msg = row.toString.getBytes
      val packet = new DatagramPacket(msg, msg.length)
      packet.setAddress(mcIPAdress)
      packet.setPort(port)
      udpSocket.send(packet)
      logger.info("Sent '{}'", row)
      Thread.sleep(1500)
    } catch {
      case e: IOException =>
        logger.error("IO problem in StreamEmiter", e)
      case e: InterruptedException =>
        logger.error("Thread problem in StreamEmiter", e)
    } finally {
      udpSocket.close()
    }
  }

  override def toString: String = ("StreamEmiter[port=" + port + ", ipMultiC=" + ip + "]")
}
