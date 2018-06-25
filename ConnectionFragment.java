package com.bignerdranch.android.p2pchat;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

import static android.os.Looper.getMainLooper;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectionFragment extends Fragment implements WifiP2pManager.PeerListListener{

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    private RecyclerView mConnectionRecyclerView;
    private ConnectionAdapter mAdapter;

    public ConnectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_connection_list, container, false);
        mConnectionRecyclerView = view.findViewById(R.id.connection_recycler_view);
        mConnectionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        mManager = (WifiP2pManager) getActivity().getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(getActivity(), getMainLooper(), null);
        mReceiver = new WifiDirectBroadcastReceiver(mManager,mChannel, (ConnectionActivity) getActivity());

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        getActivity().registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.menu.search_menu:
                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(int i) {

                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new ConnectionAdapter(ConnectionLab.getWifiP2pDeviceList());
            mConnectionRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {

    }

    private class ConnectionHolder extends RecyclerView.ViewHolder {
        public ConnectionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_connection_item, parent, false));
        }
    }

    private class ConnectionAdapter extends RecyclerView.Adapter<ConnectionHolder> {

        private Collection<WifiP2pDevice> mWifiP2pDeviceList;

        public ConnectionAdapter(WifiP2pDeviceList list) {
            mWifiP2pDeviceList = list.getDeviceList();
        }

        @NonNull
        @Override
        public ConnectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ConnectionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ConnectionHolder connectionHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return mWifiP2pDeviceList.size();
        }
    }


}
