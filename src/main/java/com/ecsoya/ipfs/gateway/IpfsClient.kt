package com.ecsoya.ipfs.gateway

import io.ipfs.api.IPFS
import io.ipfs.multiaddr.MultiAddress

class IpfsClient : IPFS {
    var gateway: String? = null

    constructor(addr: MultiAddress?) : super(addr) {}
    constructor(host: String?, port: Int, version: String?, ssl: Boolean) : super(host, port, version, ssl) {}
    constructor(
        host: String?, port: Int, version: String?, connectTimeoutMillis: Int, readTimeoutMillis: Int,
        ssl: Boolean
    ) : super(host, port, version, connectTimeoutMillis, readTimeoutMillis, ssl) {
    }

    constructor(host: String?, port: Int) : super(host, port) {}
    constructor(multiaddr: String?) : super(multiaddr) {}
}