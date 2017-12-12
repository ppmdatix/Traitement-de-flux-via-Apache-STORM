package stormTP.stream

import java.net.{DatagramPacket, InetAddress, MulticastSocket}
import java.util.concurrent.ConcurrentLinkedQueue

import org.slf4j.LoggerFactory

class StreamBuffer(private val port: Int, private val ip: String) extends Serializable {
  private val logger = LoggerFactory.getLogger(getClass)
  private val fifo = new ConcurrentLinkedQueue[String]()

  def listenStream(): Unit = {
    val mcSocket = new MulticastSocket(port)
    val mcIPAddress = InetAddress.getByName(ip);
    try {
      mcSocket.joinGroup(mcIPAddress)
      val packet = new DatagramPacket(new Array[Byte](1024), 1024)
      mcSocket.receive(packet)
      val msg = new String(packet.getData, packet.getOffset, packet.getLength)
      fifo.add(msg)
    } catch {
      case e: Throwable => logger.error("Error when listening to stream", e)
    } finally {
      try {
        mcSocket.leaveGroup(mcIPAddress)
        mcSocket.close
      } catch {
        case e: Throwable => logger.error("Error when listening to stream", e)
      }
    }
  }

  def readTuple(): String = fifo.poll()
}