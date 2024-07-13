package edu.fhsu.summer.csci441.group1.ZoomBuddy.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jakarta.servlet.http.HttpServletRequest;

public class IpChecker {

    public static boolean isLocalhost(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();

        try {
            InetAddress addr = InetAddress.getByName(remoteAddr);
            return addr.isLoopbackAddress() || addr.isAnyLocalAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
    }
}
