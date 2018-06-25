package com.bignerdranch.android.p2pchat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private ConnectionLab mConnectionLab;
    private ConnectionActivity mActivity;
    private WifiP2pManager.PeerListListener mPeerListListener;
    public boolean WIFI_STATUS = false;

    public WifiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, ConnectionActivity activity) {
        super();

        mManager = manager;
        mChannel = channel;
        mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                WIFI_STATUS = true;
            } else {
                WIFI_STATUS = false;
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            mManager.requestPeers(mChannel, (WifiP2pManager.PeerListListener) mActivity.getSupportFragmentManager().findFragmentById(R.id.fragment_container));
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }
    }
}
