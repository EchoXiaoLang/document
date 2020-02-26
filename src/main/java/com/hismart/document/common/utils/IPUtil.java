package com.hismart.document.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IPUtil {

    private static final String UNKNOWN = "unknown";

    protected IPUtil() {

    }

    /**
     * 获取 IP地址
     * 使用 Nginx等反向代理软件， 则不能通过 request.getRemoteAddr()获取 IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，
     * X-Forwarded-For中第一个非 unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }


    public static List<String> getLocalAddress() throws SocketException {
        List<String>  ips=new ArrayList<>();
        Enumeration<NetworkInterface> IFaces = NetworkInterface.getNetworkInterfaces();

        while (IFaces.hasMoreElements()) {
            NetworkInterface fInterface = IFaces.nextElement();
            if (!fInterface.isVirtual() && !fInterface.isLoopback() && fInterface.isUp()) {
                Enumeration<InetAddress> adds = fInterface.getInetAddresses();
                while (adds.hasMoreElements()) {
                    InetAddress address = adds.nextElement();
                    byte[] bs = address.getAddress();
                    if (bs.length == 4) {
                        ips.add(address.getHostAddress());
                    }
                }
            }
        }
        return ips;
    }





}
