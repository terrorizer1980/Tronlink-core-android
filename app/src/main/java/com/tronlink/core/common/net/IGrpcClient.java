package com.tronlink.core.common.net;


import com.tronlink.core.common.StringTronUtil;

public enum IGrpcClient {
    THIS;
    private GrpcClient rpcCli;

    public GrpcClient getCli() {
        if (rpcCli == null) initGRpc();
        if (!rpcCli.canUseSolidityNode()) rpcCli.connetSolidityNode();
        if (!rpcCli.canUseFullNode()) rpcCli.connetFullNode();
        return rpcCli;
    }

    public String getCurrentSol() {
        return rpcCli.randomSolodityNode();
    }

    public String getCurrentFullnode() {
        return rpcCli.randomFullNode();
    }

    public void initGRpc() {
        if (rpcCli != null) {
            try {
                rpcCli.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String ip = "", ip_sol = "";

//        try {
//            ip = SpAPI.THIS.getIP();
//            ip_sol = SpAPI.THIS.getSolIP();
//
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }

        rpcCli = new GrpcClient();
        if (StringTronUtil.isEmpty(ip_sol)) {
            rpcCli.connetSolidityNode();
        } else {
            rpcCli.connetSolidityNode(ip_sol);
        }
        if (StringTronUtil.isEmpty(ip)) {
            rpcCli.connetFullNode();
        } else {
            rpcCli.connetFullNode(ip);
        }
    }

}
