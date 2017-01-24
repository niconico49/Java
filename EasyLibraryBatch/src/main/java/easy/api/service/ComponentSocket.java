/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.api.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.codec.binary.Base64;
/**
 *
 * @author development
 */
public class ComponentSocket {
    private String magicPacketBase64 = "";
    private String ipAddress = "255.255.255.255";
    private int udpPort = 7;
    //private static final int UDP_PORT = 7;

    public String getIpAddress()
    {
        return this.ipAddress;
    }
    public void setIpAddress(String value)
    {
        this.ipAddress = value;
    }

    public int getUdpPort()
    {
        return this.udpPort;
    }
    public void setUdpPort(int value)
    {
        this.udpPort = value;
    }

    public String getMagicPacketBase64()
    {
        return this.magicPacketBase64;
    }
    public void setMagicPacketBase64(String value)
    {
        this.magicPacketBase64 = value;
    }

    public ComponentSocket getInstance()
    {
        return new ComponentSocket();
    }

    public String send() throws UnknownHostException, SocketException, IOException
    {
        String result = "";
        // create socket to IP
        final InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
        byte [] base64String = Base64.decodeBase64(this.magicPacketBase64.getBytes());
        //byte[] base64String = Base64.decodeBase64(this.magicPacketBase64);
        DatagramPacket datagramPacket = new DatagramPacket(base64String, base64String.length, inetAddress, this.udpPort);
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramSocket.send(datagramPacket);
            result += this.magicPacketBase64 + " send successfull!!\n";
        }
        catch (Exception e) {
            result += e.getMessage();
        }
        return result;
    }
}
