package com.dk.bleNfc.card;

import android.nfc.FormatException;
import android.nfc.NdefMessage;

import com.dk.bleNfc.DeviceManager.DeviceManager;
import com.dk.bleNfc.Exception.CardNoResponseException;
import com.dk.bleNfc.Tool.StringTool;

public class Type4Tag  extends Iso14443bCard {
    private static final int MAX_APDU_LEN = 200;
    private static final byte[] cc_file_id = {(byte)0xE1, 0x03};
    private static final byte[] select_app = {0x00, (byte)0xA4, 0x04, 0x00, 0x07, (byte)0xD2, 0x76, 0x00, 0x00, (byte)0x85, 0x01, 0x01, 0x00};
//    {0x00, (byte)0xA4, 0x00, 0x0C, 0x02, 0x00, 0x01}
//    private static final byte[] select_cc = {0x00, (byte)0xA4, 0x00, 0x0C, 0x02, (byte)0xE1, 0x03};
//    {0x00, (byte)0xB0, 0x00, 0x00, (byte)15},
//    {0x00, (byte)0xA4, 0x00, 0x0C, 0x02, (byte)0xE1, 0x04},
//    {0x00, (byte)0xB0, 0x00, 0x00, (byte)200},

    public Type4Tag(DeviceManager deviceManager) {
        super(deviceManager);
    }

    /**
     * 选择文件，同步阻塞方式，注意：不能在蓝牙初始化的线程里运行
     * @param           fileId - 文件ID
     * @return         true - 选择文件成功
     *                  false - 选择文件失败
     * @throws CardNoResponseException
     *                  卡片无响应时会抛出异常
     */
    public boolean selectFile(byte[] fileId) throws CardNoResponseException {
        byte[] selectFileCmd = {0x00, (byte)0xA4, 0x00, 0x0C, 0x02, fileId[0], fileId[1]};
        //通过文件ID选择文件
        byte[] selectReturnBytes = transceive(selectFileCmd);
        if ( (selectReturnBytes == null)
                || (selectReturnBytes.length != 2)
                || (selectReturnBytes[0] != (byte)0x90)
                || (selectReturnBytes[1] != (byte)0x00) ) {
            return false;
        }

        return true;
    }

    /**
     * 读文件二进制数据，同步阻塞方式，注意：不能在蓝牙初始化的线程里运行
     * @param           len - 要读取的长度
     * @return         读到的二进制数据
     * @throws CardNoResponseException
     *                  卡片无响应时会抛出异常
     */
    public byte[] readBinary(int len) throws CardNoResponseException {
        return readBinary(0, len);
    }

    /**
     * 读文件二进制数据，同步阻塞方式，注意：不能在蓝牙初始化的线程里运行
     * @param           offset - 读取的位置
     * @param           len - 要读取的长度
     * @return         读到的二进制数据
     * @throws CardNoResponseException
     *                  卡片无响应时会抛出异常
     */
    public byte[] readBinary(int offset, int len) throws CardNoResponseException {
        byte[] readBinaryCmd = {0x00, (byte)0xB0, 0x00, 0x00, (byte)MAX_APDU_LEN};

        //读取二进制数据
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;

        if ( len >= MAX_APDU_LEN ) {
            for ( i=0; (stringBuilder.length() + MAX_APDU_LEN * 2) <= len * 2; i += MAX_APDU_LEN ) {
                readBinaryCmd[2] = (byte) (((i + offset) & 0xFF00) >> 8);
                readBinaryCmd[3] = (byte) ((i + offset) & 0x00FF);
                byte[] readBytes = transceive(readBinaryCmd);
                if ((readBytes == null) || (readBytes.length <= 2)) {
                    return StringTool.hexStringToBytes(stringBuilder.toString());
                }
                byte[] bytesTemp = new  byte[readBytes.length - 2];
                System.arraycopy(readBytes, 0, bytesTemp, 0, bytesTemp.length);
                stringBuilder.append(StringTool.byteHexToSting(bytesTemp));
            }
        }

        if ( len % MAX_APDU_LEN != 0 ) {
            readBinaryCmd[2] = (byte) (((i + offset) & 0xFF00) >> 8);
            readBinaryCmd[3] = (byte) ((i + offset) & 0x00FF);
            readBinaryCmd[4] = (byte)(len % MAX_APDU_LEN);
            byte[] readBytes = transceive(readBinaryCmd);
            if ((readBytes == null) || (readBytes.length <= 2)) {
                return StringTool.hexStringToBytes(stringBuilder.toString());
            }
            byte[] bytesTemp = new  byte[readBytes.length - 2];
            System.arraycopy(readBytes, 0, bytesTemp, 0, bytesTemp.length);
            stringBuilder.append(StringTool.byteHexToSting(bytesTemp));
        }

        System.out.println("Read binary data: " + stringBuilder);
        return StringTool.hexStringToBytes(stringBuilder.toString());
    }

    /**
     * 读文件二进制数据，同步阻塞方式，注意：不能在蓝牙初始化的线程里运行
     * @param           offset - 读取的位置
     * @param           data -  要写的数据
     * @return         读到的二进制数据
     * @throws CardNoResponseException
     *                  卡片无响应时会抛出异常
     */
    public boolean writeBinary(int offset, byte[] data) throws CardNoResponseException {
        byte[] writeBinaryCmd = {0x00, (byte)0xD6, 0x00, 0x00, (byte)MAX_APDU_LEN};
        int len = data.length;

        //读取二进制数据
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;

        if ( len >= MAX_APDU_LEN ) {
            for ( i=0; (i + MAX_APDU_LEN) <= len; i += MAX_APDU_LEN ) {
                writeBinaryCmd[2] = (byte) (((i + offset) & 0xFF00) >> 8);
                writeBinaryCmd[3] = (byte) ((i + offset) & 0x00FF);
                byte writeDataCmd[] = new byte[writeBinaryCmd.length + MAX_APDU_LEN];
                System.arraycopy(writeBinaryCmd, 0, writeDataCmd, 0, writeBinaryCmd.length);
                System.arraycopy(data, i, writeDataCmd, writeBinaryCmd.length, MAX_APDU_LEN);
                byte[] readBytes = transceive(writeDataCmd);
                if ( (readBytes == null) || (readBytes.length != 2) || (readBytes[0] != (byte)0x90) || (readBytes[1] != 0x00) ) {
                    return false;
                }
            }
        }

        if ( len % MAX_APDU_LEN != 0 ) {
            writeBinaryCmd[2] = (byte) (((i + offset) & 0xFF00) >> 8);
            writeBinaryCmd[3] = (byte) ((i + offset) & 0x00FF);
            writeBinaryCmd[4] = (byte)(len % MAX_APDU_LEN);
            byte writeDataCmd[] = new byte[writeBinaryCmd.length + (writeBinaryCmd[4] & 0xFF)];
            System.arraycopy(writeBinaryCmd, 0, writeDataCmd, 0, writeBinaryCmd.length);
            System.arraycopy(data, i, writeDataCmd, writeBinaryCmd.length, writeBinaryCmd[4] & 0xFF);
            byte[] readBytes = transceive(writeDataCmd);
            if ( (readBytes == null) || (readBytes.length != 2) || (readBytes[0] != (byte)0x90) || (readBytes[1] != 0x00) ) {
                return false;
            }
        }

        return true;
    }

    /**
     * 选择并读文件二进制数据，同步阻塞方式，注意：不能在蓝牙初始化的线程里运行
     * @param           fileId - 文件ID
     * @param           len - 要读取的长度
     * @return         读到的二进制数据
     * @throws CardNoResponseException
     *                  卡片无响应时会抛出异常
     */
    public byte[] readFileBinary(byte[] fileId, int len) throws CardNoResponseException {
        if ( !selectFile(fileId) ) {
            throw new CardNoResponseException("Select file id fail!");
        }

        return readBinary(len);
    }

    /**
     * 读NDEF消息文件的文件id号，同步阻塞方式，注意：不能在蓝牙初始化的线程里运行
     * @return         读到的文件id号，2字节
     * @throws CardNoResponseException
     *                  卡片无响应时会抛出异常
     */
    public byte[] getNdefFileId() throws CardNoResponseException{
        //读取cc文件
        byte[] cc_bytes = readFileBinary(cc_file_id, 15);
        if ( (cc_bytes == null) || (cc_bytes.length != 15)) {
            throw new CardNoResponseException("Read cc file fail!");
        }

        //提取Ndef文件ID
        return new byte[] {cc_bytes[9], cc_bytes[10]};
    }

    /**
     * 读NDEF消息，同步阻塞方式，注意：不能在蓝牙初始化的线程里运行
     * @return         读到的NDEF消息
     * @throws CardNoResponseException
     *                  卡片无响应时会抛出异常
     */
    public NdefMessage NdefRead() throws CardNoResponseException {
        //第一步，选择应用文件
        byte[] app_bytes = transceive(select_app);
        if ( (app_bytes == null)
                || (app_bytes.length != 2)
                || (app_bytes[0] != (byte)0x90)
                || (app_bytes[1] != (byte)0x00) ) {
            throw new CardNoResponseException("Tag type error!");
        }

        //第二步，获取NDEF文件ID并选择文件
        boolean isSuc = selectFile( getNdefFileId() );
        if (!isSuc) {
            throw new CardNoResponseException("Select file id fail!");
        }

        //第三步，读取NDEF消息数据的长度
        byte[] lenBytes = readBinary(2);
        if (lenBytes == null || lenBytes.length != 2) {
            throw new CardNoResponseException("Read binary fail!");
        }
        int ndefLen = ((lenBytes[0] & 0xff) << 8) | (lenBytes[1] & 0xff);
        if (ndefLen > 10000) {
            throw new CardNoResponseException("Ndef message data length is too long!");
        }

        //第四步，读取NDEF消息数据
        byte[] ndefbytes = readBinary(2, ndefLen);
        if (ndefbytes == null || ndefbytes.length != ndefLen) {
            throw new CardNoResponseException("Read binary fail!");
        }

        try {
            return new NdefMessage(ndefbytes);
        } catch (FormatException e) {
            throw new CardNoResponseException("Not Ndef data found!");
        }
    }

    /**
     * 读NDEF消息，同步阻塞方式，注意：不能在蓝牙初始化的线程里运行
     * @param           ndefMessage - 要写入的NdefMassage
     * @return         是否写入成功
     * @throws CardNoResponseException
     *                  卡片无响应时会抛出异常
     */
    public boolean NdefWrite(NdefMessage ndefMessage) throws CardNoResponseException {
        if ( (ndefMessage == null) || (ndefMessage.getByteArrayLength() == 0) ) {
            return false;
        }

        //第一步，选择应用文件
        byte[] app_bytes = transceive(select_app);
        if ( (app_bytes == null)
                || (app_bytes.length != 2)
                || (app_bytes[0] != (byte)0x90)
                || (app_bytes[1] != (byte)0x00) ) {
            throw new CardNoResponseException("Tag type error!");
        }

        //第二步，获取NDEF文件ID并选择文件
        boolean isSuc = selectFile( getNdefFileId() );
        if (!isSuc) {
            throw new CardNoResponseException("Select file id fail!");
        }

        isSuc = writeBinary(0, new byte[] {0, 0});

        //第三步，提取NDEF消息数据的长度
        int ndefLen = ndefMessage.getByteArrayLength();
        byte[] lenBytes = {(byte)((ndefLen >> 8) & 0xff), (byte)(ndefLen & 0xff)};

        //第四步，写NDEF消息数据
        byte ndefbytes[] = new byte[ndefLen];
        isSuc = writeBinary(2, ndefMessage.toByteArray());
        if (!isSuc) {
            throw new CardNoResponseException("write file fail!");
        }

        //第五步，写长度
        isSuc = writeBinary(0, lenBytes);

        return isSuc;
    }

}
