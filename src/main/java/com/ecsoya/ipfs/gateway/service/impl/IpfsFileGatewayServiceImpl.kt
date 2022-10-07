package com.ecsoya.ipfs.gateway.service.impl

import com.ecsoya.ipfs.gateway.IpfsGateway
import com.ecsoya.ipfs.gateway.domain.IpfsFile
import com.ecsoya.ipfs.gateway.exception.IpfsFileException
import com.ecsoya.ipfs.gateway.service.IIpfsFileGatewayService
import com.ecsoya.ipfs.gateway.util.IpfsUtil
import io.ipfs.api.MerkleNode
import io.ipfs.api.NamedStreamable
import io.ipfs.api.NamedStreamable.ByteArrayWrapper
import io.ipfs.cid.Cid
import io.ipfs.multihash.Multihash
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class IpfsFileGatewayServiceImpl : IIpfsFileGatewayService {
    private fun parse(nodes: List<MerkleNode>?): IpfsFile? {
        return if (nodes == null || nodes.isEmpty()) {
            null
        } else IpfsFile.create(nodes[0])
    }

    @Throws(IpfsFileException::class)
    override fun uploadFile(gateway: IpfsGateway?, `in`: InputStream?, fileName: String?): IpfsFile? {
        if ((gateway == null) || !gateway.valid()) {
            throw IpfsFileException("Please config IPFS address firstly")
        }
        if (`in` == null) {
            throw IpfsFileException("Upload failed by empty InputStream")
        }
        return try {
            parse(gateway.ipfs().add(NamedStreamable.InputStreamWrapper(fileName, `in`)))
        } catch (e: Exception) {
            throw IpfsFileException("Upload failed: ", e)
        }
    }

    @Throws(IpfsFileException::class)
    override fun uploadFile(gateway: IpfsGateway?, data: ByteArray?, fileName: String?): IpfsFile? {
        if (gateway == null || !gateway.valid()) {
            throw IpfsFileException("Please config IPFS address firstly")
        }
        return try {
            parse(gateway.ipfs().add(ByteArrayWrapper(fileName, data)))
        } catch (e: Exception) {
            throw IpfsFileException("Upload failed: ", e)
        }
    }

    @Throws(IpfsFileException::class)
    override fun downloadFile(gateway: IpfsGateway?, hash: String?): IpfsFile? {
        if (gateway == null || !gateway.valid()) {
            throw IpfsFileException("Please config IPFS address firstly")
        }
        if (IpfsUtil.isEmpty(hash)) {
            throw IpfsFileException("Download failed by using empty hash")
        }
        var multihash: Multihash? = null
        multihash = try {
            Cid.decode(hash)
        } catch (e: Exception) {
            throw IpfsFileException("Download failed by using invalid hash: $hash")
        }
        return try {
            val data = gateway.ipfs()[multihash]
            val file = IpfsFile()
            file.data = data
            file.url = gateway.gateway + hash
            file
        } catch (e: Exception) {
            throw IpfsFileException("Download failed: ", e)
        }
    }
}