package com.bignerdranch.android.p2pchat;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;

import java.util.Collection;

public class ConnectionLab {
    private static Collection<WifiP2pDevice> mWifiP2pDeviceList =;

    public static void setWifiP2pDeviceList(WifiP2pDeviceList wifiP2pDeviceList) {
        mWifiP2pDeviceList = wifiP2pDeviceList.getDeviceList();
    }

    public static WifiP2pDeviceList getWifiP2pDeviceList() {
        if (mWifiP2pDeviceList == null) {
            return
        }
        return mWifiP2pDeviceList;
    }
}
