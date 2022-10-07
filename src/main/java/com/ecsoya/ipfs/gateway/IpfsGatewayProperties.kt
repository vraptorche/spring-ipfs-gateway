package com.ecsoya.ipfs.gateway

import com.ecsoya.ipfs.gateway.util.IpfsUtil
import io.ipfs.multiaddr.MultiAddress
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("spring.ipfs")
open class IpfsGatewayProperties {
    private var address: String? = "/ip4/127.0.0.1/tcp/5001"
    var host = "127.0.0.1"
    var port = 5001
    var version = "/api/v0/"
    var isSsl = false
    var connectTimeoutMillis = 10000
    var readTimeoutMillis = 60000
    var gateway: String? = null
    private var ipfs: IpfsClient? = null
    fun getAddress(): String {
        if (address == null || address == "") {
            address = "/ip4/$host/tcp/$port"
        }
        return address as String
    }

    fun setAddress(address: String?) {
        this.address = address
    }

    fun ipfs(): IpfsClient {
        if (ipfs == null) {
            if (IpfsUtil.isNotEmpty(address)) {
                try {
                    val ma = MultiAddress(address)
                    host = ma.host
                    port = ma.tcpPort
                    isSsl = ma.toString().contains("https")
                } catch (e: Exception) {
                }
            }
            ipfs = IpfsClient(host, port, version, connectTimeoutMillis, readTimeoutMillis, isSsl)
            ipfs!!.gateway = gateway
        }
        return ipfs!!
    }
}