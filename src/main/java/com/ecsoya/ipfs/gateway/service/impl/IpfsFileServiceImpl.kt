package com.ecsoya.ipfs.gateway.service.impl

import com.ecsoya.ipfs.gateway.IpfsGatewayProperties
import com.ecsoya.ipfs.gateway.domain.IpfsFile
import com.ecsoya.ipfs.gateway.exception.IpfsFileException
import com.ecsoya.ipfs.gateway.service.IIpfsFileService
import com.ecsoya.ipfs.gateway.util.IpfsUtil
import io.ipfs.api.MerkleNode
import io.ipfs.api.NamedStreamable
import io.ipfs.api.NamedStreamable.ByteArrayWrapper
import io.ipfs.cid.Cid
import io.ipfs.multihash.Multihash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class IpfsFileServiceImpl : IIpfsFileService {
    @Autowired
    private val config: IpfsGatewayProperties? = null
    private fun parse(nodes: List<MerkleNode>?): IpfsFile? {
        return if (nodes == null || nodes.isEmpty()) {
            null
        } else IpfsFile.create(nodes[0])
    }

    @Throws(IpfsFileException::class)
    override fun uploadFile(`in`: InputStream?, fileName: String?): IpfsFile? {
        if (`in` == null) {
            throw IpfsFileException("Upload failed by empty InputStream")
        }
        return try {
            parse(config!!.ipfs().add(NamedStreamable.InputStreamWrapper(fileName, `in`)))
        } catch (e: Exception) {
            throw IpfsFileException("Upload failed: ", e)
        }
    }

    @Throws(IpfsFileException::class)
    override fun uploadFile(data: ByteArray?, fileName: String?): IpfsFile? {
        if (data == null) {
            throw IpfsFileException("Upload failed by empty data")
        }
        return try {
            parse(config!!.ipfs().add(ByteArrayWrapper(fileName, data)))
        } catch (e: Exception) {
            throw IpfsFileException("Upload failed: ", e)
        }
    }

    @Throws(IpfsFileException::class)
    override fun downloadFile(hash: String?): IpfsFile? {
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
            val data = config!!.ipfs()[multihash]
            val file = IpfsFile()
            file.data = data
            file.url = config.gateway + hash
            file
        } catch (e: Exception) {
            throw IpfsFileException("Download failed: ", e)
        }
    }
}