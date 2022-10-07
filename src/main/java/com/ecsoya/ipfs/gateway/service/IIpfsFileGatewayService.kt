package com.ecsoya.ipfs.gateway.service

import com.ecsoya.ipfs.gateway.IpfsGateway
import com.ecsoya.ipfs.gateway.domain.IpfsFile
import com.ecsoya.ipfs.gateway.exception.IpfsFileException
import java.io.InputStream

interface IIpfsFileGatewayService {
    /**
     * Upload file with inputstream
     */
    @Throws(IpfsFileException::class)
    fun uploadFile(gateway: IpfsGateway?, `in`: InputStream?, fileName: String?): IpfsFile?

    /**
     * Upload file with bytes
     */
    @Throws(IpfsFileException::class)
    fun uploadFile(gateway: IpfsGateway?, data: ByteArray?, fileName: String?): IpfsFile?

    /**
     * Hex of Hash
     */
    @Throws(IpfsFileException::class)
    fun downloadFile(gateway: IpfsGateway?, hash: String?): IpfsFile?
}