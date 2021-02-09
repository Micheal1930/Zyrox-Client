package com.zyrox.client;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.zip.CRC32;
import java.util.zip.GZIPInputStream;

import com.zyrox.Configuration;
import com.zyrox.client.cache.maps.OldschoolMaps;
import com.zyrox.client.cache.node.Deque;

public class OnDemandFetcher extends OnDemandFetcherParent implements Runnable {

    public void dumpMaps() {
        File folder = new File("C:/Users/Austin/Dropbox/mapdump/");
        folder.mkdir();

        for (int i = 0; i < landscapeMapIds.length; i++) {
            try {
                byte abyte[] = Client.getClient().cacheIndices[4].get(landscapeMapIds[i]);
                if (abyte == null) {
                    System.out.println("Couldn't fetch cached landscape: " + landscapeMapIds[i]);
                    continue;
                }
                File map = new File(
                        folder.getAbsolutePath() + System.getProperty("file.separator") + landscapeMapIds[i] + ".gz");
                FileOutputStream fos = new FileOutputStream(map);
                fos.write(abyte);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < objectMapIds.length; i++) {
            try {
                byte abyte[] = Client.getClient().cacheIndices[4].get(objectMapIds[i]);
                if (abyte == null) {
                    System.out.println("Couldn't fetch cached terrain: " + objectMapIds[i]);
                    continue;
                }
                File map = new File(
                        folder.getAbsolutePath() + System.getProperty("file.separator") + objectMapIds[i] + ".gz");
                FileOutputStream fos = new FileOutputStream(map);
                fos.write(abyte);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateMapIndex(int count) {
        try {
            FileOutputStream fos = new FileOutputStream("./map_index");
            DataOutputStream out = new DataOutputStream(fos);
            out.writeShort(count);
            for (int index = 0; index < count; index++) {
                out.writeShort(regionIds[index]);
                out.writeShort(landscapeMapIds[index]);
                out.writeShort(objectMapIds[index]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Complete");

    }

    /**
     * Grabs the checksum of a file from the cache.
     *
     * @param type The type of file (0 = model, 1 = anim, 2 = midi, 3 = map).
     * @param id   The id of the file.
     * @return
     */
    private boolean crcMatches(int expectedValue, byte crcData[]) {
        if (crcData == null || crcData.length < 2)
            return false;
        int length = crcData.length - 2;
        crc32.reset();
        crc32.update(crcData, 0, length);
        int crcValue = (int) crc32.getValue();
        return crcValue == expectedValue;
    }

    public void writeAll() {
        for (int i = 0; i < 6; i++) {
            writeChecksumList(i);
            writeVersionList(i);
        }
    }

    public int getChecksum(int type, int id) {
        int crc = -1;
        byte[] data = clientInstance.cacheIndices[type + 1].get(id);
        if (data != null) {
            int length = data.length - 2;
            crc32.reset();
            crc32.update(data, 0, length);
            crc = (int) crc32.getValue();
        }
        return crc;
    }

    public int getVersion(int type, int id) {
        int version = -1;
        byte[] data = clientInstance.cacheIndices[type + 1].get(id);
        if (data != null) {
            int length = data.length - 2;
            version = ((data[length] & 0xff) << 8) + (data[length + 1] & 0xff);
        }
        return version;
    }

    public void writeChecksumList(int type) {
        try {
            DataOutputStream out = new DataOutputStream(
                    new FileOutputStream(signlink.findcachedir() + type + "_crc.dat"));
            int total = 0;
            for (int index = 0; index < clientInstance.cacheIndices[type + 1].getFileCount(); index++) {
                out.writeInt(getChecksum(type, index));
                total++;
            }
            System.out.println(type + "-" + total);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeVersionList(int type) {
        try {
            DataOutputStream out = new DataOutputStream(
                    new FileOutputStream(signlink.findcachedir() + type + "_version.dat"));
            for (int index = 0; index < clientInstance.cacheIndices[type + 1].getFileCount(); index++) {
                out.writeShort(getVersion(type, index));
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeDWord(DataOutputStream dos, int i) {
        try {
            dos.writeByte((byte) (i >> 24));
            dos.writeByte((byte) (i >> 16));
            dos.writeByte((byte) (i >> 8));
            dos.writeByte((byte) i);
        } catch (IOException ioe) {
        }
    }

    private final void readData() {
        try {
            int readAbleBytes = inputStream.available();
            if (expectedSize == 0 && readAbleBytes >= 10) {
                waiting = true;
                for (int k = 0; k < 10; k += inputStream.read(ioBuffer, k, 10 - k))
                    ;
                int receivedType = ioBuffer[0] & 0xff;
                int receivedID = ((ioBuffer[1] & 0xff) << 16) + ((ioBuffer[2] & 0xff) << 8) + (ioBuffer[3] & 0xff);
                int receivedSize = ((ioBuffer[4] & 0xff) << 32) + ((ioBuffer[5] & 0xff) << 16)
                        + ((ioBuffer[6] & 0xff) << 8) + (ioBuffer[7] & 0xff);
                int chunkId = ((ioBuffer[8] & 0xff) << 8) + (ioBuffer[9] & 0xff);
                current = null;
                for (OnDemandRequest OnDemandRequest = (OnDemandRequest) requested
                        .getFront(); OnDemandRequest != null; OnDemandRequest = (OnDemandRequest) requested.getNext()) {
                    if (OnDemandRequest.dataType == receivedType && OnDemandRequest.id == receivedID)
                        current = OnDemandRequest;
                    if (current != null)
                        OnDemandRequest.loopCycle = 0;
                }
                if (current != null) {
                    loopCycle = 0;
                    if (receivedSize == 0) {
                        System.out.println("Rej: " + receivedType + "," + receivedID);
                        current.buffer = null;
                        if (current.isNotExtraFile)
                            synchronized (zippedNodes) {
                                zippedNodes.insertBack(current);
                            }
                        else
                            current.unlink();
                        current = null;
                    } else {
                        if (current.buffer == null && chunkId == 0)
                            current.buffer = new byte[receivedSize];
                        if (current.buffer == null && chunkId != 0)
                            throw new IOException("missing start of file");
                    }
                }
                completedSize = chunkId * 500;
                expectedSize = 500;
                if (expectedSize > receivedSize - chunkId * 500)
                    expectedSize = receivedSize - chunkId * 500;
            }
            if (expectedSize > 0 && readAbleBytes >= expectedSize) {
                waiting = true;
                byte receivedData[] = ioBuffer;
                int tempCompSize = 0;
                if (current != null) {
                    receivedData = current.buffer;
                    tempCompSize = completedSize;
                }
                for (int dataReadIdx = 0; dataReadIdx < expectedSize; dataReadIdx += inputStream.read(receivedData,
                        dataReadIdx + tempCompSize, expectedSize - dataReadIdx))
                    ;
                if (expectedSize + completedSize >= receivedData.length && current != null) {
                    // if(clientInstance.cacheIndices[0] != null)
                    clientInstance.cacheIndices[current.dataType + 1].put(receivedData.length, receivedData,
                            current.id);
                    if (!current.isNotExtraFile && current.dataType == 3) {
                        current.isNotExtraFile = true;
                        current.dataType = 93;
                    }
                    if (current.isNotExtraFile)
                        synchronized (zippedNodes) {
                            zippedNodes.insertBack(current);
                        }
                    else
                        current.unlink();
                }
                expectedSize = 0;
                return;
            }
        } catch (IOException ioexception) {
            try {
                socket.close();
            } catch (Exception _ex) {
            }
            socket = null;
            inputStream = null;
            outputStream = null;
            expectedSize = 0;
        }
    }

    public final void start(CacheArchive streamLoader, Client client) {
        byte[] data = null;

        for (int cachePtr = 0; cachePtr < 6; cachePtr++) {

            byte[] crcFile = FileOperations.readFile(signlink.findcachedir() + "data/" + cachePtr + ".dat");
            int length = 0;
            length = crcFile.length / 4;
            Stream crcStream = new Stream(crcFile);
            crcs[cachePtr] = new int[length];
            fileStatus2[cachePtr] = new byte[length];
            for (int ptr = 0; ptr < length; ptr++)
                crcs[cachePtr][ptr] = crcStream.readDWord();
        }

        data = streamLoader.getDataForName("map_index");
        Stream stream = new Stream(data);
        int mapCount = stream.readUnsignedWord();

        int extraMaps = 200 + OldschoolMaps.OLDSCHOOL_REGION_IDS.length;
        regionIds = new int[mapCount + extraMaps];
        landscapeMapIds = new int[mapCount + extraMaps];
        objectMapIds = new int[mapCount + extraMaps];

        int[] invalidAreas = new int[]{5181, 5182, 5183, 5184, 5180, 5179, 5175, 5176, 4014, 3997, 5314, 5315, 5172};

        for (int i2 = 0; i2 < mapCount; i2++) {
            regionIds[i2] = stream.readUnsignedWord();
            landscapeMapIds[i2] = stream.readUnsignedWord();
            objectMapIds[i2] = stream.readUnsignedWord();

            for (int i : invalidAreas) {
                if (landscapeMapIds[i2] == i)
                    landscapeMapIds[i2] = -1;
                if (objectMapIds[i2] == i)
                    objectMapIds[i2] = -1;
            }
        }

        data = FileOperations.readFile(signlink.findcachedir() + "osrs/map_index");
        Stream osrsMapStream = new Stream(data);
        int osrsMapCount = osrsMapStream.readUnsignedWord();

        oldschoolRegionIds = new int[osrsMapCount];
        oldschoolLandscapeIds = new int[osrsMapCount];
        oldschoolObjectMapIds = new int[osrsMapCount];

        for (int i2 = 0; i2 < osrsMapCount; i2++) {
            oldschoolRegionIds[i2] = osrsMapStream.readUnsignedWord();
            oldschoolLandscapeIds[i2] = osrsMapStream.readUnsignedWord();
            oldschoolObjectMapIds[i2] = osrsMapStream.readUnsignedWord();
        }

        int currentCount = mapCount + 1;

        /**
         * Skotizo lair
         */
        setMapData(currentCount++, 6810, 10000, 10001, false);

        /**
         * Revenant cave
         */
        setMapData(currentCount++, 12701, 10002, 10003, false);
        setMapData(currentCount++, 12702, 10004, 10005, false);
        setMapData(currentCount++, 12703, 10006, 10007, false);
        setMapData(1346, 12957, 10008, 10009, false);
        setMapData(currentCount++, 12958, 10010, 10011, false);
        setMapData(currentCount++, 12959, 10012, 10013, false);

        /**
         * Abyssal sire
         */
        setMapData(currentCount++, 11850, 10014, 10015, false);
        setMapData(currentCount++, 12362, 10016, 10017, false);
        setMapData(currentCount++, 12363, 10018, 10019, false);

        /**
         * Scorpia cave
         */
        setMapData(currentCount++, 12961, 10020, 10021, false);

        setMapData(currentCount++, 5536, 3472, 3473, true);

        /**
         * Lizardman Shaman
         */
        setMapData(currentCount++, 5945, 2206, 2207, false);
        setMapData(currentCount++, 5946, 2208, 2209, false);
        setMapData(currentCount++, 6201, 2242, 2243, false);

        /**
         * Raids lobby
         */
        setMapData(currentCount++, 4919, 2820, 2821, false);


        setMapData(currentCount++, 12613, 3414, 3415, false);

        /**
         * Hydra.
         */
        setMapData(currentCount++, 5022, 10028, 10029, false);
        setMapData(currentCount++, 5023, 10022, 10023, false);
        setMapData(currentCount++, 5279, 10026, 10027, false);
        setMapData(currentCount++, 5280, 10024, 10025, false);
        setMapData(currentCount++, 5535, 10030, 10031, false);

        /**
         * Great Olm
         */
        clearNearRegions(12889);

        /**
         * Inferno
         */
        regionIds[currentCount] = 9043;
        landscapeMapIds[currentCount] = 3018;
        objectMapIds[currentCount++] = 3019;

        /** KRAKEN **/
        regionIds[941] = 14681;
        landscapeMapIds[941] = 1960;
        objectMapIds[941] = 1961;

        regionIds[942] = 14682;
        landscapeMapIds[942] = 1870;
        objectMapIds[942] = 1871;

        regionIds[956] = 14937;
        landscapeMapIds[956] = 1962;
        objectMapIds[956] = 1963;

        regionIds[957] = 14938;
        landscapeMapIds[957] = 1872;
        objectMapIds[957] = 1873;

        regionIds[958] = 14939;
        landscapeMapIds[958] = 1864;
        objectMapIds[958] = 1865;

        regionIds[940] = 14680;
        landscapeMapIds[940] = 1954;
        objectMapIds[940] = 1955;

        // regionIds[955] = 14936;
        // landscapeMapIds[955] = 1874;
        // objectMapIds[955] = 1875;

        regionIds[971] = 15192;
        landscapeMapIds[971] = 1966;
        objectMapIds[971] = 1967;

        regionIds[972] = 15193;
        landscapeMapIds[972] = 1964;
        objectMapIds[972] = 1965;

        regionIds[941] = 14681; // indices1
        landscapeMapIds[941] = 1960; // indices2
        objectMapIds[941] = 1961; // indices3

        regionIds[942] = 14682;
        landscapeMapIds[942] = 1870;
        objectMapIds[942] = 1871;


        regionIds[956] = 14937;
        landscapeMapIds[956] = 1962;
        objectMapIds[956] = 1963;

        regionIds[957] = 14938;
        landscapeMapIds[957] = 1872;
        objectMapIds[957] = 1873;

        regionIds[958] = 14939;
        landscapeMapIds[958] = 1864;
        objectMapIds[958] = 1865;

        regionIds[940] = 14680;
        landscapeMapIds[940] = 1954;
        objectMapIds[940] = 1955;


        /** ZULRAH **/
        regionIds[107] = 8751;
        landscapeMapIds[107] = 1946;
        objectMapIds[107] = 1947;

        regionIds[108] = 8752;
        landscapeMapIds[108] = 938;
        objectMapIds[108] = 939;
        regionIds[129] = 9007;
        landscapeMapIds[129] = 1938;
        objectMapIds[129] = 1939;
        regionIds[130] = 9008;
        landscapeMapIds[130] = 946;
        objectMapIds[130] = 947;
        regionIds[149] = 9263;
        landscapeMapIds[149] = 1210;
        objectMapIds[149] = 1211;
        regionIds[150] = 9264;
        landscapeMapIds[150] = 956;
        objectMapIds[150] = 957;

        for(Integer region : OldschoolMaps.OLDSCHOOL_REGION_IDS) {
            if(getOldschoolLandscapeMapId(region) != -1 && getOldschoolObjectMapId(region) != -1 && getIndicesIndex(region) != -1) {
                objectMapIds[getIndicesIndex(region)] = -1;
                landscapeMapIds[getIndicesIndex(region)] = -1;
                regionIds[getIndicesIndex(region)] = -1;
            }
            regionIds[currentCount] = region;
            objectMapIds[currentCount] = getOldschoolObjectMapId(region);
            landscapeMapIds[currentCount++] = getOldschoolLandscapeMapId(region);
        }

        data = streamLoader.getDataForName("model_index");

        int j1 = crcs[0].length;
        modelIndices = new byte[j1];
        for (int k1 = 0; k1 < j1; k1++)
            if (k1 < data.length)
                modelIndices[k1] = data[k1];
            else
                modelIndices[k1] = 0;

        data = streamLoader.getDataForName("midi_index");
        stream = new Stream(data);
        mapCount = data.length;
        midiIndices = new int[mapCount];
        for (int k2 = 0; k2 < mapCount; k2++)
            midiIndices[k2] = stream.readUnsignedByte();

        // dumpMaps();
        // updateMapIndex(mapCount);
        clientInstance = client;
        running = true;
        clientInstance.startRunnable(this, 2);
        // writeAll();
    }

    public int getOldschoolLandscapeMapId(int regionId) {
        int index = 0;
        for(Integer region : oldschoolRegionIds) {
            if(region == regionId) {
                return oldschoolLandscapeIds[index];
            }
            index++;
        }
        return -1;
    }

    public int getOldschoolObjectMapId(int regionId) {
        int index = 0;
        for(Integer region : oldschoolRegionIds) {
            if(region == regionId) {
                return oldschoolObjectMapIds[index];
            }
            index++;
        }
        return -1;
    }

    public int getIndicesIndex(int regionId) {
        int index = 0;
        for(Integer region : regionIds) {
            if(region == regionId) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public void setMapData(int index, int regionId, int floorMap, int objectMap, boolean clearNear) {
        regionIds[index] = regionId;
        landscapeMapIds[index] = floorMap;
        objectMapIds[index] = objectMap;

        if (clearNear) {
            clearNearRegions(regionId);
        }
    }

    public void clearNearRegions(int regionId) {
        int[] regions = getNearRegions(regionId);

        for (int region : regions) {
            for (int j1 = 0; j1 < regionIds.length; j1++) {
                if (regionIds[j1] == region) {
                    landscapeMapIds[j1] = -1;
                    objectMapIds[j1] = -1;
                }
            }
        }
    }

    public int getImageCount() {
        return crcs[4].length;
    }

    public int getModelIndex(int id) {
        return modelIndices[id] & 0xff;
    }

    public final void dispose() {
        running = false;
    }

    public final int getVersionCount(int idx) {
        return versions[idx].length;
    }

    public final int getModelCount() {
        return 65534;
    }

    private final void closeRequest(OnDemandRequest OnDemandRequest) {
        try {
            if (socket == null) {
                long l = System.currentTimeMillis();
                if (l - openSocketTime < 4000L)
                    return;
                openSocketTime = l;
                socket = clientInstance.openSocket(43597, Configuration.JAGGRAB_HOST);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                outputStream.write(15);
                for (int j = 0; j < 8; j++)
                    inputStream.read();

                loopCycle = 0;
            }
            ioBuffer[0] = (byte) OnDemandRequest.dataType;
            ioBuffer[1] = (byte) (OnDemandRequest.id >> 24);
            ioBuffer[2] = (byte) (OnDemandRequest.id >> 16);
            ioBuffer[3] = (byte) (OnDemandRequest.id >> 8);
            ioBuffer[4] = (byte) OnDemandRequest.id;
            if (OnDemandRequest.isNotExtraFile)
                ioBuffer[5] = 1;
            else if (!Client.loggedIn)
                ioBuffer[5] = 1;
            else
                ioBuffer[5] = 0;
            outputStream.write(ioBuffer, 0, 6);
            writeLoopCycle = 0;
            return;
        } catch (IOException ioexception) {
        }
        try {
            socket.close();
        } catch (Exception _ex) {
        }
        socket = null;
        inputStream = null;
        outputStream = null;
        expectedSize = 0;
    }

    public final int getAnimCount() {
        return 33568;
    }

    public final void requestFileData(int type, int id) {
        if (type < 0 || id < 0)
            return;
        synchronized (queue) {
            for (OnDemandRequest OnDemandRequest_1 = (OnDemandRequest) queue
                    .getFront(); OnDemandRequest_1 != null; OnDemandRequest_1 = (OnDemandRequest) queue.getNext())
                if (OnDemandRequest_1.dataType == type && OnDemandRequest_1.id == id)
                    return;

            OnDemandRequest OnDemandRequest_2 = new OnDemandRequest();
            OnDemandRequest_2.dataType = type;
            OnDemandRequest_2.id = id;
            OnDemandRequest_2.isNotExtraFile = true;
            synchronized (fetchFromCacheList) {
                fetchFromCacheList.insertBack(OnDemandRequest_2);
            }
            queue.insertBack(OnDemandRequest_2);
            queueSize = queue.getSize();
        }
    }

    public final void run() {
        try {
            while (running) {
                onDemandCycle++;
                int i = 20;
                if (filesToGet == 0 && clientInstance.cacheIndices[0] != null)
                    i = 50;
                try {
                    Thread.sleep(i);
                } catch (Exception _ex) {
                }
                waiting = true;
                for (int ptr = 0; ptr < 100; ptr++) {
                    if (!waiting)
                        break;
                    waiting = false;
                    checkReceived();
                    handleFailed();
                    if (extraFileCount == 0 && ptr >= 5)
                        break;
                    getExtraFiles();
                    if (inputStream != null)
                        readData();
                }

                boolean flag = false;
                for (OnDemandRequest OnDemandRequest = (OnDemandRequest) requested
                        .getFront(); OnDemandRequest != null; OnDemandRequest = (OnDemandRequest) requested.getNext())
                    if (OnDemandRequest.isNotExtraFile) {
                        flag = true;
                        OnDemandRequest.loopCycle++;
                        if (OnDemandRequest.loopCycle > 50) {
                            OnDemandRequest.loopCycle = 0;
                            closeRequest(OnDemandRequest);
                        }
                    }

                if (!flag) {
                    for (OnDemandRequest OnDemandRequest_1 = (OnDemandRequest) requested
                            .getFront(); OnDemandRequest_1 != null; OnDemandRequest_1 = (OnDemandRequest) requested
                            .getNext()) {
                        flag = true;
                        OnDemandRequest_1.loopCycle++;
                        if (OnDemandRequest_1.loopCycle > 50) {
                            OnDemandRequest_1.loopCycle = 0;
                            closeRequest(OnDemandRequest_1);
                        }
                    }

                }
                if (flag) {
                    loopCycle++;
                    if (loopCycle > 750) {
                        try {
                            socket.close();
                        } catch (Exception _ex) {
                        }
                        // OnDemandRequest OnDemandRequest_1 = (OnDemandRequest)requested.getFront();
                        // JOptionPane.showMessageDialog(null, "Please report this to the Desolace
                        // forums, report a bug section \n "+OnDemandRequest_1+"");
                        socket = null;
                        inputStream = null;
                        outputStream = null;
                        expectedSize = 0;
                        requested.popFront();

                    }
                } else {
                    loopCycle = 0;
                    statusString = "";
                }
                if (Client.loggedIn && socket != null && outputStream != null
                        && (filesToGet > 0 || clientInstance.cacheIndices[0] == null)) {
                    writeLoopCycle++;
                    if (writeLoopCycle > 500) {
                        writeLoopCycle = 0;
                        ioBuffer[0] = 0;
                        ioBuffer[1] = 0;
                        ioBuffer[2] = 0;
                        ioBuffer[3] = 0;
                        ioBuffer[4] = 0;
                        ioBuffer[5] = 10;
                        try {
                            outputStream.write(ioBuffer, 0, 6);
                        } catch (IOException _ex) {
                            loopCycle = 5000;
                        }
                    }
                }
            }
            return;
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("od_ex " + exception.getMessage());
        }
    }

    public final void insertExtraFilesRequest(int fileId, int idx) {
        if (clientInstance.cacheIndices[0] == null)
            return;
        if (fileStatus2[idx][fileId] == 0)
            return;
        if (filesToGet == 0)
            return;
        OnDemandRequest OnDemandRequest = new OnDemandRequest();
        OnDemandRequest.dataType = idx;
        OnDemandRequest.id = fileId;
        OnDemandRequest.isNotExtraFile = false;
        synchronized (nodeList_1) {
            nodeList_1.insertBack(OnDemandRequest);
        }
    }

    public final OnDemandRequest getNextNode() {
        OnDemandRequest OnDemandRequest;
        synchronized (zippedNodes) {
            OnDemandRequest = (OnDemandRequest) zippedNodes.popFront();
        }
        if (OnDemandRequest == null)
            return null;
        synchronized (queue) {
            OnDemandRequest.unlinkQueue();
            queueSize = queue.getSize();
        }
        if (OnDemandRequest.buffer == null)
            return OnDemandRequest;
        int readData = 0;
        try {
            GZIPInputStream gzipinputstream = new GZIPInputStream(new ByteArrayInputStream(OnDemandRequest.buffer));
            do {
                if (readData >= gzipInputBuffer.length) {
                    System.out.println("File: " + OnDemandRequest.id + " too large");
                    break;
                }
                int tempReadData = -1;
                try {
                    tempReadData = gzipinputstream.read(gzipInputBuffer, readData, gzipInputBuffer.length - readData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (tempReadData == -1)
                    break;
                readData += tempReadData;
            } while (true);
        } catch (IOException _ex) {
            _ex.printStackTrace();
            System.out.println("Failed to unzip model [" + OnDemandRequest.id + "] type = " + OnDemandRequest.dataType);
            return OnDemandRequest;
        }
        OnDemandRequest.buffer = new byte[readData];
        for (int bufferPtr = 0; bufferPtr < readData; bufferPtr++)
            OnDemandRequest.buffer[bufferPtr] = gzipInputBuffer[bufferPtr];
        return OnDemandRequest;
    }

    public int[] getNearRegions(int regionId) {
        int[] near = new int[8];
        near[0] = regionId - 256 - 1;
        near[1] = regionId - 256;
        near[2] = regionId - 256 + 1;
        near[3] = regionId - 1;
        near[4] = regionId + 1;
        near[5] = regionId + 256 - 1;
        near[6] = regionId + 256;
        near[7] = regionId + 256 + 1;
        return near;
    }

    public final int getMapIdForRegions(int landscapeOrObject, int regionY, int regionX) {
        int mapNigga2;
        int mapNigga3;
        int regionId = (regionX << 8) + regionY;
        for (int j1 = 0; j1 < regionIds.length; j1++) {
            if (regionIds[j1] == regionId) {
                if (landscapeOrObject == 0) {
                    if (landscapeMapIds[j1] == -1) {
                        return -1;
                    }
                    if (landscapeMapIds[j1] >= 10000 && landscapeMapIds[j1] <= 11000) {
                        return landscapeMapIds[j1];
                    }
                    // Soulwars
                    if (landscapeMapIds[j1] >= 3700 && landscapeMapIds[j1] <= 3840)
                        return landscapeMapIds[j1];
                    for (int cheapHax : cheapHaxValues)
                        if (landscapeMapIds[j1] == cheapHax)
                            return landscapeMapIds[j1];
                    mapNigga2 = landscapeMapIds[j1] > 3535 ? -1 : landscapeMapIds[j1];
                    return mapNigga2;
                } else {
                    if (objectMapIds[j1] == -1) {
                        return -1;
                    }
                    if (objectMapIds[j1] >= 10000 && objectMapIds[j1] <= 11000) {
                        return objectMapIds[j1];
                    }
                    if (objectMapIds[j1] >= 3700 && objectMapIds[j1] <= 3840)
                        return objectMapIds[j1];
                    for (int cheapHax : cheapHaxValues)
                        if (objectMapIds[j1] == cheapHax)
                            return objectMapIds[j1];
                    mapNigga3 = objectMapIds[j1] > 3535 ? -1 : objectMapIds[j1];
                    return mapNigga3;
                }
            }
        }
        return -1;
    }

    int[] cheapHaxValues = new int[]{3627, 3628, 3655, 3656, 3625, 3626, 3629, 3630, 4071, 4072, 5253, 1816, 1817,
            3653, 3654, 4067, 4068, 3639, 3640, 1976, 1977, 3571, 3572, 5129, 5130, 2066, 2067, 3545, 3546, 3559, 3560,
            3569, 3570, 3551, 3552, 3579, 3580, 3575, 3576, 1766, 1767, 3547, 3548, 3682, 3683, 3696, 3697, 3692, 3693,
            4013, 4079, 4080, 4082, 3996, 4083, 4084, 4075, 4076, 3664, 3993, 3994, 3995, 4077, 4078, 4073, 4074, 4011,
            4012, 3998, 3999, 4081,};

    public final void get(int id) {
        get(0, id);
    }

    public final void get(int type, int id) {
        requestFileData(type, id);
    }

    public final void fetchPriorityFile(byte fileData, int idx, int fileId) {
        if (clientInstance.cacheIndices[0] == null)
            return;
        byte data[] = clientInstance.cacheIndices[idx + 1].get(fileId);
        if (crcMatches(crcs[idx][fileId], data))
            return;
        fileStatus2[idx][fileId] = fileData;
        if (fileData > filesToGet)
            filesToGet = fileData;
        totalFiles++;
    }

    public final boolean mapIsObjectMap(int objectMap) {
        for (int ptr = 0; ptr < regionIds.length; ptr++)
            if (objectMapIds[ptr] == objectMap)
                return true;
        return false;
    }

    private final void handleFailed() {
        extraFileCount = 0;
        completedCount = 0;
        for (OnDemandRequest OnDemandRequest = (OnDemandRequest) requested
                .getFront(); OnDemandRequest != null; OnDemandRequest = (OnDemandRequest) requested.getNext())
            if (OnDemandRequest.isNotExtraFile) {
                extraFileCount++;
            } else {
                completedCount++;
            }

        while (extraFileCount < 10) {
            OnDemandRequest OnDemandRequest_1 = null;
            OnDemandRequest_1 = (OnDemandRequest) receivedFilesList.popFront();
            if (OnDemandRequest_1 == null)
                break;
            try {
                if (fileStatus2[OnDemandRequest_1.dataType][OnDemandRequest_1.id] != 0)
                    filesLoaded++;
                fileStatus2[OnDemandRequest_1.dataType][OnDemandRequest_1.id] = 0;
                requested.insertBack(OnDemandRequest_1);
                extraFileCount++;
                closeRequest(OnDemandRequest_1);
                waiting = true;
            } catch (Exception e) {
                System.out.println(OnDemandRequest_1.dataType + " " + OnDemandRequest_1.id);
            }
        }
    }

    public final void clearExtraFilesList() {
        synchronized (nodeList_1) {
            nodeList_1.clear();
        }
    }

    private final void checkReceived() {
        OnDemandRequest OnDemandRequest;
        synchronized (fetchFromCacheList) {
            OnDemandRequest = (OnDemandRequest) fetchFromCacheList.popFront();
        }
        while (OnDemandRequest != null) {
            waiting = true;
            byte data[] = null;
            if (clientInstance.cacheIndices[0] != null)
                data = clientInstance.cacheIndices[OnDemandRequest.dataType + 1].get(OnDemandRequest.id);
            if (Configuration.JAGCACHED_ENABLED) {
                if (!crcMatches(crcs[OnDemandRequest.dataType][OnDemandRequest.id], data)) {
                    data = null;
                }
            }
            synchronized (fetchFromCacheList) {
                if (data == null) {
                    receivedFilesList.insertBack(OnDemandRequest);
                } else {
                    OnDemandRequest.buffer = data;
                    synchronized (zippedNodes) {
                        zippedNodes.insertBack(OnDemandRequest);
                    }
                }
                OnDemandRequest = (OnDemandRequest) fetchFromCacheList.popFront();
            }
        }
    }

    public void dump(int index) {

        if(!new File(signlink.findcachedir() + "/index"+index).exists())
            new File(signlink.findcachedir() + "/index"+index).mkdir();

        for (int fileId = 0; fileId < clientInstance.cacheIndices[index].getFileCount(); fileId++) {
            try {
                byte abyte[] = clientInstance.cacheIndices[index].get(fileId);
                File map = new File(signlink.findcachedir() + "/index"+index+"/" + fileId + ".gz");
                FileOutputStream fos = new FileOutputStream(map);
                fos.write(abyte);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Dumped index "+index+".");
    }

    private final void getExtraFiles() {
        while (extraFileCount == 0 && completedCount < 10) {
            if (filesToGet == 0)
                break;
            OnDemandRequest OnDemandRequest;
            synchronized (nodeList_1) {
                OnDemandRequest = (OnDemandRequest) nodeList_1.popFront();
            }
            while (OnDemandRequest != null) {
                if (fileStatus2[OnDemandRequest.dataType][OnDemandRequest.id] != 0) {
                    fileStatus2[OnDemandRequest.dataType][OnDemandRequest.id] = 0;
                    requested.insertBack(OnDemandRequest);
                    closeRequest(OnDemandRequest);
                    waiting = true;
                    if (filesLoaded < totalFiles)
                        filesLoaded++;
                    statusString = "Loading extra files - " + (filesLoaded * 100) / totalFiles + "%";
                    System.out.println(statusString);
                    completedCount++;
                    if (completedCount == 10)
                        return;
                }
                synchronized (nodeList_1) {
                    OnDemandRequest = (OnDemandRequest) nodeList_1.popFront();
                }
            }
            for (int j = 0; j < 5; j++) {
                byte fileData[] = fileStatus2[j];
                int k = fileData.length;
                for (int l = 0; l < k; l++)
                    if (fileData[l] == filesToGet) {
                        fileData[l] = 0;
                        OnDemandRequest OnDemandRequest_1 = new OnDemandRequest();
                        OnDemandRequest_1.dataType = j;
                        OnDemandRequest_1.id = l;
                        OnDemandRequest_1.isNotExtraFile = false;
                        requested.insertBack(OnDemandRequest_1);
                        closeRequest(OnDemandRequest_1);
                        waiting = true;
                        if (filesLoaded < totalFiles)
                            filesLoaded++;
                        statusString = "Loading extra files - " + (filesLoaded * 100) / totalFiles + "%";
                        completedCount++;
                        if (completedCount == 10)
                            return;
                    }

            }

            filesToGet--;
        }
    }

    public final boolean midiExists(int i) {
        return midiIndices[i] == 1;
    }

    public OnDemandFetcher() {
        requested = new Deque();
        statusString = "";
        crc32 = new CRC32();
        ioBuffer = new byte[500];
        fileStatus2 = new byte[7][];
        nodeList_1 = new Deque();
        running = true;
        waiting = false;
        zippedNodes = new Deque();
        gzipInputBuffer = new byte[1000000];
        queue = new Queue();
        versions = new int[7][];
        receivedFilesList = new Deque();
        fetchFromCacheList = new Deque();
        crcs = new int[6][];
    }

    public int getRemaining() {
        return queueSize;
    }

    public int[] regionIds;
    public int[] landscapeMapIds;
    public int[] objectMapIds;

    public int[] oldschoolRegionIds;
    public int[] oldschoolLandscapeIds;
    public int[] oldschoolObjectMapIds;

    private int queueSize;

    private int totalFiles;
    private Deque requested;
    private int filesToGet;
    public String statusString;
    private int writeLoopCycle;
    private long openSocketTime;
    private byte ioBuffer[];
    public int onDemandCycle;
    private byte fileStatus2[][];
    private Client clientInstance;
    private Deque nodeList_1;
    private int completedSize;
    private int expectedSize;
    private int midiIndices[];
    private int filesLoaded;
    private boolean running;
    private OutputStream outputStream;
    private boolean waiting;
    private Deque zippedNodes;
    private byte gzipInputBuffer[];
    private Queue queue;
    private InputStream inputStream;
    private Socket socket;
    private int versions[][];
    private int extraFileCount;
    private int completedCount;
    private Deque receivedFilesList;
    private OnDemandRequest current;
    private Deque fetchFromCacheList;
    private int loopCycle;
    private final int[][] crcs;
    private byte[] modelIndices;
    private final CRC32 crc32;
}
