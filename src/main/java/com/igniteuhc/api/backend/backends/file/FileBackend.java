package com.igniteuhc.api.backend.backends.file;

import com.igniteuhc.api.backend.AbstractBackend;
import com.igniteuhc.api.backend.data.DataValueType;

import java.util.UUID;

public class FileBackend extends AbstractBackend{
    public FileBackend() {
        super("filebackend.yml");
    }

    public FileData load(DataValueType type, Object o) {
        return new FileData(String.valueOf(o));
    }
}
