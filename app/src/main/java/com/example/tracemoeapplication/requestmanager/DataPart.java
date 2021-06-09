package com.example.tracemoeapplication.requestmanager;

class DataPart {
    private String fileName;
    private byte[] content;
    private String type;

    public DataPart() {
    }

    DataPart(String _fileName, byte[] _content, String _type) {
        fileName = _fileName;
        content = _content;
        type = _type;
    }

    String getFileName() {
        return fileName;
    }

    byte[] getContent() {
        return content;
    }

    String getType() {
        return type;
    }

}