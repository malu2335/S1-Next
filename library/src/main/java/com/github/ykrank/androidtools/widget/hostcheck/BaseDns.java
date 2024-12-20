package com.github.ykrank.androidtools.widget.hostcheck;


import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.github.ykrank.androidtools.util.L;
import com.github.ykrank.androidtools.util.LooperUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Dns;

public class BaseDns implements Dns {
    private static final Dns SYSTEM = Dns.SYSTEM;

    private final BaseHostUrl baseHostUrl;

    private List<InetAddress> inetAddressList;
    private String forceHostIp;

    public BaseDns(BaseHostUrl baseHostUrl) {
        this.baseHostUrl = baseHostUrl;
    }

    @NonNull
    @Override
    public List<InetAddress> lookup(@NonNull String hostname) throws UnknownHostException {
        try {
            checkInetAddress();
            if (inetAddressList != null && !inetAddressList.isEmpty() && baseHostUrl.getBaseHttpUrl() != null
                    && TextUtils.equals(baseHostUrl.getBaseHttpUrl().host(), hostname)) {
                return inetAddressList;
            }
        } catch (Exception e) {
            L.print(e);
        }
        return SYSTEM.lookup(hostname);
    }

    public void checkInetAddress() throws UnknownHostException {
        if (!TextUtils.equals(forceHostIp, baseHostUrl.getHostIp())) {
            inetAddressList = checkHostIp(baseHostUrl.getHostIp());
            forceHostIp = baseHostUrl.getHostIp();
        }
    }

    /**
     * Check whether this ip list is valid. ip should like
     *
     * @param hostIpList ip list, sep by ','
     * @return valid InetAddress
     */
    @WorkerThread
    @NonNull
    public static List<InetAddress> checkHostIp(@Nullable String hostIpList) {
        LooperUtil.enforceOnWorkThread();
        List<InetAddress> inetAddressList = new ArrayList<>();
        if (!TextUtils.isEmpty(hostIpList)) {
            String[] hostIps = hostIpList.split(",");
            for (String hostIp : hostIps) {
                try {
                    inetAddressList.add(InetAddress.getByName(hostIp.trim()));
                } catch (UnknownHostException e) {
                    L.print(e);
                }
            }
        }

        return inetAddressList;
    }
}
