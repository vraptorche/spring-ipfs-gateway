package com.ecsoya.ipfs.gateway.domain

import io.ipfs.api.MerkleNode

class IpfsFile {
    lateinit var data: ByteArray
    var name: String? = null
    var hash: String? = null
    var url: String? = null
    override fun toString(): String {
        return String.format("IpfsFile [name=%s, hash=%s, url=%s]", name, hash, url)
    }

    companion object {
        fun create(node: MerkleNode?): IpfsFile? {
            if (node == null) {
                return null
            }
            val file = IpfsFile()
            file.data = node.data.orElse(null)
            file.hash = node.hash.toString()
            file.name = node.name.orElse(null)
            return file
        }
    }
}