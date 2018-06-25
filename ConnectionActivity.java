package com.bignerdranch.android.p2pchat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ConnectionActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ConnectionFragment();
    }
}
