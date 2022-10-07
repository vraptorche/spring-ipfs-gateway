package com.ecsoya.ipfs.gateway

import io.ipfs.multiaddr.MultiAddress
import org.springframework.util.ObjectUtils

class IpfsGateway(var address: String, var gateway: String) {
    private var ipfs: IpfsClient? = null
    override fun toString(): String {
        return String.format("IpfsGateway [address=%s, gateway=%s]", address, gateway)
    }

    fun ipfs(): IpfsClient {
        if (ipfs == null) {
            ipfs = IpfsClient(address)
            ipfs!!.gateway = gateway
        }
        return ipfs!!
    }

    fun valid(): Boolean {
        if (ObjectUtils.isEmpty(address)) {
            return false
        }
        try {
            MultiAddress(address)
        } catch (e: Exception) {
            return false
        }
        return true
    }
}