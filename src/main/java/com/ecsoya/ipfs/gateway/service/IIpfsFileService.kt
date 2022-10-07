package com.ecsoya.ipfs.gateway.service

import com.ecsoya.ipfs.gateway.domain.IpfsFile
import com.ecsoya.ipfs.gateway.exception.IpfsFileException
import java.io.InputStream

interface IIpfsFileService {
    /**
     * Upload file with inputstream
     */
    @Throws(IpfsFileException::class)
    fun uploadFile(`in`: InputStream?, fileName: String?): IpfsFile?

    /**
     * Upload file with bytes
     */
    @Throws(IpfsFileException::class)
    fun uploadFile(data: ByteArray?, fileName: String?): IpfsFile?

    /**
     * Hex of Hash
     */
    @Throws(IpfsFileException::class)
    fun downloadFile(hash: String?): IpfsFile?
}